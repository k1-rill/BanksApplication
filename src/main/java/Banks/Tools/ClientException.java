package Banks.Tools;

/**
 * класс кастомных исключений клиента
 */
public class ClientException extends RuntimeException{
    /**
     * конструктор исключения
     * @param message - сообщение исключения
     */
    public ClientException(String message){
        super(message);
    }
    /**
     * исключение, которое бросается если имя клиента null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException InvalidClientName() throws ClientException {
        throw new ClientException("client name is null or empty");
    }
    /**
     * исключение, которое бросается если не найден клиент в банке
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException InvalidFindClient() throws ClientException {
        throw new ClientException("can't find client");
    }
    /**
     * исключение, которое бросается если уведомление клиента null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException InvalidNotificationMessage() throws ClientException {
        throw new ClientException("message is null or empty");
    }
    /**
     * исключение, которое бросается если фамилия клиента null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException InvalidClientSurname() throws ClientException {
        throw new ClientException("client surname is null or empty");
    }
    /**
     * исключение, которое бросается если адрес клиента null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException InvalidClientAddress() throws ClientException {
        throw new ClientException("client address is null or whitespace");
    }
    /**
     * исключение, которое бросается если паспорт клиента null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException InvalidClientPassport() throws ClientException {
        throw new ClientException("client passport is null or whitespace");
    }
    /**
     * исключение, которое бросается если параметр null
     * @return - возвращает исключение
     * @throws AccountException - бросаемое исключение
     */
    public static ClientException NullException() throws ClientException{
        throw new ClientException("param is null");
    }
}
