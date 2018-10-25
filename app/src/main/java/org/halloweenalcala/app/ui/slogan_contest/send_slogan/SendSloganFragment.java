package org.halloweenalcala.app.ui.slogan_contest.send_slogan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;
import org.halloweenalcala.app.util.Util;
import org.halloweenalcala.app.util.WindowUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendSloganFragment extends BaseFragment implements SendSloganView, View.OnClickListener {


    private SendSloganPresenter presenter;
    private LinearLayout viewEnterEmail;
    private AppCompatEditText editEmail;
    private AppCompatButton btnSendEmail;
    private TextView tvEmail;
    private View viewEnterSlogan;
    private AppCompatEditText editSlogan;
    private AppCompatButton btnSendSlogan;
    private TextView tvNumSlogansPending;
    private TextView tvSeeContestRules;

    private void findViews(View layout) {
        viewEnterEmail = (LinearLayout)layout.findViewById( R.id.view_enter_email );
        editEmail = (AppCompatEditText)layout.findViewById( R.id.edit_email );
        btnSendEmail = (AppCompatButton)layout.findViewById( R.id.btn_send_email );
        tvEmail = (TextView)layout.findViewById( R.id.tv_email );
        tvNumSlogansPending = (TextView)layout.findViewById( R.id.tv_num_slogans_pending );
        viewEnterSlogan = layout.findViewById( R.id.view_enter_slogan );
        editSlogan = (AppCompatEditText)layout.findViewById( R.id.edit_slogan );
        btnSendSlogan = (AppCompatButton)layout.findViewById( R.id.btn_send_slogan );
        tvSeeContestRules = (TextView)layout.findViewById( R.id.tv_see_contest_rules );

        btnSendEmail.setOnClickListener( this );
        btnSendSlogan.setOnClickListener( this );
        tvSeeContestRules.setOnClickListener( this );
    }


    public SendSloganFragment() {
        // Required empty public constructor
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = SendSloganPresenter.newInstance(this, getActivity());
        View layout = inflater.inflate(R.layout.fragment_send_slogan, container, false);
        findViews(layout);

        presenter.onCreate();


        return layout;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        presenter.onHiddenChanged(hidden);
    }


    // INTERACTIONS

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_send_email:
                WindowUtils.hideSoftKeyboard(getActivity());
                String email = editEmail.getText().toString();
                presenter.onSendEmailButtonClick(email);
                break;

            case R.id.btn_send_slogan:

                WindowUtils.hideSoftKeyboard(getActivity());
                String slogan = editSlogan.getText().toString();
                presenter.onSendSloganButtonClick(slogan);
                break;

            case R.id.tv_see_contest_rules:
                showContestRules();
                break;
        }
    }


    private void showContestRules() {

        TextView textView = new TextView(getActivity());
        int padding = getResources().getDimensionPixelSize(R.dimen.dp15);
        textView.setPadding(padding, padding, padding, padding);
        Util.setHtmlLinkableText(textView, getString(R.string.contest_rules_text_html));
        new AlertDialog.Builder(getActivity())
//                .setTitle(R.string.contest_rules)
                .setView(textView)
                .setPositiveButton(R.string.back, null)
                .show();
    }


    // PRESENTER CALLBACKS

    @Override
    public void setActiveAndshowEmail(String email) {
        viewEnterEmail.setVisibility(email != null ? View.GONE : View.VISIBLE);
        viewEnterSlogan.setVisibility(email != null ? View.VISIBLE : View.GONE);
        tvEmail.setText(email);

        if (email != null) {
            editSlogan.requestFocus();
        }
    }

    @Override
    public void showMySlogans(List<Slogan> slogans) {

    }

    @Override
    public void showInvalidEmailMessage(String errorMessage) {
        editEmail.setError(errorMessage);
    }

    @Override
    public void clearSloganText() {
        editSlogan.setText("");
    }

    @Override
    public void showSlogansPending(int numSlogansPending) {
        tvNumSlogansPending.setText(String.format(getString(R.string.num_slogans_pending_msg), numSlogansPending));
        if (numSlogansPending == 0) {
            tvNumSlogansPending.setText(String.format(getString(R.string.all_slogans_sent), App.NUM_SLOGANS));
            viewEnterSlogan.setAlpha(0.5f);
            editSlogan.setEnabled(false);
            btnSendSlogan.setEnabled(false);
        }
    }
}
