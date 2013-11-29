package pl.edu.agh.useraccounts.service.model;

import java.util.HashMap;

/**
 * Parametry użytkownika (mapa)
 */
public class Parameters {
    private HashMap<String, String> map;

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
