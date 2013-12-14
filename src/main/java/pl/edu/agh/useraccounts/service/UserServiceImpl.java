package pl.edu.agh.useraccounts.service;


import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.model.User;

/**
 *Implementacja serwisu zarządzania kontem użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.UserService")
public class UserServiceImpl implements UserService{
    
    @Autowired
    UserDao userDao;

    @Override
    public int register(String login, String email, String password) {
        
        int result = 0;
        
        boolean vLogin = validateLogin(login);
        boolean vEmail = validateEmail(email);
        boolean vPass = validatePass(password);
        
        if(!vLogin)
            result = 1;
        
        if(!vEmail)
            result = 2;
        
        if(!vLogin && !vEmail)
            result = 3;
        
        if(!vPass)
            result = 4;
        
        if(!vLogin && !vPass)
            result = 5;
        
        if(!vPass && !vEmail)
            result = 6;
        
        if(!vLogin && !vEmail && !vPass)
            result = 7;
        
        if(userDao.getUserForLogin(login)!=null)
            result = 8;
        
        if(userDao.getUserForLogin(login)!=null && !vEmail)
            result = 10;
        
        if(userDao.getUserForLogin(login)!=null && !vPass)
            result = 12;
        
        if(userDao.getUserForLogin(login)!=null && !vEmail && !vPass)
            result = 14;
        
        if(result==0) {
            User u = new User();
            u.setLogin(login);
            u.setEmail(email);
            u.setPassword(password);
            userDao.save(u);
        }
        
        return result;
    }

    @Override
    public int authorization(String login, String password) {
        
        User u = userDao.getUserForLogin(login);
        
        if(u==null)
            return 1;
        
        if(u.getPassword()==null || !u.getPassword().equals(password))
            return 2;

        return 0;
    }

    @Override
    public List<String> getUsers() {   
        
        List<User> users = userDao.getAllUsers();
        List<String> logins = new ArrayList<String>();
        
        for(User u : users){
            logins.add(u.getLogin());
        }
        
        return logins;
    }

    @Override
    public int remindPassword(String login) {
        
        User u = userDao.getUserForLogin(login);
        
        if(u==null)
            return 1;
        
        //tu jakies smtp
        //sendMain(u.getEmail(), u.getPassword(), u.getLogin());

        return 0;
    }

    @Override
    public int changePassword(String login, String oldPassword, String newPassword) {
        
        User u = userDao.getUserForLogin(login);
        
        if(u==null)
            return 1;
        
        boolean vOldPass = validatePass(oldPassword);
        boolean vNewPass = validatePass(newPassword);
        
        if(!u.getPassword().equals(oldPassword) && !vNewPass)
            return 4;
        
        if(!u.getPassword().equals(oldPassword))
            return 2;
        
        if(!vNewPass)
            return 3;
        
        u.setPassword(newPassword);
        userDao.save(u);
        
        return 0;
    }

    @Override
    public int changeEmail(String login, String emial) {
        
        User u = userDao.getUserForLogin(login);
        
        if(u==null)
            return 1;
        
        if(!validateEmail(emial))
            return 2;
        
        u.setEmail(emial);
        userDao.save(u);
        
        return 0;
    }

    @Override
    public int removeUser(String login) {
        
        User u = userDao.getUserForLogin(login);
        
        if(u==null)
            return 1;
        
        userDao.delete(u);
        
        return 0;
    }

    private boolean validateLogin(String login) {
        String loginRegEx = "^[\\._A-Za-z0-9]{8,16}";
        
        Pattern pattern = Pattern.compile(loginRegEx);
        Matcher matcher = pattern.matcher(login);
        
        return matcher.matches();
    }
    
    private boolean validateEmail(String email) {
        String emailRegEx = "^[_A-Za-z0-9-\\+]+(\\\\.[_A-Za-z0-9-]+)*@+[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        Pattern pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }
    
    private boolean validatePass(String pass) {
        String passRegEx = "^[\\._A-Za-z0-9]{8,16}";
        
        Pattern pattern = Pattern.compile(passRegEx);
        Matcher matcher = pattern.matcher(pass);
        
        return matcher.matches();
    }
    
    private void sendMain(String sendTo, String pass, String login) {
        String from = "remainder@userapp.agh.edu.pl";
        String host = "localhost";
        
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        
        Session session = Session.getDefaultInstance(properties);
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setSubject("Przypomnienie hasla dla "+login);
            message.setText(pass);
            
            Transport.send(message);
        }
        catch(MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
