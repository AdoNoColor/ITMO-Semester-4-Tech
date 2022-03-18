package Transactions;

import Accounts.Account;
import Accounts.AccountType;
import Tools.BanksException;

import java.util.UUID;

public class Transferring implements Operation {
    public String Id;
    public Account AccountFrom;
    public Account AccountTo;
    public Double AmountOfMoney;
    public Boolean isCancelled;

    public Transferring(Account accountFrom, Account accountTo, Double money) throws BanksException {
        Id = UUID.randomUUID().toString();
        AccountFrom = accountFrom;
        AccountTo = accountTo;
        AmountOfMoney = money;
        isCancelled = false;
        if (AccountFrom.AccountType == AccountType.Credit)
            throw new BanksException("Cannot transfer money from that type of account!");

        AccountTo.changeBalance(AmountOfMoney);
        AccountFrom.changeBalance(-AmountOfMoney);
        accountFrom.transactions.add(this);
    }

    public void cancelOperation() throws BanksException {
        if (isCancelled) throw new BanksException("Operation has been already cancelled!");
        AccountTo.changeBalance(-AmountOfMoney);
        AccountFrom.changeBalance(AmountOfMoney);
        isCancelled = true;
    }
}
