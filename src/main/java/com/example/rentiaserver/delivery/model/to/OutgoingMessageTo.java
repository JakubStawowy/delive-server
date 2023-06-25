package com.example.rentiaserver.delivery.model.to;

import com.example.rentiaserver.delivery.api.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingMessageTo extends MessageTo {
    private MessageType messageType;
    private boolean replied;
}
