package ma.ensa.transferservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.dto.tx.SendDto;
import ma.ensa.transferservice.dto.tx.TransferDto;
import ma.ensa.transferservice.dto.tx.TransferResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/transfer")
@Tag(
        name= "Transfer",
        description = "responsible for sending and manipulating a transfer"
)
public interface TransferController {

    @GetMapping
    List<TransferResponseDto> getAllTransfers(
            @RequestBody SearchFilter filter
    );

    @GetMapping("/resume")
    TransferResume getTransferResumeBySender(
            @RequestParam long senderId,
            @RequestParam int lastDays
    );

    @GetMapping("/{ref}")
    TransferResponseDto getTransfer(
            @PathVariable long ref
    );

    @PostMapping
    @ResponseStatus(CREATED)
    List<Long> emitTransfer(
            @RequestBody SendDto dto,
            @RequestHeader("By-User") String byUser
    );

    @PutMapping("/{ref}/{action}")
    Object updateStatus(
        @PathVariable(required = false) Long ref,
        @PathVariable String action,
        @RequestBody TransferDto dto
    );

}
