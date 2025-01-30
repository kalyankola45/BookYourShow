package com.example.ticketsbooking.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
public class SecurityClass {

    private final Userdetimp userdetimp;
    private final Jwtfilter jwtfilter;

    @Autowired
    public SecurityClass(Userdetimp userdetimp, Jwtfilter jwtfilter) {
        this.userdetimp = userdetimp;
        this.jwtfilter = jwtfilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .csrf().disable()
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/user/**").permitAll();
            auth.requestMatchers("/management/**").hasRole("MANAGER");
            auth.requestMatchers("/login/**").permitAll();
            auth.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN"); // Use ROLE_ADMIN here
        })
        .authenticationProvider(adminAuthenticationProvider())
        .authenticationManager(managerAuthenticationManager())
        .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        var admin = org.springframework.security.core.userdetails.User.withUsername("admin@gmail.com")
                        .password(passwordEncoder.encode("admin@123"))
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Arrays.asList("http://localhost:3000")); 
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        cors.setExposedHeaders(Arrays.asList("Authorization")); // Allow frontend to access the Authorization header
        cors.setAllowCredentials(true);
        cors.setMaxAge(3600L); // Cache the CORS response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "adminAuthenticationManager")
    @Primary // Mark this as the primary AuthenticationManager
    public AuthenticationManager adminAuthenticationManager() {
        return new ProviderManager(adminAuthenticationProvider());
    }

    @Bean(name = "managerAuthenticationManager")
    public AuthenticationManager managerAuthenticationManager() {
        return new ProviderManager(managerAuthenticationProvider());
    }


    @Bean
    public AuthenticationProvider managerAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userdetimp);
        return provider;
    }

    @Bean
    public AuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(inMemoryUserDetailsManager(passwordEncoder()));
        return provider;
    }
}
