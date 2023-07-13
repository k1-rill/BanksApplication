package Banks.Tools;

/**
 * класс кастомных исключений банка
 */
public class BankException extends RuntimeException {
    /**
     * конструктор исключения
     * @param message - сообщение исключения
     */
    public BankException(String message){
        super(message);
    }
    /**
     * исключение, которое бросается если имя банка null или пустое
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidBankNameException() throws BankException {
        throw new BankException("bank name can't be null or empty");
    }
    /**
     * исключение, которое бросается если параметры банка null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidSettingsException() throws BankException {
        throw new BankException("settings can't be null ");
    }
    /**
     * исключение, которое бросается если не найден банк
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidBankFindException() throws BankException {
        throw new BankException("no such bank found");
    }
    /**
     * исключение, которое бросается если процент меньше минимума
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidPercentException() throws BankException {
        throw new BankException("percents can't be < 0");
    }
    /**
     * исключение, которое бросается если количетво дней меньше минимума
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidDaysException() throws BankException {
        throw new BankException("days can't be < 0");
    }
    /**
     * исключение, которое бросается если количество денег меньше минимума
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidMoneyException() throws BankException {
        throw new BankException("money can't be < 0");
    }
    /**
     * исключение, которое бросается если неверно введены депозитные проценты
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidDepositPercentException() throws BankException {
        throw new BankException("deposit percents are wrong");
    }
    /**
     * исключение, которое бросается не существует клиента в банке
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException InvalidClientExistenceException() throws BankException {
        throw new BankException("such client exist in bank");
    }
    /**
     * исключение, которое бросается если параметр null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static BankException NullException() throws BankException{
        throw new BankException("param is null");
    }
}
