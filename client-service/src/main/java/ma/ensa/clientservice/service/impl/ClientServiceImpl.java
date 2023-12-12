package ma.ensa.clientservice.service.impl;

import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.exceptions.ClientNotFound;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.repositories.ClientRepository;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {


    @Autowired
    private ClientRepository clientRepository;

    @Value("${years-of-validity}")
    private int yearsOfValidity;


    private boolean isExpired(LocalDateTime updatedAt){
        return updatedAt
            .plusYears(yearsOfValidity)
            .isAfter(LocalDateTime.now());

        // 12/12/2023 -----> 12/12/2025    **now**(02-01-2026)
    }

    @Override
    public Client addClient( ClientDto clientDTO) {

        // create the client instance
        var client = new Client();

        // ............
        BeanUtils.copyProperties(clientDTO, client);

        // save the instance into the database
        return clientRepository.save(client);

    }
    @Override
    public Client getClientByRef(Long ref) { //TODO: get by idNumber i.e. HH125495

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