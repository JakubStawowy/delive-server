package com.example.rentiaserver.data.po;

import com.example.rentiaserver.delivery.po.MessagePo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "MESSAGE_PACKAGES")
public class MessagePackagePo extends PackagePo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private MessagePo messagePo;

    public MessagePackagePo(BigDecimal packageLength, BigDecimal packageWidth, BigDecimal packageHeight, MessagePo messagePo) {
        super(packageLength, packageWidth, packageHeight);
        this.messagePo = messagePo;
    }

    public MessagePackagePo() {
    }

    public MessagePo getMessagePo() {
        return messagePo;
    }

    public void setMessagePo(MessagePo messagePo) {
        this.messagePo = messagePo;
    }
}
