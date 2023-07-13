package Banks.Transactions;

import Banks.AccountTypes.Account;
import Banks.Tools.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * класс транзакции - снятие со счета
 */
public class WithdrawTransaction extends Transaction {
    /**
     * конструктор транзакции снятие со счета
     * @param sender - отправитель (и получатель)
     * @param money - сумма
     * @param time - время операции
     */
    public WithdrawTransaction(Account sender, BigDecimal money, LocalDateTime time){
        super(sender, money, time);
    }

    /**
     * метод, который реализует сняте со счета
     * @param money - сумма
     * @throws TransactionException - исключение, которое бросается, если сумма null
     */
    public void withdraw(BigDecimal money) throws TransactionException{
        if (money == null)
            throw TransactionException.NullException();
        getSender().withdrawMoney(money);
    }

    /**
     * метод, который реализует отмену операции
     */
    @Override
    public void cancelTransaction() throws TransactionException {
        if (getStatus())
            throw TransactionException.InvalidCancellingException();
        getSender().updateMoney(getMoney());
        setCancelled(true);
    }
}
