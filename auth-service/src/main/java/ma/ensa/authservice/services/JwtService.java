package ma.ensa.authservice.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.exceptions.TokenExpiredException;
import ma.ensa.authservice.services.JwtConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwt;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwt.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Date extractExpiration(String jwt){
        return extractClaim(jwt, Claims::getExpiration);
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt){

        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    // generate token

    public String generateToken(UserDetails userDetails){
        return generateToken(
                Collections.emptyMap(), userDetails
        );
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(jwt.getExpirationDate())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    private boolean isTokenExpired(String token){
        return extractExpiration(token)
                .before(new Date());
    }

    public void checkValidity(String token) {

        if(isTokenExpired(token))
            throw new TokenExpiredException();

    }
}