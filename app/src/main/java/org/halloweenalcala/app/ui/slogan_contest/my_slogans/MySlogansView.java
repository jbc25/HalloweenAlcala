package org.halloweenalcala.app.ui.slogan_contest.my_slogans;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

public interface MySlogansView extends BaseView {

    void setActiveAndshowEmail(String email);

    void showMySlogans(List<Slogan> slogans);

    void showInvalidEmailMessage(String errorMessage);

    void clearSloganText();

    void showSlogansPending(int numSlogansPending);
}
