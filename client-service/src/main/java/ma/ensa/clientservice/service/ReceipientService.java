package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.ReceipientDTO;
import ma.ensa.clientservice.entities.Client;
import ma.ensa.clientservice.entities.Receipient;

import java.util.List;


public interface ReceipientService {
    Receipient addReceipient ( ReceipientDTO receipientDTO );
    Receipient getReceipientById(Long id);
    List<Receipient> getAllReceipients();
    Receipient updateReceipientById(Long id, ReceipientDTO receipientDTO);
    void deleteReceipientById(Long id);
}
