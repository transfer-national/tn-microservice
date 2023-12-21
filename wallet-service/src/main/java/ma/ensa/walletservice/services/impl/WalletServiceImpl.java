package ma.ensa.walletservice.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.walletservice.repositories.WalletRepository;
import ma.ensa.walletservice.services.WalletService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Double getBalance(String id) {
        return walletRepository
                .findBalanceById(id)
                .orElseThrow(
                        // TODO: create a custom exception `WalletNotFound`
                        () -> new RuntimeException("the wallet does not exists")
                );
    }

    @Override
    public void updateBalance(String walletId, Double amount) {

        double balance = getBalance(walletId);

        if(amount < 0 && amount > balance){
            // TODO: create a custom exception `FundNotSufficient`
            throw new RuntimeException("the fund is not sufficient");
        }

        walletRepository.updateBalance(walletId, amount);





    }
}
