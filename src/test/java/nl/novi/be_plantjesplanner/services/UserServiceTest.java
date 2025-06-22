package nl.novi.be_plantjesplanner.services;


import nl.novi.be_plantjesplanner.entities.User;
import nl.novi.be_plantjesplanner.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {
    @Test
    void testRegisterDesigner(){
        //test goal: password of new Designer-user is encrypted correctly,

        //arrange
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        UserService userService = new UserService(userRepository,passwordEncoder,jdbcTemplate);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));//let de mocked repository return the same user that was just "saved"


        User user = new User();
        user.setUsername("test_user");
        user.setPassword("supergeheim_wachtwoord");

        //act
        User newDesigner = userService.registerDesigner(user);

        //assert
        assertNotEquals("supergeheim_wachtwoord",newDesigner.getPassword()); //password must be mutated
        assertTrue(newDesigner.getPassword().startsWith("$2a$"));//bcrypt hash always starts with these characters
        assertTrue(passwordEncoder.matches("supergeheim_wachtwoord", newDesigner.getPassword()));//check if encoding the plain password yields the same result as the password saved in the repository
        //
    }
}
