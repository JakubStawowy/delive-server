package com.example.rentiaserver.security.jwt;

import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.security.api.TokenKeyConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.example.rentiaserver.base.ApplicationConstants.Security.ID_PREFIX;
import static com.example.rentiaserver.base.ApplicationConstants.Security.ROLE_PREFIX;

public class JsonWebTokenGeneratorHelper {

    private static final long TIMEOUT = 3600000;

    public static String generateUserToken(UserTo user) {

        Key signingKey = new SecretKeySpec(TokenKeyConstants.KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(ROLE_PREFIX, user.getRole())
                .claim(ID_PREFIX, user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIMEOUT))
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
}
