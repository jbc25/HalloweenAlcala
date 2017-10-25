package org.halloweenalcala.app.ui.news.list;

import android.content.Context;

import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.News;
import org.halloweenalcala.app.ui.news.detail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by julio on 17/10/17.
 */


 public class NewsPresenter extends BasePresenter {

     private final NewsView view;
    private List<News> newsList = new ArrayList<>();

    public static NewsPresenter newInstance(NewsView view, Context context) {

         return new NewsPresenter(view, context);

     }

     private NewsPresenter(NewsView view, Context context) {
         super(context, view);

         this.view = view;

     }

     public void onCreate() {

         refreshData();
     }

     public void onResume() {

     }

     public void refreshData() {


         newsList.clear();
         newsList.addAll(News.listAll(News.class));

         Collections.sort(newsList);

         view.showNews(newsList);
     }

    public void onNewsClick(int position) {
        News news = newsList.get(position);
        NewsDetailActivity.start(context, news);
    }
}
