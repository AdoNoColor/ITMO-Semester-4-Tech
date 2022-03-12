package Accounts;

import Banks.Bank;
import Clients.Client;
import Tools.BanksException;

import java.time.LocalDate;
import java.time.Period;

public class Credit extends Account {
    public Credit(Bank bank, Client client)
    {
        super(bank, client);
        AccountType = Accounts.AccountType.Credit;
        Limit = AttachedBank.creditLimit;
    }

    public double Limit;

    public void changeBalance(double amountOfMoney) throws BanksException {
        if (amountOfMoney > AttachedBank.trustLimit && !AttachedClient.levelOfTrust)
            throw new BanksException("Incorrect input for Credit account!");
        if (Balance < 0)
        {
            Balance += amountOfMoney - (amountOfMoney * AttachedBank.commission);
        }

        Balance += amountOfMoney;
        if (Balance < -Limit || Balance > Limit)
            throw new BanksException("You have reached the limit!");
    }

    public void spinTimeMechanism(LocalDate oldDate, LocalDate newDate) throws BanksException {
        changeBalance(Balance * AttachedBank.commission * Period.between(oldDate, newDate).getDays());
        Time = newDate;
    }

    public void setExpirationDate(LocalDate dateTime) throws BanksException {
        throw new BanksException("Not this type of an account!");
    }
}
