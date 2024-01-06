package ma.ensa.pincode.services;

import ma.ensa.pincode.dto.*;

import java.util.List;

public interface PinCodeService {
    void sendSMS(SMS sms);

    List<PinTx> generatePinForTx(List<PinTx> ptx);

    PinState getPinState(String relatedId);

    String validatePinCode(PinValid dto);

    String sendPinCodeForDebit(PinDebit pd);
}
