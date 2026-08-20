package com.AbnerTest.ecommerce_test.infra.security;

import com.AbnerTest.ecommerce_test.core.Users;
import com.AbnerTest.ecommerce_test.elements.AppConstants;
import com.AbnerTest.ecommerce_test.exceptions.Exceptions;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    @Value("${api.security.token.refreshSecret}")
    private String refreshSecret;

    public String generateToken(Users user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(AppConstants.JWT_ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new Exceptions.InvalidTokenCredenceException("Invalid token credence", e);
        }
    }

    public String generateRefreshToken(Users user){
        Algorithm refreshAlgorithm = Algorithm.HMAC256(refreshSecret);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(user.getLogin())
                    .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                    .sign(refreshAlgorithm);
        } catch (JWTCreationException e) {
            throw new Exceptions.InvalidTokenCredenceException("Invalid token credence", e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(AppConstants.JWT_ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            return null;
        }
    }

    public String validateRefreshToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(refreshSecret))
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }
}
