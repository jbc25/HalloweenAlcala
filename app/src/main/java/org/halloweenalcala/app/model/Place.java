package org.halloweenalcala.app.model;

import com.orm.SugarRecord;

/**
 * Created by julio on 7/10/17.
 */

public class Place extends SugarRecord {


    private int id_server;
    private String name;
    private double lat;
    private double lng;

    public Place(){

    }

    public Place(int id_server, String name, double lat, double lng) {
        this.id_server = id_server;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
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
        return "id: " + id + ", name: " + name + ", id_server:" + id_server;
    }
}
