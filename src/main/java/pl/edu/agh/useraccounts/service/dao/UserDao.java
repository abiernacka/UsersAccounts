package pl.edu.agh.useraccounts.service.dao;

import pl.edu.agh.useraccounts.service.model.User;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 08.12.13
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends BaseDao<User> {
    List<User> getAllUsers();
}
