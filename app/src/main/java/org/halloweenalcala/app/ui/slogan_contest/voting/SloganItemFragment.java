package org.halloweenalcala.app.ui.slogan_contest.voting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;

/**
 * A simple {@link Fragment} subclass.
 */
public class SloganItemFragment extends BaseFragment {


    private static final String ARG_SLOGAN = "arg_slogan";
    private static final String ARG_POSITION = "arg_position";
    private TextView tvSlogan;
    private View viewBanner;

    public SloganItemFragment() {
        // Required empty public constructor
    }

    public static SloganItemFragment newInstance(int position, Slogan slogan) {

        SloganItemFragment fragment = new SloganItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POSITION, position);
        args.putSerializable(ARG_SLOGAN, slogan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_slogan_item, container, false);

        viewBanner = layout.findViewById(R.id.view_banner);
        tvSlogan = (TextView) layout.findViewById(R.id.tv_slogan);

        Slogan slogan = (Slogan) getArguments().getSerializable(ARG_SLOGAN);
        tvSlogan.setText(slogan.getText());


        Log.i(TAG, "onCreateView: textsize: " + tvSlogan.getTextSize());

        int position = getArguments().getInt(ARG_POSITION);
        switch (position % 2) {
            case 0:
                viewBanner.setRotation(8);
                break;

            case 1:
                viewBanner.setRotation(-8);
                break;

//            case 2:
//                viewBanner.setRotation(20);
//                break;
        }

        return layout;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
