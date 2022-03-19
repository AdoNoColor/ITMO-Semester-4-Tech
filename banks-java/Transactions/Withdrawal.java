package Transactions;

import Accounts.Account;
import Accounts.AccountType;
import Tools.BanksException;

import java.util.UUID;

public class Withdrawal implements Operation {
    public String Id;
    public Accounts.Account Account;
    public Double AmountOfMoney;
    public Boolean isCancelled;

    public Withdrawal(Account account, Double amountOfMoney) throws BanksException {
        Id = UUID.randomUUID().toString();
        Account = account;
        AmountOfMoney = amountOfMoney;
        isCancelled = false;
        if (account.AccountType == AccountType.Credit)
            throw new BanksException("Incorrect input!");
        if (!Account.AttachedClient.levelOfTrust && Account.AttachedBank.depositLimit < amountOfMoney)
            throw new BanksException("Bank doesn't trust that client");
        Account.changeBalance(-AmountOfMoney);
        account.transactions.add(this);
    }

    public void cancelOperation() throws BanksException {
        if (isCancelled)
            throw new BanksException("Operation has been already cancelled!");
        Account.changeBalance(AmountOfMoney);
        isCancelled = true;
    }
}

