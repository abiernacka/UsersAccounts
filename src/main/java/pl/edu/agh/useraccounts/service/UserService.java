package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Serwis zarządzania kontem użytkownika (interfejs)
 */
@WebService(name = "UserService")
public interface UserService {

    /**
     * Rejestracja nowego użytkownika.
     * Zwracane jest 0 w przypadku powodzenia, w przypadku niepowodzenia:
     * 1 jeśli podany login jest niepoprawny
     * 2 jeśli podany adres e-mail jest niepoprawny
     * 3 jeśli podane login oraz adres e-mail są niepoprawne
     * 4 jeśli podane hasło jest niepoprawne
     * 5 jeśli podane login i hasło są niepoprawne
     * 6 jeśli podane hasło i adres e-mail są niepoprawne
     * 7 jeśli podane login, hasło oraz adres e-mail są niepoprawne
     * 8 jeśli podany login jest zajęty
     * 10 jeśli podany login jest zajęty, a adres e-mail niepoprawny
     * 12 jeśli podany login jest zajęty i hasło jest niepoprawne
     * 14 jeśli podany login jest zajęty, a adres e-mail oraz hasło niepoprawne
     * @param login Login nowego użytkownika
     * @param emial Adres e-mail nowego użytkownika
     * @param password Hasło nowego użytkownika
     * @return Rezultat (0 lub kod błędu)
     */
    @WebMethod(operationName = "register")
    public int register(@WebParam(name = "login")String login, @WebParam(name="email")String emial, @WebParam(name="password")String password);

    /**
     * Sprawdzanie autoryzacji użytkownika, tzn. czy istnieje użytkownik o podanym loginie i haśle.
     * Zwracane jest 0 w przypadku powodzenia, w przypadku niepowodzenia:
     * 1 jeśli login jest niepoprawny
     * 2 jeśli hasło jest niepoprawne
     * @param login Login użytkownika
     * @param password Hasło
     * @return Rezultat (0 lub kod błędu)
     */
    @WebMethod(operationName = "authorization")
    public int authorization(@WebParam(name = "login")String login, @WebParam(name="password")String password);

    /**
     * Pobieranie listy użytkowników systemu.
     * @return Lista użytkowników
     */
    @WebMethod(operationName = "getUsers")
    public List<String> getUsers();

    /**
     * Prośba o reset hasła.Nowe hasło wysyłane jest na podany przy rejestracji e-mail.
     * Zwracane jest 0 w przypadku powodzenia lub 1 jeśli użytkownik o podanym loginie nieistnieje.
     * @param login Login użytkownika
     * @return Rezultat (0 lub kod błędu)
     */
    @WebMethod(operationName = "remindPassword")
    public int remindPassword(@WebParam(name = "login")String login);

    /**
     * Zmiana hasła użtykownika. Zwraca 0 w przypadku powodzenia, w przypadku niepowodzenia:
     * 1 jeśli podany login jest niepoprany
     * 2 jeśli podane stare hasło jest niepoprawne
     * 3 jeśli podane nowe hasło jest niepoprawne
     * 4 oba hasła są nieporawne.
     * @param login Login użytkownika
     * @param oldPassword Stare hasło
     * @param newPassword Nowe hasło
     * @return Rezultat (0 lub kod błędu)
     */
    @WebMethod(operationName = "changePassword")
    public int changePassword(@WebParam(name = "login")String login, @WebParam(name="oldPassword")String oldPassword, @WebParam(name="newPassword")String newPassword);

    /**
     * Zmiana adresu e-mail użytkownika. Zwraca 0 w przypadku powodzenia,w przypadku niepowodzenia:
     * 1 jeśli podany login nie istnieje
     * 2 jeśli podany adres jest niepoprawny.
     * @param login Login użytkownika
     * @param emial Adres e-mail użytkownika
     * @return Rezultat (0 lub kod błędu)
     */
    @WebMethod(operationName = "changeEmail")
    public int changeEmail(@WebParam(name = "login")String login, @WebParam(name="email")String emial);

    /**
     * Usunięcie użytkownika. Zwraca 0 w przypadku powodzenia lub 1 w przypadku nieistnienia użytkownika o podanym loginie.
     * @param login Login użytkownika
     * @return Rezultat (0 lub kod błędu)
     */
    @WebMethod(operationName = "removeUser")
    public int removeUser(@WebParam(name = "login")String login);


}
