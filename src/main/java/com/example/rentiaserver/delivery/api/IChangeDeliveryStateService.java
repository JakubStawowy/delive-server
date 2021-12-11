package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.geolocation.to.LocationTo;

public interface IChangeDeliveryStateService {

    double RADIUS = 0.5;
    void finishDelivery(DeliveryPo deliveryPo, LocationTo clientLocation);
    void closeDelivery(DeliveryPo deliveryPo);
    void startDelivery(DeliveryPo deliveryPo);
    void changeDeliveryState(DeliveryPo deliveryPo, DeliveryState deliveryState);
    void completeTransfer(DeliveryPo deliveryPo);
}
