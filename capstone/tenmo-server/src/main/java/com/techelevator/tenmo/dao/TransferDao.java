package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public List<Transfer> getAllTransfers();
    public Transfer getTransferById(int id);
    public List<Transfer> getAllTransfersByAccountId(int id);
    public List<Transfer> getAllTransfersByFromId(int id);
    public List<Transfer> getAllTransfersByToId(int id);

}
