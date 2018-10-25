package org.halloweenalcala.app.api.retrofit;

import org.halloweenalcala.app.model.notifications.NotificationPush;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;


public interface FirebasePushNotificationApi {

    @POST("https://fcm.googleapis.com/fcm/send")
    Observable<Response<Void>> sendNotification(@Header("Authorization") String key, @Body NotificationPush notificationPush);

}
