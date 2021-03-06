package org.halloweenalcala.app.model.cloud;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class SloganRating {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String idSlogan;
    private String idDevice;
    private int rating;
    private String timestamp;
    @Ignore private Map<String, String> extras = new HashMap<>();


    public SloganRating() {

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

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
