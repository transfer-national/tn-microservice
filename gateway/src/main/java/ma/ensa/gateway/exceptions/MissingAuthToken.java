package ma.ensa.gateway.exceptions;

public class MissingAuthToken extends RuntimeException {
    public MissingAuthToken() {
        super("missing authorization header");
    }
}
