package ma.ensa.clientservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.clientservice.models.enums.IdType;
import ma.ensa.clientservice.models.enums.Title;
import ma.ensa.clientservice.models.user.Agent;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cli_seq")
    @SequenceGenerator(name = "cli_seq", allocationSize = 1, initialValue = 2)
    private long ref;

    private Title title; // enum

    private String firstName; // firstName & lastName

    private String lastName;

    private IdType idType; // idType: Enum

    private String emitCountry; // emitCountry

    private String idNumber;

    private LocalDate idExpiration; // idExpiration

    private LocalDate dob;

    private String profession;

    private String nationality;

    private String address;

    private String city;

    private String country;

    private String gsm;

    private String email;

    @ManyToOne
    private Agent createdBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
