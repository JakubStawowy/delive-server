package com.example.rentiaserver.security.providers;

import com.example.rentiaserver.constants.TokenKeyConstants;
import com.example.rentiaserver.security.api.IAuthenticationTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

public final class UsernamePasswordAuthenticationTokenProvider implements IAuthenticationTokenProvider<UsernamePasswordAuthenticationToken> {

    private static final String USERNAME_PREFIX = "name";
    private static final String ROLE_PREFIX = "role";

    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String header) throws ExpiredJwtException {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TokenKeyConstants.KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(header.replace(TOKEN_PREFIX,""));

        String username = claimsJws.getBody().get(USERNAME_PREFIX).toString();
        String roles = claimsJws.getBody().get(ROLE_PREFIX).toString();
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(roles))
        );
    }
}
