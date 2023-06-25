package com.example.rentiaserver.user.model.to;

import com.example.rentiaserver.base.model.to.BaseEntityTo;
import com.example.rentiaserver.user.model.bc.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTo extends BaseEntityTo {
    private String name;
    private String surname;
    private  String email;
    private String phone;
    private String balance;
    private UserRoles role;
    private String oldPassword;
    private String newPassword;
}
