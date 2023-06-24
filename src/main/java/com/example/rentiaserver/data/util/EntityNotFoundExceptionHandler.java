package com.example.rentiaserver.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EntityNotFoundExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EntityNotFoundExceptionHandler.class);

    public static <T> ResponseEntity<T> handle(EntityNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
