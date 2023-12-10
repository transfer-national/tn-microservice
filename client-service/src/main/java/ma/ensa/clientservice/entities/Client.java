package ma.ensa.clientservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ref;
    private String titre;
    private String prenom;
    private String typeIdentite;
    private String paysEmission;
    private String pieceNumero;
    private String pieceValidite;
    private Date pieceDateExpiration;
    private Date ageLegalDate;
    private String pieceType;
    private String identiteNumero;
    private String identiteValidite;
    private Date dateNaissance;
    private String profession;
    private String nationalitePays;
    private String adressePays;
    private String adresseLegale;
    private String ville;
    private String gsm;
    private String email;

}
