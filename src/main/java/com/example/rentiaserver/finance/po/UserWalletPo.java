package com.example.rentiaserver.finance.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.po.UserPo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "USER_WALLET")
public class UserWalletPo extends BaseEntityPo {

    @OneToOne(mappedBy = "userWallet")
    private UserPo user;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal balance;

    public UserWalletPo(String currency, BigDecimal balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public UserWalletPo() {
    }

    public UserPo getUser() {
        return user;
    }

    public void setUser(UserPo user) {
        this.user = user;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
