package org.halloweenalcala.app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;

import org.halloweenalcala.app.database.MyDatabase;

/**
 * Created by julio on 17/06/16.
 */

public class App extends MultiDexApplication {

    public static final String HALLOWEEN_YEAR = "2018";

    private static final String TAG = "App";

    public static final String PREFIX = "org.halloweenalcala.app.";
    public static final String SHARED_POEM_UNLOCKED = PREFIX + "shared_poem_unlocked";
    public static final String SHARED_CURRENT_DATA_VERSION = PREFIX + "shared_current_data_version";
    public static final String SHARED_FIRST_TIME = PREFIX + "shared_first_time";
    public static final String SHARED_LAST_PAGE_POEMS = PREFIX + "shared_last_page_poems";
    public static final String SHARED_LAST_NEWS_ID_LOCAL = PREFIX + "shared_last_news_id_local";
    public static final String SHARED_FIRST_RUN_OF_YEAR_XXXX = PREFIX + "shared_first_run_of_year_" + HALLOWEEN_YEAR;

    public static final String URL_GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=org.halloweenalcala.app";
    public static final String URL_DIRECT_GOOGLE_PLAY_APP = "market://details?id=org.halloweenalcala.app";
    private static MyDatabase db;

    public static final String DB_NAME = "halloween_alcala_17.db";

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.class, DB_NAME)
//                .addMigrations(MyDatabase.MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        if (getPrefs(this).getBoolean(SHARED_FIRST_RUN_OF_YEAR_XXXX, true)) {
            getPrefs(this).edit().clear().commit();
            getPrefs(this).edit().putBoolean(SHARED_FIRST_RUN_OF_YEAR_XXXX, false).commit();
        }


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

    }

    public static MyDatabase getDB() {
        return db;
    }

    public static SharedPreferences getPrefs(Context context) {
//        return new SecurePreferences(context);
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
