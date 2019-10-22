package org.halloweenalcala.app.ui.map;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Place;

import java.util.List;

/**
 * Created by julio on 6/10/17.
 */

public class MapsFragment extends BaseFragment implements OnMapReadyCallback, MapsView, GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    private MapsPresenter presenter;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        presenter = MapsPresenter.newInstance(this, getActivity());

        View layout = inflater.inflate(R.layout.fragment_maps, null);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        presenter.onCreate(getArguments());

        return layout;

    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map, menu);
    }

    // CONFIGURATIONS
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
                new LatLngBounds(new LatLng(40.480143, -3.369801),
                        new LatLng(40.483538, -3.364050)), getResources().getDimensionPixelSize(R.dimen.padding_map)));


        /*
        Styling Google Map:
        https://mapstyle.withgoogle.com/
        https://developers.google.com/maps/documentation/android-api/style-reference
         */
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getActivity(), R.raw.style_map));

        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);

        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
////                toast(marker.getTag().toString());
//                toastHalloween(R.string.hurry_no_time);
//            }
//        });


        presenter.onMapReady();

    }


    // INTERACTIONS

    @Override
    public boolean onMarkerClick(Marker marker) {
        final Place place = (Place) marker.getTag();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.onPlaceMarkerClick(place);
            }
        }, 400);

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_legend:
                showLegendInfo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLegendInfo() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        ab.setTitle(R.string.legend);
        ab.setView(View.inflate(getActivity(), R.layout.view_legend, null));
        ab.setNegativeButton(R.string.close, null);
        ab.show();
    }

    // PRESENTER CALLBACKS
    @Override
    public void showMarkers(List<Place> places, Place placeFindCode) {

        Marker markerToSelect = null;

        for (Place place : places) {

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(place.getLat(), place.getLng()))
                    .icon(BitmapDescriptorFactory.fromResource(place.getMarkerIcon()));

            if (isSelectedPlace(place, placeFindCode)) {
                if (placeFindCode.getId_server() == place.getId_server()) {
                    markerOptions.title(placeFindCode.getName());
                    markerOptions.snippet(getString(R.string.find_code));
                }

            }

            Marker marker = mMap.addMarker(markerOptions);

            marker.setTag(place);

            if (isSelectedPlace(place, placeFindCode)) {
                markerToSelect = marker;
            }
        }

        if (markerToSelect != null) {
            markerToSelect.showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLng(markerToSelect.getPosition()));
        }

    }

    private boolean isSelectedPlace(Place place, Place placeSelected) {
        return placeSelected != null && placeSelected.getId_server() == place.getId_server();
    }

}
