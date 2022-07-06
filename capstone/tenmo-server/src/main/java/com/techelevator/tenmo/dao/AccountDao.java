package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    public BigDecimal getAccountBalance(int id);

    public Account getAccountById(int id);

    public List<Account> getAllAccounts();

    public Account getAccountByUserId(int id);

}
