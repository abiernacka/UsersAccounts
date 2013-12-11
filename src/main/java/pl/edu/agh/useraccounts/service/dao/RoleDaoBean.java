package pl.edu.agh.useraccounts.service.dao;

import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.model.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class RoleDaoBean extends BaseDaoBean<Role> implements RoleDao {

    public RoleDaoBean() {
        super(Role.class);
    }

    @Override
    public List<String> getAllRoles() {
        List<Role> roles = getSessionFactory().getCurrentSession().createQuery("from " + Role.class.getName()).list();
        List<String> result = new ArrayList<String>();
        if(roles != null) {
            for(Role role: roles) {
                result.add(role.getName());
            }
        }

        return result;
    }

    @Override
    public Role getRoleForName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
