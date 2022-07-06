package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class AccountController {

    private JdbcAccountDao accountDao;
    public AccountController(JdbcAccountDao accountDao){
        this.accountDao = accountDao;

    }
    @RequestMapping (value = "account", method = RequestMethod.GET)
    public List<Account> listAllAccounts(){
        List<Account> account = accountDao.getAllAccounts();
        return account;
    }

    @RequestMapping (value = "account/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(int id){
        Account account = accountDao.getAccountByUserId(id);
        return account;
    }

}
