package ma.ensa.walletservice.controllers;


import jakarta.annotation.PostConstruct;
import ma.ensa.walletservice.dto.WalletDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public interface WalletController {

    @GetMapping("/{walletId}")
    WalletDto getWallet(@PathVariable String walletId);


    // TODO : create an DTO containing the clientRef and
    //  the initial Balance
    @PostMapping
    String createWallet(@RequestBody WalletDto dto);

    @PutMapping("/balance")
    String updateWalletBalance(@RequestBody WalletDto dto);

    @GetMapping("/exists/{ref}")
    String hasTheWallet(@PathVariable Long ref);

}