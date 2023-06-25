package com.example.rentiaserver.base.exception;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(Class<?> clazz, Object key) {
        super("Entity with type: " + clazz.getName() + " and key: " + key + " not found");
    }
}
