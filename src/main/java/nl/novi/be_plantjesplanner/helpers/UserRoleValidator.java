package nl.novi.be_plantjesplanner.helpers;

import java.util.Set;

public class UserRoleValidator {
    private static final Set<String> ROLES = Set.of("ADMIN","DESIGNER" );

    public static boolean isValidRole(String role){
        return ROLES.contains(role.toUpperCase());
    }
}
