package pl.edu.agh.useraccounts.service;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserService {

    public int register(String login, String emial, String password);

    public boolean authorization(String login, String password);

    public List<String> getUsers();

    public int remindPassword(String login);

    public int changePassword(String login, String oldPassword, String newPassword);

    public int changeEmail(String login, String emial);

    public int removeUser(String login);


}
