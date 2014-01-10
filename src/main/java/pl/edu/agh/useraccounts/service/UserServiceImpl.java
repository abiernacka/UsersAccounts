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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.UserDao;
import pl.edu.agh.useraccounts.service.model.LogEntry;
import pl.edu.agh.useraccounts.service.model.User;

/**
 *Implementacja serwisu zarządzania kontem użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.UserService")
@Transactional
public class UserServiceImpl implements UserService{
    
    @Autowired
    UserDao userDao;

    @Autowired
    LogEntryDao logDao;

    @Override
    public int register(String login, String email, String password) {
        LogEntry log = new LogEntry();
        String logString = "Próba rejestracji użytkownika " + login + " na email " + email + ".";
        
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
            logString += " Rejestracja powiodła się.";
            log.setLog(logString);
            logDao.save(log);
        } else {
            logString += " Próba nieudana (" + result + "): ";
            if(!vLogin) {
                logString += "niepoprawny login, ";
            }
            if(!vEmail) {
                logString += "niepoprawny adres e-mail";
            }
            if(!vPass) {
                logString += "niepoprawne hasło.";
            }
            log.setLog(logString);
            logDao.save(log);
        }
        
        return result;
    }

    @Override
    public int authorization(String login, String password) {
        LogEntry log = new LogEntry();
        String logString = "Próba autoryzacji użytkownika " + login + ".";
        
        User u = userDao.getUserForLogin(login);
        
        if(u==null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        
        if(u.getPassword()==null || !u.getPassword().equals(password)) {
            logString += " Próba nieudana (2): niepoprawne hasło.";
            log.setLog(logString);
            logDao.save(log);
            return 2;
        }

        return 0;
    }

    @Override
    public List<String> getUsers() {

        LogEntry log = new LogEntry();
        String logString = "Próba pobrania listy użytkowników. Lista pobrana.";
        List<User> users = userDao.getAllUsers();
        List<String> logins = new ArrayList<String>();
        
        for(User u : users){
            logins.add(u.getLogin());
        }
        log.setLog(logString);
        logDao.save(log);
        return logins;
    }

    @Override
    public int remindPassword(String login) {

        LogEntry log = new LogEntry();
        String logString = "Próba przypomnienia hasła użytkownika " + login + ".";
        User u = userDao.getUserForLogin(login);
        
        if(u==null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        
        //tu jakies smtp
        sendMain(u.getEmail(), u.getPassword(), u.getLogin());
        logString += " Wysłano hasło na adres e-mail.";
        log.setLog(logString);
        logDao.save(log);
        return 0;
    }

    @Override
    public int changePassword(String login, String oldPassword, String newPassword) {

        LogEntry log = new LogEntry();
        String logString = "Próba zmiany hasła użytkownika " + login + ".";
        User u = userDao.getUserForLogin(login);

        if(u==null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }

        boolean vOldPass = validatePass(oldPassword);
        boolean vNewPass = validatePass(newPassword);
        
        if(!u.getPassword().equals(oldPassword) && !vNewPass) {
            logString += " Próba nieudana (4): hasła nie mogą być identyczne.";
            log.setLog(logString);
            logDao.save(log);
            return 4;
        }
        
        if(!u.getPassword().equals(oldPassword)) {

            logString += " Próba nieudana (2): niepoprawne stare hasło.";
            log.setLog(logString);
            logDao.save(log);
            return 2;
        }
        
        if(!vNewPass) {

            logString += " Próba nieudana (3): niepoprawne nowe hasło.";
            log.setLog(logString);
            logDao.save(log);
            return 3;
        }
        
        u.setPassword(newPassword);
        userDao.save(u);

        logString += " Hasło zmieniono.";
        log.setLog(logString);
        logDao.save(log);
        return 0;
    }

    @Override
    public int changeEmail(String login, String email) {

        LogEntry log = new LogEntry();
        String logString = "Próba zamiany adresu e-mail użytkownika " + login + " na email " + email + ".";
        User u = userDao.getUserForLogin(login);
        
        if(u==null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        
        if(!validateEmail(email)) {
            logString += " Próba nieudana (2): niepoprawny adres e-mail.";
            log.setLog(logString);
            logDao.save(log);
            return 2;
        }
        
        u.setEmail(email);
        userDao.save(u);

        logString += " Zmieniono adres e-mail.";
        log.setLog(logString);
        logDao.save(log);
        return 0;
    }

    @Override
    public int removeUser(String login) {

        LogEntry log = new LogEntry();
        String logString = "Próba usunięcia użytkownika " + login + ".";
        User u = userDao.getUserForLogin(login);
        
        if(u==null) {
            logString += " Próba nieudana (1): niepoprawny login.";
            log.setLog(logString);
            logDao.save(log);
            return 1;
        }
        
        userDao.delete(u);

        logString += " Usunięto użytkownika.";
        log.setLog(logString);
        logDao.save(log);
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
            
//            Transport.send(message);
        }
        catch(MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setLogDao(LogEntryDao logDao) {
        this.logDao = logDao;
    }
}
