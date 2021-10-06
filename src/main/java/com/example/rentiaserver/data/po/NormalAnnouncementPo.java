package com.example.rentiaserver.data.po;

import com.example.rentiaserver.data.enums.AnnouncementType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "normal_announcement")
public class NormalAnnouncementPo extends AnnouncementPo {

    public NormalAnnouncementPo(DestinationPo destinationFrom, DestinationPo destinationTo, UserPo author, AnnouncementType announcementType, BigDecimal amount) {
        super(destinationFrom, destinationTo, author, announcementType, amount);
    }

    public NormalAnnouncementPo() {}
}
