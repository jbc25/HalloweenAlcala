package org.halloweenalcala.app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.messaging.FirebaseMessaging;

import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.database.MyDatabase;
import org.halloweenalcala.app.interactor.firestore.UserInteractor;
import org.halloweenalcala.app.model.cloud.User;
import org.halloweenalcala.app.util.NotificationHelper;
import org.halloweenalcala.app.util.Util;

/**
 * Created by julio on 17/06/16.
 */

public class App extends MultiDexApplication {

    public static final String HALLOWEEN_YEAR = "2019";
    public static final int NUM_SLOGANS = 5;

    private static final String TAG = "App";

    public static final String PREFIX = BuildConfig.APPLICATION_ID + ".";
    public static final String SHARED_POEM_UNLOCKED = PREFIX + "shared_poem_unlocked";
    public static final String SHARED_CURRENT_DATA_VERSION = PREFIX + "shared_current_data_version";
    public static final String SHARED_FIRST_TIME = PREFIX + "shared_first_time";
    public static final String SHARED_LAST_PAGE_POEMS = PREFIX + "shared_last_page_poems";
    public static final String SHARED_LAST_NEWS_ID_LOCAL = PREFIX + "shared_last_news_id_local";
    public static final String SHARED_FIRST_RUN_OF_YEAR_XXXX = PREFIX + "shared_first_run_of_year_" + HALLOWEEN_YEAR;

    public static final String SHARED_ACCEPTED_CONTEST_RULES = PREFIX + "shared_accepted_contest_rules";
    public static final String SHARED_USER_ADDED_FIRESTORE = PREFIX + "shared_user_added_firestore";
    public static final String SHARED_USER_EMAIL = PREFIX + "shared_user_email";
    public static final String SHARED_NUMBER_SLOGANS_PENDING = PREFIX + "shared_number_slogans_pending";
    public static final String SHARED_USER_BANNED = PREFIX + "shared_user_banned";
    public static final String SHARED_USER_INITIALIZED_FIRST_TIME = PREFIX + "shared_user_initialized_first_time";
    public static final String SHARED_SHOW_TUTO_BANNERS = PREFIX + "shared_show_tuto_banners";

    public static final String URL_GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=org.halloweenalcala.app";
    public static final String URL_DIRECT_GOOGLE_PLAY_APP = "market://details?id=org.halloweenalcala.app";

    public static final String URL_QUERY_PROMOTE = "share-slogan";
    public static final String URL_PROMOTE_SLOGAN = "http://www.marchazombiealcala.com/?" + URL_QUERY_PROMOTE + "=%s";


    public static final String FIREBASE_TOPIC_REPORT_SLOGAN = "slogan_denounce";
    public static final String FIREBASE_TOPIC_NEWS = "news";


    private static MyDatabase db;

    public static final String DB_NAME = "halloween_alcala_19.db";

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationHelper.with(this).initializeOreoChannelsNotification();

        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.class, DB_NAME)
//                .addMigrations(MyDatabase.MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

//        if(true) {
        if (getPrefs(this).getBoolean(SHARED_FIRST_RUN_OF_YEAR_XXXX, true)) {
            getPrefs(this).edit().clear().commit();
            getPrefs(this).edit().putBoolean(SHARED_FIRST_RUN_OF_YEAR_XXXX, false).commit();
        }

        initializeUserIfExists();

        FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_TOPIC_NEWS);

//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
//        Picasso built = builder.build();
////        built.setIndicatorsEnabled(true);
//        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/typewriter.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());


//        Crashlytics.logException(new Exception("My first Android non-fatal error"));

//        if (BuildConfig.DEBUG) {
//            FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_TOPIC_REPORT_SLOGAN);
//
//            String senderId = getString(R.string.firebase_sender_id);
//
//        }

    }

    private void initializeUserIfExists() {

        if (!getPrefs(this).getBoolean(SHARED_USER_INITIALIZED_FIRST_TIME, false)) {

            String deviceId = Util.getDeviceId(this);
            new UserInteractor(null, null).getUser(deviceId, new BaseInteractor.CallbackGetEntity<User>() {
                @Override
                public void onEntityReceived(User userReceived) {
                    if (userReceived != null) {
                        getPrefs(App.this).edit().putString(SHARED_USER_EMAIL, userReceived.getEmail()).commit();
                        getPrefs(App.this).edit().putInt(SHARED_NUMBER_SLOGANS_PENDING, userReceived.getPendingSlogans()).commit();

                        getPrefs(App.this).edit().putBoolean(App.SHARED_USER_ADDED_FIRESTORE, true).commit();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });

            getPrefs(this).edit().putBoolean(SHARED_USER_INITIALIZED_FIRST_TIME, true).commit();
        }
    }

    public static MyDatabase getDB() {
        return db;
    }

    public static SharedPreferences getPrefs(Context context) {
//        return new SecurePreferences(context);
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
