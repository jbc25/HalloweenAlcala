package org.halloweenalcala.app.ui.map;

import android.content.Context;

import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Place;

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

        List<Place> placeList = Place.listAll(Place.class);
        view.showMarkers(placeList);
        return;

    }

    public void refreshData() {


    }

}
