package pl.edu.agh.useraccounts.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 09.01.14
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class LogEntry extends BaseEntity {

    public LogEntry() {
        this.log = "";
    }

    public LogEntry(String log) {
        this.log = log;
    }

    @Column(name="LOG")
    private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
