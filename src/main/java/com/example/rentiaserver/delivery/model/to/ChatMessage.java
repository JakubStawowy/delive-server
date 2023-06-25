package com.example.rentiaserver.delivery.model.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {
    private String value;
    private String token;
    private String user;
}
