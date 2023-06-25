package com.example.rentiaserver.base.model.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@SuperBuilder
@Getter
@NoArgsConstructor
public class ResponseTo implements Serializable {
    private boolean operationSuccess;
    private HttpStatus status;
    private String message;
}
