package pl.edu.agh.useraccounts.service.model;

import javax.persistence.Entity;
import java.util.HashMap;

/**
 * Parametry użytkownika (mapa)
 */
@Entity
public class Parameters extends BaseEntity {

    private HashMap<String, String> map = new HashMap<String, String>();

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
