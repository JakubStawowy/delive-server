package com.example.rentiaserver.delivery.helpers;

import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.to.DeliveryTo;

public class DeliveryToCreateHelper {
    public static DeliveryTo create(DeliveryPo deliveryPo) {
        return new DeliveryTo(
                deliveryPo.getId(),
                deliveryPo.getUser().getId(),
                deliveryPo.getCreatedAt().toString(),
                AnnouncementToCreatorHelper.create(deliveryPo.getAnnouncement()),
                deliveryPo.getDeliveryState().name()
        );
    }
}
