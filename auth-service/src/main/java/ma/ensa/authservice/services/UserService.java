package ma.ensa.authservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.exceptions.UserNotFoundException;
import ma.ensa.authservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}