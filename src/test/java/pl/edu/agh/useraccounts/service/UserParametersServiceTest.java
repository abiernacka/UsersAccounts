package pl.edu.agh.useraccounts.service;

import junit.framework.Assert;
import org.junit.Test;
import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.LogEntry;
import pl.edu.agh.useraccounts.service.model.Parameters;
import pl.edu.agh.useraccounts.service.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created with IntelliJ IDEA.
 * User: abiernacka
 * Date: 08.12.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class UserParametersServiceTest {
    @Test
    public void getUserParamTest() throws UserException {
        UsersParametersServiceImpl userParamsService = new UsersParametersServiceImpl();
        userParamsService.userDao =  mock(UserDao.class);
        userParamsService.logDao = mock(LogEntryDao.class);
        User userHasParams = new User();
        Parameters parameters = new Parameters();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "jkowalski");
        parameters.setMap(map);
        userHasParams.setParameters(parameters);
        when(userParamsService.userDao.getUserForLogin("admin")).thenReturn(userHasParams);
        Assert.assertEquals("jkowalski", userParamsService.getUserParam("admin", "name"));
        when(userParamsService.userDao.getUserForLogin("lolek")).thenReturn(null);
        try {
            userParamsService.getUserParam("admin", "surname");
        } catch (UserException e) {
             Assert.assertEquals(2, e.getExceptionCode());
        }
        try {
            userParamsService.getUserParam("", "name");
        } catch (UserException e) {
            Assert.assertEquals(1, e.getExceptionCode());
        }
        try {
            userParamsService.getUserParam("admin", null);
        } catch (UserException e) {
            Assert.assertEquals(2, e.getExceptionCode());
        }
        try {
            userParamsService.getUserParam("lolek", "name");
        } catch (UserException e) {
            Assert.assertEquals(1, e.getExceptionCode());
        }
    }

    @Test
    public void getUserParams() throws UserException {
        UsersParametersServiceImpl userParamsService = new UsersParametersServiceImpl();
        userParamsService.userDao =  mock(UserDao.class);
        userParamsService.logDao = mock(LogEntryDao.class);
        User userHasParams = new User();
        Parameters parameters = new Parameters();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "jkowalski");
        parameters.setMap(map);
        userHasParams.setParameters(parameters);
        when(userParamsService.userDao.getUserForLogin("admin")).thenReturn(userHasParams);
        when(userParamsService.userDao.getUserForLogin("lolek")).thenReturn(null);
        Assert.assertEquals("jkowalski", userParamsService.getUserParams("admin").getMap().get("name"));
        try {
            userParamsService.getUserParams("admin");
        } catch (UserException e) {
            Assert.assertEquals(1, e.getExceptionCode());
        }
        try {
            userParamsService.getUserParams("lolek");
        } catch (UserException e) {
            Assert.assertEquals(1, e.getExceptionCode());
        }
    }

    @Test
    public void setUserParamTest() throws UserException {
        UsersParametersServiceImpl userParamsService = new UsersParametersServiceImpl();
        userParamsService.userDao =  mock(UserDao.class);
        userParamsService.logDao = mock(LogEntryDao.class);
        User user = new User();
        when(userParamsService.userDao.getUserForLogin("admin")).thenReturn(user);
        Assert.assertEquals(0, userParamsService.setUserParam("admin", "name", "jkowalski"));
        Assert.assertEquals("jkowalski", userParamsService.getUserParam("admin", "name"));
        when(userParamsService.userDao.getUserForLogin("lolek")).thenReturn(null);
        Assert.assertEquals(1, userParamsService.setUserParam("lolek", "name", "jkowalski"));
        Assert.assertEquals(1, userParamsService.setUserParam(null, "name", "jkowalski"));

    }

    @Test
    public void getLogsTest() throws UserException {
        UsersParametersServiceImpl userParamsService = new UsersParametersServiceImpl();
        userParamsService.logDao =  mock(LogEntryDao.class);
        List<LogEntry> logEntryList1 = null;
        List<LogEntry> logEntryList2 = new ArrayList<LogEntry>();
        List<LogEntry> logEntryList3 = new ArrayList<LogEntry>();
        LogEntry logEntry1 = mock(LogEntry.class);
        when(logEntry1.getLog()).thenReturn("User admin123 was autorized");
        when(logEntry1.getCreationDate()).thenReturn(new Date(90));
        LogEntry logEntry2 = mock(LogEntry.class);
        when(logEntry2.getLog()).thenReturn("User admin124 was autorized");
        when(logEntry2.getCreationDate()).thenReturn(new Date(100));
        logEntryList3.add(logEntry1);
        logEntryList3.add(logEntry2);
        when(userParamsService.logDao.getAllLogsSortedAscByDate()).thenReturn(logEntryList1).thenReturn(logEntryList2).thenReturn(logEntryList3);
        try {
            userParamsService.getLogs(new Date(90), new Date(80));
            Assert.fail();
        } catch (UserException e) {
        }
        Assert.assertEquals(userParamsService.getLogs(new Date(80), new Date(95)).size(), 0);
        Assert.assertEquals(userParamsService.getLogs(new Date(80), new Date(95)).size(), 0);
        Assert.assertEquals(userParamsService.getLogs(new Date(80), new Date(95)).size(), 1);
        Assert.assertEquals(userParamsService.getLogs(new Date(80), new Date(105)).size(), 2);

    }


}






