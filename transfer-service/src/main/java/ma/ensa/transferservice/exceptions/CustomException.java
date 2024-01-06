package ma.ensa.transferservice.exceptions;

public abstract class CustomException extends RuntimeException{

    @Override
    public String getMessage() {
        return this
            .getClass()
            .getSimpleName()
            .replaceAll("([A-Z])", " $1")
            .strip().toUpperCase();
    }

}
