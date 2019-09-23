package org.halloweenalcala.app.ui.slogan_contest.denounces;


import android.content.Context;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.interactor.firestore.SloganInteractor;
import org.halloweenalcala.app.interactor.firestore.UserInteractor;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.ArrayList;
import java.util.List;

public class DenouncesPresenter extends BasePresenter {

    private final DenouncesView view;
    private final SloganInteractor sloganInteractor;
    private List<Slogan> slogans = new ArrayList<>();


    public static DenouncesPresenter newInstance(DenouncesView view, Context context) {

        return new DenouncesPresenter(view, context);

    }

    private DenouncesPresenter(DenouncesView view, Context context) {
        super(context, view);

        this.view = view;

        sloganInteractor = new SloganInteractor(context, view);

    }

    public void onCreate() {

        refreshData();

    }


    public void refreshData() {

        sloganInteractor.getSlogansDenounced(new BaseInteractor.CallbackGetList<Slogan>() {

            @Override
            public void onListReceived(List<Slogan> list) {
                slogans.clear();
                slogans.addAll(list);
                view.showSlogans(slogans);
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });

    }

    public void onValidateSloganButtonClick(int position) {

        Slogan slogan = slogans.get(position);
        view.showProgressDialog(getString(R.string.loading));

        sloganInteractor.setSloganDenounced(slogan.getId(), false, new BaseInteractor.CallbackPost() {
            @Override
            public void onSuccess(String id) {
                view.toast(R.string.slogan_validated);
                refreshData();
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });

    }

    public void onDeleteSloganButtonClick(int position) {

        Slogan slogan = slogans.get(position);
        view.showProgressDialog(getString(R.string.deleting_slogan));
        sloganInteractor.setSloganDeleted(slogan.getId(), true, new BaseInteractor.CallbackPost() {
            @Override
            public void onSuccess(String id) {
                view.toast(R.string.slogan_deleted);
                refreshData();
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });
    }

    public void onBanUserButtonClick(int position) {

        Slogan slogan = slogans.get(position);

        view.showProgressDialog(getString(R.string.banning_user));
        new UserInteractor(context, view).setUserBanned(slogan.getIdDevice(), new BaseInteractor.CallbackPost() {
            @Override
            public void onSuccess(String id) {
                view.toast(R.string.user_banned);
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });
    }


    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            refreshData();
        }
    }
}
