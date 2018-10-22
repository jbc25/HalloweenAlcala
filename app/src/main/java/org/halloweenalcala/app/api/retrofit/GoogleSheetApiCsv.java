package org.halloweenalcala.app.api.retrofit;


import org.halloweenalcala.app.model.Configuration;
import org.halloweenalcala.app.model.News;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.model.Performance;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface GoogleSheetApiCsv {


    // Retrofit CSV: http://mateuyabar.com/blog/2015/10/01/Use-Google-Spreadsheets-as-data-for-an-Android-app/

    @GET("spreadsheets/d/e/2PACX-1vSOfGofwUHSy05JTpGlwfn6GR9iDTTyH--z7_C2a8xaBpQEkZLB2CkgAyZna5vSSkMTKEq3E3hqo8HA/pub?gid=127251563&single=true&output=csv")
    Observable<List<Participant>> getParticipants();

    //
    @GET("spreadsheets/d/e/2PACX-1vSOfGofwUHSy05JTpGlwfn6GR9iDTTyH--z7_C2a8xaBpQEkZLB2CkgAyZna5vSSkMTKEq3E3hqo8HA/pub?gid=0&single=true&output=csv")
    Observable<List<Place>> getPlaces();

    @GET("spreadsheets/d/e/2PACX-1vSOfGofwUHSy05JTpGlwfn6GR9iDTTyH--z7_C2a8xaBpQEkZLB2CkgAyZna5vSSkMTKEq3E3hqo8HA/pub?gid=1709987993&single=true&output=csv")
    Observable<List<Performance>> getPerformances();

    @GET("spreadsheets/d/e/2PACX-1vSOfGofwUHSy05JTpGlwfn6GR9iDTTyH--z7_C2a8xaBpQEkZLB2CkgAyZna5vSSkMTKEq3E3hqo8HA/pub?gid=1218680906&single=true&output=csv")
    Observable<List<News>> getNews();

    @GET("spreadsheets/d/e/2PACX-1vSOfGofwUHSy05JTpGlwfn6GR9iDTTyH--z7_C2a8xaBpQEkZLB2CkgAyZna5vSSkMTKEq3E3hqo8HA/pub?gid=1186789033&single=true&output=csv")
    Observable<List<Configuration>> getConfiguration();

}
