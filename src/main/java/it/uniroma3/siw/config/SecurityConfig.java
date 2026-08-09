package it.uniroma3.siw.config;

import it.uniroma3.siw.model.Credenziali;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

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

    //  HANDLER PER LOGIN SUCCESS (anche OAuth2)
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                //  Risorse pubbliche
                .requestMatchers("/prodotti/admin/**", "/categorie/admin/**").hasRole(Credenziali.ADMIN_ROLE)
                .requestMatchers("/", "/index", "/prodotti", "/prodotti/**", 
                                "/categorie", "/css/**", "/images/**", "/js/**").permitAll()
                .requestMatchers("/register", "/registration-success", "/login", 
                                "/oauth2/**").permitAll()
                // Solo ADMIN
                .requestMatchers("/admin/**").hasRole(Credenziali.ADMIN_ROLE)
                // Carrello e ordini - autenticato
                .requestMatchers("/carrello/**", "/ordini/**", "/recensioni/new/**").authenticated()
                .anyRequest().authenticated()
            )
            //  OAuth2 Login (Google)
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/oauth2/success", true)
                .failureUrl("/login?error=true")
                .successHandler(successHandler())
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