package org.halloweenalcala.app.ui.slogan_contest.ranking;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.firestore.SloganInteractor;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.ArrayList;
import java.util.List;

public class RankingSlogansPresenter extends BasePresenter {

    private final RankingSlogansView view;
    private List<Slogan> mySlogans = new ArrayList<>();


    public static RankingSlogansPresenter newInstance(RankingSlogansView view, Context context) {

        return new RankingSlogansPresenter(view, context);

    }

    private RankingSlogansPresenter(RankingSlogansView view, Context context) {
        super(context, view);

        this.view = view;

    }

    public void onCreate() {

        refreshData();

    }

    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            refreshData();
        }
    }
    

    public void refreshData() {

        new SloganInteractor(context, view).getRankingSlogans(new BaseInteractor.CallbackGetList<Slogan>() {

            @Override
            public void onListReceived(List<Slogan> list) {
                mySlogans.clear();
                mySlogans.addAll(list);
                view.showSlogans(mySlogans);
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });

    }

    public void onPromoteSloganClick(int position) {
        Slogan slogan = mySlogans.get(position);
//        showCustomizeMsgDialog(slogan);

        String sloganText = slogan.getText();
        String sloganLink = String.format(App.URL_PROMOTE_SLOGAN, slogan.getId());
        String urlGooglePlay = App.URL_GOOGLE_PLAY_APP;

        String textShare = String.format(getString(R.string.promote_slogan_default_message), sloganText, sloganLink, urlGooglePlay);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, textShare);
        intent.setType("text/plain");
        context.startActivity(intent);
    }

    private void showCustomizeMsgDialog(Slogan slogan) {
        View view = View.inflate(context, R.layout.view_promote_slogan_message, null);
        new AlertDialog.Builder(context);

    }
}
