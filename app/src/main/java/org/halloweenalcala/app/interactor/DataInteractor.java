package org.halloweenalcala.app.interactor;

import android.content.Context;

import org.halloweenalcala.app.api.retrofit.ApiClient;
import org.halloweenalcala.app.api.retrofit.GoogleSheetApiCsv;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.News;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.model.Performance;
import org.halloweenalcala.app.model.Place;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by julio on 8/10/17.
 */

public class DataInteractor extends BaseInteractor {


    public interface DataCallback<T> {
        void onSuccess(List<T> list);

        void onError(String message);
    }

    public DataInteractor(Context context, BaseView baseView) {
        this.baseView = baseView;
        this.context = context;
    }

    public void getPlaces(final DataCallback<Place> callback) {

        ApiClient.getInstance().create(GoogleSheetApiCsv.class).getPlaces()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<Place>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

                                    callback.onError(e.getMessage());
                               }

                               @Override
                               public void onNext(List<Place> list) {

                                   // Only one row in configuration
                                   callback.onSuccess(list);
                               }
                           }


                );
    }

    public void getParticipants(final DataCallback<Participant> callback) {

        ApiClient.getInstance().create(GoogleSheetApiCsv.class).getParticipants()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<Participant>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

                                   callback.onError(e.getMessage());
                               }

                               @Override
                               public void onNext(List<Participant> list) {

                                   // Only one row in configuration
                                   callback.onSuccess(list);
                               }
                           }


                );
    }

    public void getPerformances(final DataCallback<Performance> callback) {

        ApiClient.getInstance().create(GoogleSheetApiCsv.class).getPerformances()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<Performance>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

                                   callback.onError(e.getMessage());
                               }

                               @Override
                               public void onNext(List<Performance> list) {

                                   // Only one row in configuration
                                   callback.onSuccess(list);
                               }
                           }


                );
    }


    public void getNews(final DataCallback<News> callback) {

        ApiClient.getInstance().create(GoogleSheetApiCsv.class).getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<News>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

                                   callback.onError(e.getMessage());
                               }

                               @Override
                               public void onNext(List<News> list) {

                                   // Only one row in configuration
                                   callback.onSuccess(list);
                               }
                           }


                );
    }

}
