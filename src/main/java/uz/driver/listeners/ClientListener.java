package uz.driver.listeners;

import uz.driver.connection.TSPLConnectionClient;

public interface ClientListener {

    void connectionEstablished(TSPLConnectionClient client);

    void connectionLost(TSPLConnectionClient client);

    void connectionIsFailing(TSPLConnectionClient client, Exception e);

}
