package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
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
    @RequestMapping(path = "transfer/{id}",method = RequestMethod.GET )
    public Transfer getTransferById(@PathVariable long id){
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }
    @RequestMapping (path= "transfer/account_from", method = RequestMethod.GET)
    public List<Transfer> getTranfersByAccountFrom (@PathVariable long id){
        List<Transfer> transfers = transferDao.getAllTransfersByFromId(id);
        return transfers;
    }
    @RequestMapping (path= "transfer/account_to", method = RequestMethod.GET)
    public List<Transfer> getTranfersByAccountTo (@PathVariable long id){
        List<Transfer> transfers = transferDao.getAllTransfersByToId(id);
        return transfers;
    }
}
