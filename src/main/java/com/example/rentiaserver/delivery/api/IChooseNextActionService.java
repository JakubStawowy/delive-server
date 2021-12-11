package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.delivery.enums.DeliveryState;

import java.io.Serializable;
import java.util.Set;

public interface IChooseNextActionService {
    Set<ActionPack> getNextActionNames(DeliveryState deliveryState, boolean isUserPrincipal, boolean isUserDeliverer);
    class ActionPack implements Serializable {

        private final String value;
        private final String label;
        private String message;

        public ActionPack(String value, String label, String message) {
            this.value = value;
            this.label = label;
            this.message = message;
        }

        public ActionPack(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
