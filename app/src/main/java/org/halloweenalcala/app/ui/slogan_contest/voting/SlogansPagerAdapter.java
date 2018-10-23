package org.halloweenalcala.app.ui.slogan_contest.voting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

/**
 * Created by julio on 6/10/17.
 */

public class SlogansPagerAdapter extends FragmentPagerAdapter {

    private final List<Slogan> slogans;

    public SlogansPagerAdapter(FragmentManager fm, List<Slogan> slogans) {
        super(fm);
        this.slogans = slogans;
    }


    @Override
    public Fragment getItem(int position) {
        return SloganItemFragment.newInstance(slogans.get(position));
    }

    @Override
    public int getCount() {
        return slogans.size();
    }
}
