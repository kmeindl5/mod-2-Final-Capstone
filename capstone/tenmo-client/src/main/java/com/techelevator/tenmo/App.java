package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private RestTemplate account = new RestTemplate();
    private Account accountCl;
    private Transfer transfer;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		User user = currentUser.getUser();
        Account accounts = account.getForObject(API_BASE_URL + "account/" + user.getId(),Account.class);
        try {
            BigDecimal balance = accounts.getBalance();
            System.out.println("Your current balance is: " + balance);
        }catch (NullPointerException e){
            System.out.println("No balance available");
        }
		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {

        try{User user = currentUser.getUser();
        ResponseEntity<User[]> responseEntity = account.getForEntity(API_BASE_URL+ "account/" + user.getId(), User[].class);
        List<User> users = Arrays.asList(responseEntity.getBody());
        long userId = requestUserId(users);
        if (userId == 0) {
            System.out.println("Exiting User Selection");
            return;
        }

        BigDecimal money = requestMoneyToSend(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Transfer entityTransfer = new Transfer();
        if (money.compareTo(account.getForObject(API_BASE_URL + "accounts/" + user.getId(), Account.class).getBalance()) >= 0) {
            System.out.println("Money was greater than the balance. Please send a lower amount.");

        } else {
            entityTransfer = initializeTransfer(userId, user.getId(), money, 2, 3);
        }
        HttpEntity anEntity = new HttpEntity(entityTransfer, headers);
        Transfer transfer = account.postForObject(API_BASE_URL + "transfer", anEntity, Transfer.class);
        }catch(Exception e){
            System.out.println("No clue");
        }




    }



	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

    // TODO initial status pending
        private long requestUserId(List<User> users){
            long userId = consoleService.promptForInt("Enter ID of user you are requesting from");
            while(userId<0 || userId > users.size()){
                System.out.println("The ID entered wasn't valid, please try again");
                userId = consoleService.promptForInt("Please select a new user");
            }
            return userId;
        }

    private BigDecimal getTransferAmount(List<Transfer> transfers) {
        if(transfers.size() > 0) {
            for(Transfer aTransfer : transfers) {
                return aTransfer.getAmount();
            }
        }
        return null;
    }

        private BigDecimal requestMoneyToSend(long userId){
            double money = Double.parseDouble((String)consoleService.promptForString("How much money would you like to send to User #" + userId));
            while(money <=0){
                System.out.println("Unable to send negative amount of money.");
                money = Double.parseDouble(((String)consoleService.promptForString("How much money would you like to send to User #" + userId)));
            }
            return BigDecimal.valueOf(money);
        }

        private Transfer initializeTransfer(long toId, long fromId, BigDecimal amount,int status, int type ){
            Transfer transfer = new Transfer();
            transfer.setAccountFrom((long)fromId);
            transfer.setAccountFrom((long)toId);
            transfer.setAmount(amount);
            transfer.setTransferStatusId(status);
            transfer.setTransferTypeId(type);


            return transfer;
        }

}
