package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.models.Recipient;

import java.util.List;


public interface RecipientService {

    String addRecipient(RecipientDto dto);

    RecipientDto getRecipient(Long id);

    List<RecipientDto> getAllRecipients(Long clientRef);

    String updateRecipient(RecipientDto dto);

    void deleteRecipient(Long id);
}
