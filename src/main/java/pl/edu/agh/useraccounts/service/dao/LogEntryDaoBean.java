package pl.edu.agh.useraccounts.service.dao;

import pl.edu.agh.useraccounts.service.model.LogEntry;
import pl.edu.agh.useraccounts.service.model.Role;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 09.01.14
 * Time: 22:18
 * To change this template use File | Settings | File Templates.
 */
public class LogEntryDaoBean extends BaseDaoBean<LogEntry> implements LogEntryDao {

    public LogEntryDaoBean() {
        super(LogEntry.class);
    }

    @Override
    public List<LogEntry> getAllLogsSortedAscByDate() {
        List<LogEntry> logs = getSessionFactory().getCurrentSession().createQuery("from " + LogEntry.class.getName() + " l order by l.creationDate desc").list();
        if(logs == null) {
            logs = Collections.emptyList();
        }
        return logs;
    }
}
