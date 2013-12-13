package pl.edu.agh.useraccounts.service;

import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.Parameters;
import pl.edu.agh.useraccounts.service.model.Role;
import pl.edu.agh.useraccounts.service.model.User;

import java.util.ArrayList;
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
        User user = new User();
        when(userParamsService.userDao.getUserForLogin("admin")).thenReturn(user);
        Assert.assertEquals(0, userParamsService.setUserParam("admin", "name", "jkowalski"));
        Assert.assertEquals("jkowalski", userParamsService.getUserParam("admin", "name"));
        when(userParamsService.userDao.getUserForLogin("lolek")).thenReturn(null);
        Assert.assertEquals(1, userParamsService.setUserParam("lolek", "name", "jkowalski"));
        Assert.assertEquals(1, userParamsService.setUserParam(null, "name", "jkowalski"));

    }


}




