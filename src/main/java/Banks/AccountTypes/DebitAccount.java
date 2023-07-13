package Banks.AccountTypes;

import Banks.ClientSystem.Client;
import Banks.Tools.AccountException;

import java.math.BigDecimal;

/**
 * класс дебетового аккаунта, реализующий методы абстрактного класса аккаунт
 */
public class DebitAccount extends Account {
    private BigDecimal accountPercent;

    /**
     * конструктор дебетового аккаунта
     * @param money - сумма на счету
     * @param client - владелец
     * @param percent - процентная ставка
     * @throws AccountException - исключение, которое бросается в случае если процент меньше минимума или null
     */
    public DebitAccount(BigDecimal money, Client client, BigDecimal percent) throws AccountException{
        super(money, client);
        if (percent == null)
            throw AccountException.NullException();
        if (percent.compareTo(minPercent) < 0)
            throw AccountException.InvalidPercent();

        accountPercent = percent;
    }

    /**
     * метод, который задет процент дебетового аккаунта
     * @param accountPercent - дебетовый процент
     */
    public void setAccountPercent(BigDecimal accountPercent) {
        this.accountPercent = accountPercent;
    }

    /**
     * метод, который возвращает процентный остаток
     * @return - процентный остаток
     */
    public BigDecimal getAccountPercent() {
        return accountPercent;
    }

    /**
     * метод, который реализует пополнение денег на дебетовый счет
     * @param money - сумма, которую нужно добавить
     */
    @Override
    public void updateMoney(BigDecimal money) throws AccountException {
        if (money == null)
            throw AccountException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw AccountException.InvalidAccountMoneyException();

        setAccountMoney(getAccountMoney().add(money));
    }

    /**
     * метод, который реализует снятие денег с дебетового счета
     * @param money - сумма, которую нужно снять
     */
    @Override
    public void withdrawMoney(BigDecimal money) throws AccountException{
        if (money == null)
            throw AccountException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw AccountException.InvalidAccountMoneyException();
        if (money.compareTo(getAccountMoney()) > 0)
            throw AccountException.InvalidAccountMoneyException();
        if (money.compareTo(getVerificationLimit()) > 0)
            throw AccountException.InvalidLimitException();

        setAccountMoney(getAccountMoney().subtract(money));
    }

    /**
     * метод, который реализует симуляцию до определенного дня
     * @param days - число дней, сколько нужно просимулировать
     * @return - возвращает сумму после симуляции времени
     */
    @Override
    public BigDecimal calculateMoney(int days) throws AccountException {
        if (days < minDaysToCalculate)
            throw AccountException.InvalidDaysException();

        BigDecimal yearPercent = accountPercent.divide(yearDays);
        BigDecimal calcMoney = getCalculateMoney();
        for (int i = 0; i<days; i++){
            calcMoney = calcMoney.add(calcMoney.add(yearPercent));
        }
        setCalculateMoney(calcMoney);
        return getCalculateMoney();
    }
}
