package com.example.rentiaserver.data.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.finance.po.UserWalletPo;
import com.example.rentiaserver.finance.to.UserWalletTo;
import com.example.rentiaserver.security.enums.UserRoles;

public class UserTo extends BaseEntityTo {

    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final UserRoles role;
    private final UserWalletTo userWallet;

//    public UserTo(UserPo user) {
//        super(user.getId(), String.valueOf(user.getCreatedAt()));
//        name = user.getName();
//        surname = user.getSurname();
//        email = user.getEmail();
//        role = user.getRole();
//        phone = user.getPhone();
//        UserWalletPo userWalletPo = user.getUserWalletPo();
//        userWallet = new UserWalletTo(userWalletPo.getId(), String.valueOf(userWalletPo.getCreatedAt()),
//                String.valueOf(userWalletPo.getBalance()), userWalletPo.getCurrency());
//    }

    private UserTo(Long id, String createdAt, String name, String surname, String email, String phone, UserRoles role, UserWalletTo userWallet) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.userWallet = userWallet;
    }

    public static UserTo createLoggedUserTo(UserPo userPo) {
        UserWalletPo userWalletPo = userPo.getUserWalletPo();
        return new UserTo(
                userPo.getId(),
                String.valueOf(userPo.getCreatedAt()),
                userPo.getName(),
                userPo.getSurname(),
                userPo.getEmail(),
                userPo.getPhone(),
                userPo.getRole(),
                new UserWalletTo(userWalletPo.getId(), String.valueOf(userWalletPo.getCreatedAt()),
                    String.valueOf(userWalletPo.getBalance()), userWalletPo.getCurrency())
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
                userPo.getRole(),
                null
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

    public UserWalletTo getUserWallet() {
        return userWallet;
    }
}
