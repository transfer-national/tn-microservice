package ma.ensa.gateway.exceptions;

import org.springframework.stereotype.Component;

@Component
public class MissingAuthToken extends RuntimeException {



    public MissingAuthToken() {
        super("missing authorization header");
    }
}
