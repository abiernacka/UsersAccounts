package pl.edu.agh.useraccounts.service.dao;

import pl.edu.agh.useraccounts.service.model.LogEntry;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 09.01.14
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public interface LogEntryDao extends BaseDao<LogEntry> {

    List<LogEntry> getAllLogsSortedAscByDate();

}
