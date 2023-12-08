package ma.ensa.transferservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.dto.request.CancelDto;
import ma.ensa.transferservice.dto.request.RevertDto;
import ma.ensa.transferservice.dto.request.SendDto;
import ma.ensa.transferservice.dto.request.ServeDto;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.services.TransferService;
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

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

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
    public List<Long> emitTransfer(
            @RequestBody SendDto dto
    ){
        return service.emitTransfer(dto);
    }

    @PutMapping("/{ref}/serve")
    public String serveTransfer(
            @PathVariable(required = false) long ref,
            @RequestBody ServeDto dto
    ){
        dto.setRef(ref);
        service.serveTransfer(dto);
        return "served successfully";
    }

    @PutMapping("/{ref}/revert")
    public RevertResponseDto revertTransfer(
            @PathVariable long ref,
            @RequestBody RevertDto dto
    ){
        dto.setRef(ref);
        return service.revertTransfer(dto);
    }

    @PutMapping("/{ref}/cancel")
    public CancelResponseDto cancelTransfer(
            @PathVariable long ref,
            @RequestBody CancelDto dto
    ){
        dto.setRef(ref);
        return service.cancelTransfer(dto);
    }


}