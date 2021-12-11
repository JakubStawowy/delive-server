package com.example.rentiaserver.security.providers;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.security.api.TokenKeyConstants;
import com.example.rentiaserver.security.api.ITokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static com.example.rentiaserver.ApplicationConstants.Security.ID_PREFIX;
import static com.example.rentiaserver.ApplicationConstants.Security.ROLE_PREFIX;

@Service
public class JsonWebTokenProvider implements ITokenProvider {

    private static final long TIMEOUT = 3600000;
//    private static final long TIMEOUT = 60000;

    @Override
    public String generateUserToken(final UserPo user) {

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
