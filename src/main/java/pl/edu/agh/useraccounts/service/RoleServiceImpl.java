package pl.edu.agh.useraccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;

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
    public int createRole(String role) {

        if(role == null || role.equals("")) {
            return 1;
        }
        return roleDao.createRole(role) ? 0 : 1;
    }

    @Override
    public int removeRole(String role) {
        if(role == null || role.equals("")) {
            return 1;
        }
        return roleDao.removeRole(role) ? 0 : 1;
    }

    @Override
    public int addRole(String login, String role) {
        if(login == null || login.equals("") || !userDao.userExists(login)) {
            return 1;
        }
        if(role == null || role.equals("") || !roleDao.roleExists(role)) {
            return 2;
        }

        return roleDao.addRole(login, role) ? 0 : 2;
    }

    @Override
    public int revokeRole(String login, String role) {
        if(login == null || login.equals("") || !userDao.userExists(login)) {
            return 1;
        }
        if(role == null || role.equals("") || !roleDao.roleExists(role)) {
            return 2;
        }

        if(!roleDao.hasUserRole(login, role)) {
            return 3;
        }

        return roleDao.revoke(login, role) ? 0 : 2;
    }

    @Override
    public List<String> getUserRoles(String login) throws UserException {
        if(login == null || login.equals("") || !userDao.userExists(login)) {
            throw new UserException().setExceptionCode(1);
        }

        return roleDao.getUserRoles(login);
    }

    @Override
    public List<String> getAllRoles() {
        return roleDao.getAllRoles();
    }
}


