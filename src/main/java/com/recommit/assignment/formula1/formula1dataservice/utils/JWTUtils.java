package com.recommit.assignment.formula1.formula1dataservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.recommit.assignment.formula1.formula1dataservice.security.serviceImpl.AppUserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JWTUtils {


    public static Long ACCESS_TOKEN_EXPIRES_IN_MILLI_SECONDS;
    private static String JWT_AUTH_SECRET;

    public static String generateAccessToken(AppUserDetailsImpl appUserDetails, String ipAddress) {
        return JWT.create()
                .withSubject(appUserDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_IN_MILLI_SECONDS))
                .withIssuer(ApplicationConstants.JWT_TOKEN_AUTHOR)
                .withClaim(ApplicationConstants.CLAIM_USERNAME, appUserDetails.getUsername())
                .withClaim(ApplicationConstants.CLAIM_USERID, appUserDetails.getId())
                .withClaim(ApplicationConstants.CLAIM_IP_ADDRESS, ipAddress)
                //.withClaim(CLAIM_ROLES, appUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(JWTUtils.getAccessTokenAlgorithm());
    }

    public static DecodedJWT getVerifyAccessToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(getAccessTokenAlgorithm()).build();
        return jwtVerifier.verify(token);
    }

    private static Algorithm getAccessTokenAlgorithm() {
        return Algorithm.HMAC256(JWT_AUTH_SECRET.getBytes());
    }

    @Value("${service.param.jwt-config.secret}")
    public void setJwtAuthSecret(String jwtAuthSecret) {
        JWTUtils.JWT_AUTH_SECRET = jwtAuthSecret;
    }

    @Value("${service.param.jwt-config.expiration}")
    public void setTokenExpiresInMilliseconds(Long timeInMilliseconds) {
        JWTUtils.ACCESS_TOKEN_EXPIRES_IN_MILLI_SECONDS = timeInMilliseconds;
    }
}
