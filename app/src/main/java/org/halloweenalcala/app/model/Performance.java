package org.halloweenalcala.app.model;

import com.orm.SugarRecord;

/**
 * Created by julio on 6/10/17.
 */

public class Performance extends SugarRecord<Performance> {

    private int id_server;
    private String dateTime;
    private String name;
    private String location;
    private double lat, lng;
    private String participants;
    private int durationMins;
}
