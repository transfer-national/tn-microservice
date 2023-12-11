package ma.ensa.clientservice.service.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.RecipientDto;

import ma.ensa.clientservice.exceptions.RecipientNotFound;
import ma.ensa.clientservice.models.Client;
import ma.ensa.clientservice.models.Recipient;

import ma.ensa.clientservice.repositories.RecipientRepository;
import ma.ensa.clientservice.service.ClientService;
import ma.ensa.clientservice.service.RecipientService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipientServiceImpl implements RecipientService {

    private final RecipientRepository recipientRepository;
    private final ClientService clientService;

    @Override
    public Recipient addRecipient(RecipientDto dto) {

        Client client = clientService
            .getClientByRef(
                dto.getClientRef()
            );

        var recipient = new Recipient();
        BeanUtils.copyProperties(dto, recipient);
        recipient.setClient(client);

        return  recipientRepository.save(recipient);
    }

    @Override
    public Recipient getRecipient(Long id){
        return recipientRepository
                .findById(id)
                .orElseThrow(RecipientNotFound::new);

    }
    @Override
    public List<Recipient> getAllRecipients(Long ref){
        // get all the recipients by clientRef
        return recipientRepository.findAllByClient_Ref(ref);
    }

    @Override
    public Recipient updateRecipient(RecipientDto dto){

        // get the recipient
        var recipient = getRecipient(dto.getId());

        // copy the properties
        BeanUtils.copyProperties(dto, recipient);

        // save the update
        return recipientRepository.save(recipient);

    }
    @Override
    public void deleteRecipient(Long id){
        recipientRepository.deleteById(id);
    }

}
