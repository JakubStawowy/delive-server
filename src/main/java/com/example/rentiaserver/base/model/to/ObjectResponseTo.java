package com.example.rentiaserver.base.model.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
public class ObjectResponseTo extends ResponseTo {
    private Object object;
}
