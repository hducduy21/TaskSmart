package com.tasksmart.sharedLibrary.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;

    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    public String generateToken(String userId, String name, String username, String email, Set<String> roles, long millis){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(name)
                .claim("userId", userId)
                .claim("email", email)
                .claim("username", username)
                .claim("scope", buildScope(roles))
                .issuer("tasksmark.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(millis, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e){
            log.error("Error signing JWT token", e);
            throw new RuntimeException("Error signing JWT token");
        }
    }

    public boolean validateToken(String token){
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());

            if (!jwsObject.verify(jwsVerifier)) {
                return false;
            }

            JWTClaimsSet claims = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            Date expirationTime = claims.getExpirationTime();
            return expirationTime == null || !new Date().after(expirationTime);

            // Additional claims verification can be added here
        } catch (Exception e){
            log.error("Error validating JWT token", e);
            return false;
        }
    }

    public JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS256");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    public String buildScope(Set<String> roles){
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(roles))
            roles.forEach(joiner::add);

        return joiner.toString();
    }
}
