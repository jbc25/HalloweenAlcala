package org.halloweenalcala.app.interactor;

import android.content.Context;

import org.halloweenalcala.app.api.retrofit.ApiClient;
import org.halloweenalcala.app.api.retrofit.GoogleSheetApiCsv;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.Configuration;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by julio on 8/10/17.
 */

public class ConfigurationInteractor extends BaseInteractor {


    public interface ConfigurationCallback {
        void onSuccess(Configuration configuration);

        void onError(String message);
    }

    public ConfigurationInteractor(Context context, BaseView baseView) {
        this.baseView = baseView;
        this.context = context;
    }

    public void getConfiguration(final ConfigurationCallback callback) {

        ApiClient.getInstance().create(GoogleSheetApiCsv.class).getConfiguration()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<Configuration>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

                                    callback.onError(e.getMessage());
                               }

                               @Override
                               public void onNext(List<Configuration> configurationList) {

                                   // Only one row in configuration
                                   callback.onSuccess(configurationList.get(0));
                               }
                           }


                );
    }

}
