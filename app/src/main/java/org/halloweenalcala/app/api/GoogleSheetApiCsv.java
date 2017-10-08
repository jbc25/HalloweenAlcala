package org.halloweenalcala.app.api;


import org.halloweenalcala.app.model.Configuration;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.model.Performance;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface GoogleSheetApiCsv {


    // Retrofit CSV: http://mateuyabar.com/blog/2015/10/01/Use-Google-Spreadsheets-as-data-for-an-Android-app/

    @GET("spreadsheets/d/e/2PACX-1vTPp_zC9vHYA5AGPZqv8WSpRy3yWUzMMbIGJ0PUumHWys7p3l1lUrL8s251_0n45_HBIXJvJr4g3y1U/pub?gid=127251563&single=true&output=csv")
    Observable<List<Participant>> getParticipants();

    @GET("spreadsheets/d/e/2PACX-1vTPp_zC9vHYA5AGPZqv8WSpRy3yWUzMMbIGJ0PUumHWys7p3l1lUrL8s251_0n45_HBIXJvJr4g3y1U/pub?gid=0&single=true&output=csv")
    Observable<List<Place>> getPlaces();

    @GET("spreadsheets/d/e/2PACX-1vTPp_zC9vHYA5AGPZqv8WSpRy3yWUzMMbIGJ0PUumHWys7p3l1lUrL8s251_0n45_HBIXJvJr4g3y1U/pub?gid=1709987993&single=true&output=csv")
    Observable<List<Performance>> getPerformances();

    @GET("spreadsheets/d/e/2PACX-1vTPp_zC9vHYA5AGPZqv8WSpRy3yWUzMMbIGJ0PUumHWys7p3l1lUrL8s251_0n45_HBIXJvJr4g3y1U/pub?gid=1186789033&single=true&output=csv")
    Observable<List<Configuration>> getConfiguration();

}
