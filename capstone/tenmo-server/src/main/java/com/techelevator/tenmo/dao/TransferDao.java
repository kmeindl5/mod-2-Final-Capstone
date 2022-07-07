package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    public List<Transfer> getAllTransfers();
    public Transfer getTransferById(long id);
    public  List<Transfer> getTransferTypeId();
    Transfer sendTransfer(long userFrom, long UserTo, BigDecimal amount);
}

