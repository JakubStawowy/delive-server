package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.security.enums.UserRoles;

import java.io.Serializable;

public class UserTo implements Serializable {

    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final UserRoles role;

    public UserTo(UserPo user) {
        name = user.getUserDetails().getName();
        surname = user.getUserDetails().getSurname();
        email = user.getEmail();
        role = user.getRoles();
        phone = user.getUserDetails().getPhone();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public UserRoles getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }
}
