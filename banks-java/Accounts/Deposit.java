package Accounts;

import Banks.Bank;
import Clients.Client;
import Tools.BanksException;

import java.time.LocalDate;
import java.time.Period;

public class Deposit extends Account {
    public LocalDate ExpirationDate;
    public double Percent;
    private boolean FirstReplenishment;


    public Deposit(Bank bank, Client client) {
        super(bank, client);
        FirstReplenishment = false;
        AccountType = Accounts.AccountType.Deposit;
    }

    public void setExpirationDate(LocalDate expirationDate) throws BanksException {
        if (expirationDate.isBefore(Time))
            throw new BanksException("Incorrect input");
        ExpirationDate = expirationDate;
    }

    public void changeBalance(double amountOfMoney) throws BanksException
    {
        if (!FirstReplenishment)
        {
            setPercent(amountOfMoney);
            FirstReplenishment = true;
        }

        if (amountOfMoney < 0 && Time.isBefore(ExpirationDate))
            throw new BanksException("The deposit hasn't been expired!");
        Balance += amountOfMoney;
    }


    public void spinTimeMechanism(LocalDate oldDate, LocalDate newDate) throws BanksException {
        changeBalance(Balance * Period.between(oldDate, newDate).getDays() * Percent);
        Time = newDate;
    }

    private void setPercent(double money)
    {
        if (money < 50000)
        {
            Percent = AttachedBank.percent * 0.07;
            return;
        }

        if (money >= 50000 && money < 100000)
        {
            Percent = AttachedBank.percent * 0.14;
            return;
        }

        if (money >= 100000)
        {
            Percent = AttachedBank.percent * 0.21;
        }
    }
}
