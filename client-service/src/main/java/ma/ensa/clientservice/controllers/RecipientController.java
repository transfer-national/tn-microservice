package ma.ensa.clientservice.controllers;

import ma.ensa.clientservice.dto.RecipientDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RecipientController {

    @GetMapping("/client/{ref}/recipient")
    List<RecipientDto> getAllRecipients(
            @PathVariable Long ref
    );

    @PostMapping("/client/{ref}/recipient")
    String addRecipient(
            @PathVariable Long ref,
            @RequestBody RecipientDto dto
    );

    @GetMapping(path="/recipient/{id}")
    RecipientDto getRecipient(
            @PathVariable Long id
    );

    @PutMapping(path="/recipient/{id}")
    String updateRecipientById(
            @PathVariable Long id,
            @RequestBody RecipientDto dto
    );

    @DeleteMapping(path="/recipient/{id}")
    void deleteRecipientById(
            @PathVariable Long id
    );

}