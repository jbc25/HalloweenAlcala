package org.halloweenalcala.app.model;

import android.net.Uri;
import android.webkit.URLUtil;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import static org.halloweenalcala.app.model.Performance.stripAccents;

/**
 * Created by julio on 17/10/17.
 */

public class News extends SugarRecord<News> implements Serializable {

    private int id_server;
    private String datetime, title, text, image_url, btn_text, btn_link, link_youtube;

    public String getDatetimeHumanFormat() {

        try {
            Date dateTime = Performance.dateTimeFormatApi.parse(getDatetime());
            String dateStr = Performance.dateTimeFormatHuman.format(dateTime);
            return stripAccents(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();

            // Better than nothing :S
            return getDatetime();
        }
    }


    public boolean hasValidYoutubeVideo() {
        if (link_youtube == null || link_youtube.isEmpty()) {
            return false;
        }

        Uri uriVideo = Uri.parse(link_youtube);
        String host = uriVideo.getHost();
        return host.contains("youtu.be") || host.contains("youtube.com");

    }

    public String getYoutube_video_ID() {

        try {
            Uri uriVideo = Uri.parse(link_youtube);
            if (uriVideo.getHost().contains("youtu.be")) {
                return uriVideo.getPathSegments().get(0);
            } else if(uriVideo.getHost().contains("youtube.com")) {
                return uriVideo.getQueryParameter("v");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // -------------------

    public boolean hasValidLink() {
        return URLUtil.isValidUrl(btn_link);
    }

    public boolean hasImage() {
        return image_url != null && !image_url.isEmpty();
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBtn_text() {
        return btn_text;
    }

    public void setBtn_text(String btn_text) {
        this.btn_text = btn_text;
    }

    public String getBtn_link() {
        return btn_link;
    }

    public void setBtn_link(String btn_link) {
        this.btn_link = btn_link;
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getLink_youtube() {
        return link_youtube;
    }

    public void setLink_youtube(String link_youtube) {
        this.link_youtube = link_youtube;
    }
}
