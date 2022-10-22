package uz.driver.exceptions;

public class ConnectionClientException extends Exception{

    private static final long serialVersionUID = 2390239209029302L;

    public ConnectionClientException(){
        super();
    }

    public ConnectionClientException(String msg, Throwable t){
        super(msg, t);
    }

    public ConnectionClientException(String msg){
        super(msg);
    }

    public ConnectionClientException(Throwable t){
        super(t);
    }

}
