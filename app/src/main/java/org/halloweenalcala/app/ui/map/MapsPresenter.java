package org.halloweenalcala.app.ui.map;

import android.content.Context;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.csv.CsvConverter;
import org.halloweenalcala.app.model.Place;

import java.io.IOException;
import java.util.List;

/**
 * Created by julio on 9/10/17.
 */


 public class MapsPresenter extends BasePresenter {

     private final MapsView view;

     public static MapsPresenter newInstance(MapsView view, Context context) {

         return new MapsPresenter(view, context);

     }

     private MapsPresenter(MapsView view, Context context) {
         super(context, view);

         this.view = view;

     }

     public void onCreate() {

     }

     public void onResume() {

         refreshData();
     }

    public void onMapReady() {

        String csvPlaces = getPrefs().getString(App.SHARED_DATA_PLACES, null);
        if (csvPlaces != null) {
            try {
                List<Place> places = new CsvConverter<>(Place.class).convert(csvPlaces);
                view.showMarkers(places);
            } catch (IOException e) {
                e.printStackTrace();
                view.toast("Error parsing csv");
            }
        } else {
            view.toast("Not places data stored");
        }
    }

     public void refreshData() {


     }

}
