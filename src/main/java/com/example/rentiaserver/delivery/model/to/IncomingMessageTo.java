package com.example.rentiaserver.delivery.model.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMessageTo extends MessageTo {
    private boolean consent;
    private Long replyMessageId;

}
