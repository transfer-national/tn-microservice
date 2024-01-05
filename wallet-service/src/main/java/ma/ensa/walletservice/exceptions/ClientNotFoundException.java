package ma.ensa.walletservice.exceptions;

public class ClientNotFoundException extends NotFoundException {
    public ClientNotFoundException(String clientRef) {
        super("CLIENT NOT FOUND, id=" + clientRef);
    }
}
