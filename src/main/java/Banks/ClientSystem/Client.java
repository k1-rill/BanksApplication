package Banks.ClientSystem;

import Banks.Observer.Observer;
import Banks.Tools.ClientException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * класс клиента, который реализует в себе методы интерфейса observer
 */
public class Client implements Observer {
    private String name;
    private String surname;
    private String address;
    private String passport;
    private final UUID id;
    private final ArrayList<String> notifications;

    /**
     * конструктор клиента
     * @param name - имя клиента
     * @param surname - фамилия клиента
     * @param address - адрес клиента
     * @param passport - паспорт клиента
     * @throws ClientException - исключение бросается, если имя или фамилия клиента null или пустые
     */
    public Client(String name, String surname, String address, String passport) throws ClientException {
        if (name == null || name.isBlank())
            throw ClientException.InvalidClientName();
        if (surname == null || surname.isBlank())
            throw ClientException.InvalidClientSurname();

        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
        this.id = UUID.randomUUID();
        this.notifications = new ArrayList<String>();
    }

    /**
     * метод, который возвращает фамилию клиента
     * @return - фамилия клиента
     */
    public String getSurname() {
        return surname;
    }

    /**
     * метод, который задает фамилию клиенту
     * @param surname - фамилия клиента
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * метод, который возвращает имя клиента
     * @return - имя клиента
     */
    public String getName() {
        return name;
    }
    /**
     * метод, который задает имя клиенту
     * @param name - имя клиента
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * метод, который возвращает адрес клиента
     * @return - адрес клиента
     */
    public String getAddress() {
        return address;
    }
    /**
     * метод, который задает адрес клиента
     * @param address - адрес клиента
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * метод, который возвращает паспорт клиента
     * @return - паспорт клиента
     */
    public String getPassport() {
        return passport;
    }
    /**
     * метод, который задает паспорт клиента
     * @param passport - паспорт клиента
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }
    /**
     * метод, который возвращает id клиента
     * @return - id клиента
     */
    public UUID getId() {
        return id;
    }
    /**
     * метод, который возвращает уведомления клиента
     * @return - паспорт клиента
     */
    public ArrayList<String> getNotifications() {
        return notifications;
    }

    /**
     * метод, который проводит верификацию клиента
     * @return - возвращает true если паспорт и адрес клиента не null и не пустые, иначе false
     */
    public boolean checkVerification(){
        return this.passport!=null && this.address!=null && !this.passport.isBlank() && !this.address.isBlank();
    }

    /**
     * метод, который добавляет уведомление
     * @param message - уведомление
     * @throws ClientException - исключение, которое бросается если уведомление пустое
     */
    @Override
    public void update(String message) throws ClientException {
        if (message.isBlank())
            throw ClientException.InvalidNotificationMessage();
        notifications.add(message);
    }
}
