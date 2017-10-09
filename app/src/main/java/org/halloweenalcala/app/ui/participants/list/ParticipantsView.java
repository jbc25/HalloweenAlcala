package org.halloweenalcala.app.ui.participants.list;

import org.halloweenalcala.app.base.BaseView;
import org.halloweenalcala.app.model.Participant;

import java.util.List;

/**
 * Created by julio on 9/10/17.
 */

public interface ParticipantsView extends BaseView {

    void showParticipants(List<Participant> participants);
}
