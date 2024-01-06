package ma.ensa.clientservice;


import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.models.Recipient;
import ma.ensa.clientservice.repositories.ClientRepository;
import ma.ensa.clientservice.repositories.RecipientRepository;
import ma.ensa.clientservice.service.impl.RecipientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTest {

    @Mock
    private RecipientRepository recipientRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RecipientServiceImpl recipientService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void addRecipient() {
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setClientRef(1L);


        Client client = new Client();
        when(clientRepository.findById(recipientDto.getClientRef())).thenReturn(Optional.of(client));

        String result = recipientService.addRecipient(recipientDto);

        verify(clientRepository, times(1)).findById(recipientDto.getClientRef());
        verify(recipientRepository, times(1)).save(any(Recipient.class));

        assertNotNull(result);

    }

    @Test
    void getRecipient() {
        Long recipientId = 1L;
        Recipient recipient = new Recipient();
        when(recipientRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        RecipientDto result = recipientService.getRecipient(recipientId);

        verify(recipientRepository, times(1)).findById(recipientId);

        assertNotNull(result);

    }

    @Test
    void getAllRecipients() {
        Long clientRef = 1L;
        List<Recipient> recipients = Arrays.asList(new Recipient(), new Recipient());
        when(recipientRepository.findAllByClient_Ref(clientRef)).thenReturn(recipients);

        List<RecipientDto> result = recipientService.getAllRecipients(clientRef);

        verify(recipientRepository, times(1)).findAllByClient_Ref(clientRef);

        assertNotNull(result);
        assertEquals(recipients.size(), result.size());

    }

    @Test
    void updateRecipient() {
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setId(1L);


        Recipient recipient = new Recipient();
        when(recipientRepository.findById(recipientDto.getId())).thenReturn(Optional.of(recipient));
        when(recipientRepository.save(any(Recipient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = recipientService.updateRecipient(recipientDto);

        verify(recipientRepository, times(1)).findById(recipientDto.getId());
        verify(recipientRepository, times(1)).save(any(Recipient.class));

        assertNotNull(result);

    }

    @Test
    void deleteRecipient() {
        Long recipientId = 1L;
        recipientService.deleteRecipient(recipientId);

        verify(recipientRepository, times(1)).deleteById(recipientId);
    }
}
