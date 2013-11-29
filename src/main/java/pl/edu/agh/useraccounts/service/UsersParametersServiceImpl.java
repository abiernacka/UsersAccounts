package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;
import pl.edu.agh.useraccounts.service.model.Parameters;

import javax.jws.WebService;
import java.util.*;

/**
 * Implementacja serwisu zarządzania parametrami użytkownika
 */
@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.UsersParametersService")
public class UsersParametersServiceImpl implements UsersParametersService {

    @Override
    public String getUserParam(String login, String paramKey) throws UserException {
        return "userParamValue";
    }

    @Override
    public Parameters getUserParams(String login) throws UserException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("address", "Wall Street");
        map.put("lastpassword", "zaq123");
        Parameters params = new Parameters();
        params.setMap(map);
        return params;
    }

    @Override
    public int setUserParam(String login, String paramKey, String paramValue) {
        return 0;
    }

    @Override
    public List<String> getLogs(Date startTime, Date endTime) throws UserException {
        List<String> logs = new ArrayList<String>();
        logs.add("User jkowalski authorization: 12:03 12.03.2013");
        logs.add("User jkowalska authorization: 14:03 12.03.2013");
        return logs;
    }

}
