package Notifications;

import Banks.Bank;
import Clients.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientNotifications implements Observer {
    Bank publisher;
    public List<Client> subscribers;

    public ClientNotifications(Bank bank){
        publisher = bank;
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(Client client){
        subscribers.add(client);
    }

    @Override
    public void handleEvent() {
        for (Client client : subscribers)
            System.out.print("Send to:" + client.name + client.surname + "\nAddress:" + client.address + "\n"
                    + publisher.name + "has changed its options.\n" + "Commission:"
                    + publisher.commission + "\n" + "Percent Interest:" + publisher.percent + "\nCredit limit:"
                    + publisher.creditLimit + "\nTransaction limit (for Unverified users):" + publisher.trustLimit);
    }
}
