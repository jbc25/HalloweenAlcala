package org.halloweenalcala.app.ui.poems;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by julio on 6/10/17.
 */

public class PoemPagerAdapter extends FragmentPagerAdapter {

    public PoemPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return PoemItemFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PoemBook.poemBook.size();
    }
}
