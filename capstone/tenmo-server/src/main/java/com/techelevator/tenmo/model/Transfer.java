package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class Transfer {
    private long transferId;
    private long transferTypeId;
    private long transferStatusId;
    private long accountFrom;
    private long accountTo;
    private long transferType;

    private BigDecimal amount;

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getTransferType() {
        return transferType;
    }

    public void setTransferType(long transferType) {
        this.transferType = transferType;
    }


}
