package pl.edu.agh.useraccounts.service;

import pl.edu.agh.useraccounts.service.exceptions.UserException;

import javax.jws.WebService;
import java.util.*;

@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.UsersParametersService")
public class UsersParametersServiceImpl implements UsersParametersService {

    @Override
    public String getUserParam(String login, String paramKey) throws UserException {
        return "userParamValue";
    }

    @Override
    public Map<String, String> getUserParams(String login) throws UserException {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("address", "Wall Street");
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
        return logs;
    }

}
