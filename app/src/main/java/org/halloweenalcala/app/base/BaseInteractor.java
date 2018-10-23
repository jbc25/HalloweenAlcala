package org.halloweenalcala.app.base;

import android.content.Context;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.retrofit.ApiClient;
import org.halloweenalcala.app.util.Util;

import java.util.List;

import rx.functions.Action0;

/**
 * Created by julio on 14/02/16.
 */
public class BaseInteractor {

    public final String TAG = this.getClass().getSimpleName();

    public Context context;
    public BaseView baseView;

    public BaseInteractor(Context context, BaseView baseView) {
        this.context = context;
        this.baseView = baseView;
    }

    public <T> T getApi(Class<T> service) {
        return ApiClient.getInstance().create(service);
    }


    public Action0 actionTerminate = new Action0() {
        @Override
        public void call() {

            if (baseView != null) {
                baseView.setRefresing(false);
                baseView.hideProgressDialog();
            }

        }
    };


    public interface CallbackGetList<T> {
        void onListReceived(List<T> list);

        void onError(String error);
    }

    public interface CallbackGetEntity<T> {
        void onEntityReceived(T entity);

        void onError(String error);
    }

    public interface CallbackPost {
        void onSuccess(String id);
        void onError(String error);
    }


    public boolean isConnected() {

        boolean connected = Util.isConnected(context);

        if (!connected) {
            if (baseView != null) {
                baseView.toast(R.string.no_connection);
            }
        }

        return connected;
    }

}
