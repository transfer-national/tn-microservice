package ma.ensa.authservice.controllers;


import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public interface AuthController {

    @PostMapping("/login")
    AuthResponse login(@RequestBody AuthRequest dto);

    @GetMapping("/validate")
    AuthResponse validateToken(@RequestParam String token);

    @PutMapping("/password")
    String setPassword(@RequestBody AuthRequest dto);

}