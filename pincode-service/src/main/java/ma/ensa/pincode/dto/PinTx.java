package ma.ensa.pincode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PinTx {

    private String phoneNumber; // if not null --> send sms

    private String txRef;

    private int pinCode;

    public int generatePinCode(){
        var random = new Random();
        pinCode = random.nextInt(1_000, 10_000);
        return pinCode;
    }

}
