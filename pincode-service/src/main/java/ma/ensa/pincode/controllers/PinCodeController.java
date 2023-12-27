package ma.ensa.pincode.controllers;


import ma.ensa.pincode.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pincode")
public interface PinCodeController {

    @GetMapping
    PinState getPinState(@RequestParam("relatedId") String relId);

    @PostMapping("/debit")
    String sendPinCodeForDebit(@RequestBody PinDebit pd);


    @PostMapping("/tx")
    List<PinTx> generatePinCodeForTx(@RequestBody List<PinTx> ptx);



    @PutMapping
    String validatePinCode(@RequestBody PinValid req);

}
