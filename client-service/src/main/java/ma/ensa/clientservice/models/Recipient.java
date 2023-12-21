package ma.ensa.clientservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rec_seq")
    @SequenceGenerator(name = "rec_seq", allocationSize = 1)
    private long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @ManyToOne
    private Client client;
}
