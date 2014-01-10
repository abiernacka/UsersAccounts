package pl.edu.agh.useraccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.LogEntry;
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

    @Autowired
    LogEntryDao logDao;

    @Override
    public String getUserParam(String login, String paramKey) throws UserException {
        User user = userDao.getUserForLogin(login);
        LogEntry log = new LogEntry();
        String logString = "Użytkownik o loginie \"" + login + " próbował pobrać parametr " + paramKey + ".";
        if(login == null || login.equals("") || user == null) {
            logString += " Próba nieudana (1): błędny login.";
            log.setLog(logString);
            logDao.save(log);
            throw new UserException().setExceptionCode(1);      //nie ma takiego usera;
        }
        Parameters parameters = user.getParameters();
        if(paramKey == null || parameters == null) {
            logString += " Próba nieudana (2): brak parametrów.";
            log.setLog(logString);
            logDao.save(log);
            throw new UserException().setExceptionCode(2);         //brak parametrow
        }
        HashMap<String, String> parametersMap = parameters.getMap();
        if(parametersMap == null) {
            logString += " Próba nieudana (2): brak parametrów.";
            log.setLog(logString);
            logDao.save(log);
            throw new UserException().setExceptionCode(2);         //brak parametrow
        }
        String paramValue = parametersMap.get(paramKey);
        if(paramValue == null) {
            logString += " Próba nieudana (3): brak parametru.";
            log.setLog(logString);
            logDao.save(log);
            throw new UserException().setExceptionCode(2);         //brak parametru
        } else {
            logString += " Pobrano parametr";
            log.setLog(logString);
            logDao.save(log);
            return paramValue;
        }
    }

    @Override
    public Parameters getUserParams(String login) throws UserException {
        User user = userDao.getUserForLogin(login);
        LogEntry log = new LogEntry();
        String logString = "Użytkownik o loginie \"" + login + " próbował pobrać listę swoich parametrów.";
        if(login == null || login.equals("") || user == null) {
            logString += " Próba nieudana (1): błędny login.";
            log.setLog(logString);
            logDao.save(log);
            throw new UserException().setExceptionCode(1);      //nie ma takiego usera;
        }
        logString += " Pobrano listę parametrów.";
        log.setLog(logString);
        logDao.save(log);
        return user.getParameters();

    }

    @Override
    public int setUserParam(String login, String paramKey, String paramValue) {
        LogEntry log = new LogEntry();
        String logString = "Użytkownik o loginie \"" + login + " próbował ustawić parametr " + paramKey + " na wartość " + paramValue + ".";
        User user = userDao.getUserForLogin(login);
        if(login == null || login.equals("") || user == null) {
            logString += " Próba nieudana (1): błędny login.";
            log.setLog(logString);
            logDao.save(log);
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
        logString += " Ustawiono (0).";
        log.setLog(logString);
        logDao.save(log);
        return 0;
    }

    @Override
    public List<String> getLogs(Date startTime, Date endTime) throws UserException {
        if (startTime.getTime() >= endTime.getTime()) {
            throw new UserException();
        }
        List<String> logs = new ArrayList<String>();
        List<LogEntry> logEntryList = logDao.getAllLogsSortedAscByDate();
        if(logEntryList == null || logEntryList.size() == 0) {
            return logs;
        }
        for(LogEntry logEntry: logEntryList) {
            if(logEntry.getCreationDate().getTime() >= startTime.getTime() && logEntry.getCreationDate().getTime() <= endTime.getTime()) {
                logs.add(logEntry.getLog());
            }
        }

        return logs;
    }

}
