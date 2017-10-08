package org.halloweenalcala.app.ui.poems;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoemsFragment extends BaseFragment {

    private PoemPagerAdapter adapter;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private ViewPager viewpagerPoems;

    public PoemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_poems, container, false);

        viewpagerPoems = (ViewPager) layout.findViewById(R.id.viewpager_poems);


        adapter = new PoemPagerAdapter(getChildFragmentManager());
        viewpagerPoems.setAdapter(adapter);

        viewpagerPoems.setPageTransformer(false, new CubeOutTransformer());

        return layout;
    }

}
