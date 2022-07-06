package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Transfer> getAllTransfers(){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String query = "SELECT * FROM transfer";

        SqlRowSet results = jdbcTemplate.queryForRowSet(query);
        while(results.next()){
            Transfer transferResults = mapRowToTransfer(results);
            transfers.add(transferResults);
        }
        return transfers;
    }
    @Override
    public Transfer getTransferById (int id){
        String query = "SELECT * FROM transfer WHERE transfer_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if(results.next()){
            return mapRowToTransfer(results);
        }
        return null;
    }

    @Override
    public List<Transfer> getAllTransfersByAccountId (int id){
        List<Transfer> transfers = new ArrayList<Transfer>();
        String query = "SELECT * FROM transfer WHERE account_from IN (?) OR account_to IN(?) ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(query,id, id);
        while(results.next()){
            Transfer transferResults = mapRowToTransfer(results);
            transfers.add(transferResults);
        }
        return transfers;
    }

    @Override
    public List<Transfer> getAllTransfersByFromId (int id){
        List<Transfer> transfers = new ArrayList<>();
        String query = "SELECT * FROM transfer WHERE account_from IN (?) OR account_to IN(?) ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(query,id, id);
        while(results.next()){
            Transfer transferResults = mapRowToTransfer(results);
            transfers.add(transferResults);
        }
        return transfers;
    }

    @Override
    public List<Transfer> getAllTransfersByToId (int id){
        List<Transfer> transfers = new ArrayList<>();
        String query = "SELECT * FROM transfer WHERE account_from IN (?) OR account_to IN(?) ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(query,id, id);
        while(results.next()){
            Transfer transferResults = mapRowToTransfer(results);
            transfers.add(transferResults);
        }
        return transfers;
    }


    public Transfer createTransfer(Transfer transfer){
        String query = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
                + "VALUES (?,?,?,?,?)";
        jdbcTemplate.update(query,transfer.getTransferTypeId(),transfer.getTransferStatusId(), transfer.getAccountFrom(),
        transfer.getAccountTo(), transfer.getAmount());
        return transfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet result ){
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setTransferTypeId(result.getInt("transfer_type_id"));
        transfer.setTransferStatusId(result.getInt("transfer_status_id"));
        transfer.setAccountFrom(result.getInt("account_from"));
        transfer.setAccountTo(result.getInt("account_to"));
        transfer.setAmount(result.getBigDecimal("amount"));

        return transfer;
    }
}
