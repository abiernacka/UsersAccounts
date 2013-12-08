package pl.edu.agh.useraccounts.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 06.12.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Role extends BaseEntity {

    @Column(name="NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
