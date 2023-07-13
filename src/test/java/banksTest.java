import Banks.AccountTypes.Account;
import Banks.AccountTypes.DebitAccount;
import Banks.BankSystem.Bank;
import Banks.BankSystem.BankSettings;
import Banks.BankSystem.CentralBank;
import Banks.ClientSystem.Client;
import Banks.ClientSystem.ClientBuilder;
import Banks.Tools.AccountException;
import Banks.Tools.ClientException;
import Banks.Transactions.Transaction;
import Banks.Transactions.UpdateTransaction;
import Banks.Transactions.WithdrawTransaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class banksTest {
    private CentralBank centralBank = CentralBank.getInstance();

    @Test
    public void addBankAndClientTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(10000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.addName("name");
        clientBuilder.addSurname("surname");
        clientBuilder.addPassport("6000102030");
        clientBuilder.addAddress("street");
        Client cl = clientBuilder.makeClient();
        bank.addClient(cl);
        assertEquals(cl, bank.findClientByPassport(cl.getPassport()));
    }

    @Test
    public void createClientExceptionTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(10000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        assertThrows(ClientException.class, ()->
        {
            ClientBuilder clientBuilder = new ClientBuilder();
            clientBuilder.addName("name");
            clientBuilder.addSurname("surname");
            clientBuilder.addPassport("0");
            clientBuilder.addAddress("street");
            Client cl = clientBuilder.makeClient();
        });
    }

    @Test
    public void checkAccountCreationTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(10000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.addName("name");
        clientBuilder.addSurname("surname");
        clientBuilder.addPassport("6000102030");
        clientBuilder.addAddress("street");
        Client cl = clientBuilder.makeClient();
        bank.addClient(cl);

        Account acc = new DebitAccount(BigDecimal.valueOf(10000), cl, bankSettings.getDebitPercent());
        bank.addAccount(acc);
        assertEquals(acc, bank.getAccounts().get(0));
    }

    @Test
    public void transactionTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(10000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.addName("name");
        clientBuilder.addSurname("surname");
        clientBuilder.addPassport("6000102030");
        clientBuilder.addAddress("street");
        Client cl = clientBuilder.makeClient();
        bank.addClient(cl);

        Account acc = new DebitAccount(BigDecimal.valueOf(10000), cl, bankSettings.getDebitPercent());
        bank.addAccount(acc);
        UpdateTransaction tr1 = new UpdateTransaction(acc, BigDecimal.valueOf(1000), LocalDateTime.now());
        centralBank.registerTransaction(tr1);
        tr1.update(tr1.getMoney());
        WithdrawTransaction tr2 = new WithdrawTransaction(acc, BigDecimal.valueOf(5000), LocalDateTime.now());
        centralBank.registerTransaction(tr2);
        tr2.withdraw(tr2.getMoney());
        assertEquals(BigDecimal.valueOf(6000), acc.getAccountMoney());
    }

    @Test
    public void createNotVerifiedAccTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.addName("name");
        clientBuilder.addSurname("surname");
        Client cl = clientBuilder.makeClient();
        bank.addClient(cl);

        Account acc = new DebitAccount(BigDecimal.valueOf(10000), cl, bankSettings.getDebitPercent());
        bank.addAccount(acc);
        assertThrows(AccountException.class, ()->
        {
            WithdrawTransaction tr = new WithdrawTransaction(acc, BigDecimal.valueOf(6000), LocalDateTime.now());
            centralBank.registerTransaction(tr);
            tr.withdraw(tr.getMoney());
        });
    }

    @Test
    public void cancelTransactionTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.addName("name");
        clientBuilder.addSurname("surname");
        clientBuilder.addPassport("6000102030");
        clientBuilder.addAddress("street");
        Client cl = clientBuilder.makeClient();
        bank.addClient(cl);

        Account acc = new DebitAccount(BigDecimal.valueOf(10000), cl, bankSettings.getDebitPercent());
        bank.addAccount(acc);
        WithdrawTransaction tr = new WithdrawTransaction(acc, BigDecimal.valueOf(5000), LocalDateTime.now());
        centralBank.registerTransaction(tr);
        tr.withdraw(tr.getMoney());
        centralBank.cancelTransaction(tr.getId());
        assertEquals(BigDecimal.valueOf(10000), acc.getAccountMoney());
    }

    @Test
    public void notifyObserversTest(){
        ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
        list.add(BigDecimal.valueOf(2));
        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(4));
        BankSettings bankSettings = new BankSettings(BigDecimal.valueOf(1), list, BigDecimal.valueOf(10000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        Bank bank = centralBank.registerBank("sber", bankSettings);
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.addName("name");
        clientBuilder.addSurname("surname");
        clientBuilder.addPassport("6000102030");
        clientBuilder.addAddress("street");
        Client cl = clientBuilder.makeClient();
        bank.addClient(cl);
        bank.addObserver(cl);
        bank.changeDebitPercent(BigDecimal.valueOf(10));
        assertEquals("Banks setting: debit percent - updated to 10", cl.getNotifications().get(0));
    }
}