package org.halloweenalcala.app.model.cloud;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Slogan implements Serializable {

    private String id;
    private String text;
    private String type;
    private String idDevice;
    private boolean deleted;
    private String timestamp;

    public Slogan() {

    }

    public Slogan (String idDevice, String text, String timestamp) {
        this.idDevice = idDevice;
        this.text = text;
        this.timestamp = timestamp;
    }

    private Map<String, String> extras = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
