package Clients;

public class Client {
    public String name;
    public String surname;
    public String address;
    public String passportNumber;
    public boolean levelOfTrust;

    public Client(String name, String surname, String address, String passportNumber)
    {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportNumber = passportNumber;
        levelOfTrust = (this.address != null) && (this.passportNumber != null);
    }
}
