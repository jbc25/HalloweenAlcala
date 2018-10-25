package org.halloweenalcala.app.model.notifications;

/**
 * Created by julio on 22/01/18.
 */

public class NotificationInfo {

    public static final String KEY_OPEN_SECTION = "openSection";
    public static final String KEY_ID = "id";

    private String title;
    private String body;

    public NotificationInfo(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
