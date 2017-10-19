package org.halloweenalcala.app.model;

import com.orm.SugarRecord;

/**
 * Created by julio on 6/10/17.
 */

public class Performance extends SugarRecord<Performance> {

    private int id_server;
    private String date;
    private String time_begin;
    private String time_end;
    private String title;
    private String subtitle;
    private String info;
    private int id_place;
    private String place_text; // optional for place exceptions without id_place
//    private String participants;
//    private int durationMins;


    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }

    public String getPlace_text() {
        return place_text;
    }

    public void setPlace_text(String place_text) {
        this.place_text = place_text;
    }
}
