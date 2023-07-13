package Banks.Tools;

/**
 * класс кастомных исключений, связанных с аккаунтом
 */
public class AccountException extends RuntimeException {
    /**
     * конструктор исключения
     * @param message - сообщение исключения
     */
    public AccountException(String message){
        super(message);
    }

    /**
     * исключение, которое бросается если количество дней меньше минимума
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException InvalidDaysException() throws AccountException {
        throw new AccountException("days can't be <0");
    }
    /**
     * исключение, которое бросается если процент меньше минимума
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException InvalidPercent() throws AccountException {
        throw new AccountException("percent can't be <0");
    }
    /**
     * исключение, которое бросается если не найден нужный аккаунт
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException InvalidAccountFindException() throws AccountException {
        throw new AccountException("no such account found");
    }
    /**
     * исключение, которое бросается если пытаются снять деньги больше лимита
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException InvalidWithdrawException() throws AccountException {
        throw new AccountException("you try to withdraw money > than u have");
    }

    /**
     * исключение, которое бросается если колчество денег меньше минимума или недостаточно денег
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException InvalidAccountMoneyException() throws AccountException {
        throw new AccountException("money can't be < 0 or not enough money in account");
    }
    /**
     * исключение, которое бросается если пытаются снять деньги больше лимита для не верифицированных аккаунтов
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException InvalidLimitException() throws AccountException {
        throw new AccountException("u can't transfer or withdraw money > than Limit(not verified account)");
    }
    /**
     * исключение, которое бросается если параметр null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static AccountException NullException() throws AccountException{
        throw new AccountException("param is null");
    }
}
