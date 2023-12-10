package ma.ensa.authservice.exceptions;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(){
        super("token is expired");
    }

}
