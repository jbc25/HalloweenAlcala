package org.halloweenalcala.app.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.crashlytics.android.Crashlytics;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.BuildConfig;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.csv.CsvConverter;
import org.halloweenalcala.app.interactor.csv.ConfigurationInteractor;
import org.halloweenalcala.app.interactor.csv.DataInteractor;
import org.halloweenalcala.app.model.Configuration;
import org.halloweenalcala.app.model.News;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.model.Performance;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.util.Util;

import java.io.IOException;
import java.util.List;

import static org.halloweenalcala.app.App.URL_DIRECT_GOOGLE_PLAY_APP;
import static org.halloweenalcala.app.App.URL_GOOGLE_PLAY_APP;

/**
 * Created by julio on 8/10/17.
 */


public class MainPresenter extends BasePresenter {

    private final MainView view;
    private final ConfigurationInteractor configurationInteractor;
    private final DataInteractor dataInteractor;

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
        dataInteractor = new DataInteractor(context, view);

    }

    public void onCreate(Intent intent) {

//         if (true) {
//             return;
//         }

        if (getPrefs().getBoolean(App.SHARED_FIRST_TIME, true)) {
            if (storeDataFirstTime()) {
                getPrefs().edit().putBoolean(App.SHARED_FIRST_TIME, false).commit();
            }
        }


        checkIntentUriReceived(intent);
    }

    public void onResume() {
        refreshData();
    }

    private void refreshData() {

        configurationInteractor.getConfiguration(new ConfigurationInteractor.ConfigurationCallback() {
            @Override
            public void onSuccess(Configuration configuration) {
                checkAppVersion(configuration.getLast_app_version());
                checkDataVersion(configuration.getLast_data_version());
            }

            @Override
            public void onError(String message) {
//                 view.toast(message);
                Crashlytics.logException(new Error("Data request error (configuration): " + message));

            }
        });

        dataInteractor.getNews(new DataInteractor.DataCallback<News>() {
            @Override
            public void onSuccess(List<News> list) {

                App.getDB().newsDao().deleteAll();
                App.getDB().newsDao().insertAll(list);

                if (!list.isEmpty()) {
                    int lastNewsId = list.get(list.size() - 1).getId_server();
                    checkNewNews(lastNewsId);
                }
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Error -> getNews: " + message);
                Crashlytics.logException(new Error("Data request error (news): " + message));
            }
        });
    }


    private void checkIntentUriReceived(Intent intent) {

//        view.showSlogan("nPBWmTYuRJOqWFVwSpLy");

        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            continueWithLink(appLinkData);
        }
    }

    private void continueWithLink(final Uri uri) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.continue_str)
                .setMessage(R.string.continue_link_message)
                .setPositiveButton(R.string.open_in_web, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                })
                .setNeutralButton(R.string.remain, null)
                .show();
    }


    private void checkNewNews(int lastNewsIdServer) {
        int lastNewsIdLocal = getPrefs().getInt(App.SHARED_LAST_NEWS_ID_LOCAL, 1);
        if (lastNewsIdLocal < lastNewsIdServer) {
            view.showNewNewsIcon(true);
        }
    }

    private boolean storeDataFirstTime() {

        try {
            String csvPlaces = Util.getStringFromAssets(context, "data/places.csv");
            List<Place> places = new CsvConverter<>(Place.class).convert(csvPlaces);

            App.getDB().placeDao().deleteAll();
            App.getDB().placeDao().insertAll(places);

        } catch (IOException e) {
            Log.e(TAG, "Failed to read places.csv", e);
            return false;
        }

        try {
            String csvParticipants = Util.getStringFromAssets(context, "data/participants.csv");
            List<Participant> participants = new CsvConverter<>(Participant.class).convert(csvParticipants);

            App.getDB().participantDao().deleteAll();
            App.getDB().participantDao().insertAll(participants);

        } catch (IOException e) {
            Log.e(TAG, "Failed to read participants.csv", e);
//            view.toast("Failed to read participants.csv");
            return false;
        }

        try {
            String csvPerformances = Util.getStringFromAssets(context, "data/performances.csv");
            List<Performance> performances = new CsvConverter<>(Performance.class).convert(csvPerformances);

            App.getDB().performanceDao().deleteAll();
            App.getDB().performanceDao().insertAll(performances);
        } catch (IOException e) {
            Log.e(TAG, "Failed to read performances.csv", e);
//            view.toast("Failed to read performances.csv");
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

//        if (true) { updateDataFromServer(); }

        int currentDataVersion = getPrefs().getInt(App.SHARED_CURRENT_DATA_VERSION, BuildConfig.CURRENT_DATA_VERSION);
        if (currentDataVersion < lastDataVersion) {
            updateDataFromServer();
            getPrefs().edit().putInt(App.SHARED_CURRENT_DATA_VERSION, lastDataVersion).commit();
        }
    }

    private void updateDataFromServer() {

        dataInteractor.getPlaces(new DataInteractor.DataCallback<Place>() {
            @Override
            public void onSuccess(List<Place> list) {
                App.getDB().placeDao().deleteAll();
                App.getDB().placeDao().insertAll(list);
            }

            @Override
            public void onError(String message) {

                Crashlytics.logException(new Error("Data request error (places): " + message));
                Log.e(TAG, "Error -> getPlaces: " + message);
            }
        });

        dataInteractor.getParticipants(new DataInteractor.DataCallback<Participant>() {
            @Override
            public void onSuccess(List<Participant> list) {
                App.getDB().participantDao().deleteAll();
                App.getDB().participantDao().insertAll(list);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Error -> getParticipants: " + message);
                Crashlytics.logException(new Error("Data request error (participants): " + message));
            }
        });

        dataInteractor.getPerformances(new DataInteractor.DataCallback<Performance>() {
            @Override
            public void onSuccess(List<Performance> list) {
                App.getDB().performanceDao().deleteAll();
                App.getDB().performanceDao().insertAll(list);
                view.refreshFragmentsData();
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Error -> getPerformances: " + message);
                Crashlytics.logException(new Error("Data request error (performances): " + message));

            }
        });

    }


    public void onUpdateVersionClick() {

        Intent directPlayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_DIRECT_GOOGLE_PLAY_APP));
        if (directPlayIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(directPlayIntent);
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL_GOOGLE_PLAY_APP)));
        }
    }


    public void onNewsButtonClick() {

        int lastNewsIdServer = 0;
        List<News> newsList = App.getDB().newsDao().getAll();
        for (News news : newsList) {
            lastNewsIdServer = Math.max(news.getId_server(), lastNewsIdServer);
        }
        getPrefs().edit().putInt(App.SHARED_LAST_NEWS_ID_LOCAL, lastNewsIdServer).commit();
        view.showNewNewsIcon(false);
    }
}