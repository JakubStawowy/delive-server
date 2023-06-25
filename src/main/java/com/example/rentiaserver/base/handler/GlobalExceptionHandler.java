package com.example.rentiaserver.base.handler;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
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
    public static <T> ResponseEntity<T> handleEntityNotFound(EntityNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public static <T> ResponseEntity<T> handleEmptyResultDataAccess(EmptyResultDataAccessException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
