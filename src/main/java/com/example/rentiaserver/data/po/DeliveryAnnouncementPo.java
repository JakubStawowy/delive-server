package com.example.rentiaserver.data.po;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_announcement")
public class DeliveryAnnouncementPo extends AnnouncementPo {

    @NotNull
    private LocalDate date;

    public DeliveryAnnouncementPo(DestinationPo destinationFrom, DestinationPo destinationTo, UserPo author, @NotNull LocalDate date) {
        super(destinationFrom, destinationTo, author);
        this.date = date;
    }

    public DeliveryAnnouncementPo() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
