package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@Component
@RestController
public class TransferController {
    private JdbcTransferDao transferDao;
    private AccountDao accountdao;
    private User user;

    public TransferController(JdbcTransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @RequestMapping(value = "transfer", method = RequestMethod.GET)
    public List<Transfer> listAllTransfers() {
        List<Transfer> transfer = transferDao.getAllTransfers();
        return transfer;
    }

    @RequestMapping(path = "transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable long id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }


    /*@ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfer/{id}", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer, @PathVariable long id) {
        BigDecimal amountToTransfer = transfer.getAmount();
        Account accountFrom = accountdao.getAccountById(transfer.getAccountFrom());
        Account accountTo = accountdao.getAccountById(transfer.getAccountTo());

        accountFrom.getBalance().s
                transferDao.createTransfer(transfer);

        accountdao.subtractBalance(transfer.getAccountFrom());
        accountdao.addBalance(accountTo.getAccountId());
        return new Transfer();
    }*/

    @RequestMapping(path = "transfer", method = RequestMethod.POST)
    public Transfer sendTransferRequest(@RequestBody Transfer transfer){
        Transfer results = transferDao.sendTransfer(transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());
        return results;
    }
    /*@RequestMapping (path= "transfer/account_to", method = RequestMethod.GET)
    public List<Transfer> getTranfersByAccountTo (@PathVariable long id){
        List<Transfer> transfers = transferDao.getAllTransfersByToId(id);
        return transfers;
    }*/



}
