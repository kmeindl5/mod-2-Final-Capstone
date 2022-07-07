package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;
    private UserDao userDao;



    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao, UserDao userDao){
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
        this.userDao = userDao;

    }

    private static final String SQL_SELECT_TRANSFER = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, " +
            "aFrom.account_id as fromAcct, aFrom.user_id as fromUser, aFrom.balance as fromBal, " +
            "aTo.account_id as toAcct, aTo.user_id as toUser, aTo.balance as toBal " +
            "FROM transfer t " +
            "INNER JOIN transfer_type tt ON t.transfer_type_id = tt.transfer_type_id " +
            "INNER JOIN transfer_status ts ON t.transfer_status_id = ts.transfer_status_id " +
            "INNER JOIN account aFrom on account_from = aFrom.account_id " +
            "INNER JOIN account aTo on account_to = aTo.account_id ";

    @Override
    public List<Transfer> getAllTransfers(){
        List<Transfer> transfers = new ArrayList<Transfer>();

        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL_SELECT_TRANSFER);
        while(results.next()){
            Transfer transferResults = mapRowToTransfer(results);
            transfers.add(transferResults);
        }
        return transfers;
    }
    @Override
    public Transfer getTransferById (long transferId){
        String query = SQL_SELECT_TRANSFER + "where transfer_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(query, transferId);
        if(results.next()){
            return mapRowToTransfer(results);
        }
        return null;
    }

    public List<Transfer> getTransferTypeId(){
        List<Transfer> transfers = new ArrayList<Transfer>();

        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL_SELECT_TRANSFER + "WHERE transfer_type_id = ?");
        while(results.next()){
            Transfer transferResults = mapRowToTransfer(results);
            transfers.add(transferResults);
        }
        return transfers;
    }


    public Transfer createTransfer(Transfer transfer){
        String query = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount)"
                + "VALUES (?,?,?,?,?,?)";

        Long newTransferId = getNextTransferId();
        Long transferTypeId = transfer.getTransferTypeId();
        Long transferStatusId = transfer.getTransferStatusId();
        Account fromAccount = accountDao.getAccountByUserId(transfer.getAccountFrom());
        Account toAccount = accountDao.getAccountByUserId(transfer.getAccountTo());

        jdbcTemplate.update(query, newTransferId, transferTypeId, transferStatusId, fromAccount.getAccountId(), toAccount.getAccountId(), transfer.getAmount());
        return transfer;
    }

    private long getNextTransferId(){
        SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id)");
        if(nextIdResult.next()) {
            return nextIdResult.getLong(1);
        } else

    {
        throw new RuntimeException(("Something went wrong while getting an id for the new transfer"));
    }
    }
    @Override
    public Transfer sendTransfer(long from, long to, BigDecimal amount) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (2, 2, ?, ?, ?)";
        jdbcTemplate.update(sql, from, to, amount);
        Transfer transfer = new Transfer();
        return transfer;
    }


    private Transfer mapRowToTransfer(SqlRowSet result ){
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getLong("transfer_id"));
        transfer.setTransferTypeId(result.getLong("transfer_type_id"));
        transfer.setTransferStatusId(result.getLong("transfer_status_id"));
        transfer.setAccountFrom(result.getLong("account_from"));
        transfer.setAccountTo(result.getLong("account_to"));
        transfer.setAmount(result.getBigDecimal("amount"));

        return transfer;
    }
}
