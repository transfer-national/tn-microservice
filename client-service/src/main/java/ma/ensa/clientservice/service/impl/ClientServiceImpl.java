package ma.ensa.clientservice.service.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.exceptions.ClientNotFound;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.models.user.Agent;
import ma.ensa.clientservice.repositories.ClientRepository;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.*;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    @Value("${years-of-validity}")
    private int yearsOfValidity;


    private boolean isExpired(LocalDateTime updatedAt){

        return updatedAt
            .plusYears(yearsOfValidity)
            .isAfter(LocalDateTime.now());

    }


    @Override
    public ClientDto addClient(ClientDto dto) {

        // create the client instance
        var client = new Client();

        // copy the properties into the client instance
        copyProperties(dto, client);

        // add the agent instance
        client.setCreatedBy(
                new Agent(dto.getByAgentId())
        );

        // save the instance into the database
        client = clientRepository.save(client);

        dto.setRef(client.getRef());

        return dto;

    }
    @Override
    public ClientDto getClientByRef(Long id) {
        var client = clientRepository
                .findById(id)
                .orElseThrow(ClientNotFound::new);

        return new ClientDto(){{
            copyProperties(client, this);
        }};
    }
    @Override
    public ClientDto getClientByIdNumber(String idNumber) { //TODO: get by idNumber i.e. HH125495

        var client = clientRepository
                .findByIdNumber(idNumber)
                .orElseThrow(ClientNotFound::new);

        return new ClientDto(){{
            copyProperties(client, this);
        }};
    }

    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient(ClientDto dto){

        return clientRepository.findById(dto.getRef())
            .map(s->{
                copyProperties(dto, s);
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