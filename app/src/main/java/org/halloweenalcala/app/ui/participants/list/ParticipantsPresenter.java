package org.halloweenalcala.app.ui.participants.list;

import android.content.Context;
import android.content.Intent;

import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.ui.participants.detail.ParticipantDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julio on 9/10/17.
 */


 public class ParticipantsPresenter extends BasePresenter {

     private final ParticipantsView view;
    private List<Participant> participants = new ArrayList<>();

    public static Intent newParticipantsActivity(Context context) {

         Intent intent = new Intent(context, ParticipantsActivity.class);

         return intent;
     }

     public static ParticipantsPresenter newInstance(ParticipantsView view, Context context) {

         return new ParticipantsPresenter(view, context);

     }

     private ParticipantsPresenter(ParticipantsView view, Context context) {
         super(context, view);

         this.view = view;

     }

     public void onCreate() {

         refreshData();
     }

     public void onResume() {

     }

     public void refreshData() {

         participants.clear();
         participants.addAll(Participant.listAll(Participant.class));
         view.showParticipants(participants);

     }

    public void onItemClick(int position) {
        Participant participant = participants.get(position);
        ParticipantDetailActivity.start(context, participant);
    }
}
