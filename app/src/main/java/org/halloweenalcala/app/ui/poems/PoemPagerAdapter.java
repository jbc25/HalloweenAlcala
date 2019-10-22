package org.halloweenalcala.app.ui.poems;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
