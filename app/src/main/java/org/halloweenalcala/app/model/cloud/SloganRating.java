package org.halloweenalcala.app.model.cloud;

import java.util.HashMap;
import java.util.Map;

public class SloganRating {

    private String id;
    private String idSlogan;
    private int rating;
    private Map<String, String> extras = new HashMap<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSlogan() {
        return idSlogan;
    }

    public void setIdSlogan(String idSlogan) {
        this.idSlogan = idSlogan;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
