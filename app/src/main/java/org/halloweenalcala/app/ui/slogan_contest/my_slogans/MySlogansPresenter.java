package org.halloweenalcala.app.ui.slogan_contest.my_slogans;


import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.firestore.SloganInteractor;
import org.halloweenalcala.app.api.firestore.UserInteractor;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;
import org.halloweenalcala.app.util.Util;

import java.util.regex.Pattern;

public class MySlogansPresenter extends BasePresenter {

    private final MySlogansView view;

    public static MySlogansPresenter newInstance(MySlogansView view, Context context) {

        return new MySlogansPresenter(view, context);

    }

    private MySlogansPresenter(MySlogansView view, Context context) {
        super(context, view);

        this.view = view;

    }

    public void onCreate() {

//        FirestoreHelper.addUser();
        String email = getPrefs().getString(App.SHARED_USER_EMAIL, null);
        view.setActiveAndshowEmail(email);

        refreshSlogansPending();

//        onSendEmailButtonClick("a@b.com");
    }


    public void onSendEmailButtonClick(final String email) {

        if (!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(), email)) {
            view.showInvalidEmailMessage(getString(R.string.invalid_email));
            return;
        }

        view.showProgressDialog(getString(R.string.sending));

        String deviceId = Util.getDeviceId(context);
        new UserInteractor(context, view).setUserEmail(deviceId, email, new BaseInteractor.CallbackPost() {
            @Override
            public void onSuccess(String id) {
                getPrefs().edit().putString(App.SHARED_USER_EMAIL, email).apply();
                view.setActiveAndshowEmail(email);
            }

            @Override
            public void onError(String error) {
                view.toast(error);
            }
        });
    }

    private void updatePendingSlogansCloud() {
        String deviceId = Util.getDeviceId(context);
        new UserInteractor(context, view).setUserPendingSlogans(deviceId, getNumSlogansPending());
    }

    public void onHiddenChanged(boolean hidden) {

    }

    public void onSendSloganButtonClick(String sloganText) {

        if (TextUtils.isEmpty(sloganText)) {
            view.toast(R.string.slogan_text_empty);
            return;
        }

        Slogan slogan = new Slogan(Util.getDeviceId(context), sloganText, Util.getCurrentDateTime());
        view.showProgressDialog(getString(R.string.sending));
        new SloganInteractor(context, view).addSlogan(slogan, new BaseInteractor.CallbackPost() {
            @Override
            public void onSuccess(String id) {
                view.toast(R.string.slogan_sent_success);
                view.clearSloganText();
                updateAndRefreshSlogansPending();
            }

            @Override
            public void onError(String error) {
                view.toast(R.string.error_sending);
            }
        });
    }

    private void updateAndRefreshSlogansPending() {
        getPrefs().edit().putInt(App.SHARED_NUMBER_SLOGANS_PENDING, getNumSlogansPending() - 1).commit();
        refreshSlogansPending();
        updatePendingSlogansCloud();
    }


    private void refreshSlogansPending() {
        view.showSlogansPending(getNumSlogansPending());
    }

    private int getNumSlogansPending() {
        return getPrefs().getInt(App.SHARED_NUMBER_SLOGANS_PENDING, App.NUM_SLOGANS);
    }
}
