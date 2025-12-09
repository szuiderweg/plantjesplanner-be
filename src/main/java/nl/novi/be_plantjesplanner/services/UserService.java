package nl.novi.be_plantjesplanner.services;

import jakarta.transaction.Transactional;
import nl.novi.be_plantjesplanner.entities.Design;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.enumerations.Role;
import nl.novi.be_plantjesplanner.exceptions.RecordNotFoundException;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,JdbcTemplate jdbcTemplate){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    //register user with designer role
    @Transactional
    public User registerDesigner(User newUser){

            newUser.setRole(Role.DESIGNER);//set user properties
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));//encode password

            Design design = new Design();//initialize new default design
            design.setTitle("Mijn prachtige tuin");//default design title
            //set bidirectional one to one relation with Design
            design.setUser(newUser);
            newUser.setDesign(design);
            User savedUser = userRepository.save(newUser);
            // add authority via JDBC

            jdbcTemplate.update(
                    "INSERT INTO authorities (username, authority) VALUES (?, ?)",
                    savedUser.getUsername(), "ROLE_DESIGNER"
            );
            return savedUser;

    }
    public User findByUsername(String username) {
       Optional<User> userOptional = userRepository.findByUsername(username);
       if(userOptional.isPresent()){
           return userOptional.get();
       }
       else{
           throw new RecordNotFoundException("geen gebruiker gevonden met deze naam: "+ username);
       }
    }

    //for admins only
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //for admins only
    public User registerAdmin(User newAdmin){
        newAdmin.setRole(Role.ADMIN);//todo: moet ook authority toegekend krijgen. en hoe alleen de role en authority updaten? zie commented code hieronder
        return userRepository.save(newAdmin);
    }
//
//    @Transactional
//    public User promoteToAdmin(String username) {
//// Stap 1: Vind de gebruiker in de database
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
//
//        // Stap 2: Update rol naar ADMIN
//        user.setRole(Role.ADMIN); // Update lokale rol (voor frontend)
//        User savedUser = userRepository.save(user); // Sla aangepaste user op
//
//        // Stap 3: Verwijder bestaande ROLE_DESIGNER authority
//        jdbcTemplate.update(
//                "DELETE FROM authorities WHERE username = ? AND authority = ?",
//                savedUser.getUsername(), "ROLE_DESIGNER"
//        );
//
//        // Stap 4: Voeg nieuwe ROLE_ADMIN authority toe
//        jdbcTemplate.update(
//                "INSERT INTO authorities (username, authority) VALUES (?, ?) " +
//                        "ON CONFLICT (username, authority) DO NOTHING",
//                savedUser.getUsername(), "ROLE_ADMIN"
//        );
//
//        return savedUser;
//    }

}
