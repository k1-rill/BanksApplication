package Banks.Transactions;

import Banks.AccountTypes.Account;
import Banks.Tools.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * класс транзакции - перевод денег
 */
public class TransferTransaction extends Transaction{
    private Account recipient;

    /**
     * конструктор транзакции перевод денег
     * @param sender - отправитель
     * @param recipient - получатель
     * @param money - сумма транзакции
     * @param time - время операции
     * @throws TransactionException - исключение бросается если параметры транзакции null
     */
    public TransferTransaction(Account sender, Account recipient, BigDecimal money, LocalDateTime time) throws TransactionException{
        super(sender, money, time);
        if (recipient == null)
            throw TransactionException.NullException();
        this.recipient = recipient;
    }
    /**
     * метод, который задает получателя операции
     * @param recipient - получатель
     */
    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    /**
     * метод, который возвращает получателя
     * @return - получатель
     */
    public Account getRecipient() {
        return recipient;
    }

    /**
     * метод, который реализует перевод денег
     * @param money - сумма транзакции
     * @throws TransactionException - исключение, которое бросается если сумма меньше минимальной
     */
    public void transfer(BigDecimal money) throws TransactionException{
        if (money == null)
            throw TransactionException.NullException();
        getSender().withdrawMoney(money);
        recipient.updateMoney(money);
    }

    /**
     * метод, который реализует отмену операции
     */
    @Override
    public void cancelTransaction() throws TransactionException {
        if (getStatus())
            throw TransactionException.InvalidCancellingException();
        getSender().updateMoney(getMoney());
        recipient.withdrawMoney(getMoney());
        setCancelled(true);
    }
}
