package ma.ensa.walletservice.services.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.walletservice.dto.WalletDto;
import ma.ensa.walletservice.exceptions.WalletNotFoundException;
import ma.ensa.walletservice.models.Client;
import ma.ensa.walletservice.models.user.Wallet;
import ma.ensa.walletservice.repositories.WalletRepository;
import ma.ensa.walletservice.services.RestCall;
import ma.ensa.walletservice.services.WalletService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final RestCall restCall;

    @Override
    public WalletDto getWallet(String id) {

        var wallet = getWalletInstance(id);

        return WalletDto.builder()
                .id(id)
                .balance(wallet.getBalance())
                .client(
                    restCall.getClient(wallet.getClient().getIdNumber())
                )
                .build();
    }

    @Override
    public String createWallet(WalletDto dto) {

        // get the client info
        var client = restCall.getClient(dto.getCin());

        // create the wallet instance
        var wallet = Wallet.builder()
                .client(new Client(client.getRef()))
                .balance(dto.getBalance())
                .build();

        // save the wallet instance into the database
        var id = walletRepository.save(wallet).getId();


        // generate the password
        var password = generatePassword();

        // set the password
        restCall.setPassword(id, password);

        // sent the generated password through email asynchronously
        var email = client.getEmail();

        // what about send it through RabbitMQ

        return "WALLET CREATED SUCCESSFULLY, id = " + id;
    }

    @Override
    public String updateBalance(String walletId, Double amount) {

        // get the wallet instance
        var wallet = getWalletInstance(walletId);

        // check the balance if the amount < 0 (DEBIT)
        var balance = wallet.getBalance();

        if(amount < 0 && balance < amount ){
            // TODO : create IBE class
            throw new RuntimeException();
        }

        // update the balance
        walletRepository.updateBalance(walletId, amount);

        // return the success message
        return String.format(
                "BALANCE UPDATED SUCCESSFULLY, id = %s, new balance = %f",
                walletId, (balance + amount)
        );
    }

    @Override
    public String hasTheWallet(Long ref) {
        var client = new Client(ref);
        return walletRepository
                .findByClient(client)
                .map((w) -> "exists")
                .orElse("");

    }

    private Wallet getWalletInstance(String id){
        return walletRepository
                .findById(id)
                .orElseThrow(WalletNotFoundException::new);
    }

    private String generatePassword() {
        var random = new Random();
        return Stream.generate(() -> random.nextInt(128))
                .filter(Character::isAlphabetic)
                .limit(8)
                .map(Character::toChars)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }


}



@Data
@SuperBuilder
class AuthRequest{

    private String userId, password;

}