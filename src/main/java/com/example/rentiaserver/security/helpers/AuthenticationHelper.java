package com.example.rentiaserver.security.helpers;

import com.example.rentiaserver.security.api.TokenKeyConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.example.rentiaserver.base.ApplicationConstants.Security.ROLE_PREFIX;

public final class AuthenticationHelper {

    public static UsernamePasswordAuthenticationToken getAuthenticationToken(final String header) throws ExpiredJwtException {

        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(TokenKeyConstants.KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(header.replace("Bearer ",""));

        String roles = claimsJws.getBody()
                .get(ROLE_PREFIX)
                .toString();

        return new UsernamePasswordAuthenticationToken(
                "",
                null,
                Collections.singletonList(new SimpleGrantedAuthority(roles))
        );
    }

    public static Long getUserId(HttpServletRequest request) {
        final String header = request.getHeader("Authorization");
        return getUserId(header.replace("Bearer ",""));
    }

    public static Long getUserId(String token) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TokenKeyConstants.KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);

        return Long.valueOf(claimsJws.getBody().get("userId").toString());
    }

    private AuthenticationHelper() {
        //
    }
}
