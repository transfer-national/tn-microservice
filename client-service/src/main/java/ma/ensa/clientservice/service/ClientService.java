package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.Client;

import java.util.List;


public interface ClientService {

    ClientDto addClient(ClientDto dto);

    ClientDto getClientByIdNumber(String idNumber);

    List<Client> getAllClients();

    Client updateClient(ClientDto dto);

    void deleteClientByRef(Long ref);

    ClientDto getClientByRef(Long id);
}
