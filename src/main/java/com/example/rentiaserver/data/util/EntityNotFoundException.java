package com.example.rentiaserver.data.util;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(Class<?> clazz, Object key) {
        super("Entity with type: " + clazz.getName() + " and key: " + key + " not found");
    }
}
