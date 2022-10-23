package uz.driver.listeners;

import uz.driver.exceptions.ConnectionClientException;

public interface DataListener {
    void messageSent(String message);

    void messageReceived(String message);

    void messageSendFailed(ConnectionClientException exception, String messageToSend);
}
