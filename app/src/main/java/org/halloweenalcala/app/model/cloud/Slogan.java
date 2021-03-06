package org.halloweenalcala.app.model.cloud;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Slogan implements Serializable {

    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_RATING = "avgRating";
    public static final String FIELD_DENOUNCED = "denounced";
    public static final String FIELD_DELETED = "deleted";
    public static final String FIELD_TEXT_NORMALIZED = "text_normalized";


    private String id;
    private String text;
    private String text_normalized;
    private String type;
    private String idDevice;
    private boolean denounced;
    private boolean deleted;
    private String timestamp;

    // agregation
    private int numRatings;
    private float avgRating;

    public Slogan() {

    }

    public void normalizeText() {
        text_normalized = text.replaceAll("[^A-Za-z0-9]","").toLowerCase().replace(" ", "");
    }

    public Slogan (String idDevice, String text, String timestamp) {
        this.idDevice = idDevice;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getAvgRatingFormatted() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(getAvgRating());
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

    public boolean isDenounced() {
        return denounced;
    }

    public void setDenounced(boolean denounced) {
        this.denounced = denounced;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getText_normalized() {
        return text_normalized;
    }

    public void setText_normalized(String text_normalized) {
        this.text_normalized = text_normalized;
    }
}
