package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.dtos.UserDto;
import nl.novi.be_plantjesplanner.entities.Authority;
import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.exceptions.DuplicateResourceException;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.DesignRepository;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final DesignRepository designRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, DesignRepository designRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.designRepository = designRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //register new user
    public void registerUser(UserDto userDto, String roleName){
        //check if user already exists
        if (userRepository.existsUserByUsername(userDto.getUsername())){
            throw new DuplicateResourceException("deze gebruiker bestaat al");
        }
        //create new user with authority
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        Authority authority = new Authority();
        authority.setAuthority(roleName);
        authority.setUser(user);

        user.setAuthorities(Set.of(authority));// Spring security expects a collection of authorities instead of a single authority

        //create new design for user
        Design design = new Design();
        design.setTitle("Prachtige tuin van "+user.getUsername());
        design.setGardenSize(0.0);

        user.setDesign(design);
        designRepository.save(design);
        //save user
        userRepository.save(user);
    }

public UserDto getMyUserByUsername(){

    // obtain User properties of current user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//obtain authentication object created in current HTTPrequest
    String username = authentication.getName();

    User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));

    //Map some userDetail data to a UserDto
    UserDto myUser = new UserDto();
    myUser.setUsername(foundUser.getUsername());
    myUser.setPassword("******");//
    myUser.setRole(extractRole(foundUser.getAuthorities()));
    return myUser;
    }



    //UserService helpers

    //extract role description as a String and remove the ROLE_ prefix from the authorities of a
    private String extractRole(Set<Authority> authorities) {
        for (Authority authority : authorities) {//iterate through the collection of authorities
            String name = authority.getAuthority();
            if (name.startsWith("ROLE_")) { //check prefix
                return name.substring(5);//return everything after "_" . return inside the for-loop because in this app Users have at most 1 authority (business rule)
            }
        }
        return null;
    }

}
