package Banks.BankSystem;

import Banks.AccountTypes.Account;
import Banks.Tools.AccountException;
import Banks.Tools.BankException;
import Banks.Tools.TransactionException;
import Banks.Transactions.Transaction;
import Banks.Transactions.TransferTransaction;

import javax.sound.midi.Track;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * класс центрального банка
 */
public class CentralBank {
    private final int minDays = 0;
    private final BigDecimal minMoney = BigDecimal.valueOf(0);
    private static CentralBank instance;
    private static final ArrayList<Bank> banks = new ArrayList<Bank>();
    private static final ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public CentralBank(){ }
    public static CentralBank getInstance(){
        if (instance == null)
            return new CentralBank();
        return instance;
    }

    /**
     * метод, который находит транзакцию по заданному id
     * @param id - id транзакции
     * @return - возвращает найденную транзакцию
     * @throws TransactionException - исключение бросается если транзакция не найдена или id null
     */
    public Transaction findTransaction(UUID id) throws TransactionException{
        if (id == null)
            throw TransactionException.NullException();
        return transactions.stream().filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(TransactionException::InvalidTransactionFindException);
    }

    /**
     * метод, который возвращает все банки в центральном
     * @return - банки, которые зарегистрированы
     */
    public List<Bank> getBanks(){
        return Collections.unmodifiableList(banks);
    }
    /**
     * метод, который возвращает все транзакции в центральном
     * @return - транзакции, которые зарегистрированы
     */
    public List<Transaction> getTransactions(){
        return Collections.unmodifiableList(transactions);
    }

    /**
     * метод, который ищет банк по названию
     * @param name - название банка
     * @return - возвращает найденный банк
     * @throws BankException - исключение бросается если банк не найден, имя банка null или пустое
     */
    public Bank findBankByName(String name) throws BankException{
        if (name == null || name.isBlank())
            throw TransactionException.NullException();
        return banks.stream().filter(b -> b.getName() == name)
                .findFirst()
                .orElseThrow(TransactionException::InvalidTransactionFindException);
    }

    /**
     * метод, который регистрирует банк в центральном
     * @param name - название банка
     * @param settings - настройки банка
     * @return - возвращает добавленный банк
     * @throws BankException - исключение бросается если имя пустое или null или настройки банка null
     */
    public Bank registerBank(String name, BankSettings settings) throws BankException{
        if (name == null || settings == null || name.isBlank())
            throw BankException.NullException();
        Bank bank = new Bank(name, settings);
        banks.add(bank);
        return bank;
    }
    /**
     * метод, который регистрирует транзакцию в центральном банке
     * @param transaction - транзакция
     * @return - возвращает добавленную транзакцию
     * @throws BankException - исключение бросается если транзакция null
     */
    public Transaction registerTransaction(Transaction transaction) throws TransactionException{
        if (transaction == null)
            throw TransactionException.NullException();
        transactions.add(transaction);
        return transaction;
    }

    /**
     * метод, который реализует перевод денег с одного счета на другой
     * @param money - сумма перевода
     * @param sender - отправитель
     * @param recipient - получатель
     * @return - возвращает произведенную транзакцию
     * @throws BankException - исключение бросается если сумма перевода меньше минимума
     * @throws AccountException - исключение бросается если сумма перевода больше верифицированного лимита
     * @throws TransactionException - исключение бросается если хотя бы один из входных данных null
     */
    public Transaction transferMoney(BigDecimal money, Account sender, Account recipient) throws TransactionException, BankException, AccountException{
        if (money == null || sender == null || recipient == null)
            throw TransactionException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw BankException.InvalidMoneyException();
        if (money.compareTo(sender.getVerificationLimit()) > 0)
            throw AccountException.InvalidLimitException();
        TransferTransaction transfer = new TransferTransaction(sender, recipient, money, LocalDateTime.now());
        registerTransaction(transfer);
        sender.withdrawMoney(money);
        recipient.updateMoney(money);
        return transfer;
    }

    /**
     * метод, который реализует отмену транзакции
     * @param id - id транзакции
     * @throws TransactionException - исключение бросается если id null или не существует такой транзакции
     */
    public void cancelTransaction(UUID id) throws TransactionException {
        if (id == null)
            throw TransactionException.NullException();
        Transaction transaction = findTransaction(id);
        if (transaction == null)
            throw TransactionException.InvalidTransactionFindException();
        transaction.cancelTransaction();
    }

    /**
     * метод, который симулирует время до определенного дня
     * @param account - аккаунт, для которого необходимо посчитать сумму
     * @param days - количество дней симуляции
     * @throws TransactionException - исключение бросается если аккаунт null
     * @throws BankException - исключение бросается если количество дней меньше минимума
     */
    public void simulateDays(Account account, int days) throws TransactionException, BankException{
        if (account == null)
            throw TransactionException.NullException();
        if (days < minDays)
            throw BankException.InvalidDaysException();
        account.calculateMoney(days);
    }
}
