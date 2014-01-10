package pl.edu.agh.useraccounts.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import junit.framework.Assert;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.Role;
import pl.edu.agh.useraccounts.service.model.User;

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
        when(roleService.roleDao.getRoleForName("admin")).thenReturn(null).thenReturn(new Role());
        roleService.logDao = mock(LogEntryDao.class);

        Assert.assertEquals(0, roleService.createRole("admin"));
        Assert.assertEquals(1, roleService.createRole("admin"));
        Assert.assertEquals(1, roleService.createRole(""));
        Assert.assertEquals(1, roleService.createRole(null));
    }

    @Test
    public void removeRoleTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.roleDao =  mock(RoleDao.class);
        roleService.logDao = mock(LogEntryDao.class);
        when(roleService.roleDao.getRoleForName("administrator")).thenReturn(new Role()).thenReturn(null);
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
        roleService.logDao = mock(LogEntryDao.class);
        User userHasRole = new User();
        List<Role> list = new ArrayList<Role>();
        Role role = new Role();
        role.setName("administrator");
        list.add(role);
        userHasRole.setRoles(list);
        User userHasNotRole = new User();
        when(roleService.userDao.getUserForLogin("admin")).thenReturn(userHasNotRole).thenReturn(userHasNotRole)
                .thenReturn(userHasNotRole).thenReturn(userHasNotRole).thenReturn(userHasRole).thenReturn(userHasRole)
                .thenReturn(userHasRole).thenReturn(userHasRole);
        when(roleService.userDao.getUserForLogin("linda")).thenReturn(null);
        when(roleService.roleDao.getRoleForName("administrator")).thenReturn(role).thenReturn(null);
        when(roleService.roleDao.getRoleForName("moderator")).thenReturn(null);

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
        roleService.logDao = mock(LogEntryDao.class);
        User userHasRole = new User();
        List<Role> list = new ArrayList<Role>();
        Role role = new Role();
        role.setName("administrator");
        list.add(role);
        userHasRole.setRoles(list);
        User userHasNotRole = new User();
        when(roleService.userDao.getUserForLogin("admin")).thenReturn(userHasRole).thenReturn(userHasNotRole)
                .thenReturn(userHasNotRole).thenReturn(userHasNotRole).thenReturn(userHasRole).thenReturn(userHasRole)
                .thenReturn(userHasRole).thenReturn(userHasRole);
        when(roleService.userDao.getUserForLogin("linda")).thenReturn(null);
        when(roleService.roleDao.getRoleForName("administrator")).thenReturn(role).thenReturn(role);
        when(roleService.roleDao.getRoleForName("moderator")).thenReturn(null);

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
      roleService.logDao = mock(LogEntryDao.class);
      User userHasRole = new User();
      List<Role> list = new ArrayList<Role>();
      Role role1 = new Role();
      role1.setName("moderator");
      Role role2 = new Role();
      role2.setName("moderator");
      list.add(role1);
      list.add(role2);
      userHasRole.setRoles(list);
      when(roleService.userDao.getUserForLogin("admin")).thenReturn(userHasRole);
      when(roleService.userDao.getUserForLogin("linda")).thenReturn(null);
      Assert.assertEquals(2, roleService.getUserRoles("admin").size());
      exception.expect(UserException.class);
      roleService.getUserRoles("linda");


  }
    @Test
    public void  getAllRolesTest() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.logDao = mock(LogEntryDao.class);
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("super user");
        roleService.roleDao =  mock(RoleDao.class);
        when(roleService.roleDao.getAllRoles()).thenReturn(roles);
        Assert.assertEquals(2, roleService.getAllRoles().size());
    }


}




