package ma.ensa.clientservice.controllers;

import ma.ensa.clientservice.dto.ReceipientDTO;
import ma.ensa.clientservice.entities.Client;
import ma.ensa.clientservice.entities.Receipient;
import ma.ensa.clientservice.service.ReceipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceipientController {
    @Autowired
    private ReceipientService receipientService;

    @PostMapping(path="/receipient")
    public Receipient ajoutReceipient(@RequestBody ReceipientDTO receipientDTO){
        return receipientService.addReceipient(receipientDTO);
    }

    @GetMapping(path="/receipient/{id}")
    public Receipient getReceipientById(@PathVariable Long id){
        return receipientService.getReceipientById(id);
    }

    @GetMapping(path="/receipient")
    public List<Receipient> getAllReceipients(){
        return receipientService.getAllReceipients();
    }

    @PutMapping(path="/receipient/{id}")
    public Receipient updateReceipientById(@PathVariable Long id,@RequestBody ReceipientDTO receipientDTO){

        return receipientService.updateReceipientById(id,receipientDTO);
    }

    @DeleteMapping(path="/receipient/{id}")
    public void deleteReceipientById(@PathVariable Long id){
        receipientService.deleteReceipientById(id);
    }

}
