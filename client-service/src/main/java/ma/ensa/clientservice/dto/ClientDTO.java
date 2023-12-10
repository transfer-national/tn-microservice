package ma.ensa.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class ClientDTO {


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
