package ConsoleInterface;

import Accounts.Account;
import Banks.Bank;
import Banks.CentralBank;
import Clients.Client;
import Tools.BanksException;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleInterface
{
    private Bank attachedBank;
    private final Scanner in = new Scanner(System.in);

    public void startApplication(Bank attachedBank) throws BanksException {
        System.out.flush();

        do {
            Client client = authorize();
            boolean exitMenuFlag = false;
            while (!exitMenuFlag) {
                menu();
                int option = in.nextInt();
                switch (option) {
                    case 1 -> {
                        subscribeToNotifications(client, attachedBank);
                        System.out.flush();
                    }
                    case 2 -> {
                        showAccounts(client);
                        System.out.flush();
                    }
                    case 3 -> {
                        registerAccount(client);
                        System.out.flush();
                    }
                    case 4 -> {
                        transferTransaction();
                        System.out.flush();
                    }
                    case 5 -> {
                        topUpTheBalance();
                        System.out.flush();
                    }
                    case 6 -> {
                        withdrawMoney();
                        System.out.flush();
                    }
                    case 7 -> {
                        exitMenuFlag = true;
                        System.out.print("Bye bye," + client.name + " " + client.surname);
                    }
                }
            }
        } while (true);
    }

    private void menu()
    {
        System.out.print("Welcome to" + attachedBank.name + "!");
        System.out.print("""
                1 - Subscribe to notifications
                2 - Show all Accounts
                3 - Register Account
                4 - Transfer money
                5 - Top Up Account
                6 - Withdraw Money
                7 - Exit
                Type the number:""");
    }

    private Client authorize()
    {
        System.out.print("Please type your name:");
        attachedBank.clientBuilder.setName(in.nextLine());
        System.out.print("Please type your surname:");
        attachedBank.clientBuilder.setSurname(in.nextLine());
        System.out.print("Please type your address:");
        attachedBank.clientBuilder.setAddress(in.nextLine());
        System.out.print("Please type your passport number:");
        attachedBank.clientBuilder.setPassportNumber(in.nextLine());
        System.out.print("Please type your name:");
        System.out.print("Thank you!:");
        return attachedBank.clientBuilder.getClient();
    }

    private void subscribeToNotifications(Client client, Bank attachedBank)
    {
        attachedBank.addToNotificationsList(client);
        System.out.print("You've successfully subscribed!");
    }

    private void showAccounts(Client client)
    {
        for (Account account: attachedBank.accounts) {
            if (account.AttachedClient == client) {
                System.out.print("Type:" + account.AccountType + "\n" + "Id:" + account.Id + "\n" + "Balance:" + account.Balance);
            }
        }
    }

    private void registerAccount(Client client)
    {
        System.out.print("What type of an account do you want?");
        System.out.print("""
                1 - Debit
                2 - Credit
                3 - Deposit
                """);
        int accountType = in.nextInt();
        Account account = attachedBank.addAccount(client, accountType);
        System.out.print("Account" + account.Id + "has been successfully created!");
    }

    private void transferTransaction() throws BanksException {
        Account accountFrom = null;
        Account accountTo = null;
        System.out.print("Please type the Id of your account:");
        String idFrom = in.nextLine();
        System.out.print("Please type the Id of an account in which you want to transfer your money:");
        String idTo = in.nextLine();
        System.out.print("Please type how much money you want to transfer:");
        int balance = in.nextInt();
        System.out.print("Does the another account belong to Our bank?");
        System.out.print("""
                1 - yes
                2 - no
                """);
        int decision = in.nextInt();
        switch (decision) {
            case 1 -> {
                for (Account account : attachedBank.accounts) {
                    if (Objects.equals(idFrom, account.Id))
                        accountFrom = account;
                    if (Objects.equals(idTo, account.Id))
                        accountTo = account;
                    if (accountFrom != null && accountTo != null)
                        CentralBank.getCentralBank().transferMoney(accountFrom, accountTo, balance);
                    System.out.print("Transaction was successful!");
                    break;
                }

                System.out.print("Something went wrong!");
            }
            case 2 -> {
                {
                    for (Bank bank : CentralBank.getCentralBank().getBanks()) {
                        for (Account account : attachedBank.accounts) {
                            if (Objects.equals(idFrom, account.Id))
                                accountFrom = account;
                            if (Objects.equals(idTo, account.Id))
                                accountTo = account;
                            System.out.print("Transaction was successful!");
                            if (accountFrom == null || accountTo == null) continue;
                            CentralBank.getCentralBank().transferMoney(accountFrom, accountTo, balance);
                            break;
                        }
                    }
                }

                System.out.print("Something went wrong!");
            }
        }
    }

    private void topUpTheBalance() throws BanksException {
        System.out.print("Please type the Id of your account:");
        String id = in.nextLine();
        System.out.print("How much money do you want to add?");
        int balance = in.nextInt();
        for (var account : attachedBank.accounts)
        {
            if (Objects.equals(account.Id, id)){
                CentralBank.getCentralBank().topUpBalance(account, balance);
                System.out.print("Transaction was successful!");
                return;
            }
        }

        System.out.print("Something went wrong!");
    }

    private void withdrawMoney() throws BanksException {
        System.out.print("Please type the Id of your account:");
        String id = in.next();
        System.out.print("How much money do you want to withdraw?");
        int balance = in.nextInt();
        for (var account : attachedBank.accounts) if (Objects.equals(account.Id, id))
        {
            if (Objects.equals(account.Id, id)){
                CentralBank.getCentralBank().withdrawMoney(account, balance);
                System.out.print("Transaction was successful!");
                return;
            }
        }

        System.out.print("Something went wrong!");
    }
}

