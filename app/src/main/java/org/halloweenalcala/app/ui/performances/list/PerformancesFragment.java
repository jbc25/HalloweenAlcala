package org.halloweenalcala.app.ui.performances.list;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Performance;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerformancesFragment extends BaseFragment implements PerformancesView, PerformancesAdapter.OnItemClickListener {


    private PerformancesPresenter presenter;
    private RecyclerView recyclerPerformances;
    private PerformancesAdapter adapter;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    public PerformancesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = PerformancesPresenter.newInstance(this, getActivity());

        View layout = inflater.inflate(R.layout.fragment_performances, container, false);
        recyclerPerformances = layout.findViewById(R.id.recycler_performances);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerPerformances.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());
        Drawable verticalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        dividerItemDecoration.setDrawable(verticalDivider);
        recyclerPerformances.addItemDecoration(dividerItemDecoration);

        presenter.onCreate();

        return layout;
    }

    @Override
    public void showPerformances(List<Performance> performances) {

        if (adapter == null) {

            adapter = new PerformancesAdapter(getActivity(), performances);
            adapter.setOnItemClickListener(this);

            // https://github.com/edubarr/header-decor
            StickyHeaderDecoration decor = new StickyHeaderDecoration(adapter);

            recyclerPerformances.setAdapter(adapter);
            recyclerPerformances.addItemDecoration(decor);

        } else {
            adapter.updateData(performances);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.onPerformanceClicked(position);
    }
}
