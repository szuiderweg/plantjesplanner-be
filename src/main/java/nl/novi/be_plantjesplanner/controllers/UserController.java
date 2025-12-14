package nl.novi.be_plantjesplanner.controllers;

import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder){
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    //register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody UserDto userDto){
        //check if user already exists using jdbcUserDetailsManager
        if (jdbcUserDetailsManager.userExists(userDto.getUsername())){
            return ResponseEntity.badRequest().body("deze gebruiker bestaat al");
        }
        else{
            jdbcUserDetailsManager.createUser(User.withUsername(userDto.getUsername()).password(passwordEncoder.encode(userDto.getPassword())).roles("DESIGNER").build());//new users are always assigned the role DESIGNER initially
            return ResponseEntity.ok("gebruiker aangemaakt: "+userDto.getUsername());
        }

    }

    //obtain requester's own user properties


}
