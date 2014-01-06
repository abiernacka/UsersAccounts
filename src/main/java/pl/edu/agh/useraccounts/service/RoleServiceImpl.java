package pl.edu.agh.useraccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.Role;
import pl.edu.agh.useraccounts.service.model.User;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja serwisu zarządzania rolami użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.RoleService")
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @Override
    public int createRole(String roleName) {

        if(roleName == null || roleName.equals("")) {
            return 1;
        }
        if(roleDao.getRoleForName(roleName) != null) {
            return 1;//rola już istanieje
        } else {
            Role role = new Role();
            role.setName(roleName);
            roleDao.save(role);
            return 0;
        }
    }

    @Override
    public int removeRole(String roleName) {
        if(roleName == null || roleName.equals("")) {
            return 1;
        }
        if(roleDao.getRoleForName(roleName) != null) {
            roleDao.delete(roleDao.getRoleForName(roleName));
            return 0;
        } else {
            return 1;    //rola nie istnieje
        }
    }

    @Override
    public int addRole(String login, String roleName) {
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            return 1;
        }
        Role role = roleDao.getRoleForName(roleName);
        if(roleName == null || roleName.equals("") || role == null) {
            return 2;
        }
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userDao.save(user);
        return 0;
    }

    @Override
    public int revokeRole(String login, String roleName) {
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            return 1;                           //user nie istnieje
        }
        Role role = roleDao.getRoleForName(roleName);
        if(roleName == null || roleName.equals("") || role == null) {
            return 2;              //rola nie istnieje
        }

        List<Role> roles = user.getRoles();

        for(Role roleTmp: roles) {
            if(roleTmp.getName().equals(roleName)) {
                roles.remove(roleTmp);
                user.setRoles(roles);
                userDao.save(user);
                return 0;
            }
        }
        roles.add(role);
        return 3;         //user nie miał danej roli
    }

    @Override
    public List<String> getUserRoles(String login) throws UserException {
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            throw new UserException().setExceptionCode(1);      //nie ma takiego usera
        }
        List<String> roleNames = new ArrayList<String>();
        for(Role role: user.getRoles()) {
            roleNames.add(role.getName());
        }
        return roleNames;
    }

    @Override
    public List<String> getAllRoles() {
        return roleDao.getAllRoles();
    }
}


