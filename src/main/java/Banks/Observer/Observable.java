package Banks.Observer;

import java.math.BigDecimal;

/**
 * интерфейс издателя, изменения которого хотят отслеживать
 */
public interface Observable {
    /**
     * метод, который добавляет наблюдателя
     * @param observer - наблюдатель
     */
    void addObserver(Observer observer);

    /**
     * метод, который удаляет наблюдателя
     * @param observer - наблюдатель
     */
    void removeObserver(Observer observer);

    /**
     * метод, который позволяет уведомить наблюдателей
     * @param message - уведомление
     * @param newLimit - изменения
     */
    void notify(String message, BigDecimal newLimit);
}
