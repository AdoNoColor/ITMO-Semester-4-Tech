package Banks;

import Accounts.Account;
import Tools.BanksException;
import Transactions.Operation;
import Transactions.TopUp;
import Transactions.Transferring;
import Transactions.Withdrawal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private static CentralBank centralBank;
    private static final List<Bank> banks = new ArrayList<>();
    private static LocalDate currentDate;

    public static synchronized CentralBank getCentralBank() {
        if (centralBank == null) {
            centralBank = new CentralBank();
        }

        return centralBank;
    }

    private CentralBank(){
        currentDate = LocalDate.now();
    }

    public Bank registerBank(String name, double limit, double percent, double commission, double trustLimit, double creditLimit) throws BanksException {
        var bank = new Bank(name, limit, percent, commission, trustLimit, creditLimit);

        for(Bank anotherBank: banks){
            if (bank == anotherBank)
                throw new BanksException("Bank like this exists!");
        }

        banks.add(bank);
        return bank;
    }
    public List<Bank> getBanks() {
        return banks;
    }

    public LocalDate getDate() {
        return currentDate;
    }

    public void spinTime(LocalDate newDate) throws BanksException {
        if (newDate == currentDate)
            throw new BanksException("The same date!");
        if (newDate.isBefore(currentDate))
            throw new BanksException("Lost in the past!");

        if (currentDate.getMonth() == newDate.getMonth()) return;

        for (Bank bank: banks) {
            for (Account account: bank.accounts) account.spinTimeMechanism(currentDate, newDate);
        }

        currentDate = newDate;
    }


    public Operation transferMoney(Account accountFrom, Account accountTo, double amountOfMoney) throws BanksException {
        return new Transferring(accountFrom, accountTo, amountOfMoney);
    }

    public Operation withdrawMoney(Account account, double amountOfMoney) throws BanksException {
        return new Withdrawal(account, amountOfMoney);
    }

    public Operation topUpBalance(Account account, double amountOfMoney) throws BanksException {
        return new TopUp(account, amountOfMoney);
    }

    public void cancelOperation(Operation operation)
    {
        operation.cancelOperation();
    }
}

