package org.halloweenalcala.app.ui.slogan_contest.voting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.cloud.Slogan;

/**
 * A simple {@link Fragment} subclass.
 */
public class SloganItemFragment extends Fragment {


    private static final String ARG_SLOGAN = "arg_slogan";
    private TextView tvSlogan;

    public SloganItemFragment() {
        // Required empty public constructor
    }

    public static SloganItemFragment newInstance(Slogan slogan) {

        SloganItemFragment fragment = new SloganItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SLOGAN, slogan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_slogan_item, container, false);

        tvSlogan = (TextView) layout.findViewById(R.id.tv_slogan);

        Slogan slogan = (Slogan) getArguments().getSerializable(ARG_SLOGAN);
        tvSlogan.setText(slogan.getText());

        return layout;
    }

}
