package com.example.rentiaserver.base.model.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntityTo implements Serializable {
    private Long id;
    private Date createdAt;
}



