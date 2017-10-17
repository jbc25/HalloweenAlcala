package org.halloweenalcala.app.ui.news.list;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.News;

import java.util.List;

/**
 * Created by julio on 17/10/17.
 */

public interface NewsView extends BaseView {

    void showNews(List<News> newsList);
}
