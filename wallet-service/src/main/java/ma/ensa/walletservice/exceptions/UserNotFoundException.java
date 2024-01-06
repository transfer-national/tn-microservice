package ma.ensa.walletservice.exceptions;

import org.aspectj.weaver.ast.Not;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
