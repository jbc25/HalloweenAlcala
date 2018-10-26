package org.halloweenalcala.app.ui.slogan_contest.voting;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.firestore.SloganInteractor;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;
import org.halloweenalcala.app.model.cloud.SloganRating;
import org.halloweenalcala.app.model.notifications.NotificationPush;
import org.halloweenalcala.app.util.Util;

import java.util.ArrayList;
import java.util.List;

public class VotingPresenter extends BasePresenter {

    public static final String ARG_ID_SLOGAN = "arg_id_slgan";

    private final VotingView view;
    private final SloganInteractor sloganInteractor;
    private List<Slogan> slogans = new ArrayList<>();
    private SloganRating currentSloganRating;
    private Slogan currentSlogan;
    private String idSlogan;

    public static VotingPresenter newInstance(VotingView view, Context context) {

        return new VotingPresenter(view, context);

    }

    private VotingPresenter(VotingView view, Context context) {
        super(context, view);

        this.view = view;
        sloganInteractor = new SloganInteractor(context, view);

    }

    public static VotingFragment newFragment(String idSlogan) {
        VotingFragment votingFragment = new VotingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_SLOGAN, idSlogan);
        votingFragment.setArguments(args);
        return votingFragment;
    }

    public void onCreate(Bundle args) {

        // If user enters app through promote slogan link, go to slogan after loading
        if (args != null) {
            idSlogan = args.getString(ARG_ID_SLOGAN);
        }

//        showMockData();
        refreshData();
    }


    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            refreshData();
        }
    }

    private void showMockData() {

        Slogan slogan0 = new Slogan();
        slogan0.setText("Participa en el concurso de lemas 8MZ a través de la app:");

        Slogan slogan1 = new Slogan();
        slogan1.setText("Sube tus lemas reivindicativos");

        Slogan slogan2 = new Slogan();
        slogan2.setText("por los derechos de los zombies");

        Slogan slogan3 = new Slogan();
        slogan3.setText("y vota a los más originales");

        Slogan slogan4 = new Slogan();
        slogan4.setText("diviértete y envía los tuyos. ¡Habrá premios para los más votados!");

        Slogan slogan5 = new Slogan();
        slogan5.setText("Descárgala gratuitamente en Google Play");

        Slogan slogan6 = new Slogan();
        slogan6.setText("#8MZ");

        slogans.add(slogan0);
        slogans.add(slogan1);
        slogans.add(slogan2);
        slogans.add(slogan3);
        slogans.add(slogan4);
        slogans.add(slogan5);
        slogans.add(slogan6);
        view.showSlogans(slogans);
    }

    private void refreshData() {
        view.showProgressDialog(getString(R.string.loading_zombifestacion));
        sloganInteractor.getSlogans(new BaseInteractor.CallbackGetList<Slogan>() {
            @Override
            public void onListReceived(List<Slogan> list) {
                slogans.clear();
                slogans.addAll(list);
                view.showSlogans(slogans);

                checkGoToSloganPromote();
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });
    }

    private void checkGoToSloganPromote() {
        if (idSlogan != null) {
            for (int i = 0; i < slogans.size(); i++) {
                if (TextUtils.equals(slogans.get(i).getId(), idSlogan)) {
                    view.goToSloganPosition(i);
                    currentSlogan = slogans.get(i);
                    view.showSloganCounter(i+1, slogans.size());
                    idSlogan = null;
                    return;
                }
            }

            view.toast(R.string.slogan_not_found);
        }
    }

    public void onSloganPageSelected(int position) {
        if (slogans.isEmpty()) {
            currentSlogan = null;
            view.showSloganCounter(0, 0);
            view.showRating(0);
            return;
        }

        currentSlogan = slogans.get(position);
        checkIfAlreadyVoted();

        view.showSloganCounter(position+1, slogans.size());
    }

    private void checkIfAlreadyVoted() {
        SloganRating sloganRating = App.getDB().sloganRatingDao().getRatingOfSlogan(currentSlogan.getId());

        view.showRating(sloganRating != null ? sloganRating.getRating() : 0);


        currentSloganRating = sloganRating;
    }

    public void onRatingChange(int rating) {
        if (currentSloganRating != null) {
            view.toast(R.string.already_voted_slogan);
            view.showRating(currentSloganRating.getRating());
        } else {

            final SloganRating sloganRating = new SloganRating();
            sloganRating.setIdDevice(currentSlogan.getIdDevice());
            sloganRating.setIdSlogan(currentSlogan.getId());
            sloganRating.setRating(rating);
            sloganRating.setTimestamp(Util.getCurrentDateTime());

            view.showProgressDialog(getString(R.string.sending));

//            if (true) {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.hideProgressDialog();
//                    }
//                }, 2000);
//
//                return;
//            }

            sloganInteractor.addSloganRating(sloganRating, new BaseInteractor.CallbackPost() {
                @Override
                public void onSuccess(String id) {

                    App.getDB().sloganRatingDao().insert(sloganRating);
                    currentSloganRating = sloganRating;
                    view.hideProgressDialog();
                }

                @Override
                public void onError(String error) {
                    view.hideProgressDialog();
                    view.toast(R.string.error_sending_voting);

                    if (TextUtils.equals(sloganRating.getIdSlogan(), currentSlogan.getId())) {
                        // User still is in the same slogan
                        view.showRating(0);
                    }
                }
            });

        }
    }

    public void onDenounceSloganButtonClick() {

        new AlertDialog.Builder(context)
                .setTitle(R.string.denounce_slogan)
                .setMessage(R.string.denounce_slogan_message)
                .setPositiveButton(R.string.denounce, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        denounceSlogan();
                        sendNotificationToManagers();
                    }

                })
                .setNeutralButton(R.string.return_back, null)
                .show();

    }

    private void denounceSlogan() {

        view.showProgressDialog(getString(R.string.denouncing_slogan));

        if (currentSlogan != null) {
            sloganInteractor.setSloganDenounced(currentSlogan.getId(), true, new BaseInteractor.CallbackPost() {
                @Override
                public void onSuccess(String id) {
                    view.toast(R.string.slogan_denounced_thanks);
                    refreshData();
                }

                @Override
                public void onError(String error) {
                    view.toast(error);
                }
            });
        }
    }

    private void sendNotificationToManagers() {
        String to = "/topics/" + App.FIREBASE_TOPIC_REPORT_SLOGAN;
        String title = "¡Frase denunciada!";
        String body = currentSlogan != null ? currentSlogan.getText() : "slogan null";
        NotificationPush notificationPush = NotificationPush.createNotification(to, title, body);
        sloganInteractor.sendNotification(notificationPush, new BaseInteractor.CallbackPost() {
            @Override
            public void onSuccess(String id) {
                Log.i(TAG, "onSuccess: sendNotificationToManagers");
            }

            @Override
            public void onError(String error) {
                Log.i(TAG, "onError: sendNotificationToManagers");
            }
        });
    }
}
