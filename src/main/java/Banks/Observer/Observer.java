package Banks.Observer;

import Banks.Tools.ClientException;

/**
 * класс наблюдателя
 */
public interface Observer {
    /**
     * метод, который позволяет добавить уведомление в список
     * @param message - уведомление
     * @throws ClientException - исключение, которое бросается если уведомление null
     */
    void update(String message) throws ClientException;
}
