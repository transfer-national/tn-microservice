package ma.ensa.clientservice;

import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.models.enums.IdType;
import ma.ensa.clientservice.models.enums.Title;
import ma.ensa.clientservice.repositories.ClientRepository;
import ma.ensa.clientservice.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void addClient() throws Exception {
        // Données à tester
        ClientDto clientDto = new ClientDto();
        clientDto.setTitle(Title.MME);
        clientDto.setFirstName("RIM");
        clientDto.setLastName("Mazzine");
        clientDto.setIdType(IdType.CIN);
        clientDto.setEmitCountry("Morocco");
        clientDto.setIdNumber("AAA19238");
        clientDto.setIdExpiration(LocalDate.of(2027, 11, 7));
        clientDto.setDob(LocalDate.of(2001, 9, 20));
        clientDto.setProfession("Student");
        clientDto.setNationality("Moroccan");
        clientDto.setAddress("AAAAAA");
        clientDto.setCity("KECH");
        clientDto.setCountry("Morocco");
        clientDto.setGsm("212627382929");
        clientDto.setEmail("rim.mz@gmail.com");
        String byAgentId = "agent123";


        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            Client savedClient = invocation.getArgument(0);
            savedClient.setRef(1L);
            return savedClient;
        });


        ClientDto result = clientService.addClient(clientDto);


        verify(clientRepository, times(1)).save(any(Client.class));


        assertNotNull(result);
        assertNotNull(result.getRef());
        assertEquals(clientDto.getFirstName(), result.getFirstName());
        assertEquals(clientDto.getLastName(), result.getLastName());
    }

    @Test
    void getClientByRef() {
        Long clientId = 1L;
        Client client = new Client();
        client.setRef(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        ClientDto result = clientService.getClientByRef(clientId);

        verify(clientRepository, times(1)).findById(clientId);

        assertNotNull(result);
        assertEquals(client.getRef(), result.getRef());
    }


    @Test
    void getClientByIdNumber() {
        String idNumber = "AAA19238";
        Client client = new Client();
        when(clientRepository.findByIdNumber(idNumber)).thenReturn(Optional.of(client));

        ClientDto result = clientService.getClientByIdNumber(idNumber);

        verify(clientRepository, times(1)).findByIdNumber(idNumber);

        assertNotNull(result);

    }

    @Test
    void getAllClients() {
        List<Client> clients = Arrays.asList(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        verify(clientRepository, times(1)).findAll();

        assertNotNull(result);
        assertEquals(clients.size(), result.size());

    }

    @Test
    void updateClient() {
        ClientDto clientDto = new ClientDto();
        clientDto.setRef(1L);

        Client client = new Client();
        when(clientRepository.findById(clientDto.getRef())).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client updatedClient = clientService.updateClient(clientDto);

        verify(clientRepository, times(1)).findById(clientDto.getRef());
        verify(clientRepository, times(1)).save(any(Client.class));

        assertNotNull(updatedClient);

    }

    @Test
    void deleteClientByRef() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        clientService.deleteClientByRef(clientId);

        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).delete(any(Client.class));
    }
}
