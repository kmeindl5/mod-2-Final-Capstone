package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    public BigDecimal getAccountBalance(long id);

    public Account getAccountById(long id);

    public List<Account> getAllAccounts();

    public Account getAccountByUserId(long id);

    public BigDecimal addBalance(long id, BigDecimal amount);

    public BigDecimal subtractBalance(long id, BigDecimal amount);

    public boolean checkValidBalance(long id, BigDecimal amount);

}
