package org.halloweenalcala.app.model;

import android.webkit.URLUtil;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by julio on 17/10/17.
 */

public class News extends SugarRecord<News> implements Serializable {

    private int id_server;
    private String datetime, title, text, image_url, btn_text, btn_link;


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

}
