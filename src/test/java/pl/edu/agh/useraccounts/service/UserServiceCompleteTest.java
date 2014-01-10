package pl.edu.agh.useraccounts.service;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.exceptions.UserException;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.test.cfg.xml")
public class UserServiceCompleteTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogEntryDao logDao;
    
    @Test
    @Transactional
    public void test1() throws UserException {
        UsersParametersServiceImpl usersParametersService = new UsersParametersServiceImpl();
        usersParametersService.logDao = logDao;
        UserServiceImpl userService = new UserServiceImpl();
        userService.userDao = userDao;
        userService.logDao = logDao;
        usersParametersService.userDao = userDao;
        Assert.assertEquals(0, userService.register("admin123", "admin@gmail.com", "Hqfksjdj123"));
        try {
            usersParametersService.getUserParam("admin112", "name");
        } catch(UserException e) {
            Assert.assertEquals(e.getExceptionCode(), 1);
        }
        try {
            usersParametersService.getUserParam("admin123", "name");
        } catch(UserException e) {
            Assert.assertEquals(e.getExceptionCode(), 2);
        }
        Assert.assertEquals(0, usersParametersService.getUserParams("admin123").getMap().size());
        Assert.assertEquals(1, usersParametersService.setUserParam("admin122","name", "Jan"));
        Assert.assertEquals(0, usersParametersService.setUserParam("admin123","name", "Jan"));
        Assert.assertEquals(1, usersParametersService.getUserParams("admin123").getMap().size());
        Assert.assertEquals("Jan", usersParametersService.getUserParam("admin123", "name"));
        try {
            usersParametersService.getUserParams("admin122");
        } catch(UserException e) {
            Assert.assertEquals(e.getExceptionCode(), 1);
        }

    }

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setLogDao(LogEntryDao logDao) {
		this.logDao = logDao;
	}
    
    
}
