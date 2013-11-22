package pl.edu.agh.useraccounts.service;

import javax.jws.WebService;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebService
public interface UsersParametersService {

    public String getUserParam(String login, String paramKey);

    public Map<String, String> getUserParams(String login);

    public int setUserParam(String login, String paramKey, String paramValue);

    public List<String> getLogs(Date startTime, Date endTime);

}
