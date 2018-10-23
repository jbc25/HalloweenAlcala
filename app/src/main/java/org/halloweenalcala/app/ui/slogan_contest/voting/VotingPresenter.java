package org.halloweenalcala.app.ui.slogan_contest.voting;


import android.content.Context;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.firestore.SloganInteractor;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;
import org.halloweenalcala.app.model.cloud.SloganRating;

import java.util.ArrayList;
import java.util.List;

public class VotingPresenter extends BasePresenter {

    private final VotingView view;
    private final SloganInteractor sloganInteractor;
    private List<Slogan> slogans = new ArrayList<>();
    private SloganRating currentSloganRating;
    private Slogan currentSlogan;

    public static VotingPresenter newInstance(VotingView view, Context context) {

        return new VotingPresenter(view, context);

    }

    private VotingPresenter(VotingView view, Context context) {
        super(context, view);

        this.view = view;
        sloganInteractor = new SloganInteractor(context, view);

    }

    public void onCreate() {

        refreshData();
    }

    private void refreshData() {
        view.showProgressDialog(getString(R.string.loading_zombifestacion));
        sloganInteractor.getSlogans(new BaseInteractor.CallbackGetList<Slogan>() {
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


    public void onSloganPageSelected(int position) {
        currentSlogan = slogans.get(position);
        checkIfAlreadyVoted();
    }

    private void checkIfAlreadyVoted() {
        SloganRating sloganRating = App.getDB().sloganRatingDao().getRatingOfSlogan(currentSlogan.getId());
        if (sloganRating != null) {
            view.showRating(sloganRating.getRating());
        }

        currentSloganRating = sloganRating;
    }

    public void onRatingChange(int rating) {
        if (currentSloganRating != null) {
            view.toast(R.string.already_voted_slogan);
        } else {
            // todo send to cloud and store locally
//            sloganInteractor.addSloganRating();
        }
    }
}
