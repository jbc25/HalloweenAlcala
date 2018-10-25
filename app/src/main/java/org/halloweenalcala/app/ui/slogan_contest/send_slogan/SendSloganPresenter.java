package org.halloweenalcala.app.ui.slogan_contest.send_slogan;


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
import org.halloweenalcala.app.model.cloud.User;
import org.halloweenalcala.app.util.Util;

import java.util.regex.Pattern;

public class SendSloganPresenter extends BasePresenter {

    private final SendSloganView view;

    public static SendSloganPresenter newInstance(SendSloganView view, Context context) {

        return new SendSloganPresenter(view, context);

    }

    private SendSloganPresenter(SendSloganView view, Context context) {
        super(context, view);

        this.view = view;

    }

    public void onCreate() {

//        FirestoreHelper.addUser();
        String email = getPrefs().getString(App.SHARED_USER_EMAIL, null);
        view.setActiveAndshowEmail(email);

        if (email != null) {
            refreshSlogansPending();
        }

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
                refreshSlogansPending();
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

        final Slogan slogan = new Slogan(Util.getDeviceId(context), sloganText, Util.getCurrentDateTime());
        view.showProgressDialog(getString(R.string.sending));

        checkUserNotBanned(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    private void checkUserNotBanned(final Runnable runnable) {
        if (getPrefs().getBoolean(App.SHARED_USER_BANNED, false)) {
            view.alert(getString(R.string.you_are_banned));
            view.hideProgressDialog();
            return;
        }

        String deviceId = Util.getDeviceId(context);
        new UserInteractor(null, null).getUser(deviceId, new BaseInteractor.CallbackGetEntity<User>() {
            @Override
            public void onEntityReceived(User userReceived) {
                if (userReceived != null) {
                    if (userReceived.isBanned()) {
                        getPrefs().edit().putBoolean(App.SHARED_USER_BANNED, true).commit();
                        view.alert(getString(R.string.you_are_banned));
                    } else {
                        runnable.run();
                    }
                } else {
                    view.toast(R.string.error_retrieving_data);
                }
                view.hideProgressDialog();
            }

            @Override
            public void onError(String error) {
                view.hideProgressDialog();
                view.toast(error);
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
