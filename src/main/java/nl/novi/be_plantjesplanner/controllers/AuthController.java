package nl.novi.be_plantjesplanner.controllers;

import nl.novi.be_plantjesplanner.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import nl.novi.be_plantjesplanner.dtos.AuthRequestDto;

//authenticate user and provide JWT token using Spring's inbuilt Userdetails feature and JDBC authentication
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,UserDetailsService userDetailsService){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthRequestDto authRequestDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDto.getUsername());
        return jwtUtil.generateToken(userDetails);
    }



}
