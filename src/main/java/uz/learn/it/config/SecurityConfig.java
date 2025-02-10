package uz.learn.it.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.learn.it.filter.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter authenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/signup").hasRole("MANAGER")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
