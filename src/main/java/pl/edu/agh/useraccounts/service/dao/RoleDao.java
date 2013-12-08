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
    List<Role> getAllRoles();
}
