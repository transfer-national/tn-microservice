package ma.ensa.clientservice.controllers;

import ma.ensa.clientservice.dto.ClientDTO;
import ma.ensa.clientservice.entities.Client;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping(path="/client")
    public Client ajouterClient(@RequestBody ClientDTO clientDTO) {

        return clientService.addClient(clientDTO);
    }

    @GetMapping(path ="/client/{ref}")
    public Client getClientByRef(@PathVariable Long ref) {
        return clientService.getClientByRef(ref);
    }

    @GetMapping(path="/client")
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @PutMapping(path ="/client/{ref}")
    public Client updateClientByRef(@PathVariable Long ref ,@RequestBody ClientDTO clientDTO){
        return clientService.updateClientByRef(ref,clientDTO);
    }
    @DeleteMapping(path ="/client/{ref}")
    public void deleteClientByRef(@PathVariable Long ref){
       clientService.deleteClientByRef(ref);
    }

}
