package org.halloweenalcala.app.ui.slogan_contest.denounces;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

public interface DenouncesView extends BaseView {
    void showSlogans(List<Slogan> slogans);
}
