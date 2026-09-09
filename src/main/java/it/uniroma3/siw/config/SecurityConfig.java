package it.uniroma3.siw.config;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.security.OAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
@EnableWebSecurity  
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcDaoImpl manager = new JdbcDaoImpl();
        manager.setDataSource(dataSource);
        manager.setUsersByUsernameQuery(
            "SELECT username, password, true as enabled FROM credenziali WHERE username=?"
        );
        manager.setAuthoritiesByUsernameQuery(
            "SELECT username, 'ROLE_' || ruolo FROM credenziali WHERE username=?"
        );
        return manager;
    }

    //  HANDLER PER LOGIN SUCCESS (form login classico)
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, 
                                               HttpServletResponse response,
                                               Authentication authentication) throws IOException {
                response.sendRedirect("/");
            }
        };
    }

    /** Permette al frontend React (Vite, porta 5173 in sviluppo) di chiamare le API REST. */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("Content-Type", "X-XSRF-TOKEN"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    /**
     * Richieste verso /api/** non autenticate ricevono 401 (per il frontend React);
     * tutte le altre pagine mantengono il classico redirect a /login.
     */
    private AuthenticationEntryPoint apiAwareAuthenticationEntryPoint() {
        RequestMatcher apiMatcher = request -> request.getRequestURI().startsWith(request.getContextPath() + "/api/");
        AuthenticationEntryPoint apiEntryPoint =
                (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<>();
        entryPoints.put(apiMatcher, apiEntryPoint);

        DelegatingAuthenticationEntryPoint delegating = new DelegatingAuthenticationEntryPoint(entryPoints);
        delegating.setDefaultEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        return delegating;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, OAuth2SuccessHandler oauth2SuccessHandler) throws Exception {
        http
            .cors(cors -> {})
            // Per le pagine Thymeleaf un utente non autenticato viene rediretto a /login
            // (comportamento di default). Per le API REST (usate dal frontend React)
            // rispondiamo invece 401, così il frontend può gestirlo senza seguire redirect.
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(apiAwareAuthenticationEntryPoint()))
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // niente mascheramento XOR: il frontend React legge il valore grezzo dal
                // cookie XSRF-TOKEN e lo rimanda identico nell'header, come da pattern
                // ufficiale Spring Security per le SPA cross-origin
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
            )
            .authorizeHttpRequests(auth -> auth
                //  Risorse pubbliche
                .requestMatchers("/", "/index", "/error",
                				"/prodotti", "/prodotti/**",
                                "/categorie",
                                "/assistenza-clienti", "/assistenza-clienti/**",
                                "/da-implementare",
                                "/css/**", "/images/**", "/uploads/**").permitAll()
                .requestMatchers("/register", "/registration-success", "/login",
                                "/oauth2/**").permitAll()
                // API REST pubbliche in lettura, usate dal frontend React
                .requestMatchers(HttpMethod.GET, "/api/prodotti/**", "/api/categorie/**", "/api/config").permitAll()
                // Solo ADMIN
                .requestMatchers("/admin/**", "/api/admin/**").hasRole(Credenziali.ADMIN_ROLE)
                // Carrello e ordini - autenticato
                .requestMatchers("/carrello/**", "/ordini/**", "/recensioni/new/**").authenticated()
                .anyRequest().authenticated()
            )
            //  OAuth2 Login (Google)
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .successHandler(oauth2SuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Form Login
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            //  Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()
            );
        
        return http.build();
    }
}