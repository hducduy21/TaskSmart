package com.tasksmart.activity_tracker.configs;

import com.tasksmart.sharedLibrary.utils.JWTUtil;
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

/**
 * This class is responsible for configuring the security of the application.
 * @author Duy Hoang
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * The JWTUtil object to be used for JWT operations.
     */
    private final JWTUtil jwtUtil;

    private static final String[] PERMIT_ALL_ENDPOINTS = {"/actuator/**", "/actuator/prometheus"};
//    private static final String[] PERMIT_ONLY_GET_ENDPOINTS = {};
//    private static final String[] PERMIT_ONLY_POST_ENDPOINTS = {};
//    private static final String[] PERMIT_ONLY_PUT_ENDPOINTS = {};
//    private static final String[] PERMIT_ONLY_PATCH_ENDPOINTS = {};
//    private static final String[] PERMIT_ONLY_DELETE_ENDPOINTS = {};

    /**
     * This method is responsible for configuring the security of the application.
     * @param httpSecurity the HttpSecurity object to be configured
     * @return the SecurityFilterChain object
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
//                .csrf(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PERMIT_ALL_ENDPOINTS).permitAll()
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