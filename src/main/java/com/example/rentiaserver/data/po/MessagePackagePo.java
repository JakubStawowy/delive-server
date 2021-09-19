package com.example.rentiaserver.data.po;

import com.example.rentiaserver.delivery.po.MessagePo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "message_package")
public class MessagePackagePo extends PackagePo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "message_id")
    private MessagePo message;

    public MessagePackagePo(@NotNull BigDecimal packageLength, @NotNull BigDecimal packageWidth, @NotNull BigDecimal packageHeight, MessagePo message) {
        super(packageLength, packageWidth, packageHeight);
        this.message = message;
    }

    public MessagePackagePo() {
    }

    public MessagePo getMessage() {
        return message;
    }

    public void setMessage(MessagePo message) {
        this.message = message;
    }
}
