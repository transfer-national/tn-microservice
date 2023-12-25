package ma.ensa.clientservice.exceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(){
        super("client not found");
    }


}
