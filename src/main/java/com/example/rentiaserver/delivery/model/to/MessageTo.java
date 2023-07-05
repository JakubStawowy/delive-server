package com.example.rentiaserver.delivery.model.to;

import com.example.rentiaserver.base.model.to.BaseEntityTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageTo extends BaseEntityTo {
    private Long orderId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String vehicleRegistrationNumber;
    private String phoneNumber;
}
