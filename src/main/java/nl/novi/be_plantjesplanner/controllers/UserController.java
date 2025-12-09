package nl.novi.be_plantjesplanner.controllers;
import jakarta.validation.Valid;
import nl.novi.be_plantjesplanner.dtos.UserDto;
import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.helpers.Mapper;
import nl.novi.be_plantjesplanner.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //for anyone without credentials
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerDesigner(@Valid @RequestBody UserDto userDto){
        User user = Mapper.mapFromUserDto(userDto);
        User savedUser = userService.registerDesigner(user);
        UserDto responseDto = Mapper.mapToUserDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    //----for admins and designers
    //GET own User properties
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyUser() {
        // obtain User properties of current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//obtain authentication object created in current HTTPrequest
        String username = authentication.getName();
        User myUser = userService.findByUsername(username);
        return ResponseEntity.ok(Mapper.mapToUserDto(myUser));
    }

    //edit eigen account: wachtwoord resetten
    //eigen account deleten


    //---for designers only
    //

    //-----for logged in user or admin


    //for admins only
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        List<UserDto> allUsersDto = new ArrayList<>();
        for(User user : allUsers){
            allUsersDto.add(Mapper.mapToUserDto(user));
        }
        return ResponseEntity.ok().body(allUsersDto);
    }
    //andere user opzoeken: users/{id}
    //password van een ander resetten
    //nieuw admin account maken
    //account van een ander verwijderen

}
