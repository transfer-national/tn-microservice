package ma.ensa.agentservice.exceptions;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(){
        super("INSUFFICIENT BALANCE");
    }

}
