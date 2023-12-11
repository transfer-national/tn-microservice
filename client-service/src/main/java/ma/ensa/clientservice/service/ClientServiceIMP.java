package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.ClientDTO;
import ma.ensa.clientservice.entities.Client;
import ma.ensa.clientservice.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceIMP implements ClientService {
    @Autowired
    private ClientRepository clientRepository;



    @Override
    public Client addClient( ClientDTO clientDTO) {
        Client client = new Client(
                clientDTO.getRef(),
                clientDTO.getTitre(),
                clientDTO.getPrenom(),
                clientDTO.getTypeIdentite(),
                clientDTO.getPaysEmission(),
                clientDTO.getPieceNumero(),
                clientDTO.getPieceValidite(),
                clientDTO.getPieceDateExpiration(),
                clientDTO.getAgeLegalDate(),
                clientDTO.getPieceType(),
                clientDTO.getIdentiteNumero(),
                clientDTO.getIdentiteValidite(),
                clientDTO.getDateNaissance(),
                clientDTO.getProfession(),
                clientDTO.getNationalitePays(),
                clientDTO.getAdressePays(),
                clientDTO.getAdresseLegale(),
                clientDTO.getVille(),
                clientDTO.getGsm(),
                clientDTO.getEmail()

        );

        return clientRepository.save(client);

    }
    @Override
    public Client getClientByRef(Long ref) {

        return clientRepository.findByRef(ref);
    }
    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client updateClientByRef(Long ref,ClientDTO clientDTO){
        Client client = clientRepository.findByRef(ref);
        client.setTitre(clientDTO.getTitre());
        client.setPrenom(clientDTO.getPrenom());
        client.setTypeIdentite(clientDTO.getTypeIdentite());
        client.setPaysEmission(clientDTO.getPaysEmission());
        client.setPieceNumero(clientDTO.getPieceNumero());
        client.setPieceValidite(clientDTO.getPieceValidite());
        client.setPieceDateExpiration(clientDTO.getPieceDateExpiration());
        client.setAgeLegalDate(clientDTO.getAgeLegalDate());
        client.setPieceType(clientDTO.getPieceType());
        client.setIdentiteNumero(clientDTO.getIdentiteNumero());
        client.setIdentiteValidite(clientDTO.getIdentiteValidite());
        client.setDateNaissance(clientDTO.getDateNaissance());
        client.setProfession(clientDTO.getProfession());
        client.setNationalitePays(clientDTO.getNationalitePays());
        client.setAdressePays(clientDTO.getAdressePays());
        client.setAdresseLegale(clientDTO.getAdresseLegale());
        client.setVille(clientDTO.getVille());
        client.setGsm(clientDTO.getGsm());
        client.setEmail(clientDTO.getEmail());

        return clientRepository.save(client);

    }

    @Override
    public void deleteClientByRef(Long ref){
        Client client = clientRepository.findByRef(ref);
        clientRepository.delete(client);


    }




}