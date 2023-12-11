package ma.ensa.clientservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.models.Recipient;
import ma.ensa.clientservice.service.RecipientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipientController {

    private final RecipientService service;

    @GetMapping("/clients/{ref}/recipients")
    public List<Recipient> getAllRecipients(
            @PathVariable Long ref
    ){
        return service.getAllRecipients(ref);
    }

    @PostMapping("/clients/{ref}/recipients")
    public Recipient addRecipient(
            @PathVariable Long ref,
            @RequestBody RecipientDto dto
    ){
        dto.setClientRef(ref);
        return service.addRecipient(dto);
    }

    // not required to

    @GetMapping(path="/recipients/{id}")
    public Recipient getRecipient(
            @PathVariable Long id
    ){
        return service.getRecipient(id);
    }

    @PutMapping(path="/recipients/{id}")
    public Recipient updateRecipientById(
            @PathVariable Long id,
            @RequestBody RecipientDto dto
    ){
        dto.setId(id);
        return service.updateRecipient(dto);
    }

    @DeleteMapping(path="/recipients/{id}")
    public void deleteRecipientById(
            @PathVariable Long id
    ){
        service.deleteRecipient(id);
    }

}
