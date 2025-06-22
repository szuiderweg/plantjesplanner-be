package nl.novi.be_plantjesplanner.dtos;

//DTO for authentication requests in AuthController
public class AuthRequest {
    private String username;
    private String password;

    //getters & setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
