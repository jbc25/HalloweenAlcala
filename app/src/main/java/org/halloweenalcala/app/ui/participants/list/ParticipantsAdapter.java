package org.halloweenalcala.app.ui.participants.list;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.Participant;

import java.util.List;

/**
 * Created by julio on 9/10/17.
 */

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder> {


    private List<Participant> participants;
    private Context context;
    private OnItemClickListener itemClickListener;


    public ParticipantsAdapter(Context context, List<Participant> participants) {
        this.context = context;
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_participant, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        final int safePosition = holder.getAdapterPosition();

        final Participant participant = getItemAtPosition(safePosition);

        holder.tvParticipantName.setText(participant.getName());

        if (participant.hasImage1()) {
            Picasso.get()
                    .load(participant.getImage1())
                    .placeholder(R.mipmap.ic_app)
//                        .error(R.mipmap.ic_mes_v2_144)
//                .resizeDimen(R.dimen.width_image_small, -1)
                    .into(holder.imgParticipant);
        }

        addClickListener(holder.rootView, safePosition);


    }

    private void addClickListener(View view, final int position) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public Participant getItemAtPosition(int position) {
        return participants.get(position);
    }

    public void updateData(List<Participant> entities) {
        this.participants = entities;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        private AppCompatImageView imgParticipant;
        private View viewFilter;
        private TextView tvParticipantName;


        public ViewHolder(View itemView) {

            super(itemView);

            imgParticipant = (AppCompatImageView) itemView.findViewById(R.id.img_participant);
            viewFilter = (View) itemView.findViewById(R.id.view_filter);
            tvParticipantName = (TextView) itemView.findViewById(R.id.tv_participant_name);

            rootView = itemView;
        }

    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}

