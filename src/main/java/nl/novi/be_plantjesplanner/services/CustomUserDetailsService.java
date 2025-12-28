package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RecordNotFoundException("gebruiker niet gevonden: "+ username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),user.isEnabled(),true, true, true,user.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).toList());

    }
}
