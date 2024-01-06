package ma.ensa.clientservice.controllers.impl;


import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.controllers.ClientController;
import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService service;

    public List<Client> getAllClients(){
        return service.getAllClients();
    }

    @Override
    public ClientDto getClientByIdNumber(String idNumber){
        return service.getClientByIdNumber(idNumber);
    }

    @Override
    public ClientDto getClientByRef(Long id) {
        return service.getClientByRef(id);
    }


    public ClientDto addClient(ClientDto dto, String byAgentId){
        dto.setByAgentId(byAgentId);
        return service.addClient(dto);
    }

    public Client updateClientByRef(Long ref, ClientDto dto){
        dto.setRef(ref);
        return service.updateClient(dto);
    }

    public void deleteClientByRef(Long ref){
        service.deleteClientByRef(ref);
    }

}
