package org.halloweenalcala.app.ui.performances.list;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Performance;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.ui.performances.detail.PerformanceDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by julio on 20/10/17.
 */


 public class PerformancesPresenter extends BasePresenter {

     private final PerformancesView view;
    private List<Performance> performances = new ArrayList<>();

     public static PerformancesPresenter newInstance(PerformancesView view, Context context) {

         return new PerformancesPresenter(view, context);

     }

     private PerformancesPresenter(PerformancesView view, Context context) {
         super(context, view);

         this.view = view;

     }

     public void onCreate() {

         refreshData();
     }

     public void refreshData() {

         performances.clear();
         List<Performance> performanceList = App.getDB().performanceDao().getAll();
         Collections.sort(performanceList);

         for (Performance performance : performanceList) {

             // Fucking Sugar orm. No more. https://stackoverflow.com/a/30192363/1365440
             Place place = App.getDB().placeDao().getById(performance.getId_place());
             performance.setPlace(place);
         }

         performances.addAll(performanceList);
         view.showPerformances(performances);

     }

    public void onPerformanceClicked(int position) {
        Performance performance = performances.get(position);
        PerformanceDetailActivity.launch(context, performance);
//        showPerformanceInfo(performance);
    }

    private void showPerformanceInfo(Performance performance) {
        AlertDialog.Builder ab = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        ab.setTitle(performance.getTitle());
        ab.setMessage(performance.getSubtitle() + "\n\n" + performance.getInfo() + "\n\n" +
                performance.getPlaceInfo());
        ab.setNegativeButton(R.string.close, null);
        ab.show();
    }
}
