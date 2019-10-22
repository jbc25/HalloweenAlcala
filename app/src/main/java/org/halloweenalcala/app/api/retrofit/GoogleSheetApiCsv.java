package org.halloweenalcala.app.api.retrofit;


import org.halloweenalcala.app.model.Configuration;
import org.halloweenalcala.app.model.News;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.model.Performance;
import org.halloweenalcala.app.model.Place;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface GoogleSheetApiCsv {


    // Retrofit CSV: http://mateuyabar.com/blog/2015/10/01/Use-Google-Spreadsheets-as-data-for-an-Android-app/

    @GET("spreadsheets/d/e/2PACX-1vSh_wTT0k3XBfwoaAZeocBX9LCjd3sYk8Y0DbrGqPeTWCETpTy9TWmcDMSxWjODVrfXgHMQuMAk9e7i/pub?gid=127251563&single=true&output=csv")
    Observable<List<Participant>> getParticipants();

    //
    @GET("spreadsheets/d/e/2PACX-1vSh_wTT0k3XBfwoaAZeocBX9LCjd3sYk8Y0DbrGqPeTWCETpTy9TWmcDMSxWjODVrfXgHMQuMAk9e7i/pub?gid=0&single=true&output=csv")
    Observable<List<Place>> getPlaces();

    @GET("spreadsheets/d/e/2PACX-1vSh_wTT0k3XBfwoaAZeocBX9LCjd3sYk8Y0DbrGqPeTWCETpTy9TWmcDMSxWjODVrfXgHMQuMAk9e7i/pub?gid=1709987993&single=true&output=csv")
    Observable<List<Performance>> getPerformances();

    @GET("spreadsheets/d/e/2PACX-1vSh_wTT0k3XBfwoaAZeocBX9LCjd3sYk8Y0DbrGqPeTWCETpTy9TWmcDMSxWjODVrfXgHMQuMAk9e7i/pub?gid=1218680906&single=true&output=csv")
    Observable<List<News>> getNews();

    @GET("spreadsheets/d/e/2PACX-1vSh_wTT0k3XBfwoaAZeocBX9LCjd3sYk8Y0DbrGqPeTWCETpTy9TWmcDMSxWjODVrfXgHMQuMAk9e7i/pub?gid=1186789033&single=true&output=csv")
    Observable<List<Configuration>> getConfiguration();

}
