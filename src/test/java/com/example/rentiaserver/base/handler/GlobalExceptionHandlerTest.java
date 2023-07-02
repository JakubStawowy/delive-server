package com.example.rentiaserver.base.handler;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void shouldReturnNotFoundResponseWhenEntityNotFoundException() {

        // Given
        EntityNotFoundException ex = new EntityNotFoundException(Object.class, 1L);

        // When
        ResponseEntity<?> responseEntity = GlobalExceptionHandler.handleEntityNotFound(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundResponseWhenEmptyResultDataAccessException() {

        // Given
        EmptyResultDataAccessException ex = new EmptyResultDataAccessException(1);

        // When
        ResponseEntity<?> responseEntity = GlobalExceptionHandler.handleEmptyResultDataAccess(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
