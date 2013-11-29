package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja serwisu zarządzania rolami użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.RoleService")
public class RoleServiceImpl implements RoleService{

    @Override
    public int createRole(String role) {
        return 0;
    }

    @Override
    public int removeRole(String role) {
        return 0;
    }

    @Override
    public int addRole(String login, String role) {
        return 0;
    }

    @Override
    public int revokeRole(String login, String role) {
        return 0;
    }

    @Override
    public List<String> getUserRoles(String login) throws UserException {
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("super user");
        return roles;
    }

    @Override
    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("super user");
        return roles;
    }
}
