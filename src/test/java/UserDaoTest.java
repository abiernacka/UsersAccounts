import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.model.Parameters;
import pl.edu.agh.useraccounts.service.model.Role;
import pl.edu.agh.useraccounts.service.model.User;

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
public class UserDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void testCRUD() {
        Assert.assertEquals(0, userDao.getAllUsers().size());
        User user = new User();
        user.setEmail("test@test.pl");
        user.setLogin("Testerek");
        user.setPassword("HasloDoTestow");

        userDao.save(user);
        List<User> users = userDao.getAllUsers();
        Assert.assertEquals(1, users.size());
        
        Assert.assertEquals("Testerek", users.get(0).getLogin());
        Assert.assertEquals("test@test.pl", users.get(0).getEmail());
        Assert.assertEquals("HasloDoTestow", users.get(0).getPassword());
        
        Assert.assertNotNull(users.get(0).getRoles());
        Assert.assertEquals(0, users.get(0).getRoles().size());

        //test add role
        Role role = new Role();
        role.setName("ROLE1");
        roleDao.save(role);
        user = users.get(0);
        user.getRoles().add(role);

        userDao.save(user);

        users = userDao.getAllUsers();
        user = users.get(0);
        Assert.assertEquals(1, user.getRoles().size());
        Assert.assertEquals("ROLE1", user.getRoles().get(0).getName());
    }

    @Test
    @Transactional
    public void testParameters() {
        Assert.assertEquals(0, userDao.getAllUsers().size());

        User user = new User();
        user.setEmail("test@test.pl");

        user.getParameters().getMap().put("age", "18");
        userDao.save(user);

        user = userDao.getAllUsers().get(0);
        Parameters params = user.getParameters();
        Assert.assertEquals("18", params.getMap().get("age"));
    }

    @Test
    @Transactional
    public void testGetUserForLogin() {
        User user = new User();
        user.setEmail("user@login.for");
        user.setLogin("foobar");
        userDao.save(user);

        User result = userDao.getUserForLogin("barfoo");
        Assert.assertNull(result);

        result = userDao.getUserForLogin("foobar");
        Assert.assertNotNull(result);
        Assert.assertEquals("foobar", result.getLogin());
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
