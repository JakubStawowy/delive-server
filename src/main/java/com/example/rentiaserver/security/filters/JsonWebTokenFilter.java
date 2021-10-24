package com.example.rentiaserver.security.filters;

import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JsonWebTokenFilter extends BasicAuthenticationFilter {

    private static final String AUTHORIZATION_PREFIX = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final Logger logger = Logger.getLogger(JsonWebTokenFilter.class.getName());

    public JsonWebTokenFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(AUTHORIZATION_PREFIX);

        if (header == null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken result = JsonWebTokenHelper.getAuthenticationToken(header);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (JwtException e) {
            logger.log(Level.INFO, e.getMessage());
            SecurityContextHolder.clearContext();
        } finally {
            chain.doFilter(request, response);
        }
    }
}
