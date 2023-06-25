package com.example.rentiaserver.user.model.mappers;

import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.model.to.UserTo;

public class UserMapper {

    public static UserTo mapUserPoToTo(UserPo userPo) {
        // TODO
        return null;
    }

    public static UserPo mapUserToToPo(UserTo user) {
        // TODO
        return null;
    }

    public static UserTo mapLoggedUserPoToTo(UserPo userPo) {
        return baseBuilder(userPo)
                .balance(String.valueOf(userPo.getBalance()))
                .build();
    }

    public static UserTo mapSimpleUserPoToTo(UserPo userPo) {
        return baseBuilder(userPo).build();
    }

    private static UserTo.UserToBuilder<?, ?> baseBuilder(UserPo userPo) {
        return UserTo.builder()
                .id(userPo.getId())
                .createdAt(userPo.getCreatedAt())
                .name(userPo.getName())
                .surname(userPo.getSurname())
                .email(userPo.getEmail())
                .phone(userPo.getPhone())
                .role(userPo.getRole());
    }
}
