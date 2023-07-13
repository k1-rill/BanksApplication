package Banks.ClientSystem;

import Banks.Tools.ClientException;

import java.math.BigDecimal;

/**
 * класс строитель (строитель клиента)
 */
public class ClientBuilder {
    public final BigDecimal minPassportNumber = BigDecimal.valueOf(100000000);
    private String name;
    private String surname;
    private String address;
    private String passport;

    /**
     * метод, который возвращает имя клиента
     * @return - имя клиента
     */
    public String getName() {
        return name;
    }
    /**
     * метод, который задает имя клиента
     * @param name - имя клиента
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * метод, который возвращает фамилию клиента
     * @return - фамилию клиента
     */
    public String getSurname() {
        return surname;
    }
    /**
     * метод, который задает фамилию клиента
     * @param surname - фамилия клиента
     */
    public void setSurname(String surname) {
        this.surname = surname;
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
     * метод, который добавляет имя клиенту
     * @param name - имя клиента
     * @throws ClientException - исключение бросается если имя null или пустое
     */
    public void addName(String name) throws ClientException {
        if (name == null)
            throw ClientException.NullException();
        if (name.isBlank())
            throw ClientException.InvalidClientName();
        setName(name);
    }
    /**
     * метод, который добавляет фамилия клиенту
     * @param surname - фамилия клиента
     * @throws ClientException - исключение бросается если фамилия null или пустое
     */
    public void addSurname(String surname) throws ClientException{
        if (surname == null)
            throw ClientException.NullException();
        if (surname.isBlank())
            throw ClientException.InvalidClientSurname();
        setSurname(surname);
    }
    /**
     * метод, который добавляет адрес клиенту
     * @param address - адрес клиента
     * @throws ClientException - исключение бросается если адрес null или пустое
     */
    public void addAddress(String address) throws ClientException{
        if (surname == null)
            throw ClientException.NullException();
        if (address.isBlank())
            throw ClientException.InvalidClientAddress();
        setAddress(address);
    }
    /**
     * метод, который добавляет паспорт клиенту
     * @param passport - паспорт клиента
     * @throws ClientException - исключение бросается паспорт имя null или пустое или меньше минимального номера паспорта
     */
    public void addPassport(String passport) throws ClientException{
        if (passport == null)
            throw ClientException.NullException();
        if (passport.isBlank())
            throw ClientException.InvalidClientPassport();
        BigDecimal passportNumber = new BigDecimal(passport);
        if (passportNumber.compareTo(minPassportNumber) < 0)
            throw ClientException.InvalidClientPassport();
        setPassport(passport);
    }

    /**
     * метод, который строит клиента
     * @return - клиент
     */
    public Client makeClient() {
        return new Client(name, surname, address, passport);
    }
}
