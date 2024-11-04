package dk.obhnothing.security.daos;

import dk.bugelhartmann.UserDTO;
import dk.obhnothing.security.entities.User;
import dk.obhnothing.security.exceptions.ValidationException;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    User addRole(UserDTO user, String newRole);
}
