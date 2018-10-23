package org.halloweenalcala.app.ui.slogan_contest;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.firestore.UserInteractor;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BaseInteractor;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.errors.UserNotAddedError;
import org.halloweenalcala.app.model.cloud.User;
import org.halloweenalcala.app.ui.slogan_contest.my_slogans.MySlogansFragment;
import org.halloweenalcala.app.ui.slogan_contest.voting.VotingFragment;
import org.halloweenalcala.app.util.Util;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SloganContestFragment extends BaseFragment implements TabLayout.BaseOnTabSelectedListener {

    private TabLayout tabsContest;
    private FrameLayout frameContest;
    private VotingFragment votingFragment;
    private MySlogansFragment mySlogansFragment;
    private ArrayList<Fragment> sections = new ArrayList<>();
    private int currentSection = -1;

    private void findViews(View layout) {
        tabsContest = (TabLayout) layout.findViewById(R.id.tabs_contest);
        frameContest = (FrameLayout) layout.findViewById(R.id.frame_contest);

        tabsContest.addOnTabSelectedListener(this);
    }


    public SloganContestFragment() {
        // Required empty public constructor
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_slogan_contest, container, false);
        findViews(layout);

        votingFragment = new VotingFragment();
        mySlogansFragment = new MySlogansFragment();

        sections.add(votingFragment);
        sections.add(mySlogansFragment);

//        getPrefs().edit().putBoolean(App.SHARED_ACCEPTED_CONTEST_RULES, false).apply();

        addUserToFirestoreIfNotExists();

        show(0);

        return layout;
    }

    private void addUserToFirestoreIfNotExists() {

        // To vote this is needed, to send slogans email must be set

        if (!getPrefs().getBoolean(App.SHARED_USER_ADDED_FIRESTORE, false)) {
            final String deviceId = Util.getDeviceId(getActivity());
            User user = new User();
            user.setIdDevice(deviceId);
            user.setPendingSlogans(App.NUM_SLOGANS);
            user.setTimestamp(Util.getCurrentDateTime());
            new UserInteractor(getActivity(), this).addUser(user, new BaseInteractor.CallbackPost() {
                @Override
                public void onSuccess(String id) {
                    getPrefs().edit().putBoolean(App.SHARED_USER_ADDED_FIRESTORE, true).apply();
                }

                @Override
                public void onError(String error) {
                    Crashlytics.logException(new UserNotAddedError("deviceId: " + deviceId + ", error: " + error));
                }
            });
        }


    }


    private void show(int sectionNumber) {

        Fragment fragmentToShow = sections.get(sectionNumber);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);

        if (!fragmentToShow.isAdded()) {
            fragmentTransaction.add(R.id.frame_contest, fragmentToShow);
        } else if (fragmentToShow.isHidden()) {
            fragmentTransaction.show(fragmentToShow);
        } else {
            throw new IllegalStateException("WTF happen with this fragment: " + fragmentToShow.toString());
        }


        if (currentSection >= 0) {
            Fragment fragmentToHide = sections.get(currentSection);
            fragmentTransaction.hide(fragmentToHide);
        }
        fragmentTransaction.commit();

        currentSection = sectionNumber;

    }

    private void showContestRules() {

        TextView textView = new TextView(getActivity());
        int padding = getResources().getDimensionPixelSize(R.dimen.dp15);
        textView.setPadding(padding, padding, padding, padding);
        Util.setHtmlLinkableText(textView, getString(R.string.contest_rules_text_html));
        new AlertDialog.Builder(getActivity())
//                .setTitle(R.string.contest_rules)
                .setView(textView)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPrefs().edit().putBoolean(App.SHARED_ACCEPTED_CONTEST_RULES, true).apply();
                    }
                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tabsContest.getTabAt(0).select();
                        toast(R.string.must_accept_rules_to_participate);
                    }
                })
                .setCancelable(false)
                .show();
    }


    // INTERACTIONS
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 1) {
            if (!getPrefs().getBoolean(App.SHARED_ACCEPTED_CONTEST_RULES, false)) {
                showContestRules();
            }
        }
        show(tab.getPosition());
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
