package org.halloweenalcala.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.halloweenalcala.app.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julio on 7/10/17.
 */

@Entity(tableName = "PLACE")
public class Place implements Serializable {

    public static final String TYPE_MAIN = "MAIN";
    public static final String TYPE_OTHERS = "OTHERS";

    @PrimaryKey
    private int id_server;
    private String name;
    private String name_halloween;
    private double lat;
    private double lng;
    private String type;

    @Ignore
    private List<Performance> performances = new ArrayList<>();

    public int getMarkerIcon() {
        return TYPE_MAIN.equals(type) ? R.mipmap.ic_marker_zombie3 : R.mipmap.ic_marker_rip;
    }

    public String getRealNameWithHalloweenName() {
        if (getName_halloween() != null && !getName_halloween().isEmpty()) {
            return getName_halloween() + " - (" + getName() + ")";
        } else {
            return getName();
        }
    }

    public Place(){

    }


    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "name: " + name + ", id_server:" + id_server;
    }

    public String getName_halloween() {
        return name_halloween;
    }

    public void setName_halloween(String name_halloween) {
        this.name_halloween = name_halloween;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public String getType() {
        return type;
    }
}
