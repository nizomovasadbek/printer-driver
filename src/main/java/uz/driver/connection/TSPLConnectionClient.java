package uz.driver.connection;

import uz.driver.commands.label.TSPLLabel;

public interface TSPLConnectionClient {

    void init();

    void connect();

    void disconnect();

    void shutdown();

    boolean isConnected();

    void send(String tsplMessage);

    void send(TSPLLabel label);

    void addClientListener(ClientListener listener);

    void removeClientListener(ClientListener listener);

    void addDataListener(DataListener listener);

    void removeDataListener(DataListener listener);

}
