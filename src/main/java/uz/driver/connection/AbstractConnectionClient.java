package uz.driver.connection;

import uz.driver.exceptions.ConnectionClientException;
import uz.driver.listeners.ClientListener;
import uz.driver.listeners.DataListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractConnectionClient implements TSPLConnectionClient {

    protected List<ClientListener> clientListeners = new ArrayList<>();
    protected List<DataListener> dataListeners = new ArrayList<>();
    protected ExecutorService listenerExecutorService = Executors.newCachedThreadPool();
    protected boolean isConnected = Boolean.FALSE;
    protected boolean alive = Boolean.FALSE;

    @Override
    public void addClientListener(ClientListener listener){
        this.clientListeners.add(listener);
    }

    @Override
    public void removeClientListener(ClientListener listener){
        this.clientListeners.remove(listener);
    }

    @Override
    public void addDataListener(DataListener listener){
        this.dataListeners.add(listener);
    }

    @Override
    public void removeDataListener(DataListener listener){
        this.dataListeners.remove(listener);
    }

    @Override
    public boolean isConnected(){
        return isConnected;
    }

    protected void notifyMessageReceived(String message){
        dataListeners.forEach(dataListener -> listenerExecutorService.execute(()
        -> dataListener.messageReceived(message)));
    }

    protected void notifyMessageSent(String message){
        dataListeners.forEach(dataListener -> listenerExecutorService.execute(()
        -> dataListener.messageSent(message)));
    }

    protected void notifyMessageSendFailed(final ConnectionClientException exception,
                                           final String messageToSend){
        dataListeners.forEach(dataListener -> listenerExecutorService.execute(()
        -> dataListener.messageSendFailed(exception, messageToSend)));
    }

    protected void notifyConnection(){
        clientListeners.forEach(clientListener -> listenerExecutorService.execute(()
        -> clientListener.connectionEstablished(AbstractConnectionClient.this)));
    }

    protected void notifyConnectionLost(){
        clientListeners.forEach(clientListener -> listenerExecutorService.execute(() ->
                clientListener.connectionLost(AbstractConnectionClient.this)));
    }

    protected void notifyConnectionFailed(){
        clientListeners.forEach(clientListener -> listenerExecutorService.execute(()
        -> clientListener.connectionIsFailing(AbstractConnectionClient.this, null)));
    }

    protected void notifyDisconnected(){
        clientListeners.forEach(clientListener -> listenerExecutorService.execute(()
        -> clientListener.connectionLost(AbstractConnectionClient.this)));
    }


}
