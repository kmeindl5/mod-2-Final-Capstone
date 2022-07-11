package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.BaseStream;
@Component
@RestController

public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    public AccountController(AccountDao accountDao, UserDao userDao, TransferDao transferDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    @RequestMapping (value = "account", method = RequestMethod.GET)
    public List<Account> listAllAccounts(){
        List<Account> account = accountDao.getAllAccounts();
        return account;
    }

    @RequestMapping (value = "account/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable long id){
        Account account = accountDao.getAccountByUserId(id);
        return account;
    }


    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) throws UsernameNotFoundException {
        Long userId = getCurrentUserId (principal);
        return accountDao.getAccountByUserId(userId).getBalance();
    }

    /*@RequestMapping(value = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal) {
        return transferDao .getTransferTypeId(getCurrentUserId(principal));
    }*/

    private long getCurrentUserId(Principal principal){
        return userDao.findByUsername(principal.getName()).getId();
    }
}
