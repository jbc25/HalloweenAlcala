package org.halloweenalcala.app.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.halloweenalcala.app.R;

/**
 * Created by julio on 27/01/16.
 */
public abstract class BaseFragment extends Fragment implements BaseView {


    public final String TAG = this.getClass().getSimpleName();
    protected BaseActivity baseActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.baseActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("The activity "
                    + "hosting this fragment does not extend BaseActivity");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String screenName = this.getClass().getSimpleName();
        FirebaseAnalytics.getInstance(getActivity()).setCurrentScreen(getActivity(), screenName, screenName);
    }

    public abstract BasePresenter getPresenter();

    protected SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }


    @Override
    public void showProgressDialog(String message) {

        ((BaseActivity) getActivity()).showProgressDialog(message);
    }

    @Override
    public void hideProgressDialog() {

        ((BaseActivity) getActivity()).hideProgressDialog();
    }


    @Override
    public void setRefresing(boolean refresing) {

    }


    @Override
    public void toast(int stringResId) {
        ((BaseActivity) getActivity()).toast(stringResId);
    }

    @Override
    public void toast(String mensaje) {
        ((BaseActivity) getActivity()).toast(mensaje);
    }

    public void toastHalloween(String message) {

        // Customize Toast: https://stackoverflow.com/questions/11288475/custom-toast-in-android-a-simple-example

        View toastView = View.inflate(getActivity(), R.layout.toast, null);
        TextView tvToast = toastView.findViewById(R.id.tv_toast);
        tvToast.setText(message);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);

//        View toastView = toast.getView(); //This'll return the default View of the Toast.
//        /* And now you can get the TextView of the default View of the Toast. */
//        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
//        toastMessage.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_toast_halloween));
//        toastMessage.setTextColor(Color.RED);
////        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_fly, 0, 0, 0);
//        toastMessage.setGravity(Gravity.CENTER);
////        toastMessage.setCompoundDrawablePadding(16);
//        toastView.setBackgroundColor(Color.TRANSPARENT);
//
//        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.show();
    }

    public void toastHalloween(int stringResId) {
        toastHalloween(getString(stringResId));
    }

    @Override
    public void alert(String title, String message) {
        ((BaseActivity) getActivity()).alert(title, message);
    }

    @Override
    public void alert(String message) {
        ((BaseActivity) getActivity()).alert(message);
    }

    @Override
    public void onInvalidToken() {

    }
}
