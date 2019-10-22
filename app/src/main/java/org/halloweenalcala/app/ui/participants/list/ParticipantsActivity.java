package org.halloweenalcala.app.ui.participants.list;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.util.SpacesItemDecoration;

import java.util.List;

/**
 * Created by julio on 9/10/17.
 */

public class ParticipantsActivity extends BaseActivity implements ParticipantsView, ParticipantsAdapter.OnItemClickListener {
    private ParticipantsPresenter presenter;
    private RecyclerView recyclerParticipants;
    private ParticipantsAdapter adapter;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    private void findViews() {
        recyclerParticipants = (RecyclerView) findViewById(R.id.recycler_participants);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        presenter = ParticipantsPresenter.newInstance(this, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        configureSecondLevelActivity();

        findViews();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView.ItemDecoration itemDecoration = new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.space_grid));

        recyclerParticipants.setLayoutManager(layoutManager);
        recyclerParticipants.addItemDecoration(itemDecoration);

        presenter.onCreate();
    }


    // INTERACTIONS

    @Override
    public void onItemClick(View view, int position) {

        presenter.onItemClick(position);
    }


    // PRESENTER CALLBACKS

    @Override
    public void showParticipants(List<Participant> participants) {

        if (adapter == null) {
            adapter = new ParticipantsAdapter(this, participants);
            adapter.setOnItemClickListener(this);
            recyclerParticipants.setAdapter(adapter);
        } else {
            adapter.updateData(participants);
        }
    }

}
