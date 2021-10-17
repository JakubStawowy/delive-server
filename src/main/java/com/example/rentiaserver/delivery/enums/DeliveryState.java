package com.example.rentiaserver.delivery.enums;

import com.example.rentiaserver.delivery.actions.*;
import com.example.rentiaserver.delivery.api.ChangeDeliveryStateAction;

public enum DeliveryState {

    REGISTERED(null),
    STARTED(new StartDeliveryAction()),
    FINISHED(new FinishDeliveryAction()),
    CLOSED(new CloseDeliveryAction()),
    TO_ACCEPT(new AcceptDeliveryAction()),
    TO_DISCARD(new RestartDeliveryAction()),
    RESTARTED(new RestartDeliveryAction());

    private final ChangeDeliveryStateAction action;

    DeliveryState(ChangeDeliveryStateAction action) {
        this.action = action;
    }

    public static DeliveryState getNextStateAfterAction(String action) {
        if ("start".equals(action))
            return STARTED;
        if ("finish".equals(action))
            return FINISHED;
        if ("close".equals(action))
            return CLOSED;
        if ("restart".equals(action))
            return RESTARTED;
        if ("toAccept".equals(action))
            return TO_ACCEPT;
        if ("toDiscard".equals(action))
            return TO_DISCARD;
        throw new IllegalArgumentException("Wrong action name");
    }

    public ChangeDeliveryStateAction getAction() {
        return action;
    }
}
