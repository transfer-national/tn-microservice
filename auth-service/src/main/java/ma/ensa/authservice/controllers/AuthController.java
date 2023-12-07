package ma.ensa.authservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import ma.ensa.authservice.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;


    @GetMapping("/test")
    public String testGateway(){
        return "hello world";
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest dto
    ){
        return service.login(dto);
    }

    @GetMapping("/validate")
    public AuthResponse validateToken(@RequestParam String token){
        return service.checkToken(token);
    }

}
