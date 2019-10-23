package org.halloweenalcala.app.ui.zombiselfie;


import android.content.Context;

import org.halloweenalcala.app.base.BasePresenter;

public class ZombiSelfiePresenter extends BasePresenter {

    private final ZombiSelfieView view;

    public static ZombiSelfiePresenter newInstance(ZombiSelfieView view, Context context) {

        return new ZombiSelfiePresenter(view, context);

    }

    private ZombiSelfiePresenter(ZombiSelfieView view, Context context) {
        super(context, view);

        this.view = view;

    }

    public void onCreate() {

    }

    public void onResume() {

        refreshData();
    }

    public void refreshData() {


    }

}
