package ma.ensa.sironservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.services.SironService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/siron")
@RequiredArgsConstructor
public class SironController {

    private final SironService service;

    @GetMapping("/sender/{ref}")
    public boolean checkTransferForSender(
            @PathVariable("ref") long ref
    ){
        return service.isSenderBlackListed(ref);
    }

    @GetMapping("/recipient/{ref}")
    public boolean checkTransferForRecipient(
            @PathVariable("ref") long ref
    ){
        return service.isRecipientBlackListed(ref);
    }

}
