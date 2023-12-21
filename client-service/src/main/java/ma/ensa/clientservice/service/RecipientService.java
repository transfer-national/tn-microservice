package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.RecipientDto;
import ma.ensa.clientservice.models.Recipient;

import java.util.List;


public interface RecipientService {

    Recipient addRecipient(RecipientDto dto);

    Recipient getRecipient(Long id);

    List<Recipient> getAllRecipients(Long clientRef);

    Recipient updateRecipient(RecipientDto dto);

    void deleteRecipient(Long id);
}
