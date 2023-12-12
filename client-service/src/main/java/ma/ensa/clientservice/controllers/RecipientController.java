package ma.ensa.clientservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.models.Recipient;
import ma.ensa.clientservice.service.RecipientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface RecipientController {

    @GetMapping("/clients/{ref}/recipients")
    List<Recipient> getAllRecipients(
            @PathVariable Long ref
    );

    @PostMapping("/clients/{ref}/recipients")
    Recipient addRecipient(
            @PathVariable Long ref,
            @RequestBody RecipientDto dto
    );

    @GetMapping(path="/recipients/{id}")
    Recipient getRecipient(
            @PathVariable Long id
    );

    @PutMapping(path="/recipients/{id}")
    Recipient updateRecipientById(
            @PathVariable Long id,
            @RequestBody RecipientDto dto
    );

    @DeleteMapping(path="/recipients/{id}")
    void deleteRecipientById(
            @PathVariable Long id
    );

}
