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
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
