package ma.ensa.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class ClientDto {

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

    private String byAgentId; // id of the agent

    private boolean isExpired;

}
