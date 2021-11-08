package com.example.rentiaserver.data.enums;

public enum FeedbackRate {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    public static FeedbackRate getByNumberValue(int numberValue) {
        if (numberValue == 0) {
            return ONE;
        }

        if (numberValue == 1) {
            return TWO;
        }

        if (numberValue == 2) {
            return THREE;
        }

        if (numberValue == 3) {
            return FOUR;
        }

        if (numberValue == 4) {
            return FIVE;
        }

        throw new IllegalArgumentException("Wrong number value");
    }
}
