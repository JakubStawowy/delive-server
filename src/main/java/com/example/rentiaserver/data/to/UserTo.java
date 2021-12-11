package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.security.enums.UserRoles;

public class UserTo extends BaseEntityTo {

    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final String balance;
    private final UserRoles role;

    public UserTo(Long id, String createdAt, String name, String surname, String email, String phone, String balance, UserRoles role) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.role = role;
    }

    public static UserTo createLoggedUserTo(UserPo userPo) {
        return new UserTo(
                userPo.getId(),
                String.valueOf(userPo.getCreatedAt()),
                userPo.getName(),
                userPo.getSurname(),
                userPo.getEmail(),
                userPo.getPhone(),
                String.valueOf(userPo.getBalance()),
                userPo.getRole()
        );
    }

    public static UserTo createUserTo(UserPo userPo) {
        return new UserTo(
                userPo.getId(),
                String.valueOf(userPo.getCreatedAt()),
                userPo.getName(),
                userPo.getSurname(),
                userPo.getEmail(),
                userPo.getPhone(),
                null,
                userPo.getRole()
        );
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

    public String getBalance() {
        return balance;
    }
}
