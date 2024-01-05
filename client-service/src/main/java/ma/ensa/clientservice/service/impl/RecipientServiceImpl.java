package ma.ensa.clientservice.service.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.clientservice.dto.RecipientDto;

import ma.ensa.clientservice.exceptions.ClientNotFoundException;
import ma.ensa.clientservice.exceptions.RecipientNotFound;
import ma.ensa.clientservice.models.Recipient;

import ma.ensa.clientservice.repositories.ClientRepository;
import ma.ensa.clientservice.repositories.RecipientRepository;
import ma.ensa.clientservice.service.RecipientService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipientServiceImpl implements RecipientService {

    private final RecipientRepository recipientRepository;
    private final ClientRepository clientRepository;


    public RecipientDto toDto(Recipient recipient){

        return RecipientDto.builder()
                .id(recipient.getId())
                .firstName(recipient.getFirstName())
                .lastName(recipient.getLastName())
                .phoneNumber(recipient.getPhoneNumber())
                .build();
    }


    @Override
    public String addRecipient(RecipientDto dto) {

        var client = clientRepository
                .findById(dto.getClientRef())
                .orElseThrow(ClientNotFoundException::new);

        // create the recipient instance
        var recipient = new Recipient();
        BeanUtils.copyProperties(dto, recipient);
        recipient.setClient(client);

        // save the new recipient into the database
        recipientRepository.save(recipient);

        return  "RECIPIENT ADDED SUCCESSFULLY";
    }

    @Override
    public RecipientDto getRecipient(Long id){
        return recipientRepository
                .findById(id)
                .map(this::toDto)
                .orElseThrow(RecipientNotFound::new);

    }
    @Override
    public List<RecipientDto> getAllRecipients(Long ref){
        // get all the recipients by clientRef
        return recipientRepository
                .findAllByClient_Ref(ref)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public String updateRecipient(RecipientDto dto){

        // get the recipient
        var recipient = recipientRepository
                .findById(dto.getId())
                .orElseThrow();

        // copy the properties
        BeanUtils.copyProperties(dto, recipient);

        // save the update
        recipientRepository.save(recipient);

        return "UPDATED SUCCESSFULLY";

    }
    @Override
    public void deleteRecipient(Long id){
        recipientRepository.deleteById(id);
    }

}
