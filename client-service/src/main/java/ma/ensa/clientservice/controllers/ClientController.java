package ma.ensa.clientservice.controllers;


import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController  {

    private final ClientService clientService;

    @GetMapping
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping(path ="/{ref}")
    public Client getClient(@PathVariable Long ref){
        return clientService.getClientByRef(ref);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Client addClient(@RequestBody ClientDto dto){
        return clientService.addClient(dto);
    }

    @PutMapping(path ="/{ref}")
    public Client updateClientByRef(
            @PathVariable Long ref ,
            @RequestBody ClientDto dto
    ){
        dto.setRef(ref);
        return clientService.updateClient(dto);
    }

    @DeleteMapping(path ="/{ref}")
    public void deleteClientByRef(@PathVariable Long ref){
        clientService.deleteClientByRef(ref);
    }


}
