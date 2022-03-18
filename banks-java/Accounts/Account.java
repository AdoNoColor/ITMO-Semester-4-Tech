package Accounts;

import Banks.Bank;
import Banks.CentralBank;
import Clients.Client;
import Tools.BanksException;
import Transactions.Operation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {
    public Client AttachedClient;
    public Bank AttachedBank;
    public Double Balance;
    public LocalDate Time;
    public AccountType AccountType;
    public String Id;
    public List<Operation> transactions;


    public Account(Bank bank, Client client)
    {
        Id = UUID.randomUUID().toString();
        Balance = 0.0;
        transactions = new ArrayList<>();
        AttachedBank = bank;
        AttachedClient = client;
        Time = CentralBank.getCentralBank().getDate();
    }

    public abstract void changeBalance(Double amountOfMoney) throws BanksException;

    public abstract void spinTimeMechanism(LocalDate oldDate, LocalDate newDate) throws BanksException;

    public abstract void setExpirationDate(LocalDate dateTime) throws BanksException;

    public String getId()
    {
        return Id;
    }
}
