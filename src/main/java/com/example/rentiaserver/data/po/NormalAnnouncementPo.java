package com.example.rentiaserver.data.po;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "normal_announcement")
public class NormalAnnouncementPo extends AnnouncementPo {

    public NormalAnnouncementPo(DestinationPo destinationFrom, DestinationPo destinationTo, UserPo author) {
        super(destinationFrom, destinationTo, author);
    }

    public NormalAnnouncementPo() {}
}
