package nl.novi.be_plantjesplanner.dtos;


import org.springframework.lang.Nullable;

public class UserDto{
    private String username;
    @Nullable
    private String password;
    @Nullable
    private String role;

    //getters & setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
