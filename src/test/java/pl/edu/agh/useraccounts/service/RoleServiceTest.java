package pl.edu.agh.useraccounts.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import junit.framework.Assert;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: abiernacka
 * Date: 08.12.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class RoleServiceTest {

    @Test
    public void createRoleTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.roleDao =  mock(RoleDao.class);
        when(roleService.roleDao.createRole("admin")).thenReturn(true).thenReturn(false);
        Assert.assertEquals(0, roleService.createRole("admin"));
        Assert.assertEquals(1, roleService.createRole("admin"));
        Assert.assertEquals(1, roleService.createRole(""));
        Assert.assertEquals(1, roleService.createRole(null));
    }

    @Test
    public void removeRoleTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.roleDao =  mock(RoleDao.class);
        when(roleService.roleDao.removeRole("administrator")).thenReturn(true).thenReturn(false);
        Assert.assertEquals(0, roleService.removeRole("administrator"));
        Assert.assertEquals(1, roleService.removeRole("administrator"));
        Assert.assertEquals(1, roleService.removeRole(""));
        Assert.assertEquals(1, roleService.removeRole(null));
    }

    @Test
    public void addRoleTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.roleDao =  mock(RoleDao.class);
        roleService.userDao =  mock(UserDao.class);
        when(roleService.userDao.userExists("admin")).thenReturn(true);
        when(roleService.userDao.userExists("linda")).thenReturn(false);
        when(roleService.roleDao.roleExists("administrator")).thenReturn(true);
        when(roleService.roleDao.roleExists("moderator")).thenReturn(false);
        when(roleService.roleDao.addRole("admin", "administrator")).thenReturn(true).thenReturn(false);

        Assert.assertEquals(0, roleService.addRole("admin", "administrator"));
        Assert.assertEquals(2, roleService.addRole("admin", "administrator"));
        Assert.assertEquals(1, roleService.addRole("linda", "moderator"));
        Assert.assertEquals(1, roleService.addRole("linda", null));
        Assert.assertEquals(1, roleService.addRole("", "moderator"));
        Assert.assertEquals(2, roleService.addRole("admin", ""));
        Assert.assertEquals(1, roleService.addRole(null, "moderator"));
        Assert.assertEquals(2, roleService.addRole("admin", null));
    }

    @Test
    public void revokeRoleTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.roleDao =  mock(RoleDao.class);
        roleService.userDao =  mock(UserDao.class);
        when(roleService.userDao.userExists("admin")).thenReturn(true);
        when(roleService.userDao.userExists("linda")).thenReturn(false);
        when(roleService.roleDao.roleExists("administrator")).thenReturn(true);
        when(roleService.roleDao.roleExists("moderator")).thenReturn(false);
        when(roleService.roleDao.revoke("admin", "administrator")).thenReturn(true).thenReturn(false);
        when(roleService.roleDao.hasUserRole("admin", "administrator")).thenReturn(true).thenReturn(false);

        Assert.assertEquals(0, roleService.revokeRole("admin", "administrator"));
        Assert.assertEquals(3, roleService.revokeRole("admin", "administrator"));
        Assert.assertEquals(1, roleService.revokeRole("linda", "moderator"));
        Assert.assertEquals(1, roleService.revokeRole("linda", null));
        Assert.assertEquals(1, roleService.revokeRole("", "moderator"));
        Assert.assertEquals(2, roleService.revokeRole("admin", ""));
        Assert.assertEquals(1, roleService.revokeRole(null, "moderator"));
        Assert.assertEquals(2, roleService.revokeRole("admin", null));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void  getUserRoleTest() throws UserException {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("super user");
        roleService.roleDao =  mock(RoleDao.class);
        roleService.userDao =  mock(UserDao.class);
        when(roleService.userDao.userExists("admin")).thenReturn(true);
        when(roleService.userDao.userExists("linda")).thenReturn(false);
        when(roleService.roleDao.getUserRoles("admin")).thenReturn(roles);
        Assert.assertEquals(2, roleService.getUserRoles("admin").size());
        exception.expect(UserException.class);
        roleService.getUserRoles("linda");


    }

    @Test
    public void  getAllRolesTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("super user");
        roleService.roleDao =  mock(RoleDao.class);
        when(roleService.roleDao.getAllRoles()).thenReturn(roles);
        Assert.assertEquals(2, roleService.getAllRoles().size());
    }


}



