package ma.ensa.transferservice.exceptions;

public class BlackListedException extends CustomException {

    private final String reason;

    public BlackListedException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {

        return String.format(
            "%s - REASON : %s",
            super.getMessage(), reason
        );

    }
}
