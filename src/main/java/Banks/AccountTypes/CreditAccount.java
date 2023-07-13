package Banks.AccountTypes;

import Banks.ClientSystem.Client;
import Banks.Tools.AccountException;

import java.math.BigDecimal;

/**
 * класс кредитного аккаунта, реализующий методы абстрактного класса аккаунт
 */
public class CreditAccount extends Account {
    private BigDecimal accountCommission;

    /**
     * конструктор кредитного аккаунта
     * @param money - сумма на аккаунте
     * @param client - владелец
     * @param commission - комиссия аккаунта
     * @throws AccountException - исключение, которое бросается если комиссия меньше минимума или null
     */
    public CreditAccount(BigDecimal money, Client client, BigDecimal commission) throws AccountException{
        super(money, client);
        if (commission == null)
            throw AccountException.NullException();
        if (commission.compareTo(minMoney) < 0)
            throw AccountException.InvalidAccountMoneyException();

        accountCommission = commission;
    }

    /**
     * метод задает комиссию для кредитного аккаунта
     * @param accountCommission - комиссия, которую необходимо задать
     */
    public void setAccountCommission(BigDecimal accountCommission) {
        this.accountCommission = accountCommission;
    }

    /**
     * метод возвращает комиссию аккаунта
     * @return - комиссия аккаунта
     */
    public BigDecimal getAccountCommission() {
        return accountCommission;
    }

    /**
     * метод, который реализует пополнение денег на кредитный счет
     * @param money - сумма, которую нужно добавить
     */
    @Override
    public void updateMoney(BigDecimal money) throws AccountException{
        if (money == null)
            throw AccountException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw AccountException.InvalidAccountMoneyException();

        setAccountMoney(getAccountMoney().add(money));
    }

    /**
     * метод, который реализует снятие денег с кредитного счета
     * @param money - сумма, которую нужно снять
     */
    @Override
    public void withdrawMoney(BigDecimal money) throws AccountException {
        if (money == null)
            throw AccountException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw AccountException.InvalidAccountMoneyException();
        if (money.compareTo(getAccountMoney()) > 0)
            throw AccountException.InvalidWithdrawException();
        if (money.compareTo(getVerificationLimit()) > 0)
            throw AccountException.InvalidLimitException();

        if (getAccountMoney().compareTo(minMoney) < 0) {
            setAccountMoney(getAccountMoney().subtract(money.multiply(accountCommission)));
        } else {
            setAccountMoney(getAccountMoney().subtract(money));
        }
    }

    /**
     * метод, который реализует симуляцию до определенного дня
     * @param days - число дней, сколько нужно просимулировать
     * @return - возвращает сумму после симуляции времени
     */
    @Override
    public BigDecimal calculateMoney(int days) throws AccountException{
        if (days < minDaysToCalculate)
            throw AccountException.NullException();
        return getCalculateMoney();
    }
}
