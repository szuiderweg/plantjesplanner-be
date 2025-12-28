package nl.novi.be_plantjesplanner.controllers;


import nl.novi.be_plantjesplanner.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

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

        //Map some userDetail data to a UserDto
        UserDto myUser = new UserDto();
        myUser.setUsername(userDetails.getUsername());
        myUser.setPassword("******");//
        myUser.setRole(extractRole(userDetails.getAuthorities()));
        return ResponseEntity.ok(myUser);
    }

    //GET list of all usernames -- ADMIN only
//    public ResponseEntity<List<String>> getAllUsernames()
//    {
//        List<String> usernames = userDetailsManager.
//    }

    //UserController helpers

    //extract role description as a String and remove the ROLE_ prefix from the authorities of a Userdetails object (Spring security). "authorities" is a collection of type GrantedAuthority. In this application Users have at most 1 authority
    private String extractRole(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {//iterate through the collection of authorities
            String name = authority.getAuthority();
            if (name.startsWith("ROLE_")) { //check prefix
                return name.substring(5);//return everything after "_" . return inside the for-loop because in this app Users have at most 1 authority (business rule)
            }
        }
        return null;
    }
}



