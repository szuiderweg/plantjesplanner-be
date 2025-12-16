package nl.novi.be_plantjesplanner.controllers;


import nl.novi.be_plantjesplanner.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder){
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    //register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser( @RequestBody UserDto userDto){
        //check if user already exists using jdbcUserDetailsManager
        if (userDetailsManager.userExists(userDto.getUsername())){
            return ResponseEntity.badRequest().body("deze gebruiker bestaat al");
        }
        else{
            userDetailsManager.createUser(User.withUsername(userDto.getUsername()).password(passwordEncoder.encode(userDto.getPassword())).roles("DESIGNER").build());//new users are always assigned the role DESIGNER initially
            return ResponseEntity.ok("gebruiker aangemaakt: "+userDto.getUsername());
        }

    }

    //GET own User properties -- anyone with valid JWT
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyUser() {
        // obtain User properties of current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//obtain authentication object created in current HTTPrequest
        String username = authentication.getName();
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);//retrieve from the database the Userdetails that belong to the username from the authentication using JDBC

        //todo Map some userDetail data to a UserDto
        UserDto myUser = new UserDto();



        return ResponseEntity.ok(myUser);
    }


}



