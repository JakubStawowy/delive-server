package com.example.rentiaserver.data.util;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.api.BaseEntityTo;

import java.util.Optional;

public class EntityValidator {

    public static <T extends BaseEntityTo> void validateOptionalToPresence(Optional<T> optional) {
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Missing object of type: " + optional.getClass());
        }
    }

    public static <T extends BaseEntityPo> void validateOptionalPoPresence(Optional<T> optional) {
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Missing object of type: " + optional.getClass());
        }
    }
}
