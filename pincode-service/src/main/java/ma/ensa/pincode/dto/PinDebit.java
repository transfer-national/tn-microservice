package ma.ensa.pincode.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
public class PinDebit {

    private String walletId;

    private String phoneNumber;

    private String amount;

    private int pinCode;

    public int generatePinCode(){
        var random = new Random();
        pinCode = random.nextInt(1_000, 10_000);
        return pinCode;
    }

}
