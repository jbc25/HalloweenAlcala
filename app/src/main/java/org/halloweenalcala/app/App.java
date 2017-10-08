package org.halloweenalcala.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by julio on 17/06/16.
 */

public class App extends Application {

    private static final String TAG = "App";

    public static final String PREFIX = "org.halloweenalcala.app.";
    public static final String SHARED_POEM_UNLOCKED = PREFIX + "shared_poem_unlocked";
    public static final String SHARED_CURRENT_DATA_VERSION = PREFIX + "shared_current_data_version";

    public static final String URL_GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=org.halloweenalcala.app";
    public static final String URL_DIRECT_GOOGLE_PLAY_APP = "market://details?id=org.halloweenalcala.app";

    @Override
    public void onCreate() {
        super.onCreate();


        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
//        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);


    }

    public static SharedPreferences getPrefs(Context context) {
//        return new SecurePreferences(context);
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
