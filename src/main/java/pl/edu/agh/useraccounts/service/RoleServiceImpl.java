package pl.edu.agh.useraccounts.service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "pl.edu.agh.useraccounts.service")
public class RoleServiceImpl implements RoleService{

    public int createRole(String role) {
        return 0;
    }

    public int removeRole(String role) {
        return 0;
    }

    public int addRole(String login, String role) {
        return 0;
    }

    public int revokeRole(String login, String role) {
        return 0;
    }

    public List<String> getUserRoles(String login) {
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        return roles;
    }

    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        return roles;
    }
}
