package ma.ensa.clientservice.service;

import ma.ensa.clientservice.dto.ReceipientDTO;

import ma.ensa.clientservice.entities.Receipient;

import ma.ensa.clientservice.repositories.ReceipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceipientServiceIMP implements ReceipientService {
    @Autowired
    private ReceipientRepository receipientRepository;

    @Autowired
    private ClientService clientService;


    @Override
    public Receipient addReceipient(ReceipientDTO receipientDTO) {

        Receipient receipient = new Receipient();
        receipient.setFirstName(receipientDTO.getFirstName());
        receipient.setLastName(receipientDTO.getLastName());
        receipient.setPhoneNumber(receipientDTO.getPhoneNumber());
        receipient.setClient( clientService.getClientByRef(1L));


        return  receipientRepository.save(receipient);
    }
    @Override
    public Receipient getReceipientById(Long id){
        return receipientRepository.findById(id).get();

    }
    @Override
    public List<Receipient> getAllReceipients(){
        return receipientRepository.findAll();
    }
    @Override
    public Receipient updateReceipientById(Long id, ReceipientDTO receipientDTO){
        Receipient receipient= receipientRepository.findById(id).get();
        receipient.setFirstName(receipientDTO.getFirstName());
        receipient.setLastName(receipientDTO.getLastName());
        receipient.setPhoneNumber(receipientDTO.getPhoneNumber());
        receipient.setClient(receipientDTO.getClient());

        return receipientRepository.save(receipient);

    }
    @Override
    public void deleteReceipientById(Long id){
        Receipient receipient= receipientRepository.findById(id).get();
        receipientRepository.delete(receipient);
    }

}
