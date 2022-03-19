package Banks;

import Accounts.Account;
import Accounts.Credit;
import Accounts.Debit;
import Accounts.Deposit;
import Clients.Client;
import Clients.ClientBuilder;
import Notifications.ClientNotifications;
import Tools.BanksException;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    public ClientNotifications notifications;
    public List<Client> clients = new ArrayList<>();
    public List<Account> accounts = new ArrayList<>();
    public String name;
    public Double depositLimit;
    public Double percent;
    public Double commission;
    public Double trustLimit;
    public Double creditLimit;
    public Clients.ClientBuilder clientBuilder = new ClientBuilder();

    public Bank(String name, double limit, double percent, double commission, double trustLimit, double creditLimit)
    {
        notifications = new ClientNotifications(this);
        this.name = name;
        this.depositLimit = limit;
        this.percent = percent;
        this.commission = commission;
        this.trustLimit = trustLimit;
        this.creditLimit = creditLimit;
    }

    public void changeTrustLimit(Double trustLimit)
    {
        this.trustLimit = trustLimit;
        notifications.handleEvent();
    }

    public void changePercent(Double percent)
    {
        this.percent = percent;
        notifications.handleEvent();
    }

    public void changeDepositLimit(Double depositLimit)
    {
        this.depositLimit = depositLimit;
        notifications.handleEvent();
    }

    public void changeCreditLimit(Double creditLimit)
    {
        this.creditLimit = creditLimit;
        notifications.handleEvent();
    }

    public void addToNotificationsList(Client client){
        notifications.addSubscriber(client);
    }

    public Account addAccount(Client client, int accountType) {
        if (!clients.contains(client)){
            clients.add(client);
        }


        switch (accountType) {
            case 0 -> {
                var account = new Debit(this, client);
                accounts.add(account);
                return account;
            }
            case 1 -> {
                var account = new Credit(this, client);
                accounts.add(account);
                return account;
            }
            case 2 -> {
                var account = new Deposit(this, client);
                accounts.add(account);
                return account;
            }
            default -> throw new BanksException("Type of an account does not exist!");
        }
    }
}
