import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.useraccounts.service.dao.LogEntryDao;
import pl.edu.agh.useraccounts.service.dao.RoleDao;
import pl.edu.agh.useraccounts.service.model.LogEntry;
import pl.edu.agh.useraccounts.service.model.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.test.cfg.xml")
public class LogEntryDaoTest {

    private static final String LOG1 = "LOG1";

    private static final String LOG2 = "LOG2";

    private static final String LOG3 = "LOG3";

    @Autowired
    private LogEntryDao logDao;

    @Test
    @Transactional
    public void testGetLogs() {
        Assert.assertEquals(0, logDao.getAllLogsSortedAscByDate().size());

        LogEntry logEntry1 = new LogEntry(LOG1);
        logDao.save(logEntry1);
        System.out.println(); //println added to wait a few ms before creating next log
        LogEntry logEntry2 = new LogEntry(LOG2);
        logDao.save(logEntry2);
        System.out.println();
        LogEntry logEntry3 = new LogEntry(LOG3);
        logDao.save(logEntry3);

        List<LogEntry> logs = logDao.getAllLogsSortedAscByDate();
        Assert.assertEquals(3, logs.size());
        Assert.assertEquals(LOG3, logs.get(0).getLog());
        Assert.assertEquals(LOG2, logs.get(1).getLog());
        Assert.assertEquals(LOG1, logs.get(2).getLog());
    }

    public void setLogDao(LogEntryDao logDao) {
        this.logDao = logDao;
    }
}
