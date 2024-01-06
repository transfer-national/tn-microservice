package ma.ensa.pincode.exceptions;

import ma.ensa.pincode.models.PinCode;

public class PinAlreadyExistsException extends RuntimeException {

    public PinAlreadyExistsException(PinCode pinCode){

    }

}
