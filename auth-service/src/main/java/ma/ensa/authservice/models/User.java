package ma.ensa.authservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static jakarta.persistence.InheritanceType.JOINED;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Inheritance(strategy = JOINED)
@Entity
public abstract class User implements UserDetails {

    @Id
    private String id;

    private String password;

    @PrePersist
    public void init(){
        Random random = new Random();
        char r = getRole().name().toLowerCase().charAt(0);

        id = r + "-" + random.nextLong(
                1_000_000_000L,
                10_000_000_000L
        );
    }


    public Role getRole(){
        return Enum.valueOf(
            Role.class,
            this.getClass().getSimpleName().toUpperCase()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
