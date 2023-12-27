package ma.ensa.pincode.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.pincode.dto.*;
import ma.ensa.pincode.services.PinCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pincode")
@RequiredArgsConstructor
public class PinCodeControllerImpl implements PinCodeController {

    private final PinCodeService service;

    @Override
    public PinState getPinState(String relId) {
        return service.getPinState(relId);
    }

    @Override
    public String sendPinCodeForDebit(PinDebit pd) {
        return service.sendPinCodeForDebit(pd);
    }

    @Override
    public List<PinTx> generatePinCodeForTx(List<PinTx> ptx) {
        return service.generatePinForTx(ptx);
    }


    @Override
    public String validatePinCode(PinValid req) {
        return service.validatePinCode(req);
    }

}
