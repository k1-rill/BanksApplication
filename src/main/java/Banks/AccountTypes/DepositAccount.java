package Banks.AccountTypes;

import Banks.ClientSystem.Client;
import Banks.Tools.AccountException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * класс депозитного аккаунта, реализующий методы абстрактного класса аккаунт
 */
public class DepositAccount extends Account{
    private BigDecimal accountPercent;
    private boolean isDeposit;
    private Date accountDuration;

    /**
     * конструктор депозитного аккаунта
     * @param money - сумма на счету
     * @param client - владелец
     * @param percent - процент счета
     * @param duration - длительность
     * @throws AccountException - исключение, которое бросается если параметры null
     */
    public DepositAccount(BigDecimal money, Client client, BigDecimal percent, Date duration) throws AccountException{
        super(money, client);
        if (percent == null)
            throw AccountException.NullException();
        if (percent.compareTo(minPercent) < 0)
            throw AccountException.InvalidPercent();

        accountDuration = duration;
        accountPercent = percent;
        isDeposit = true;
    }

    /**
     * метод, который задает длительность депозитного аккаунта
     * @param accountDuration - длительность аккаунта
     */
    public void setAccountDuration(Date accountDuration) {
        this.accountDuration = accountDuration;
    }

    /**
     * метод, который записывает, является ли аккаунт дебетовым
     * @param deposit - статус дебетового аккаунта
     */
    public void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    /**
     * метод, который задает депозитный процент
     * @param accountPercent - дебетовый процент аккаунта
     */
    public void setAccountPercent(BigDecimal accountPercent) {
        this.accountPercent = accountPercent;
    }

    /**
     * метод, который возвращает длительность счета
     * @return - длительность счета
     */
    public Date getAccountDuration() {
        return accountDuration;
    }
    /**
     * метод, который возвращает, является ли счет депозитным
     * @return - true - если счет еще открыт, false - если закрыт
     */
    public boolean isDeposit() {
        return isDeposit;
    }
    /**
     * метод, который возвращает процент счета
     * @return - процент счета
     */
    public BigDecimal getAccountPercent() {
        return accountPercent;
    }

    /**
     * метод, который реализует пополнение денег на дебетовый счет
     * @param money - сумма, которую нужно прибавить
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
     * метод, который реализует снятие денег с депозитного счета
     * @param money - сумма, которую нужно снять
     * @throws RuntimeException - исключение бросается всегда, так как снимать с дебетового счета нельзя
     */
    @Override
    public void withdrawMoney(BigDecimal money) throws RuntimeException {
        throw new RuntimeException("you can't withdraw money from deposit");
    }

    /**
     * метод, который реализует симуляцию до определенного дня
     * @param days - число дней, сколько нужно просимулировать
     * @return - возвращает сумму после симуляции времени
     */
    @Override
    public BigDecimal calculateMoney(int days) {
        if (days < minDaysToCalculate)
            throw AccountException.InvalidDaysException();

        BigDecimal calcMoney = getCalculateMoney();
        BigDecimal yearPercent = accountPercent.divide(yearDays);
        for (int i = 0; i<days; i++){
            calcMoney = calcMoney.add(calcMoney.add(yearPercent));
        }
        setCalculateMoney(calcMoney);
        return getCalculateMoney();
    }
}
