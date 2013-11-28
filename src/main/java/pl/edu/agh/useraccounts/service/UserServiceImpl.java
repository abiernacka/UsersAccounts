package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.UserService")
public class UserServiceImpl implements UserService{

    @Override
    public int register(String login, String emial, String password) {
        return 0;
    }

    @Override
    public int authorization(String login, String password) {
        return 0;
    }

    @Override
    public List<String> getUsers() {
        List<String> users = new ArrayList<String>();
        users.add("jkowalski");
        return users;
    }

    @Override
    public int remindPassword(String login) {
        return 0;
    }

    @Override
    public int changePassword(String login, String oldPassword, String newPassword) {
        return 0;
    }

    @Override
    public int changeEmail(String login, String emial) {
        return 0;
    }

    @Override
    public int removeUser(String login) {
        return 0;
    }


}
