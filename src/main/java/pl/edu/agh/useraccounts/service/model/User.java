package pl.edu.agh.useraccounts.service.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 06.12.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User extends BaseEntity {

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Parameters parameters = new Parameters();

    @OneToMany
    private List<Role> roles = new ArrayList<Role>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
