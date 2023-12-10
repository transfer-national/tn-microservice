package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.ClientDTO;
import ma.ensa.clientservice.entities.Client;

import java.util.List;


public interface ClientService {
    Client addClient(ClientDTO clientDTO);
    Client getClientByRef(Long ref);
    List<Client> getAllClients ();
    Client updateClientByRef(Long ref,ClientDTO clientDTO);
    void deleteClientByRef(Long ref);

}
