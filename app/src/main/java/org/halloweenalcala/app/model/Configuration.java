package org.halloweenalcala.app.model;

/**
 * Created by julio on 8/10/17.
 */

public class Configuration {

    private int last_data_version;
    private int last_app_version;

    public Configuration() {
    }

    public int getLast_data_version() {
        return last_data_version;
    }

    public void setLast_data_version(int last_data_version) {
        this.last_data_version = last_data_version;
    }

    public int getLast_app_version() {
        return last_app_version;
    }

    public void setLast_app_version(int last_app_version) {
        this.last_app_version = last_app_version;
    }
}
