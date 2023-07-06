package com.example.rentiaserver.base.handler;

import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.LocationNotFoundException;
import com.example.rentiaserver.base.exception.RegisterConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public static ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        logger.error(ex.getMessage());
        return createNotFoundResponse(ex);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public static ResponseEntity<String> handleEmptyResultDataAccess(EmptyResultDataAccessException ex) {
        logger.error(ex.getMessage());
        return createNotFoundResponse(ex);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public static ResponseEntity<String> handleLocationNotFound(LocationNotFoundException ex) {
        logger.error(ex.getMessage());
        return createNotFoundResponse(ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public static ResponseEntity<String> handleAuthenticationFailure(AuthenticationException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RegisterConflictException.class)
    public static ResponseEntity<String> handleRegisterConflict(RegisterConflictException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private static ResponseEntity<String> createNotFoundResponse(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
