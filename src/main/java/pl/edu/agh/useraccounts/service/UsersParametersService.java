package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(name = "UsersParametersService")
//@Stateless
public interface UsersParametersService {

    /**
     * Pobranie wartości parametru użytkownika o loginie. Parametr jest określany przez podany klucz.
     * @param login Login użytkownika
     * @param paramKey Klucz parametru użytkownika
     * @return Wartość danego parametru
     * @throws UserException Wyjątek zawierający kod błędu (1 - użytkownik o podanym loginie nie istenije, 2 - użytkownik nie ma przypisanego parametru o podanym kluczu)
     */
    @WebMethod(operationName = "getUserParam")
    public String getUserParam(@WebParam(name = "login")String login, @WebParam(name = "paramKey")String paramKey) throws UserException;

    /**
     * Pobranie parametrów użytkownika jako mapy klucz-wartość.
     * @param login Login użytkownika
     * @return Mapa parametrów użytkownika
     * @throws UserException Wyjątek z kodem błędu (1 - niepoprawny login)
     */
    @WebMethod(operationName = "getUserParams")
    public Map<String, String> getUserParams(@WebParam(name = "login")String login) throws UserException;

    /**
     * Nadanie użytownikowi nowego parametru. Jeśli użytkownik miał już dany parametr do zostaje mu przypisana nowa wartość, jeśli parametr nie istniał to zostanie utworzony.
     * @param login Login użytkownika
     * @param paramKey Klucz parametru
     * @param paramValue Wartość dla parametru
     * @return Rezulatat (0 lub kod błędu)
     */
    @WebMethod(operationName = "setUserParam")
    public int setUserParam(@WebParam(name = "login")String login, @WebParam(name = "paramKey")String paramKey, @WebParam(name = "paramValue")String paramValue);

    /**
     * Pobranie listy logów systemowych w podanym zakresie czasowym.
     * @param startTime Początek zakersu czasowego pobieranych logów
     * @param endTime Koniec zakresu czasowego podanych logów
     * @return Lista logów
     * @throws UserException Wyjątek z kodem błędu (niepoprany format lub zakres dat)
     */
    @WebMethod(operationName = "getLogs")
    public List<String> getLogs(@WebParam(name = "startTime")Date startTime, @WebParam(name = "endTime")Date endTime) throws UserException;

}
