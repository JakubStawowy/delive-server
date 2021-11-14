package com.example.rentiaserver.data.api;

public class OperationsRepository {
    public static final String[] OPERATIONS = {">=", "<=", "<", ">", "!=", "="};
    public static final String GREATER_THAN_EQUALS = OPERATIONS[0];
    public static final String LESS_THAN_EQUALS = OPERATIONS[1];
    public static final String LESS_THAN = OPERATIONS[2];
    public static final String GREATER_THAN = OPERATIONS[3];
    public static final String NOT_EQUAL = OPERATIONS[4];
    public static final String EQUALS = OPERATIONS[5];
    private OperationsRepository() {}
}