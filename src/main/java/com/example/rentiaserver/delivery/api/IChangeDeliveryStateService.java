package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.security.to.ResponseTo;

public interface IChangeDeliveryStateService {

    ResponseTo finishDelivery(DeliveryPo deliveryPo, LocationTo clientLocation);
    void closeDelivery(DeliveryPo deliveryPo);
    void startDelivery(DeliveryPo deliveryPo);
    void acceptDeliveryFinishRequest(DeliveryPo deliveryPo);
    void changeDeliveryState(DeliveryPo deliveryPo, DeliveryState deliveryState);
    void completeTransfer(DeliveryPo deliveryPo);
}
