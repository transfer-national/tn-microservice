package ma.ensa.authservice.models;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@NoArgsConstructor
@SuperBuilder

@Entity
public class Wallet extends Account{}
