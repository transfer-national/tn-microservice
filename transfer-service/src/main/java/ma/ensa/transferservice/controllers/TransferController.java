package ma.ensa.transferservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.services.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@Tag(
        name= "Transfer",
        description = "responsible for sending and manipulating a transfer"
)
public class TransferController {

    private final TransferService service;


    @GetMapping
    public List<?> getAllTransfers(
            @RequestBody SearchFilter filter
    ){
        return service.getAllTransfers(filter);
    }

    @GetMapping("/{ref}")
    public Transfer getTransfer(
            @PathVariable long ref
    ){
        return service.getTransfer(ref);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Long emitTransfer(
            @RequestBody TransferRequestDto dto
    ){
        return service.emitTransfer(dto);
    }

    @PutMapping("/{ref}/serve")
    public String serveTransfer(
            @PathVariable(required = false) long ref,
            @RequestBody PaymentDto dto
    ){
        dto.setRef(ref);
        service.serveTransfer(dto);
        return "served successfully";
    }

    @PutMapping("/{ref}/revert")
    public String revertTransfer(
            @PathVariable long ref,
            @RequestBody RevertDto dto
    ){
        dto.setRef(ref);
        service.revertTransfer(dto);
        return "reverted successfully";
    }

    @PutMapping("/{ref}/cancel")
    public String cancelTransfer(
            @PathVariable long ref,
            @RequestBody PaymentDto dto
    ){
        dto.setRef(ref);
        return "cancelled successfully";
    }


}