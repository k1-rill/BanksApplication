package Banks.BankSystem;

import Banks.AccountTypes.Account;
import Banks.ClientSystem.Client;
import Banks.Observer.Observable;
import Banks.Observer.Observer;
import Banks.Tools.AccountException;
import Banks.Tools.BankException;
import Banks.Tools.ClientException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * класс банка, реализующий методы интерфейса Observable
 */
public class Bank implements Observable {
    private final BigDecimal minMoney = BigDecimal.valueOf(0);
    private final ArrayList<Account> allAccounts;
    private final ArrayList<Client> allClients;
    private final ArrayList<Observer> observers = new ArrayList<Observer>();
    private String name;
    private BankSettings settings;
    private UUID id;

    /**
     * конструктор банка
     * @param name - имя банка
     * @param settings - параметры банка(кредитные лимиты, комиссии и тд)
     * @throws BankException - исключение, которое бросается если имя банка пустое или параметры банка null
     */
    public Bank(String name, BankSettings settings) throws BankException{
        if (name.isBlank())
            throw BankException.InvalidBankNameException();
        if (settings == null)
            throw BankException.InvalidSettingsException();
        this.name = name;
        this.settings = settings;
        this.id = UUID.randomUUID();
        this.allAccounts = new ArrayList<Account>();
        this.allClients = new ArrayList<Client>();
    }
    /**
     * метод, который задает название банка
     * @param name - название банка
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * метод, который задает параметры банка
     * @param settings - параметры банка
     */
    public void setSettings(BankSettings settings) {
        this.settings = settings;
    }
    /**
     * метод, который задает id банка
     * @param id - id банка
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * метод возвращает лист аккаунтов, которые есть в банке
     * @return - лист аккаунтов банка
     */
    public List<Account> getAccounts(){
        return Collections.unmodifiableList(allAccounts);
    }

    /**
     * метод возвращает лист клиентов, которые есть в банке
     * @return - лист клиентов банка
     */
    public List<Client> getClients(){
        return Collections.unmodifiableList(allClients);
    }
    /**
     * метод возвращает id банка
     * @return - id банка
     */
    public UUID getId() {
        return id;
    }
    /**
     * метод возвращает настройки банка
     * @return - настройки банка
     */
    public BankSettings getSettings() {
        return settings;
    }

    /**
     * метод возвращает название банка
     * @return - название банка
     */
    public String getName() {
        return name;
    }

    /**
     * метод, который проверяет наличие клиента в банке
     * @param client - клиент, которого необходимо найти
     * @return - возвращает true если клиент найдет, иначе false
     * @throws ClientException - исключение бросается если клиент null
     */
    public boolean checkClientExist(Client client) throws ClientException{
        if (client == null)
            throw ClientException.NullException();
        return allClients.stream().anyMatch(c -> c.getPassport() == client.getPassport());
    }

    /**
     * метод, который находит клиента в банке по паспорту
     * @param passport - паспорт клиента
     * @return - возвращает клиента если он найден иначе бросается исключение
     * @throws ClientException - исключение бросается если не найден клиент
     */
    public Client findClientByPassport(String passport) throws ClientException {
        return allClients.stream()
                .filter(c -> c.getPassport() == passport)
                .findFirst()
                .orElseThrow(ClientException::InvalidFindClient);
    }

    /**
     * метод возвращает аккаунт, который есть у клиента в банке
     * @param client - клиент, чей аккаунт нужно найти
     * @return - возвращает аккаунт клиента в банке
     * @throws AccountException - исключение бросается в случае, если не найден аккаунт у клиента в банке
     */
    public Account findAccountOwner(Client client) throws AccountException {
        return allAccounts.stream()
                .filter(c -> c.getAccountClient().getPassport() == client.getPassport())
                .findFirst()
                .orElseThrow(AccountException::InvalidAccountFindException);
    }

    /**
     * метод, который добавляет клиента в банк
     * @param client - клиент, которого необходимо добавить
     * @return - возвращаемое значение - клиент, которого добавился
     * @throws BankException - исключение бросается если клиент null или если уже существует такой клиент в банке
     */
    public Client addClient(Client client) throws BankException{
        if (client == null)
            throw BankException.NullException();
        if (checkClientExist(client))
            throw BankException.InvalidClientExistenceException();
        allClients.add(client);
        return client;
    }
    /**
     * метод, который добавляет аккаунт в банк
     * @param account - аккаунт, который необходимо добавить
     * @return - возвращаемое значение - аккаунт, который добавился
     * @throws BankException - исключение бросается если аккаунт null
     */
    public Account addAccount(Account account) throws AccountException{
        if (account == null)
            throw AccountException.NullException();
        if (!account.getIsVerified())
            account.setVerificationLimit(settings.getNotVerifiedLimit());
        allAccounts.add(account);
        return account;
    }

    /**
     * метод, который выбирает депозитный процент банка для аккаунта
     * @param money - депозитный процент
     * @return возвращаемое значение - депозитный процент, который назначен аккаунту
     * @throws AccountException - исключение бросается если процент null или меньше минимума
     * @throws BankException - исключение бросается если депозитный процент слишком большой
     */
    public BigDecimal chooseDepositPercentToAccount(BigDecimal money) throws AccountException, BankException{
        if (money == null)
            throw AccountException.NullException();
        if (money.compareTo(minMoney) < 0)
            throw AccountException.NullException();
        List<BigDecimal> depPercents = settings.getDepositPercentages();
        if (money.compareTo(depPercents.get(0)) < 0)
            return depPercents.get(0);
        if (money.compareTo(depPercents.get(1)) < 0)
            return depPercents.get(1);
        if (money.compareTo(depPercents.get(2)) < 0)
            return depPercents.get(2);
        throw BankException.InvalidDepositPercentException();
    }

    /**
     * метод, который изменяет дебетовый процент банка
     * @param percent - дебетовый процент, на который нужно поменять значение
     * @return - возвращает измененные настройки банка
     * @throws BankException - исключение, которое бросается если процент null
     */
    public BankSettings changeDebitPercent(BigDecimal percent) throws BankException{
        if (percent == null)
            throw BankException.NullException();
        settings.changeDebitPercent(percent);
        notify("debit percent", percent);
        return settings;
    }

    /**
     * метод, который меняет депозитные проценты банка
     * @param newDeposit - депозитные проценты, на которые нужно поменять текущие
     * @return - возвращает измененные настройки банка
     * @throws BankException - исключение бросается если проценты null
     */
    public BankSettings changeDepositSettings(ArrayList<BigDecimal> newDeposit) throws BankException{
        if (newDeposit == null)
            throw BankException.NullException();
        settings.changeDepositPercentages(newDeposit);
        for (BigDecimal percent: newDeposit){
            notify("deposit percent", percent);
        }
        return settings;
    }
    /**
     * метод, который меняет кредитную комиссию банка
     * @param newCommission - новая комиссия, на которую нужно поменять текущую
     * @return - возвращает измененные настройки банка
     * @throws BankException - исключение бросается если комиссия null
     */
    public BankSettings changeCreditSettings(BigDecimal newCommission) throws BankException{
        if (newCommission == null)
            throw BankException.NullException();
        settings.changeCreditCommission(newCommission);
        notify("credit commission", newCommission);
        return settings;
    }

    /**
     * метод, который добавляет наблюдателя в список
     * @param observer - наблюдатель
     * @throws BankException - исключение бросается если наблюдатель null
     */
    public void addObserver(Observer observer) throws BankException{
        if (observer == null)
            throw BankException.NullException();
        observers.add(observer);
    }
    /**
     * метод, который удаляет наблюдателя из списка
     * @param observer - наблюдатель
     * @throws BankException - исключение бросается если наблюдатель null
     */
    public void removeObserver(Observer observer) throws BankException{
        if (observer == null)
            throw BankException.NullException();
        observers.remove(observer);
    }

    /**
     * метод, который уведомляет наблюдателей об изменениях в банке
     * @param message - сообщение, которое получат все наблюдатели
     * @param newLimit - изменения в банке, которые произошли
     */
    public void notify(String message, BigDecimal newLimit){
        observers.forEach(observer -> observer.update("Banks setting: " + message + " - updated to " + newLimit));
    }
}
