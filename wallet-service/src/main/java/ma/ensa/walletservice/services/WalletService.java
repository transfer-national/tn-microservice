package ma.ensa.walletservice.services;


import ma.ensa.walletservice.dto.WalletDto;


public interface WalletService {

    /**
     * retrieves the information about the wallet
     * @param id the wallet id
     * @return {@link WalletDto} instance
     */
    WalletDto getWallet(String id);


    String createWallet(WalletDto dto);

    String updateBalance(String walletId, Double amount);


    String hasTheWallet(Long ref);
}
