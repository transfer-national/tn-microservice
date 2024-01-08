package ma.ensa.walletservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.walletservice.controllers.WalletController;
import ma.ensa.walletservice.dto.WalletDto;
import ma.ensa.walletservice.services.WalletService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@Slf4j
public class WalletControllerImpl implements WalletController {

    private final WalletService service;

    @Override
    public WalletDto getWallet(String walletId) {
        return service.getWallet(walletId);
    }

    @Override
    public String createWallet(WalletDto dto) {
        log.info("the endpoint is triggered");
        return service.createWallet(dto);
    }

    @Override
    public String updateWalletBalance(WalletDto dto) {
        return service.updateBalance(
                dto.getId(), dto.getAmount()
        );
    }

    @Override
    public String hasTheWallet(Long ref) {
        return service.hasTheWallet(ref);
    }
}
