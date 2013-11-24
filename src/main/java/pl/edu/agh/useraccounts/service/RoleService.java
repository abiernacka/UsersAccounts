package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.List;


@WebService
public interface RoleService {

    /**
     * Utworzenie nowej roli w systemie.
     * W przypadku powodzenia zwraca 0, 1 w przypadku niepowodzenia (podana rola już istanieje).
     * @param role Nazwa nowej roli
     * @return Rezultat (zero lub kod błędu)
     */
    public int createRole(String role);

    /**
     * Usunięcie roli z systemu. Jednocześnie użytkonicy, którzy mieli przypisaną daną rolę tracą ją.
     * W przypadku powodzenia zwraca 0, 1 w przypadku niepowodzenia (podana rola nie istanieje).
     * @param role Nazwa roli do usunięcia
     * @return Rezultat (zero lub kod błędu)
     */
    public int removeRole(String role);

    /**
     * Dodanie użytkownikowi danej roli (jeśli użytkownik miał już nadaną podaną rolę zwracane jest 0).
     * W przypadku powodzenia zwraca 0, w przypadku niepowodzenia:
     * 1 gdy nie istanie podany login
     * 2 jeśli login poprawny ale nie istanie podana rola.
     * @param login Login użytkownika, któremu ma zostać nadana rola
     * @param role Rola (nazwa roli) jaka ma być nadana użytkownikowi
     * @return Rezultat (zero lub kod błędu)
     */
    public int addRole(String login, String role);

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
    public int revokeRole(String login, String role);

    /**
     * Pobieranie listy ról użytkownika.
     * @param login Login użytkownika
     * @return Lista ról użytkownika
     * @throws UserException Wyjątek zawierający kod błędu (1 - użytkownik o podanym loginie nie istenije).
     */
    public List<String> getUserRoles(String login) throws UserException;

    /**
     * Pobranie listy wszystkich ról w systemie.
     * @return Lista wszystkich ról w systemie
     */
    public List<String> getAllRoles();
}
