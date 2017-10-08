package org.halloweenalcala.app.ui.shows;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowsFragment extends BaseFragment {


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public ShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

}
