package org.example.healthproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                "/api/auth/**",
                                "/",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // POST endpoints
                        .requestMatchers(HttpMethod.POST, "/api/clinics/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/doctors/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/medicalrecords/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/appointments/**").permitAll()

                        // GET endpoints (list and by id)
                        .requestMatchers(HttpMethod.GET, "/api/clinics/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/doctors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/appointments/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/medicalrecords/**").permitAll()

                        // PUT endpoints (update by id)
                        .requestMatchers(HttpMethod.PUT, "/api/clinics/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/appointments/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/medicalrecords/**").permitAll()

                        // DELETE endpoints (delete by id)
                        .requestMatchers(HttpMethod.DELETE, "/api/clinics/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/medicalrecords/**").permitAll()

                        // All other requests need authentication
                        .anyRequest().authenticated()
                )
                // No session - stateless REST API
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Add JWT token filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
