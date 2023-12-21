package ma.ensa.walletservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.walletservice.services.WalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    @Value("${wallet.transferCeil}")
    private double transferCeil;

    private final WalletService service;


    @GetMapping("/{walletId}")
    public ResponseEntity<Double> getBalance(
            @PathVariable String walletId
    ){
        return ok(service.getBalance(walletId));
    }


    @PutMapping("/{walletId}")
    public ResponseEntity<?> updateBalance(
            @PathVariable String walletId,
            @RequestBody Double amount
    ){
        service.updateBalance(walletId, amount);
        return ok("updated successfully");
    }

    @GetMapping("/y")
    public ResponseEntity<?> checkYearlyCeiling(){
        return null;
    }

    @GetMapping("/bg")
    public ResponseEntity<Double> checkTransferCeiling(){
        return ok(transferCeil);

    }

}
