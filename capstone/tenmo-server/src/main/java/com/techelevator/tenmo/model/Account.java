package com.techelevator.tenmo.model;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;

public class Account {
    private long accountId;
    private long userId;
    private BigDecimal balance;

    public Account (long accountId, long userId, BigDecimal balance){
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public long getAccountId(){
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void transfer(Account accountTo, BigDecimal amountToTransfer) {
        if(this.balance.compareTo(amountToTransfer) >= 0) {
            this.balance = this.balance.subtract(amountToTransfer);
            accountTo.balance = accountTo.balance.add(amountToTransfer);
        }
        }
    @Override
    public boolean equals (Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass() )
            return false;
        Account account = (Account ) o;
        return accountId == account.accountId && userId == account.userId && balance.equals(account.balance);
    }
}

