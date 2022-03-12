package Transactions;

import Accounts.Account;
import Tools.BanksException;

import java.util.UUID;

public class TopUp implements Operation {
    public double money;
    public Accounts.Account account;
    public boolean isCancelled;
    public String id;

    public TopUp(Account account, double money) throws BanksException {
        id = UUID.randomUUID().toString();
        if (money < 0)
            throw new BanksException("Incorrect input");
        this.money = money;
        this.account = account;
        account.changeBalance(this.money);
        isCancelled = false;
        account.transactions.add(this);
    }

    public void cancelOperation() throws BanksException {
        if (isCancelled)
            throw new BanksException("Operation has been already cancelled!");
        account.changeBalance(-money);
        isCancelled = true;
    }
}
