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
        String query = "SELECT * FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if( results.next()){
            return mapRowToAccount(results);
        }
        return null;
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
    public Account getAccountByUserId(int id){
        String query = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(query, id);
        if( results.next()){
            return mapRowToAccount(results);
        }
        return null;
    }

    @Override
    public BigDecimal addBalance(int id, BigDecimal amount){
        String query = "UPDATE account SET balance = (balance + ?) WHERE user_id = ?";
        jdbcTemplate.update(query, amount, id);
        return getAccountBalance((int)id);

    }

    @Override
    public BigDecimal subtractBalance(int id, BigDecimal amount) {
        if (checkValidBalance((int) id, amount)) {
            String query = "UPDATE account SET balance = (balance - ?) WHERE user_id = ?";
            jdbcTemplate.update(query, amount, id);
        }else{
            System.out.println("Not enough money in your account.");
        }

        return getAccountBalance((int)id);

    }

    @Override
    public boolean checkValidBalance(int id, BigDecimal amount){
        if(amount.compareTo(getAccountBalance((int)id)) >= 0){
            return false;
        }
        return true;
    }


    private Account mapRowToAccount(SqlRowSet result ){
        Account account = new Account();
        account.setAccountId(result.getInt("account_id"));
        account.setBalance(result.getBigDecimal("balance"));
        account.setUserId(result.getInt("user_id"));
        return account;
    }
}
