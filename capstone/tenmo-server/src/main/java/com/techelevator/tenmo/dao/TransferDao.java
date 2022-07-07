package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public List<Transfer> getAllTransfers();
    public Transfer getTransferById(long id);
    public  List<Transfer> getTransferTypeId();

}

