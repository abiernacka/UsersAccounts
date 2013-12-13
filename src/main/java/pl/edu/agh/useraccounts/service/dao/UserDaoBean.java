package pl.edu.agh.useraccounts.service.dao;

import pl.edu.agh.useraccounts.service.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoBean extends BaseDaoBean<User> implements UserDao {

    public UserDaoBean() {
        super(User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return getSessionFactory().getCurrentSession().createQuery("from " + User.class.getName()).list();
    }

    @Override
    public User getUserForLogin(String login) {
        List<User> userList = getSessionFactory().getCurrentSession().createQuery("from " + User.class.getName() + " u where u.login ='" + login + "'").list();
        User user = null;
        if(userList != null && !userList.isEmpty()) {
            user = userList.get(0);
        }
        return user;
    }
}
