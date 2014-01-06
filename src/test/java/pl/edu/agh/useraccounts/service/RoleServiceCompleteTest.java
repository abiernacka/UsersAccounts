package pl.edu.agh.useraccounts.service;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.Role;

import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.test.cfg.xml")
public class RoleServiceCompleteTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void test1() throws UserException {
        RoleServiceImpl roleService = new RoleServiceImpl();
        UserServiceImpl userService = new UserServiceImpl();
        userService.userDao = userDao;
        roleService.roleDao =  roleDao;
        roleService.userDao =  userDao;
        Assert.assertEquals(0, userService.register("admin123", "admin@gmail.com", "Hqfksjdj123"));
        Assert.assertEquals(0, roleService.getAllRoles().size());
        Assert.assertEquals(0, roleService.createRole("admin"));
        Assert.assertEquals(1, roleService.getAllRoles().size());
        Assert.assertEquals(1, roleService.createRole("admin"));
        Assert.assertEquals(0, roleService.getUserRoles("admin123").size());
        Assert.assertEquals(0, roleService.addRole("admin123", "admin"));
        Assert.assertEquals(1, roleService.addRole("admin12", "admin"));
        Assert.assertEquals(2, roleService.addRole("admin123", "adminn"));
        Assert.assertEquals(1, roleService.getUserRoles("admin123").size());
        Assert.assertEquals(0, roleService.revokeRole("admin123", "admin"));
        Assert.assertEquals(1, roleService.revokeRole("admin12", "admin"));
        Assert.assertEquals(2, roleService.revokeRole("admin123", "adminn"));
        Assert.assertEquals(3, roleService.revokeRole("admin123", "admin"));
        Assert.assertEquals(0, roleService.getUserRoles("admin123").size());
        Assert.assertEquals(0, roleService.removeRole("admin"));
        Assert.assertEquals(1, roleService.removeRole("admin"));
        Assert.assertEquals(0, roleService.getAllRoles().size());
    }


    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
