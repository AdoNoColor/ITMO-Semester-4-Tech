package Clients;

public class ClientBuilder {
    private String _name;
    private String _surname;
    private String _address;
    private String _passportNumber;

    public void resetSettings()
    {
        _name = null;
        _surname = null;
        _address = null;
        _passportNumber = null;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public void setSurname(String surname)
    {
        _surname = surname;
    }

    public void setAddress(String address)
    {
        _address = address;
    }

    public void setPassportNumber(String number)
    {
        _passportNumber = number;
    }

    public Client getClient()
    {
        var client = new Client(_name, _surname, _address, _passportNumber);
        resetSettings();
        return client;
    }
}
