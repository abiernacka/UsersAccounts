package pl.edu.agh.useraccounts.service;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl {

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
