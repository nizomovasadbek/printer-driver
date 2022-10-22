package uz.driver.exceptions;

public class PrinterException extends RuntimeException{
    private static final long serialVersionUID = -7032802716798268348L;
    private String message;

    public PrinterException(){
        super();
    }

    public PrinterException(String message){
        super(message);
        this.message = message;
    }

}
