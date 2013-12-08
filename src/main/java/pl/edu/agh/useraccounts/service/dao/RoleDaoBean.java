package pl.edu.agh.useraccounts.service.dao;

import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.model.Role;

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
    public List<Role> getAllRoles() {
        return getSessionFactory().getCurrentSession().createQuery("from " + Role.class.getName()).list();
    }
}
