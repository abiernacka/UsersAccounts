package pl.edu.agh.useraccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.LogEntry;
import pl.edu.agh.useraccounts.service.model.Role;
import pl.edu.agh.useraccounts.service.model.User;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja serwisu zarządzania rolami użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.RoleService")
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @Autowired
    LogEntryDao logDao;

    @Override
    public int createRole(String roleName) {
        LogEntry log = new LogEntry();
        String logString = "Próba utowrzenia roli " + roleName + ".";
        if(roleName == null || roleName.equals("")) {
            logString += " Próba nieudana (1): niepoprawna nazwa roli.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        if(roleDao.getRoleForName(roleName) != null) {
            logString += " Próba nieudana (1): rola już istnieje.";
            log.setLog(logString);
            logDao.save(log);
            return 1;//rola już istanieje
        } else {
            Role role = new Role();
            role.setName(roleName);
            roleDao.save(role);
            logString += " Zapisano.";
            log.setLog(logString);
            logDao.save(log);
            return 0;
        }
    }

    @Override
    public int removeRole(String roleName) {
        LogEntry log = new LogEntry();
        String logString = "Próba usunięcia roli " + roleName + ".";
        if(roleName == null || roleName.equals("")) {
            logString += " Próba nieudana (1): niepoprawna nazwa roli.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        if(roleDao.getRoleForName(roleName) != null) {
            roleDao.delete(roleDao.getRoleForName(roleName));
            logString += " Usunięto.";
            log.setLog(logString);
            logDao.save(log);
            return 0;
        } else {
            logString += " Próba nieudana (1): rola nieistnieje.";
            log.setLog(logString);
            logDao.save(log);
            return 1;    //rola nie istnieje
        }
    }

    @Override
    public int addRole(String login, String roleName) {
        User user = userDao.getUserForLogin(login);
        LogEntry log = new LogEntry();
        String logString = "Próba nadania roli " + roleName + " użytownikowi " + login + ".";
        if(login == null || login.equals("") || user == null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        Role role = roleDao.getRoleForName(roleName);
        if(roleName == null || roleName.equals("") || role == null) {
            logString += " Próba nieudana (2): niepoprawna nazwa roli.";
            log.setLog(logString);
            logDao.save(log);
            return 2;
        }
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userDao.save(user);
        logString += " Nadano rolę.";
        log.setLog(logString);
        logDao.save(log);
        return 0;
    }

    @Override
    public int revokeRole(String login, String roleName) {
        User user = userDao.getUserForLogin(login);
        LogEntry log = new LogEntry();
        String logString = "Próba odebrania roli " + roleName + " użytkownikowi " + login + ".";
        if(login == null || login.equals("") || user == null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;                           //user nie istnieje
        }
        Role role = roleDao.getRoleForName(roleName);
        if(roleName == null || roleName.equals("") || role == null) {
            logString += " Próba nieudana (1): niepoprawna nazwa roli.";
            log.setLog(logString);
            logDao.save(log);
            return 2;              //rola nie istnieje
        }

        List<Role> roles = user.getRoles();

        for(Role roleTmp: roles) {
            if(roleTmp.getName().equals(roleName)) {
                roles.remove(roleTmp);
                user.setRoles(roles);
                userDao.save(user);
                logString += " Odebrano rolę.";
                log.setLog(logString);
                logDao.save(log);
                return 0;
            }
        }
        logString += " Próba nieudana (1): użytkownik nie posiadał danej roli.";
        log.setLog(logString);
        logDao.save(log);
        return 3;         //user nie miał danej roli
    }

    @Override
    public List<String> getUserRoles(String login) throws UserException {
        User user = userDao.getUserForLogin(login);
        LogEntry log = new LogEntry();
        String logString = "Próba pobrania ról użytkownika " + login + ".";
        if(login == null || login.equals("") || user == null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            throw new UserException().setExceptionCode(1);      //nie ma takiego usera
        }
        List<String> roleNames = new ArrayList<String>();
        for(Role role: user.getRoles()) {
            roleNames.add(role.getName());
        }
        logString += " Pobrano listę ról użytkownika.";
        log.setLog(logString);
        logDao.save(log);
        return roleNames;
    }

    @Override
    @Transactional
    public List<String> getAllRoles() {
        LogEntry log = new LogEntry();
        String logString = "Próba pobrania dostępnej listy ról. Listę pobrano";
        System.out.println("getAkkRikes " + roleDao);
        log.setLog(logString);
        logDao.save(log);
        return roleDao.getAllRoles();
    }
}


