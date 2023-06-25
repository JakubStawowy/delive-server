package com.example.rentiaserver.delivery.service;

import com.example.rentiaserver.delivery.api.DeliveryState;
import lombok.Builder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NextActionBuilder {

    @Builder
    public static class ActionPack implements Serializable {

        private String value;

        private String label;

        private String message;

    }

    public static Set<ActionPack> buildNextAction(
            DeliveryState deliveryState,
            boolean isUserPrincipal,
            boolean isUserDeliverer) {

        Set<ActionPack> actionPacks = new HashSet<>();

        if (isUserPrincipal) {
            switch (deliveryState) {
                case TO_START:
                    actionPacks.add(ActionPack.builder()
                            .value("start")
                            .label("hand over package to deliverer")
                            .message("Your delivery has started")
                            .build());
                    break;
                case FINISHED:
                    actionPacks.add(ActionPack.builder()
                            .value("close")
                            .label("close delivery")
                            .message("Your delivery has been closed")
                            .build());
                    break;
                case TO_ACCEPT:
                    actionPacks.add(ActionPack.builder()
                            .value("accept")
                            .label("package delivered")
                            .message("Your delivery commissioner accepted your finish request! You can now check your wallet")
                            .build());
                    actionPacks.add(ActionPack.builder()
                            .value("discard")
                            .label("package not delivered")
                            .message("Unfortunately, your delivery commissioner discarded your finish request")
                            .build());
                    break;
                default:
                    break;
            }
        }

        if (isUserDeliverer) {
            switch (deliveryState) {
                case REGISTERED:
                    actionPacks.add(ActionPack.builder()
                            .value("pick")
                            .label("pick up the load")
                            .message("Deliverer has arrived to starting location")
                            .build());
                    break;
                case STARTED:
                    actionPacks.add(ActionPack.builder()
                            .value("finish")
                            .label("package delivered")
                            .build());
                    break;
                default:
                    break;
            }
        }

        if (DeliveryState.CLOSED.equals(deliveryState)) {
            actionPacks.add(ActionPack.builder()
                    .value("-")
                    .label("-")
                    .build());
        }

        if (actionPacks.isEmpty()) {
            actionPacks.add(ActionPack.builder()
                    .value("waiting")
                    .label("waiting")
                    .build());
        }

        return actionPacks;
    }
}
