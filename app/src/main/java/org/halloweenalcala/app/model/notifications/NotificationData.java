package org.halloweenalcala.app.model.notifications;

/**
 * Created by julio on 22/01/18.
 */

public class NotificationData {

    private String openSection;
    private String id;

    public NotificationData(String openSection, String id) {
        this.id = id;
        this.openSection = openSection;
    }

    public String getOpenSection() {
        return openSection;
    }

    public void setOpenSection(String openSection) {
        this.openSection = openSection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
