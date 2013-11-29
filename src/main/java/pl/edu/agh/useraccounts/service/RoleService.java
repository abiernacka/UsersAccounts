package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService(name = "RoleService")
public interface RoleService {

    /**
     * Utworzenie nowej roli w systemie.
     * W przypadku powodzenia zwraca 0, 1 w przypadku niepowodzenia (podana rola już istanieje).
     * @param role Nazwa nowej roli
     * @return Rezultat (zero lub kod błędu)
     */
    @WebMethod(operationName = "createRole")
    public int createRole(@WebParam(name = "role")String role);

    /**
     * Usunięcie roli z systemu. Jednocześnie użytkonicy, którzy mieli przypisaną daną rolę tracą ją.
     * W przypadku powodzenia zwraca 0, 1 w przypadku niepowodzenia (podana rola nie istanieje).
     * @param role Nazwa roli do usunięcia
     * @return Rezultat (zero lub kod błędu)
     */
    @WebMethod(operationName = "removeRole")
    public int removeRole(@WebParam(name = "role")String role);

    /**
     * Dodanie użytkownikowi danej roli (jeśli użytkownik miał już nadaną podaną rolę zwracane jest 0).
     * W przypadku powodzenia zwraca 0, w przypadku niepowodzenia:
     * 1 gdy nie istanie podany login
     * 2 jeśli login poprawny ale nie istanie podana rola.
     * @param login Login użytkownika, któremu ma zostać nadana rola
     * @param role Rola (nazwa roli) jaka ma być nadana użytkownikowi
     * @return Rezultat (zero lub kod błędu)
     */
    @WebMethod(operationName = "addRole")
    public int addRole(@WebParam(name = "login")String login, @WebParam(name = "role")String role);

    /**
     * Odbieranie użytkownikowi danej roli.
     * W przypadku powodzenia zwraca 0, w przypadku niepowodzenia:
     * 1 gdy nie istanie podany login
     * 2 jeśli login poprawny ale nie istanie podana rola
     * 3 jeśli login i rola istanieje, ale nie jest przypisana danemu użytkownikowi.
     * @param login Login użytkownika, któremu ma zostać nadana rola
     * @param role Rola (nazwa roli) jaka ma być nadana użytkownikowi
     * @return Rezultat (zero lub kod błędu)
     */
    @WebMethod(operationName = "revokeRole")
    public int revokeRole(@WebParam(name = "login")String login, @WebParam(name = "role")String role);

    /**
     * Pobieranie listy ról użytkownika.
     * @param login Login użytkownika
     * @return Lista ról użytkownika
     * @throws UserException Wyjątek zawierający kod błędu (1 - użytkownik o podanym loginie nie istenije).
     */
    @WebMethod(operationName = "getUserRole")
    public List<String> getUserRoles(@WebParam(name = "login")String login) throws UserException;

    /**
     * Pobranie listy wszystkich ról w systemie.
     * @return Lista wszystkich ról w systemie
     */
    @WebMethod(operationName = "getAllRoles")
    public List<String> getAllRoles();
}
