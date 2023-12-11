package ma.ensa.clientservice.exceptions;

public class ClientNotFound extends RuntimeException{

    public ClientNotFound(){
        super("client not found");
    }


}
