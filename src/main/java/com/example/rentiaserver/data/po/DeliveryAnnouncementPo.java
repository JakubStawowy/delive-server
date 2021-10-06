package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.enums.AnnouncementType;
import com.example.rentiaserver.delivery.po.DeliveryPo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "delivery_announcement")
public class DeliveryAnnouncementPo extends AnnouncementPo {


    public DeliveryAnnouncementPo(DestinationPo destinationFrom, DestinationPo destinationTo, UserPo author, @NotNull LocalDateTime date) {
        super(destinationFrom, destinationTo, author, date, AnnouncementType.DELIVERY);
    }

    public DeliveryAnnouncementPo() {}

}
