package com.example.rentiaserver.data.util;

public class UnsupportedArgumentException extends Exception {

    public UnsupportedArgumentException(String argName) {
        super("Unsupported argument: " + argName);
    }
}
