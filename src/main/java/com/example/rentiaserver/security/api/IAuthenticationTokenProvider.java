package com.example.rentiaserver.security.api;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public interface IAuthenticationTokenProvider<T extends AbstractAuthenticationToken> {
    String TOKEN_PREFIX = "Bearer ";
    T getAuthenticationToken(String header);
}
