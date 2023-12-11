package ma.ensa.clientservice.service.impl;

import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.exceptions.ClientNotFound;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.repositories.ClientRepository;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {


    @Autowired
    private ClientRepository clientRepository;



    @Override
    public Client addClient( ClientDto clientDTO) {

        var client = new Client();

        // you can use this line
        BeanUtils.copyProperties(clientDTO, client);

        return clientRepository.save(client);

    }
    @Override
    public Client getClientByRef(Long ref) {

        return clientRepository
                .findById(ref)
                .orElseThrow(ClientNotFound::new);
    }
    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient(ClientDto dto){

        return clientRepository.findById(dto.getRef())
            .map(s->{
                BeanUtils.copyProperties(dto, s);
                return s;
            })
            .map(clientRepository::save)
            .orElseThrow();
    }

    @Override
    public void deleteClientByRef(Long ref){

        clientRepository
            .findById(ref)
            .ifPresent(clientRepository::delete);
    }




}