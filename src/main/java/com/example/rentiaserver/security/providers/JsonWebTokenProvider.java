package com.example.rentiaserver.security.providers;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.TokenKeyConstants;
import com.example.rentiaserver.security.api.ITokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JsonWebTokenProvider implements ITokenProvider {

    private static final String USERNAME_PREFIX = "name";
    private static final String ROLE_PREFIX = "role";
    private static final long TIMEOUT = 3600000;

    @Override
    public String generateUserToken(final UserPo user) {

        Key signingKey = new SecretKeySpec(TokenKeyConstants.KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
        String name = user.getName() + " " + user.getSurname();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(ROLE_PREFIX, user.getRole())
                .claim(USERNAME_PREFIX, name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIMEOUT))
//                .setExpiration(new Date(System.currentTimeMillis()+10000))
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
}
