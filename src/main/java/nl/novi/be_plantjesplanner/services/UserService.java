package nl.novi.be_plantjesplanner.services;

import jakarta.transaction.Transactional;
import nl.novi.be_plantjesplanner.entities.Design2;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.enumerations.Role;
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

            newUser.setRole(Role.ROLE_DESIGNER);//set user properties
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));//encode password

            Design2 design = new Design2();//initialize new default design
            design.setTitle("Mijn prachtige tuin");
            //set bidirectional one to one relation with Design
            design.setUser(newUser);
            newUser.setDesign2(design);
            User savedUser = userRepository.save(newUser);
            // Voeg authority toe via JDBC

            jdbcTemplate.update(
                    "INSERT INTO authorities (username, authority) VALUES (?, ?)",
                    savedUser.getUsername(), "ROLE_DESIGNER"
            );
            return savedUser;

    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //for admins only
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //for admins only
    public User registerAdmin(User newUser){
        newUser.setRole(Role.ROLE_ADMIN);
        return userRepository.save(newUser);
    }



}
