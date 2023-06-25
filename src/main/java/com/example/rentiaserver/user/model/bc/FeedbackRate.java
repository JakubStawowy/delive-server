package com.example.rentiaserver.user.model.bc;


public enum FeedbackRate {

    ONE(1),

    TWO(2),

    THREE(3),

    FOUR(4),

    FIVE(5);

    private final int value;

    FeedbackRate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FeedbackRate getByNumberValue(int numberValue) {
        switch (numberValue) {
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            default:
                throw new IllegalArgumentException("Wrong number value");
        }
    }

}
