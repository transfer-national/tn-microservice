package ma.ensa.clientservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.models.Recipient;
import ma.ensa.clientservice.service.RecipientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface RecipientController {

    @GetMapping("/client/{ref}/recipient")
    List<Recipient> getAllRecipients(
            @PathVariable Long ref
    );

    @PostMapping("/client/{ref}/recipient")
    Recipient addRecipient(
            @PathVariable Long ref,
            @RequestBody RecipientDto dto
    );

    @GetMapping(path="/recipient/{id}")
    Recipient getRecipient(
            @PathVariable Long id
    );

    @PutMapping(path="/recipient/{id}")
    Recipient updateRecipientById(
            @PathVariable Long id,
            @RequestBody RecipientDto dto
    );

    @DeleteMapping(path="/recipient/{id}")
    void deleteRecipientById(
            @PathVariable Long id
    );

}
