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

    public UserTo(UserPo user) {
        super(user.getId(), String.valueOf(user.getCreatedAt()));
        name = user.getUserDetails().getName();
        surname = user.getUserDetails().getSurname();
        email = user.getEmail();
        role = user.getRoles();
        phone = user.getUserDetails().getPhone();
        UserWalletPo userWalletPo = user.getUserWallet();
        userWallet = new UserWalletTo(userWalletPo.getId(), String.valueOf(userWalletPo.getCreatedAt()),
                String.valueOf(userWalletPo.getBalance()), userWalletPo.getCurrency());
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
