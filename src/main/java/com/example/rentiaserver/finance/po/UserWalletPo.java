package com.example.rentiaserver.finance.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.po.UserPo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "USER_WALLET")
public class UserWalletPo extends BaseEntityPo {

    @OneToOne(mappedBy = "userWalletPo", fetch = FetchType.LAZY)
    private UserPo user;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "contractorWalletPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TransferPo> contractorTransferPos;

    @OneToMany(mappedBy = "principalWalletPo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TransferPo> principalTransferPos;

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

    public Set<TransferPo> getContractorTransferPos() {
        return contractorTransferPos;
    }

    public void setContractorTransferPos(Set<TransferPo> contractorTransferPos) {
        this.contractorTransferPos = contractorTransferPos;
    }

    public Set<TransferPo> getPrincipalTransferPos() {
        return principalTransferPos;
    }

    public void setPrincipalTransferPos(Set<TransferPo> principalTransferPos) {
        this.principalTransferPos = principalTransferPos;
    }
}
