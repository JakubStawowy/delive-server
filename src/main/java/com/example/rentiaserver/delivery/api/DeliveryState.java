package com.example.rentiaserver.delivery.api;

import java.util.Arrays;

public enum DeliveryState {

    REGISTERED("registered"),

    TO_START("toStart"),

    STARTED("started"),

    FINISHED("finished"),

    CLOSED("closed"),

    TO_ACCEPT("toAccept");

    private final String code;

    DeliveryState(String code) {
        this.code = code;
    }

    public static DeliveryState getNextStateAfterAction(String action) {
        return Arrays.stream(values())
                .filter(value -> action.equals(value.code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong action name"));
    }
}
