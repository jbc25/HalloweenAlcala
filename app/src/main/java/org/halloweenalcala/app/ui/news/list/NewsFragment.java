package org.halloweenalcala.app.ui.news.list;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.News;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment implements NewsView, NewsAdapter.OnItemClickListener {


    private NewsPresenter presenter;
    private RecyclerView recyclerNews;
    private NewsAdapter adapter;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = NewsPresenter.newInstance(this, getActivity());

        View layout = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerNews = layout.findViewById(R.id.recycler_news);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        RecyclerView.ItemDecoration itemDecoration = new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.space_grid));

        recyclerNews.setLayoutManager(layoutManager);
//        recyclerNews.addItemDecoration(itemDecoration);

        presenter.onCreate();

        baseActivity.setToolbarTitle(R.string.news);

        return layout;
    }

    @Override
    public void showNews(List<News> newsList) {

        if (adapter == null) {
            adapter = new NewsAdapter(getActivity(), newsList);
            adapter.setOnItemClickListener(this);
            recyclerNews.setAdapter(adapter);
        } else {
            adapter.updateData(newsList);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.onNewsClick(position);
    }
}
