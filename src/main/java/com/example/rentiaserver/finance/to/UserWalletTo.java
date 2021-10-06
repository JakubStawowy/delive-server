package com.example.rentiaserver.finance.to;

import com.example.rentiaserver.data.api.BaseEntityTo;

public class UserWalletTo extends BaseEntityTo {

    private final String balance;
    private final String currency;

    public UserWalletTo(Long id, String createdAt, String balance, String currency) {
        super(id, createdAt);
        this.balance = balance;
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
