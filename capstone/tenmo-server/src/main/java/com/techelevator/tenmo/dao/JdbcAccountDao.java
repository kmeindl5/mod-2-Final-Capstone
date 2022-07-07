package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component

public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }
    @Override
    public BigDecimal getAccountBalance( long id){
        Account account = getAccountById(id);
        return account.getBalance();
    }

    @Override
    public Account getAccountById(long id){
        String query = "SELECT * FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if( results.next()){
            return mapRowToAccount(results);
        }else{
            System.out.println("Account not found");
            return null;
        }

    }

    @Override
    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<Account>();
        String query = "SELECT * FROM account";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query);
        while(results.next()){
            Account accountResults = mapRowToAccount(results);
            accounts.add(accountResults);
        }
        return accounts;
    }
    @Override
    public Account getAccountByUserId(long id){
        String query = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if( results.next()){
            return mapRowToAccount(results);
        }else{
            System.out.println("Account not found");
            return null;
        }

    }

    @Override
    public BigDecimal addBalance(long id, BigDecimal amount){
        String query = "UPDATE account SET balance = (balance + ?) WHERE user_id = ?";
        jdbcTemplate.update(query, amount, id);
        return getAccountBalance((long)id);

    }

    @Override
    public BigDecimal subtractBalance(long id, BigDecimal amount) {
        if (checkValidBalance((long) id, amount)) {
            String query = "UPDATE account SET balance = (balance - ?) WHERE user_id = ?";
            jdbcTemplate.update(query, amount, id);
        }else{
            System.out.println("Not enough money in your account.");
        }

        return getAccountBalance((long)id);

    }

    @Override
    public boolean checkValidBalance(long id, BigDecimal amount){
        if(amount.compareTo(getAccountBalance((int)id)) >= 0){
            return false;
        }
        return true;
    }


    private Account mapRowToAccount(SqlRowSet result ){
      return new Account(result.getLong("account_id"), result.getLong("user_id"), result.getBigDecimal("balance"));
    }
}
