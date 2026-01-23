package nl.novi.be_plantjesplanner.controllers;
import nl.novi.be_plantjesplanner.dtos.UserDto;
import nl.novi.be_plantjesplanner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //register new user with designer Role
    @PostMapping("/register")
    public ResponseEntity<String> registerNewDesigner( @RequestBody UserDto userDto){

           userService.registerUser(userDto, "ROLE_DESIGNER" );
            return ResponseEntity.ok("gebruiker aangemaakt: "+userDto.getUsername());
    }
    //register new user with admin Role (admin only)
    @PostMapping("/admin")
    public ResponseEntity<String> registerNewAdmin( @RequestBody UserDto userDto){

        userService.registerUser(userDto, "ROLE_ADMIN");
        return ResponseEntity.ok("gebruiker aangemaakt: "+userDto.getUsername());
    }

    //GET own User properties -- anyone with valid JWT
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyUser() {
        UserDto myUser = userService.getMyUserByUsername();
        return ResponseEntity.ok(myUser);
    }
}



