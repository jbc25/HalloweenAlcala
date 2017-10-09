package org.halloweenalcala.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julio on 7/10/17.
 */

public class Place {

    private int id;
    private String name;
    private double lat;
    private double lng;

    public static final List<Place> places = new ArrayList<>();
    static {
        places.add(new Place(1, "Plaza Cervantes", 0, 0));
    }

    public Place(){

    }

    public Place(int id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "id: " + id + ", name: " + name;
    }
}
