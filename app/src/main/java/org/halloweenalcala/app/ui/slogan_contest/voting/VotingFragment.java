package org.halloweenalcala.app.ui.slogan_contest.voting;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;


public class VotingFragment extends BaseFragment implements VotingView, ViewPager.OnPageChangeListener, BrainRatingView.OnRatingChangeListener, View.OnClickListener {

    private ViewPager viewpagerSlogans;
    private BrainRatingView brainRatingView;
    private VotingPresenter presenter;
    private SlogansPagerAdapter adapterViewPager;
    private TextView tvSloganCounter;
    private View btnDenounceSlogan;
    private View imgTutoBanners;

    private void findViews(View layout) {
        viewpagerSlogans = (ViewPager)layout.findViewById( R.id.viewpager_slogans );
        brainRatingView = (BrainRatingView)layout.findViewById( R.id.brain_rating_view );
        tvSloganCounter = (TextView) layout.findViewById(R.id.tv_slogan_counter);
        btnDenounceSlogan = layout.findViewById(R.id.btn_denounce_slogan);
        btnDenounceSlogan.setOnClickListener(this);

        imgTutoBanners = layout.findViewById(R.id.img_tuto_banners);
        imgTutoBanners.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imgTutoBanners.setVisibility(View.GONE);
                getPrefs().edit().putBoolean(App.SHARED_SHOW_TUTO_BANNERS, false).commit();
                return false;
            }
        });
    }


    public VotingFragment() {

    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = VotingPresenter.newInstance(this, getActivity());
        View layout = inflater.inflate(R.layout.fragment_voting, container, false);
        findViews(layout);

        viewpagerSlogans.setPageTransformer(true, new ZombieProtestTransformer());
        viewpagerSlogans.addOnPageChangeListener(this);

        brainRatingView.setOnRatingChangeListener(this);

        getChildFragmentManager();

        presenter.onCreate(getArguments());

        if (getPrefs().getBoolean(App.SHARED_SHOW_TUTO_BANNERS, true)) {
            imgTutoBanners.setVisibility(View.VISIBLE);
        }

        return layout;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        presenter.onHiddenChanged(hidden);
    }

    // INTERACTIONS

    // Viewpager
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.onSloganPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_denounce_slogan:
                presenter.onDenounceSloganButtonClick();
                break;
        }

    }

    // PRESENTER CALLBACKS

    @Override
    public void showSlogans(List<Slogan> slogans) {
        if (adapterViewPager == null) {
            adapterViewPager = new SlogansPagerAdapter(getChildFragmentManager(), slogans);
            viewpagerSlogans.setAdapter(adapterViewPager);
            onPageSelected(0);
        } else {
            adapterViewPager.notifyDataSetChanged();
            onPageSelected(viewpagerSlogans.getCurrentItem());
        }

    }

    @Override
    public void showRating(int rating) {
        brainRatingView.setRating(rating);
    }

    @Override
    public void onRatingChange(int rating) {
        presenter.onRatingChange(rating);
    }

    @Override
    public void goToSloganPosition(int position) {
        viewpagerSlogans.setCurrentItem(position, true);
    }

    @Override
    public void showSloganCounter(int current, int total) {
        tvSloganCounter.setText(String.format(getString(R.string.counter_format), current, total));
    }

}
