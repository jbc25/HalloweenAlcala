package org.halloweenalcala.app.ui.map;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Performance;
import org.halloweenalcala.app.model.Place;

import java.util.Collections;
import java.util.List;

/**
 * Created by julio on 9/10/17.
 */


public class MapsPresenter extends BasePresenter {

    private static final String EXTRA_PLACE_FIND_CODE = "EXTRA_PLACE_FIND_CODE";

    private final MapsView view;
    private Place placeFindCode;

    public static MapsPresenter newInstance(MapsView view, Context context) {

        return new MapsPresenter(view, context);

    }

    public static MapsFragment newFragment(Place place) {

        MapsFragment mapsFragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PLACE_FIND_CODE, place);
        mapsFragment.setArguments(args);
        return mapsFragment;
    }

    private MapsPresenter(MapsView view, Context context) {
        super(context, view);

        this.view = view;

    }

    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            this.placeFindCode = (Place) arguments.getSerializable(EXTRA_PLACE_FIND_CODE);
        }
    }

    public void onResume() {

        refreshData();
    }

    public void onMapReady() {

        List<Place> placeList = Place.listAll(Place.class);

        for (Place place : placeList) {
            List<Performance> performanceList = Select.from(Performance.class).where(Condition.prop("idplace").eq(place.getId_server())).list();
            Collections.sort(performanceList);
            place.setPerformances(performanceList);
        }

        view.showMarkers(placeList, placeFindCode);



        return;

    }

    public void refreshData() {


    }

    public void onPlaceMarkerClick(Place place) {

        String title = place.getRealNameWithHalloweenName();
        String text = "";
        for (Performance performance : place.getPerformances()) {
            text += "- " + performance.getDateTimeHumanFriendly() + ": " + performance.getTitle() + "\n\n";
        }

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle(title)
                .setMessage(text)
                .setNegativeButton(R.string.close, null)
                .show();
    }
}
