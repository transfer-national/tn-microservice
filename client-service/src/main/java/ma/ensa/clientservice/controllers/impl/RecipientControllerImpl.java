package ma.ensa.clientservice.controllers.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.controllers.RecipientController;
import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.service.RecipientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipientControllerImpl implements RecipientController {

    private final RecipientService service;

    public List<RecipientDto> getAllRecipients(Long ref){
        return service.getAllRecipients(ref);
    }

    public String addRecipient(Long ref, RecipientDto dto){
        dto.setClientRef(ref);
        return service.addRecipient(dto);
    }

    public RecipientDto getRecipient(Long id){
        return service.getRecipient(id);
    }

    public String updateRecipientById(Long id, RecipientDto dto){
        dto.setId(id);
        return service.updateRecipient(dto);
    }

    public void deleteRecipientById(Long id){
        service.deleteRecipient(id);
    }

}
