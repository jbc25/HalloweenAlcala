package org.halloweenalcala.app.ui.map;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.Place;

import java.util.List;

/**
 * Created by julio on 9/10/17.
 */

public interface MapsView extends BaseView{

    void showMarkers(List<Place> places, Place placeFindCode);
}
