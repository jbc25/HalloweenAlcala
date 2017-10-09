package org.halloweenalcala.app.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.BuildConfig;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.interactor.ConfigurationInteractor;
import org.halloweenalcala.app.model.Configuration;
import org.halloweenalcala.app.util.Util;

import java.io.IOException;

import static org.halloweenalcala.app.App.URL_DIRECT_GOOGLE_PLAY_APP;
import static org.halloweenalcala.app.App.URL_GOOGLE_PLAY_APP;

/**
 * Created by julio on 8/10/17.
 */


 public class MainPresenter extends BasePresenter {

    private final MainView view;
    private final ConfigurationInteractor configurationInteractor;

    public static Intent newMainActivity(Context context) {

         Intent intent = new Intent(context, MainActivity.class);

         return intent;
     }

     public static MainPresenter newInstance(MainView view, Context context) {

         return new MainPresenter(view, context);

     }

     private MainPresenter(MainView view, Context context) {
         super(context, view);

         this.view = view;
         configurationInteractor = new ConfigurationInteractor(context, view);

     }

     public void onCreate() {

//         if (true) {
//             return;
//         }

         if (getPrefs().getBoolean(App.SHARED_FIRST_TIME, true)) {
             if (storeDataFirstTime()) {
                 getPrefs().edit().putBoolean(App.SHARED_FIRST_TIME, false).commit();
             }
         }

         configurationInteractor.getConfiguration(new ConfigurationInteractor.ConfigurationCallback() {
             @Override
             public void onSuccess(Configuration configuration) {
                 checkAppVersion(configuration.getLast_app_version());
                 checkDataVersion(configuration.getLast_data_version());
             }

             @Override
             public void onError(String message) {
                view.toast(message);
             }
         });

     }

    private boolean storeDataFirstTime() {

        try {
            String csvPlaces = Util.getStringFromAssets(context, "data/places.csv");
            getPrefs().edit().putString(App.SHARED_DATA_PLACES, csvPlaces).commit();
        } catch (IOException e) {
            Log.e(TAG, "Failed to read places.csv", e);
            view.toast("Failed to read places.csv");
            return false;
        }

        try {
            String csvPlaces = Util.getStringFromAssets(context, "data/participants.csv");
            getPrefs().edit().putString(App.SHARED_DATA_PARTICIPANTS, csvPlaces).commit();
        } catch (IOException e) {
            Log.e(TAG, "Failed to read participants.csv", e);
            view.toast("Failed to read participants.csv");
            return false;
        }

        try {
            String csvPlaces = Util.getStringFromAssets(context, "data/performances.csv");
            getPrefs().edit().putString(App.SHARED_DATA_PERFORMANCES, csvPlaces).commit();
        } catch (IOException e) {
            Log.e(TAG, "Failed to read performances.csv", e);
            view.toast("Failed to read performances.csv");
            return false;
        }

        return true;
    }

    private void checkAppVersion(int lastAppVersion) {
        int currentAppVersion = BuildConfig.VERSION_CODE;
        if (currentAppVersion < lastAppVersion) {
            view.showUpdateAppView();
        }

    }

    private void checkDataVersion(int lastDataVersion) {

        int currentDataVersion = getPrefs().getInt(App.SHARED_CURRENT_DATA_VERSION, 1);
        if (currentDataVersion < lastDataVersion) {
            updateDataFromServer();
        }
    }

    private void updateDataFromServer() {

        //todo get csv from google sheets and store in SharedPreferences as csv string
    }


    public void onUpdateVersionClick() {

        Intent directPlayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_DIRECT_GOOGLE_PLAY_APP));
        if (directPlayIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(directPlayIntent);
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL_GOOGLE_PLAY_APP)));
        }
    }


}