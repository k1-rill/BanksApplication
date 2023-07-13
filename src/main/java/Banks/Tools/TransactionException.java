package Banks.Tools;

/**
 * класс кастомных исключений транзакций
 */
public class TransactionException extends RuntimeException{
    /**
     * конструктор исключения
     * @param message - сообщение исключения
     */
    public TransactionException(String message) {
        super(message);
    }

    /**
     * конструктор исключения
     * @return - возвращаемое исключение
     * @throws TransactionException - бросаемое исключение
     */
    public static TransactionException InvalidMoneyException() throws TransactionException {
        throw new TransactionException("money can't be <0");
    }
    /**
     * исключение бросается если транзакцию, которую хотели отменить уже отменена
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */

    public static TransactionException InvalidCancellingException() throws TransactionException {
        throw new TransactionException("transaction is already cancelled");
    }
    /**
     * исключение, которое бросается если не найдена транзакция
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */

    public static TransactionException InvalidTransactionFindException() throws TransactionException {
        throw new TransactionException("no such transaction found");
    }
    /**
     * исключение, которое бросается если параметр клиента null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static TransactionException NullException() throws TransactionException{
        throw new TransactionException("param is null");
    }
}
