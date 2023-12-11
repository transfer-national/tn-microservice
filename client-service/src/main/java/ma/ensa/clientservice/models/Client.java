package ma.ensa.clientservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.clientservice.models.enums.IdType;
import ma.ensa.clientservice.models.enums.Title;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ref;

    private Title title; // enum

    private String firstName; // firstName & lastName

    private String lastName;

    private IdType idType; // idType: Enum

    private String emitCountry; // emitCountry

    private String idNumber;

    private Date idExpiration; // idExpiration

    private Date dob;

    private String profession;

    private String nationality;

    private String address;

    private String city;

    private String country;

    private String gsm;

    private String email;

}
