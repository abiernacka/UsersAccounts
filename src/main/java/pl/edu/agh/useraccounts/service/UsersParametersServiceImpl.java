package pl.edu.agh.useraccounts.service;

import java.util.*;

public class UsersParametersServiceImpl {

    public String getUserParam(String login, String paramKey) {
        return "userParamValue";
    }

    public Map<String, String> getUserParams(String login) {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("address", "Wall Street");
        return params;
    }

    public int setUserParam(String login, String paramKey, String paramValue) {
        return 0;
    }

    public List<String> getLogs(Date startTime, Date endTime) {
        List<String> logs = new ArrayList<String>();
        logs.add("User jkowalski authorization: 12:03 12.03.2013");
        return logs;
    }

}
