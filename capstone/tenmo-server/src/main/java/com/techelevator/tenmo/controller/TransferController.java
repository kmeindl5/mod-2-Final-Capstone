package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class TransferController {
    private JdbcTransferDao transferDao;

    public TransferController (JdbcTransferDao transferDao){
        this.transferDao = transferDao;
    }

    @RequestMapping(value = "transfer", method = RequestMethod.GET)
    public List<Transfer> listAllTransfers(){
        List<Transfer> transfer = transferDao.getAllTransfers();
        return transfer;
    }
}
