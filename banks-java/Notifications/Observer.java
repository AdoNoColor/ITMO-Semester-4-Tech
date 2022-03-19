package Notifications;

import Clients.Client;

import java.util.ArrayList;

public interface Observer {
    ArrayList<Client> subscribers = new ArrayList<>();
    void handleEvent();
}
