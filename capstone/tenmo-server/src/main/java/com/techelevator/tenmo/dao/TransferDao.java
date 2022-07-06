package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public List<Transfer> getAllTransfers();
    public Transfer getTransferById(int id);
    public List<Transfer> getAllTransfersByAccountId();
    public List<Transfer> getAllTransfersFromId();
    public List<Transfer> getAllTransfersToId();

}
