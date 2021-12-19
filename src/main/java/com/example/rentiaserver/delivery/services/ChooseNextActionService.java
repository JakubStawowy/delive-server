package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.delivery.api.IChooseNextActionService;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class ChooseNextActionService implements IChooseNextActionService {
    @Override
    public Set<ActionPack> getNextActionNames(DeliveryState deliveryState, boolean isUserPrincipal, boolean isUserDeliverer) {
        if (DeliveryState.REGISTERED.equals(deliveryState) && isUserDeliverer) {
            return Collections.singleton(new ActionPack("pick", "pick up the load", "Deliverer has arrived to starting location"));
        }
        if (DeliveryState.TO_START.equals(deliveryState) && isUserPrincipal) {
            return Collections.singleton(new ActionPack("start", "hand over package to deliverer", "Your delivery has started"));
        }
        if (DeliveryState.STARTED.equals(deliveryState) && isUserDeliverer) {
            return Collections.singleton(new ActionPack("finish", "package delivered"));
        }
        if (DeliveryState.FINISHED.equals(deliveryState) && isUserPrincipal) {
            return Collections.singleton(new ActionPack("close", "close delivery", "Your delivery has been closed"));
        }
        if (DeliveryState.TO_ACCEPT.equals(deliveryState) && isUserPrincipal) {
            return new HashSet<>(Arrays.asList(new ActionPack("accept", "package delivered", "Your delivery commissioner accepted your finish request! You can now check your wallet"),
                    new ActionPack("discard", "package not delivered", "Unfortunately, your delivery commissioner discarded your finish request")));
        }
        if (DeliveryState.CLOSED.equals(deliveryState)) {
            return Collections.singleton(new ActionPack("-", "-"));
        }
        return Collections.singleton(new ActionPack("waiting", "waiting"));
    }
}
