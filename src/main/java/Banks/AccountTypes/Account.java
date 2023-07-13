package Banks.AccountTypes;

import Banks.ClientSystem.Client;
import Banks.Tools.AccountException;
import Banks.Tools.ClientException;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * абстрактный класс, описывающий аккаунт
 */
public abstract class Account {
    protected final BigDecimal minMoney = BigDecimal.valueOf(0);
    protected final BigDecimal minPercent = BigDecimal.valueOf(0);
    protected final int minDaysToCalculate = 0;
    protected final BigDecimal yearDays = BigDecimal.valueOf(365);
    private Client accountClient;
    private BigDecimal accountMoney;
    private BigDecimal calculateMoney;
    private final UUID id;
    private boolean isVerified;
    private BigDecimal verificationLimit;

    /**
     * базовый конструктор абстрактного класса
     * @param money - начальная сумма на аккаунте
     * @param client - владелец аккаунта
     * @throws AccountException - исключение, которое бросается если money null или меньше нуля
     * @throws ClientException - исключение, которое бросается если client null
     */
    public Account(BigDecimal money, Client client) throws AccountException, ClientException{
        if (money == null)
            throw AccountException.NullException();
        if (client == null)
            throw ClientException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw AccountException.InvalidAccountMoneyException();

        accountClient = client;
        accountMoney = money;
        calculateMoney = money;
        id = UUID.randomUUID();
        isVerified = client.checkVerification();
        verificationLimit = accountMoney;
    }

    /**
     * метод, который задает аккаунту его статус верифицированости
     * @param verified - статус верификации аккаунта
     */
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    /**
     * метод, который задает аккаунту его сумму через время
     * @param calculateMoney - сумма через промежуток времени
     */
    public void setCalculateMoney(BigDecimal calculateMoney) {
        this.calculateMoney = calculateMoney;
    }
    /**
     * метод, который задает аккаунту сумму этого акканута
     * @param accountMoney - сумма на аккаунте
     */
    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }
    /**
     * метод, который задает аккаунту его владельца
     * @param accountClient - сумма через промежуток времени
     */
    public void setAccountClient(Client accountClient) {
        this.accountClient = accountClient;
    }

    /**
     * метод, который ставит аккаунту лимит, в случае если аккаунт не верифицирован
     * @param verificationLimit - максимальная сумма, которую можно снять
     */
    public void setVerificationLimit(BigDecimal verificationLimit) {
        this.verificationLimit = verificationLimit;
    }

    /**
     * метод, который получает значение лимита
     * @return - возвращает лимит аккаунта
     */
    public BigDecimal getVerificationLimit() {
        return verificationLimit;
    }

    /**
     * метод, который получает значение id
     * @return - возвращает id аккаунта
     */
    public UUID getId() {
        return id;
    }

    /**
     * метод, который возвращает верифицирован ли аккаунт
     * @return - возвращает статус аккаунта
     */
    public boolean getIsVerified() {
        return isVerified;
    }

    /**
     * метод, который возвращает сумму аккаунта через какой-то промежуток времени
     * @return - возвращает вычисленную сумму
     */
    public BigDecimal getCalculateMoney() {
        return calculateMoney;
    }

    /**
     * метод, который возвращает сумму аккаунта
     * @return - возвращает сумму на аккаунта
     */
    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    /**
     * метод, который возвращает владельца аккаунта
     * @return - владелец аккаунта
     */
    public Client getAccountClient() {
        return accountClient;
    }

    /**
     * метод, который добавляет деньги на счет
     * @param money - сумма, которую нужно прибавить
     */
    public abstract void updateMoney(BigDecimal money);

    /**
     * метод, который снимает деньги со счета
     * @param money - сумма, которую необходимо снять
     */
    public abstract void withdrawMoney(BigDecimal money);

    /**
     * метод, который вычисляет накопленную сумму за определенный период
     * @param days - период времени, за который нужно узнать сумму
     * @return - возвращает сумму через какое - то время
     */
    public abstract BigDecimal calculateMoney(int days);
}
