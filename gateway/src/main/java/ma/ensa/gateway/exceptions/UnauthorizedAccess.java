package ma.ensa.gateway.exceptions;

public class UnauthorizedAccess extends RuntimeException {

    public UnauthorizedAccess(){
        super("YOU ARE NOT AUTHORIZED TO ACCESS ");
    }


}
