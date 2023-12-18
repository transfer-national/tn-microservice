package ma.ensa.clientservice.controllers;


import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public interface ClientController {

    @GetMapping
    List<Client> getAllClients();

    @GetMapping(path ="/cin/{idNumber}")
    ClientDto getClientByIdNumber(@PathVariable String idNumber);

    @GetMapping(path="/{id}")
    ClientDto getClientByRef(@PathVariable Long id);


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ClientDto addClient(
            @RequestBody ClientDto dto,
            @RequestHeader("By-User") String byAgent
    );

    @PutMapping(path ="/{ref}")
    Client updateClientByRef(
            @PathVariable Long ref ,
            @RequestBody ClientDto dto
    );

    @DeleteMapping(path ="/{ref}")
    void deleteClientByRef(@PathVariable Long ref);

}