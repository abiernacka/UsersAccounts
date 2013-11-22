package pl.edu.agh.useraccounts.service;

import javax.jws.WebService;
import java.util.List;


@WebService
public interface RoleService {

    public int createRole(String role);

    public int removeRole(String role);

    public int addRole(String login, String role);

    public int revokeRole(String login, String role);

    public List<String> getUserRoles(String login);

    public List<String> getAllRoles();
}
