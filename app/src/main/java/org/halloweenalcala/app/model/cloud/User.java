package org.halloweenalcala.app.model.cloud;

import java.util.ArrayList;
import java.util.List;

public class User {

    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PENDING_SLOGANS = "pendingSlogans";
    public static final String FIELD_DEVICE_ID = "idDevice";
    public static final String FIELD_BANNED = "banned";

    private String idDevice;
    private String alias;
    private String email;
    private int pendingSlogans;
    private String timestamp;
    private boolean banned;
    private List<String> mySlogansIds = new ArrayList<>();

    public User() {

    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getPendingSlogans() {
        return pendingSlogans;
    }

    public void setPendingSlogans(int pendingSlogans) {
        this.pendingSlogans = pendingSlogans;
    }

    public List<String> getMySlogansIds() {
        return mySlogansIds;
    }

    public void setMySlogansIds(List<String> mySlogansIds) {
        this.mySlogansIds = mySlogansIds;
    }
}
