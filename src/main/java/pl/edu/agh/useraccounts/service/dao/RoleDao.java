package pl.edu.agh.useraccounts.service.dao;

import pl.edu.agh.useraccounts.service.model.Role;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public interface RoleDao extends BaseDao<Role> {
    List<String> getAllRoles();
    boolean createRole(String roleName);
    boolean removeRole(String roleName);
    boolean roleExists(String roleName);
    boolean addRole(String login, String roleName);
    boolean revoke(String login, String role);
    boolean hasUserRole(String login, String role);
    List<String> getUserRoles(String login);

}

