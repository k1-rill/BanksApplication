package Banks.BankSystem;

import Banks.Tools.BankException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * класс настроек банка
 */
public class BankSettings {
    private final BigDecimal minPercent = BigDecimal.valueOf(0);
    private final BigDecimal minMoneyLimit = BigDecimal.valueOf(0);
    private ArrayList<BigDecimal> depositPercentages;
    private BigDecimal debitPercent;
    private BigDecimal creditLimit;
    private BigDecimal creditCommission;
    private BigDecimal notVerifiedLimit;

    /**
     * конструктор настроек банка
     * @param debitPercent - дебетовый процент
     * @param depositPercentages - депозитные проценты
     * @param creditLimit - кредитный лимит
     * @param creditCommission - кредитная комиссия
     * @param notVerifiedLimit - лимит для не верифицированных аккаунтов
     * @throws BankException - исключение, которое бросается если какой то из параметров null
     */
    public BankSettings(
            BigDecimal debitPercent,
            ArrayList<BigDecimal> depositPercentages,
            BigDecimal creditLimit,
            BigDecimal creditCommission,
            BigDecimal notVerifiedLimit) throws BankException{
        if (debitPercent == null ||
        depositPercentages == null ||
        creditLimit == null ||
        notVerifiedLimit == null)
            throw BankException.NullException();
        if (checkSettingsOnLimits(debitPercent, depositPercentages, creditLimit, creditCommission, notVerifiedLimit))
        {
            throw BankException.InvalidPercentException();
        }
        if (depositPercentages.get(0).compareTo(depositPercentages.get(1)) >= 0 ||
        depositPercentages.get(1).compareTo(depositPercentages.get(2)) >= 0){
            throw BankException.InvalidDepositPercentException();
        }
        this.debitPercent = debitPercent;
        this.creditCommission = creditCommission;
        this.creditLimit = creditLimit;
        this.depositPercentages = new ArrayList<BigDecimal>();
        this.notVerifiedLimit = notVerifiedLimit;
    }
    /**
     * метод, который задает депозитные проценты
     * @param depositPercentages - депозитные проценты
     */
    public void setDepositPercentages(ArrayList<BigDecimal> depositPercentages) {
        this.depositPercentages = depositPercentages;
    }
    /**
     * метод, который задает дебетовый процент
     * @param debitPercent - дебетовый процент
     */
    public void setDebitPercent(BigDecimal debitPercent) {
        this.debitPercent = debitPercent;
    }
    /**
     * метод, который задает кредитный лимит
     * @param creditLimit - кредитный лимит
     */
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * метод, который задает кредитную комиссию аккаунта
     * @param creditCommission - кредитная комиссия
     */
    public void setCreditCommission(BigDecimal creditCommission) {
        this.creditCommission = creditCommission;
    }
    /**
     * метод, который задает лимит для не верифицированных аккаунтов
     * @param notVerifiedLimit - лимит аккаунта
     */
    public void setNotVerifiedLimit(BigDecimal notVerifiedLimit) {
        this.notVerifiedLimit = notVerifiedLimit;
    }

    /**
     * метод, который получает депозитный процент
     * @return - возвращает депозитный процент
     */
    public List<BigDecimal> getDepositPercentages() {
        return Collections.unmodifiableList(depositPercentages);
    }
    /**
     * метод, который получает не верифицированный лимит
     * @return -возвращает не верифицированный лимит
     */
    public BigDecimal getNotVerifiedLimit() {
        return notVerifiedLimit;
    }
    /**
     * метод, который получает кредитную комиссию
     * @return - возвращает кредитную комиссию
     */
    public BigDecimal getCreditCommission() {
        return creditCommission;
    }
    /**
     * метод, который получает кредитный лимит
     * @return - возвращает кредитный лимит
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }
    /**
     * метод, который получает дебетовый процент
     * @return - возвращает дебетовый процент
     */
    public BigDecimal getDebitPercent() {
        return debitPercent;
    }

    /**
     * метод, который изменяет значение дебетового процента
     * @param percent - дебетовый процент
     * @return - возвращает новый дебетовый процент
     * @throws BankException - исключение бросается, если процент null или меньше минимума
     */
    public BigDecimal changeDebitPercent(BigDecimal percent) throws BankException{
        if (percent == null)
            throw BankException.NullException();
        if (percent.compareTo(minMoneyLimit) < 0)
            throw BankException.InvalidPercentException();
        debitPercent = percent;
        return percent;
    }

    /**
     * метод, который проверяет все настройки банка, что они меньше минимума
     * @param debitPercent - дебетовый процент
     * @param depositPercentages - депозитные проценты
     * @param creditLimit - кредитный лимит
     * @param creditCommission - кредитная комиссия
     * @param notVerifiedLimit - не верифицированный лимит
     * @return - возвращает true если все значение больше минимума, иначе false
     */
    private boolean checkSettingsOnLimits(BigDecimal debitPercent,
                                          ArrayList<BigDecimal> depositPercentages,
                                          BigDecimal creditLimit,
                                          BigDecimal creditCommission,
                                          BigDecimal notVerifiedLimit){
        return debitPercent.compareTo(minPercent) < 0 ||
                depositPercentages.stream().anyMatch(c -> c.compareTo(minPercent) < 0) ||
                creditLimit.compareTo(minMoneyLimit) < 0 ||
                creditCommission.compareTo(minMoneyLimit) < 0 ||
                notVerifiedLimit.compareTo(minMoneyLimit) < 0;
    }
    /**
     * метод, который изменяет значение депозитных процентов
     * @param depositPercentages - депозитные проценты
     * @return - возвращает новый дебетовый процент
     * @throws BankException - исключение бросается, если проценты null или меньше минимума
     */
    public ArrayList<BigDecimal> changeDepositPercentages(ArrayList<BigDecimal> depositPercentages) throws BankException{
        if (depositPercentages == null)
            throw BankException.NullException();
        if (depositPercentages.stream().anyMatch(c -> c.compareTo(minPercent) < 0))
            throw BankException.InvalidDepositPercentException();
        this.depositPercentages = depositPercentages;
        return depositPercentages;
    }
    /**
     * метод, который изменяет значение кредитного лимита
     * @param money - кредитный лимит
     * @return - возвращает новый кредитный лимит
     * @throws BankException - исключение бросается, если лимит null или меньше минимума
     */
    public BigDecimal changeCreditCommission(BigDecimal money) throws BankException{
        if (money == null)
            throw BankException.NullException();
        if (money.compareTo(minMoneyLimit) < 0)
            throw BankException.InvalidPercentException();
        creditCommission = money;
        return money;
    }
}
