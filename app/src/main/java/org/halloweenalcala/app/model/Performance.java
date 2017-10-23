package org.halloweenalcala.app.model;

import android.support.annotation.NonNull;

import com.google.firebase.crash.FirebaseCrash;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.halloweenalcala.app.util.DateUtils.formatDateApi;

/**
 * Created by julio on 6/10/17.
 */

public class Performance extends SugarRecord<Performance> implements Comparable, Serializable {

    public static final int ID_MARCHA_ZOMBIE = 20;

    DateFormat dateFormatFriendlyText = new SimpleDateFormat("EEEE, dd MMMM");
    static SimpleDateFormat dateFormatApi = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat timeFormatApi = new SimpleDateFormat("HH:mm");
    static DateFormat dateTimeFormatApi = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static DateFormat dateTimeFormatHuman = new SimpleDateFormat("dd/MM HH:mm");

    private int id_server;
    private String date;
    private String time_begin;
    private String time_end;
    private String title;
    private String subtitle;
    private String info;
    private int id_place;
    private String image_url;
    private int highlight;
    private String place_text; // optional for place exceptions without id_place

    private Place place;
//    private String participants;
//    private int durationMins;

    public String getDayHeaderFormat() {

        try {
            Date dateDay = dateFormatApi.parse(getDate());
            String dateStr = dateFormatFriendlyText.format(dateDay);
            return dateStr;
//            return stripAccents(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();

            // Better than nothing :S
            return getDate();
        }

    }
    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public String getDateTimeHumanFriendly() {

        try {
            Date dateTime = dateTimeFormatApi.parse(getDateTime());
            return dateTimeFormatHuman.format(dateTime);
        } catch (ParseException e) {
            FirebaseCrash.report(e);
        }

        return getTime_begin();
    }

    public long getDayId() {

        try {
            Date date = formatDateApi.parse(getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            int dayInt = calendar.get(Calendar.DAY_OF_MONTH);
            return (long) dayInt;
        } catch (ParseException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
            throw new IllegalStateException("wrong date");
        }
    }


    public String getPlaceInfo() {

        if (place != null) {
            return place.getName();
        } else if (getPlace_text() != null && !getPlace_text().isEmpty()) {
            return getPlace_text();
        } else {
            return "";
        }
    }


    @Override
    public int compareTo(@NonNull Object o) {
        try {
            Date time = getDateMidnightFix();
            Date timeOther = ((Performance) o).getDateMidnightFix();
            return time.compareTo(timeOther);
        } catch (NullPointerException e) {
        }
        return 0;
    }

    private Date getDateMidnightFix() {

        try {
            Date dateTime = dateTimeFormatApi.parse(getDateTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateTime.getTime());
            if (calendar.get(Calendar.HOUR_OF_DAY) < 5) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dateTime.setTime(calendar.getTimeInMillis());
            }
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

        return null;
    }

    private String getDateTime() {
        return getDate() + " " + getTime_begin();
    }

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

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getHighlight() {
        return highlight;
    }

    public void setHighlight(int highlight) {
        this.highlight = highlight;
    }

    public boolean isHighlight() {
        return getHighlight() == 1;
    }
}
