package Accounts;

import Banks.Bank;
import Clients.Client;
import Tools.BanksException;

import java.time.LocalDate;
import java.time.Period;

public class Debit extends Account {
    public Debit(Bank bank, Client client){
        super(bank, client);
        AccountType = Accounts.AccountType.Debit;
    }

    public void changeBalance(Double amountOfMoney) throws BanksException {
        Balance += amountOfMoney;
        if (Balance < 0)
            throw new BanksException("You have reached the minus!");
    }

    public void spinTimeMechanism(LocalDate oldDate, LocalDate newDate) throws BanksException {
        changeBalance(Balance * Period.between(oldDate, newDate).getDays() * AttachedBank.percent);
        Time = newDate;
    }

    public void setExpirationDate(LocalDate dateTime) throws BanksException {
        throw new BanksException("Not this type of an account!");
    }
}
