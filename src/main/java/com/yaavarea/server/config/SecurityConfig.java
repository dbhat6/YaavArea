package com.yaavarea.server.config;

import com.yaavarea.server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthService authService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    SecurityConfig(AuthService authService, BCryptPasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // TODO: Fix csrf
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                                auth
//                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                        .requestMatchers("/user").authenticated()
                                        .requestMatchers("/constituency/**").hasRole("ADMIN")
                                        .requestMatchers("/user/register").permitAll() // TODO: Change this later on to only admins
                                        .requestMatchers("/user/**").authenticated()
                                        .requestMatchers("/actuator/prometheus").permitAll()
                                        .requestMatchers("/actuator/health").permitAll()
                                        .requestMatchers("/actuator/*").authenticated()
                                        .requestMatchers("/v2/api-docs").permitAll()
                                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .build();
    }

}