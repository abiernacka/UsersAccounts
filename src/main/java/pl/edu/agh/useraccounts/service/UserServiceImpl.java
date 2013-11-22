package pl.edu.agh.useraccounts.service;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl {

    public int register(String login, String emial, String password) {
        return 0;
    }

    public boolean authorization(String login, String password) {
        return false;
    }

    public List<String> getUsers() {
        List<String> users = new ArrayList<String>();
        users.add("jkowalski");
        return users;
    }

    public int remindPassword(String login) {
        return 0;
    }

    public int changePassword(String login, String oldPassword, String newPassword) {
        return 0;
    }

    public int changeEmail(String login, String emial) {
        return 0;
    }

    public int removeUser(String login) {
        return 0;
    }


}
