package org.halloweenalcala.app.ui.slogan_contest.ranking;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

public interface RankingSlogansView extends BaseView {
    void showSlogans(List<Slogan> slogans);
}
