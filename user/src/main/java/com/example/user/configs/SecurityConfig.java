package com.example.user.configs;

import com.example.user.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTUtil jwtUtil;

    private static final String[] PERMIT_ALL_ENDPOINTS = {
            "/api/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PERMIT_ALL_ENDPOINTS).permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                                .anyRequest().authenticated())
        ;

        httpSecurity.oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer.jwt(jwt ->
                        jwt.decoder(jwtUtil.jwtDecoder())
                                .jwtAuthenticationConverter(jwtUtil.jwtAuthenticationConverter())
                )

        )
        ;
        return httpSecurity.build();
    }

}