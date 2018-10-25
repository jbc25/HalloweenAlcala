package org.halloweenalcala.app.ui.slogan_contest.ranking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingSlogansFragment extends BaseFragment implements RankingSlogansView, RankingSlogansAdapter.OnItemClickListener {


    private RankingSlogansPresenter presenter;
    private RecyclerView recyclerSlogans;
    private RankingSlogansAdapter adapter;

    public RankingSlogansFragment() {
        // Required empty public constructor
    }

    private void findViews(View layout) {
        recyclerSlogans = (RecyclerView) layout.findViewById(R.id.recycler_my_slogans);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = RankingSlogansPresenter.newInstance(this, getActivity());
        View layout =  inflater.inflate(R.layout.fragment_ranking_slogans, container, false);
        findViews(layout);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerSlogans.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerSlogans.addItemDecoration(divider);

        presenter.onCreate();

        return layout;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        presenter.onHiddenChanged(hidden);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }


    // INTERACTIONS

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onPromoteSloganClick(int position) {
        presenter.onPromoteSloganClick(position);
    }


    // PRESENTER CALLBACKS

    @Override
    public void showSlogans(List<Slogan> slogans) {
        if (adapter == null) {
            adapter = new RankingSlogansAdapter(getActivity(), slogans);
//            adapter.setOnItemClickListener(this);
            recyclerSlogans.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
