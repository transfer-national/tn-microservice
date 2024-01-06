package ma.ensa.walletservice;
import ma.ensa.walletservice.dto.ClientDto;
import ma.ensa.walletservice.dto.WalletDto;
import ma.ensa.walletservice.exceptions.WalletNotFoundException;
import ma.ensa.walletservice.models.Client;
import ma.ensa.walletservice.models.user.Wallet;
import ma.ensa.walletservice.repositories.WalletRepository;
import ma.ensa.walletservice.services.RestCall;
import ma.ensa.walletservice.services.impl.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private RestCall restCall;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void testGetWallet() throws WalletNotFoundException {

        String walletId = "123";
        Wallet wallet = Wallet.builder()
                .id(walletId)
                .balance(100.0)
                .client(Client.builder().ref(Long.valueOf(1L)).build())
                .build();

        ClientDto clientDto = ClientDto.builder()
                .firstName("Rim")
                .lastName("Mazzine")
                .idNumber("0627392031")
                .gsm("555-555-5555")
                .email("rim.mz@example.com")
                .build();

        when(walletRepository.findById(walletId)).thenReturn(java.util.Optional.of(wallet));
        when(restCall.getClient(1L)).thenReturn(clientDto);


        WalletDto result = walletService.getWallet(walletId);


        assertEquals(walletId, result.getId());
        assertEquals(wallet.getBalance(), result.getBalance());
        assertEquals("Rim", result.getClient().getFirstName());
        assertEquals("Mazzine", result.getClient().getLastName());
        assertEquals("rim.mz@example.com", result.getClient().getEmail());

    }
}
