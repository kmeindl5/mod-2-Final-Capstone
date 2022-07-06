package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component

public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }
    @Override
    public BigDecimal getAccountBalance(int id){
        Account account = getAccountById(id);
        return account.getBalance();
    }

    @Override
    public Account getAccountById(int id){
        String query = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if( results.next()){
            return mapRowToAccount(results);
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<Account>();
        String query = "SELECT * FROM accounts";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query);
        while(results.next()){
            Account accountResults = mapRowToAccount(results);
            accounts.add(accountResults);
        }
        return accounts;
    }
    @Override
    public Account getAccountByUserId(int id){
        String query = "SELECT * FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if( results.next()){
            return mapRowToAccount(results);
        }
        return null;
    }


    private Account mapRowToAccount(SqlRowSet result ){
        Account account = new Account();
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getBigDecimal("balance"));
        account.setUserId(result.getInt("user_id"));
        return account;
    }
}
