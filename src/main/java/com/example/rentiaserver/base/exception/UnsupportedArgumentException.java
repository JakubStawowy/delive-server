package com.example.rentiaserver.base.exception;

public class UnsupportedArgumentException extends Exception {

    public UnsupportedArgumentException(String argName) {
        super("Unsupported argument: " + argName);
    }
}
