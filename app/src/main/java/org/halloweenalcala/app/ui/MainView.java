package org.halloweenalcala.app.ui;

import org.halloweenalcala.app.base.BaseView;

/**
 * Created by julio on 8/10/17.
 */

public interface MainView extends BaseView {

    void showUpdateAppView();

    void refreshFragmentsData();

    void showNewNewsIcon(boolean show);

    void showSlogan(String idSlogan);
}
