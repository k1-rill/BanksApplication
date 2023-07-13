package Banks.Transactions;

import Banks.AccountTypes.Account;
import Banks.Tools.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * класс транзакции - пополнение счета
 */
public class UpdateTransaction extends Transaction {
    /**
     * конструктор транзакции пополнение счета
     * @param sender - отправитель(он же и получатель)
     * @param money - сумма
     * @param time - время перевода
     */
    public UpdateTransaction(Account sender, BigDecimal money, LocalDateTime time){
        super(sender, money, time);
    }

    /**
     * метод, который реализует пополнение счета
     * @param money - сумма пополнения
     * @throws TransactionException - исключение, которое бросается если сумма null
     */
    public void update(BigDecimal money) throws TransactionException{
        if (money == null)
            throw TransactionException.NullException();
        getSender().updateMoney(money);
    }

    /**
     * метод, который реализует отмену операции
     */
    @Override
    public void cancelTransaction() throws TransactionException {
        if (getStatus())
            throw TransactionException.InvalidCancellingException();
        getSender().withdrawMoney(getMoney());
        setCancelled(true);
    }
}
