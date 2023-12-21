package ma.ensa.walletservice.services;


import org.springframework.stereotype.Service;


public interface WalletService {

    Double getBalance(String id);

    void updateBalance(String walletId, Double amount);



}
