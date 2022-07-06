package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private long transferId;
    private int transferTypeId;
    private int transferStatusId;
    private long accountFrom;
    private long accountTo;
    private BigDecimal amount;

    public long getTransferId(){
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
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

    @Override
    public String toString(){
        String typeString = "";
        switch(transferTypeId){
            case 1: typeString = "Request";
            break;
            case 2: typeString = "Send";
            break;
        }
        String s = "";
        switch (transferStatusId){
            case 1: s = "Pending";
            break;
            case 2: s = "Approved";
            break;
        }
        return "\n Transfer if: " + transferId + "\nTransfer Type: " + typeString + "\nTransfer Status id: " + accountFrom;
    }
}
