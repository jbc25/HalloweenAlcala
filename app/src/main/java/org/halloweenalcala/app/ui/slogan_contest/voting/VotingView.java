package org.halloweenalcala.app.ui.slogan_contest.voting;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

public interface VotingView extends BaseView {

    void showSlogans(List<Slogan> slogans);

    void showRating(int rating);
}
