package Banks.Transactions;

import Banks.AccountTypes.Account;
import Banks.Tools.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * класс транзакция
 */
public abstract class Transaction {
    private final BigDecimal minMoney = BigDecimal.valueOf(0);
    private Account sender;
    private BigDecimal money;
    private LocalDateTime transactionTime;
    private UUID id;
    private boolean isCancelled;

    /**
     * конструктор транзакции
     * @param sender - отправитель
     * @param money - получатель
     * @param time - время транзакции
     */
    protected Transaction(Account sender, BigDecimal money, LocalDateTime time){
        if (sender == null ||
        money == null ||
        time == null)
            throw TransactionException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw TransactionException.InvalidMoneyException();
        this.sender = sender;
        this.id = UUID.randomUUID();
        this.money = money;
        this.transactionTime = time;
        isCancelled = false;
    }

    /**
     * метод, который задает отправителя операции
     * @param sender - отправитель
     */
    public void setSender(Account sender) {
        this.sender = sender;
    }
    /**
     * метод, который задает сумму операции
     * @param money - сумма операции
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    /**
     * метод, который задает время транзакции
     * @param transactionTime - время транзакции
     */
    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
    /**
     * метод, который задает id операции
     * @param id - id операции
     */
    public void setId(UUID id) {
        this.id = id;
    }
    /**
     * метод, который задает статус операции
     * @param cancelled - статус операции
     */
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    /**
     * метод, который возвращает статус транзакции
     * @return - возвращаемый статус транзакции
     */
    public boolean getStatus() {
        return isCancelled;
    }
    /**
     * метод, который возвращает id транзакции
     * @return - возвращаемый id транзакции
     */
    public UUID getId() {
        return id;
    }
    /**
     * метод, который возвращает время транзакции
     * @return - возвращаемое время транзакции
     */
    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
    /**
     * метод, который возвращает сумму транзакции
     * @return - возвращаемая сумма транзакции
     */
    public BigDecimal getMoney() {
        return money;
    }
    /**
     * метод, который возвращает отправителя транзакции
     * @return - возвращаемый отправителя транзакции
     */
    public Account getSender() {
        return sender;
    }

    /**
     * абстрактный метод, который реализуется наследниками для отмены операции
     */
    public abstract void cancelTransaction();
}
