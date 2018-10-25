package org.halloweenalcala.app.model.notifications;

/**
 * Created by julio on 22/01/18.
 */

public class NotificationPush {

    private String to;
    private NotificationInfo notification;
    private NotificationData data;


    public static NotificationPush createNotification(String to, String title, String body) {
        return createNotification(to, title, body, null, null);
    }

    public static NotificationPush createNotification(String to, String title, String body, String openSection) {
        return createNotification(to, title, body, openSection, null);
    }

    public static NotificationPush createNotification(String to, String title, String body, String openSection, String id) {
        NotificationPush notificationPush = new NotificationPush();
        notificationPush.setTo(to);

        NotificationInfo notification = new NotificationInfo(title, body);
        notificationPush.setNotification(notification);

        NotificationData data = new NotificationData(openSection, id);
        notificationPush.setData(data);

        return notificationPush;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationInfo getNotification() {
        return notification;
    }

    public void setNotification(NotificationInfo notification) {
        this.notification = notification;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}
