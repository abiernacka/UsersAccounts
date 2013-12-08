import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.RoleDaoBean;
import pl.edu.agh.useraccounts.service.model.Role;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.test.cfg.xml")
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @Transactional
    public void testCRUD() {
        Assert.assertEquals(0, roleDao.getAllRoles().size());
        Role role = new Role();
        role.setName("TEST_ROLE1");
        roleDao.save(role);

        List<Role> roles = roleDao.getAllRoles();
        Assert.assertEquals(1, roles.size());
        Assert.assertEquals("TEST_ROLE1", roles.get(0).getName());

        roleDao.delete(roles.get(0));
        roles = roleDao.getAllRoles();
        Assert.assertEquals(0, roles.size());
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
