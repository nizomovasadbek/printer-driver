package uz.driver.exceptions;

public class LabelParserException extends RuntimeException{

    private static final long serialVersionUID = -7032802716798268348L;
    private String message;

    public LabelParserException(){
        super();
    }

    public LabelParserException(String msg){
        super(msg);
        message = msg;
    }

}
