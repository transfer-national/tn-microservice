package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.Client;

import java.util.List;


public interface ClientService {

    Client addClient(ClientDto clientDTO);

    Client getClientByRef(Long ref);

    List<Client> getAllClients();

    Client updateClient(ClientDto dto);


    void deleteClientByRef(Long ref);

}
