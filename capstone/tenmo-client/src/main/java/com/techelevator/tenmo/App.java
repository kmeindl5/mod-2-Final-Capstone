package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TransferService transferService = new TransferService();
    private final UserService userService = new UserService();

    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();
    private Account account;
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
        Account accounts = restTemplate.getForObject(API_BASE_URL + "account/" + user.getId(), Account.class);
        try {
            BigDecimal balance = accounts.getBalance();
            System.out.println("Your current balance is: " + balance);
        } catch (NullPointerException e) {
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
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(currentUser.getUser().getId());

        System.out.println("Select who you would like to send money to from below.");
        String user = consoleService.promptForString(String.valueOf(userService.listUsers()));

        //System.out.println(consoleService.promptForBigDecimal("Please enter a decimal number."));
        transfer.setAccountTo(transfer.getAccountTo());
        transfer.setAmount(consoleService.promptForBigDecimal("Please enter a decimal number. "));
        Transfer transferResult = transferService.promptForTransferData(transfer);
        if(transferResult == null){
            System.out.println("try Again");
            consoleService.promptForString(String.valueOf(userService.listUsers()));
        }
        return;


        /*try {
            User user = currentUser.getUser();
            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(API_BASE_URL + "tenmo_user/" + user.getId(), User[].class);
            List<User> users = Arrays.asList(responseEntity.getBody());
            long userId = requestUserId(users);

            System.out.println("Enter number");
            return;

            BigDecimal money = requestMoneyToSend(userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Transfer entityTransfer = new Transfer();
            if (money.compareTo(restTemplate.getForObject(API_BASE_URL + "accounts/" + user.getId(), Account.class).getBalance()) >= 0) {
                System.out.println("Money was greater than the balance. Please send a lower amount.");

            } else {
                entityTransfer = initializeTransfer(userId, user.getId(), money, 2, 3);
            }
            HttpEntity anEntity = new HttpEntity(entityTransfer, headers);
            Transfer transfer = restTemplate.postForObject(API_BASE_URL + "transfer", anEntity, Transfer.class);
        } catch (Exception e) {
            System.out.println("No clue");

        }*/

    }


    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    // TODO initial status pending
    private long requestUserId(List<User> users) {
        long userId = consoleService.promptForInt("Enter ID of user you are requesting from");
        while (userId < 0 || userId > users.size()) {
            System.out.println("The ID entered wasn't valid, please try again");
            userId = consoleService.promptForInt("Please select a new user");
        }
        return userId;
    }

    private BigDecimal getTransferAmount(List<Transfer> transfers) {
        if (transfers.size() > 0) {
            for (Transfer aTransfer : transfers) {
                return aTransfer.getAmount();
            }
        }
        return null;
    }

    private BigDecimal requestMoneyToSend(long userId) {
        BigDecimal money = consoleService.promptForBigDecimal("Enter number");
        while (money.compareTo(account.getBalance()) <= 0) {
            System.out.println("Unable to send negative amount of money.");
        }
        return money;
    }

    private Transfer initializeTransfer(long toId, long fromId, BigDecimal amount, int status, int type) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom((long) fromId);
        transfer.setAccountFrom((long) toId);
        transfer.setAmount(amount);
        transfer.setTransferStatusId(status);
        transfer.setTransferTypeId(type);


        return transfer;
    }

}
