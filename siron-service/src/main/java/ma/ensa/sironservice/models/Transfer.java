package ma.ensa.sironservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Transfer {

    @Id
    private long ref;

    @ManyToOne
    private Client sender;

    @ManyToOne
    private Recipient recipient;


}
