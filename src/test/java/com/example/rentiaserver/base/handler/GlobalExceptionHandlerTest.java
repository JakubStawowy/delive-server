package com.example.rentiaserver.base.handler;

import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO Parameterized tests
class GlobalExceptionHandlerTest {

    @Test
    void shouldReturnNotFoundResponseWhenEntityNotFoundException() {

        // Given
        EntityNotFoundException ex = new EntityNotFoundException(Object.class, 1L);

        // When
        ResponseEntity<String> responseEntity = GlobalExceptionHandler.handleEntityNotFound(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundResponseWhenEmptyResultDataAccessException() {

        // Given
        EmptyResultDataAccessException ex = new EmptyResultDataAccessException(1);

        // When
        ResponseEntity<String> responseEntity = GlobalExceptionHandler.handleEmptyResultDataAccess(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundResponseWhenLocationNotFoundException() {

        // Given
        LocationNotFoundException ex = new LocationNotFoundException("");

        // When
        ResponseEntity<String> responseEntity = GlobalExceptionHandler.handleLocationNotFound(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnUnauthorizedResponseWhenAuthorizationException() {

        // Given
        AuthenticationException ex = new AuthenticationException("");

        // When
        ResponseEntity<String> responseEntity = GlobalExceptionHandler.handleAuthenticationFailure(ex);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }
}
