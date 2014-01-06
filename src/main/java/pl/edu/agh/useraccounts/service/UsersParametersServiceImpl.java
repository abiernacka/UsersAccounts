package pl.edu.agh.useraccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.Parameters;
import pl.edu.agh.useraccounts.service.model.User;

import javax.jws.WebService;
import java.util.*;

/**
 * Implementacja serwisu zarządzania parametrami użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.UsersParametersService")
@Transactional
public class UsersParametersServiceImpl implements UsersParametersService {

    @Autowired
    UserDao userDao;

    @Override
    public String getUserParam(String login, String paramKey) throws UserException {
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            throw new UserException().setExceptionCode(1);      //nie ma takiego usera;
        }
        Parameters parameters = user.getParameters();
        if(paramKey == null || parameters == null) {
            throw new UserException().setExceptionCode(2);         //brak parametrow
        }
        HashMap<String, String> parametersMap = parameters.getMap();
        if(parametersMap == null) {
            throw new UserException().setExceptionCode(2);         //brak parametrow
        }
        String paramValue = parametersMap.get(paramKey);
        if(paramValue == null) {
            throw new UserException().setExceptionCode(2);         //brak parametru
        } else {
            return paramValue;
        }
    }

    @Override
    public Parameters getUserParams(String login) throws UserException {
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            throw new UserException().setExceptionCode(1);      //nie ma takiego usera;
        }
        return user.getParameters();

    }

    @Override
    public int setUserParam(String login, String paramKey, String paramValue) {
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            return 1;      //nie ma takiego usera;
        }
        Parameters parameters = user.getParameters();
        if(parameters == null) {
           parameters = new Parameters();
        }
        HashMap<String, String> parametersMap = parameters.getMap();
        if(parametersMap == null) {
           parametersMap = new HashMap<String, String>();
        }
        parametersMap.put(paramKey,paramValue);
        parameters.setMap(parametersMap);
        user.setParameters(parameters);
        userDao.save(user);
        return 0;
    }

    @Override
    public List<String> getLogs(Date startTime, Date endTime) throws UserException {
        List<String> logs = new ArrayList<String>();
        logs.add("User jkowalski authorization: 12:03 12.03.2013");
        logs.add("User jkowalska authorization: 14:03 12.03.2013");
        return logs;
    }

}
