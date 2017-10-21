package org.halloweenalcala.app.ui.performances;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.Performance;

import java.util.List;

/**
 * Created by julio on 20/10/17.
 */

public interface PerformancesView extends BaseView {

    void showPerformances(List<Performance> performances);
}
