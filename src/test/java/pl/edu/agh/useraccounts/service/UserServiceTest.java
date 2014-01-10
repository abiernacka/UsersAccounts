/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.edu.agh.useraccounts.service;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.model.User;

/**
 *
 * @author Marcin
 */
public class UserServiceTest {
    
    @Test
    public void registerTest() {
        UserServiceImpl usi = new UserServiceImpl();
        usi.userDao = mock(UserDao.class);
        when(usi.userDao.getUserForLogin("jkowalski")).thenReturn(null).thenReturn(new User());
        Assert.assertEquals(0, usi.register("jkowalski", "kowalski@gmail.com", "qWeRtY1234"));
        Assert.assertEquals(1, usi.register("jkow@alski1", "kowalski@gmail.com", "qWeRtY.1234"));
        Assert.assertEquals(2, usi.register("jkowalski2", "kowalski-gmail.com", "qWeRtY.1234"));
        Assert.assertEquals(3, usi.register("jkow#alski3", "kowalski-gmail.com", "qWeRtY.1234"));
        Assert.assertEquals(4, usi.register("jkowalski4", "kowalski@gmail.com", "qWeRtY.12%^&34"));
        Assert.assertEquals(5, usi.register("jko%alski5", "kowalski@gmail.com", "qWeRtY*1234"));
        Assert.assertEquals(6, usi.register("jkowalski6", "kowalski-gmail,com", "qWeR*tY1234"));
        Assert.assertEquals(7, usi.register("jkowa*lski7", "kowal*ski@gmail.com", "qWe*RtY1234"));
        Assert.assertEquals(8, usi.register("jkowalski", "kowalski@gmail.com", "qWeRtY1234"));
        Assert.assertEquals(10, usi.register("jkowalski", "kowalsk#gmailcom", "qWeRtY1234"));
        Assert.assertEquals(12, usi.register("jkowalski", "kowalski@gmail.com", "qWe#RtY1234"));
        Assert.assertEquals(14, usi.register("jkowalski", "kowalski!gmail.com", "qWeRtY^1234"));
    }
    
    @Test
    public void authorizationTest(){
        UserServiceImpl usi = new UserServiceImpl();
        usi.userDao = mock(UserDao.class);
        
        User u = new User();
        u.setPassword("qwerty");
        u.setLogin("jkowalski");
        
        when(usi.userDao.getUserForLogin("jkowalski")).thenReturn(null).thenReturn(u);
        
        Assert.assertEquals(1, usi.authorization("jkowalski", "qwerty"));
        Assert.assertEquals(2, usi.authorization("jkowalski", "qwerty2"));
        Assert.assertEquals(0, usi.authorization("jkowalski", "qwerty"));  
    }
    
    @Test
    public void getUsersTest(){
        UserServiceImpl userService = new UserServiceImpl();
        
        List<User> u = new ArrayList<User>();
        
        u.add(new User());
        
        userService.userDao =  mock(UserDao.class);
        
        when(userService.userDao.getAllUsers()).thenReturn(u);
        
        Assert.assertEquals(1, userService.getUsers().size());
    }
    
    @Test
    public void remindPasswordTest(){
        UserServiceImpl usi = new UserServiceImpl();
        usi.userDao = mock(UserDao.class);
        
        User u = new User();
        u.setPassword("qwerty");
        u.setLogin("jkowalski");
        u.setEmail("jkowalski@userapp.agh.edu.pl");
        
        when(usi.userDao.getUserForLogin("jkowalski")).thenReturn(null).thenReturn(u);
        
        Assert.assertEquals(1, usi.remindPassword("jkowalski"));
        Assert.assertEquals(0, usi.remindPassword("jkowalski"));  
    }
    
    @Test
    public void changePasswordTest(){
        UserServiceImpl usi = new UserServiceImpl();
        usi.userDao = mock (UserDao.class);
        
        User u = new User();
        u.setPassword("qwertyuiop");
        u.setLogin("jkowalski");
        
        when(usi.userDao.getUserForLogin("jkowalski")).thenReturn(u);
        
        Assert.assertEquals(0, usi.changePassword("jkowalski", "qwertyuiop", "ksiezniczka"));
        Assert.assertEquals(1, usi.changePassword("mareczek", "qwertyuiop", "ksiezniczka"));
        Assert.assertEquals(2, usi.changePassword("jkowalski", "qwerty123", "ksiezniczka"));
        Assert.assertEquals(3, usi.changePassword("jkowalski", "ksiezniczka", "ksiezniczka$"));
        Assert.assertEquals(4, usi.changePassword("jkowalski", "qwerty123", "ksiezniczka#$%"));
    }
    
    @Test
    public void changeEmailTest(){
        UserServiceImpl usi = new UserServiceImpl();
        usi.userDao = mock (UserDao.class);
        
        User u = new User();
        u.setPassword("qwertyuiop");
        u.setLogin("jkowalski");
        u.setEmail("jkowalski@o2.pl");
        
        when(usi.userDao.getUserForLogin("jkowalski")).thenReturn(null).thenReturn(u);
        
        Assert.assertEquals(1, usi.changeEmail("jkowalski", "janeczek@buziaczek.pl"));
        Assert.assertEquals(0, usi.changeEmail("jkowalski", "janeczek@buziaczek.pl"));
        Assert.assertEquals(2, usi.changeEmail("jkowalski", "kazik&janek-wp.pl"));
    }
    
    @Test
    public void removeUserTest(){
        UserServiceImpl usi = new UserServiceImpl();
        usi.userDao = mock (UserDao.class);
        
        User u = new User();
        u.setPassword("qwertyuiop");
        u.setLogin("jkowalski");
        u.setEmail("jkowalski@o2.pl");
        
        when(usi.userDao.getUserForLogin("jkowalski")).thenReturn(u);
        
        Assert.assertEquals(1, usi.removeUser("adelajda"));
        Assert.assertEquals(0, usi.removeUser("jkowalski"));
    }
}
