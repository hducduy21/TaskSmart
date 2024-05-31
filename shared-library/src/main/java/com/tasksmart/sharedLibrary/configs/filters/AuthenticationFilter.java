package com.tasksmart.sharedLibrary.configs.filters;

import com.tasksmart.sharedLibrary.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * This class is used to filter the request to check the authentication.
 * If the request has the Authorization header, it will check the token and set the authentication to the SecurityContext.
 * If the request has no Authorization header, it will pass the request
 *
 * @author Duy Hoang
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {
    /** The JWTUtil instance for decoding the token.*/
    private final JWTUtil jwtUtil;

    /**
     * This method is used to filter the request to check the authentication.
     * If the request has the Authorization header, it will check the token and set the authentication to the SecurityContext.
     * If the request has no Authorization header, it will pass the request
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authHeader != null && authHeader.startsWith("Bearer") && authentication != null) {
            JwtDecoder jwtDecoder = jwtUtil.jwtDecoder();
            Jwt jwt = jwtDecoder.decode(authHeader.substring(7));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwt.getClaim("userId"), null, authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
