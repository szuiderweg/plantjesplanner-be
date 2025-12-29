package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.entities.Authority;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.dtos.UserDto;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser( @RequestBody UserDto userDto){
        //check if user already exists using jdbcUserDetailsManager
        if (userRepository.existsUserByUsername(userDto.getUsername())){
            return ResponseEntity.badRequest().body("deze gebruiker bestaat al");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        Authority authority = new Authority();
        authority.setAuthority("ROLE_DESIGNER");
        authority.setUser(user);

        user.setAuthorities(Set.of(authority));

        userRepository.save(user);

            return ResponseEntity.ok("gebruiker aangemaakt: "+userDto.getUsername());

    }

    //GET own User properties -- anyone with valid JWT
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyUser() {
        // obtain User properties of current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//obtain authentication object created in current HTTPrequest
        String username = authentication.getName();

        User foundUser = userRepository.findByUsername(username).orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));

        //Map some userDetail data to a UserDto
        UserDto myUser = new UserDto();
        myUser.setUsername(foundUser.getUsername());
        myUser.setPassword("******");//
        myUser.setRole(extractRole(foundUser.getAuthorities()));
        return ResponseEntity.ok(myUser);
    }

    //GET list of all usernames -- ADMIN only
//    public ResponseEntity<List<String>> getAllUsernames()
//    {
//        List<String> usernames = userDetailsManager.
//    }

    //UserController helpers

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



