package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;


public class TransferService {
private final Scanner scanner = new Scanner(System.in);
private Account account;

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    public Transfer createTransfer(Transfer newTransfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer>entity = new HttpEntity<>(newTransfer, headers);
        Transfer returnedTransfer = null;
        try {
            returnedTransfer = restTemplate.postForObject(API_BASE_URL + "transfer", entity, Transfer.class);
        } catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }
    public Transfer promptForTransferData(Transfer existingTransfer) {
        Transfer newTransfer = null;
        while (newTransfer== null) {
            System.out.println("--------------------------------------------");
            System.out.println("Enter transfer data as a comma separated list containing:");
            System.out.println("Test");
            if (existingTransfer != null) {
                System.out.println(existingTransfer);
            } else {
                System.out.println("Teesting 12");
            }
            System.out.println("--------------------------------------------");
            System.out.println();
            newTransfer = makeTransfer(scanner.nextLine());

            if (newTransfer == null) {
                System.out.println("Invalid entry. Please try again.");
            }
        }
        if (existingTransfer != null) {
            newTransfer.setTransferId(existingTransfer.getTransferId());
        }
        return newTransfer;
    }
    private Transfer makeTransfer(String makeTran) {
        Transfer transfer = new Transfer();
        transfer = restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class ).getBody();
        try {
                transfer = new Transfer();
                transfer.setAccountTo (account.getAccountId());
                transfer.setAccountFrom(account.getAccountId());
                transfer.setAmount(account.getBalance());


            } catch (NumberFormatException e) {
                transfer = null;
            }
               return transfer;
    }
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("");
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;	}
}
