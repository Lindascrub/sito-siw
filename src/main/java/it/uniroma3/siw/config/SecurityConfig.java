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

import javax.sql.DataSource;

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
            "SELECT username, ruolo FROM credenziali WHERE username=?"
        );
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/categorie", "/prodotti", "/prodotto/**", "/css/**", "/images/**").permitAll()
                .requestMatchers("/register", "/registration-success").permitAll()
                .requestMatchers("/admin/**").hasRole(Credenziali.ADMIN_ROLE)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }
}