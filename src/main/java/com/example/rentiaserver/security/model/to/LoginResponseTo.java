package com.example.rentiaserver.security.model.to;

import com.example.rentiaserver.user.model.bc.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseTo {
    private Long userId;
    private String token;
    private UserRoles role;
}
