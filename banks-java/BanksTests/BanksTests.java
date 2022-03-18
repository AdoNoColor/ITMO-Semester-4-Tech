package BanksTests;

import Accounts.Account;
import Banks.Bank;
import Banks.CentralBank;
import Clients.Client;
import Tools.BanksException;
import Transactions.Operation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BanksTests {
    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll executed");
    }


    @Test
    public void CreatingClientsAndAccount() {
        Bank sberbank = CentralBank.getCentralBank().registerBank("Sberbank", 5000.0, 0.3, 0.1, 50000.0, 300000.0);
        sberbank.clientBuilder.setName("Maxim");
        sberbank.clientBuilder.setSurname("Ivanov");
        sberbank.clientBuilder.setPassportNumber("3013 456969");
        sberbank.clientBuilder.setAddress("Kronversky pr., d. 49");
        Client max = sberbank.clientBuilder.getClient();
        Account account = sberbank.addAccount(max, 1);
        Assertions.assertTrue(sberbank.accounts.contains(account));
        Assertions.assertTrue(sberbank.clients.contains(max));
        Assertions.assertTrue(CentralBank.getCentralBank().getBanks().contains(sberbank));
    }

    @Test
    public void Transactions() {
        Bank tinkoff = CentralBank.getCentralBank().registerBank("Tinkoff", 5000.0, 0.3, 0.1, 50000.0, 300000.0);
        Bank sberbank = CentralBank.getCentralBank().registerBank("Sberbank", 5000.0, 0.3, 0.1, 50000.0, 300000.0);

        sberbank.clientBuilder.setName("Maxim");
        sberbank.clientBuilder.setSurname("Ivanov");
        sberbank.clientBuilder.setPassportNumber("3013 456969");
        sberbank.clientBuilder.setAddress("Kronversky pr., d. 49");
        Client max = sberbank.clientBuilder.getClient();

        tinkoff.clientBuilder.setName("Josh");
        tinkoff.clientBuilder.setSurname("Hopkins");
        tinkoff.clientBuilder.setPassportNumber("1234234");
        tinkoff.clientBuilder.setAddress("Hollywood");
        Client josh = tinkoff.clientBuilder.getClient();

        Account maxDebitAccount = sberbank.addAccount(max, 0);
        Account maxCreditAccount = sberbank.addAccount(max, 1);
        Account maxDepositAccount = sberbank.addAccount(max, 0);
        Account joshDebitAccount = tinkoff.addAccount(josh, 1);

        CentralBank.getCentralBank().topUpBalance(maxDebitAccount, 300000.0);
        Assertions.assertEquals(300000.0, maxDebitAccount.Balance);

        CentralBank.getCentralBank().withdrawMoney(maxDebitAccount, 5000.0);
        Assertions.assertEquals(295000, maxDebitAccount.Balance);

        Assertions.assertThrows(BanksException.class, () -> CentralBank.getCentralBank().withdrawMoney(maxCreditAccount, 5000.0));
        Assertions.assertThrows(BanksException.class, () -> CentralBank.getCentralBank().withdrawMoney(maxDepositAccount, 5000.0));

        CentralBank.getCentralBank().transferMoney(maxDebitAccount, joshDebitAccount, 3000.0);
        Assertions.assertEquals(3000, joshDebitAccount.Balance);
        Assertions.assertEquals(292000, maxDebitAccount.Balance);
    }

    @Test
    public void CancellingTransaction() {
        Bank tinkoff = CentralBank.getCentralBank().registerBank("Tinkoff", 5000.0, 0.3, 0.1, 50000.0, 300000.0);
        Bank sberbank = CentralBank.getCentralBank().registerBank("Sberbank", 5000.0, 0.3, 0.1, 50000.0, 300000.0);
        sberbank.clientBuilder.setName("Maxim");
        sberbank.clientBuilder.setSurname("Ivanov");
        sberbank.clientBuilder.setPassportNumber("3013 456969");
        sberbank.clientBuilder.setAddress("Kronversky pr., d. 49");
        Client max = sberbank.clientBuilder.getClient();
        Account maxDebitAccount = sberbank.addAccount(max, 0);

        tinkoff.clientBuilder.setName("Josh");
        tinkoff.clientBuilder.setSurname("Hopkins");
        tinkoff.clientBuilder.setPassportNumber("1234234");
        tinkoff.clientBuilder.setAddress("Hollywood");
        Client josh = tinkoff.clientBuilder.getClient();
        Account joshDebitAccount = tinkoff.addAccount(josh, 1);

        Operation topUpOperation = CentralBank.getCentralBank().topUpBalance(maxDebitAccount, 300000.0);
        CentralBank.getCentralBank().cancelOperation(topUpOperation);
        Assertions.assertThrows(BanksException.class, () -> CentralBank.getCentralBank().cancelOperation(topUpOperation));

        CentralBank.getCentralBank().topUpBalance(maxDebitAccount, 300000.0);
        Operation withdrawOperation = CentralBank.getCentralBank().withdrawMoney(maxDebitAccount, 100.0);
        CentralBank.getCentralBank().cancelOperation(withdrawOperation);
        Assertions.assertThrows(BanksException.class, () -> CentralBank.getCentralBank().cancelOperation(topUpOperation));

        Operation transferOperation = CentralBank.getCentralBank().transferMoney(maxDebitAccount, joshDebitAccount, 100.0);
        Assertions.assertEquals(100, joshDebitAccount.Balance);
        CentralBank.getCentralBank().cancelOperation(transferOperation);
        Assertions.assertEquals(0, joshDebitAccount.Balance);
    }

    @Test
    public void Notifications(){
        Bank sberbank = CentralBank.getCentralBank().registerBank("Sberbank", 5000.0, 0.3, 0.1, 50000.0, 300000.0);
        sberbank.clientBuilder.setName("Maxim");
        sberbank.clientBuilder.setSurname("Ivanov");
        sberbank.clientBuilder.setPassportNumber("3013 456969");
        sberbank.clientBuilder.setAddress("Kronversky pr., d. 49");
        Client max = sberbank.clientBuilder.getClient();
        sberbank.addToNotificationsList(max);
        Assertions.assertTrue(sberbank.notifications.subscribers.contains(max));
    }

    @AfterAll
    static void tear() {
        System.out.println("@AfterAll executed");
        System.out.println("All tests have successfully passed!");
    }
}
