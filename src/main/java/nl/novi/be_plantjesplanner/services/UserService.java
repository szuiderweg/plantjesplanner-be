package nl.novi.be_plantjesplanner.services;

import nl.novi.be_plantjesplanner.entities.Design2;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.enumerations.Role;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //register user with designer role
    public User registerDesigner(User newUser){
        newUser.setRole(Role.DESIGNER);//set user properties
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));//encode password

        Design2 design = new Design2();//initialize new default design
        design.setTitle("Mijn prachtige tuin");
        design.setUser(newUser);

        newUser.setDesign2(design);
        return userRepository.save(newUser);
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
        newUser.setRole(Role.ADMIN);
        return userRepository.save(newUser);
    }



}
