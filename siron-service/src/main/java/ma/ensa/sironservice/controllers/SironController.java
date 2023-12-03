package ma.ensa.sironservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.services.SironService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SironController {

    private final SironService service;


    @GetMapping("/transfer/{ref}")
    public boolean checkForTransfer(@PathVariable("ref") long ref){
        return service.checkByTransfer(ref);
    }

}
