package com.example.rentiaserver.finance.po;

import com.example.rentiaserver.data.api.BaseEntityPo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TRANSFERS")
public class TransferPo extends BaseEntityPo {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRINCIPAL_WALLET_ID")
    private UserWalletPo principalWalletPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTRACTOR_WALLET_PO")
    private UserWalletPo contractorWalletPo;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private boolean finished;

    public TransferPo(UserWalletPo principalWalletPo, UserWalletPo contractorWalletPo, BigDecimal amount, String currency) {
        this.principalWalletPo = principalWalletPo;
        this.contractorWalletPo = contractorWalletPo;
        this.amount = amount;
        this.currency = currency;
    }

    public TransferPo() {}

    @Override
    public void init() {
        super.init();
        finished = false;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UserWalletPo getPrincipalWalletPo() {
        return principalWalletPo;
    }

    public void setPrincipalWalletPo(UserWalletPo principalWalletPo) {
        this.principalWalletPo = principalWalletPo;
    }

    public UserWalletPo getContractorWalletPo() {
        return contractorWalletPo;
    }

    public void setContractorWalletPo(UserWalletPo contractorWalletPo) {
        this.contractorWalletPo = contractorWalletPo;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
