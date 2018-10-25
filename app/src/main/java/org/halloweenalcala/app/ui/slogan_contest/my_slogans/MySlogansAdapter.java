package org.halloweenalcala.app.ui.slogan_contest.my_slogans;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

public class MySlogansAdapter extends RecyclerView.Adapter<MySlogansAdapter.ViewHolder> {


    private List<Slogan> slogans;
    private Context context;
    private OnItemClickListener itemClickListener;


    public MySlogansAdapter(Context context, List<Slogan> slogans) {
        this.context = context;
        this.slogans = slogans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_my_slogan, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        final Slogan slogan = getItemAtPosition(holder.getAdapterPosition());

        holder.tvSloganText.setText(slogan.getText());
        holder.tvSloganRating.setText(slogan.getAvgRatingFormatted());
        holder.tvSloganNumVotes.setText(String.format(context.getString(R.string.votes_format), slogan.getNumRatings()));


    }

    @Override
    public int getItemCount() {
        return slogans.size();
    }

    public Slogan getItemAtPosition(int position) {
        return slogans.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSloganText;
        private final TextView tvSloganRating;
        private final TextView tvSloganNumVotes;
        private final View btnPromoteSlogan;
        public View rootView;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSloganText = (TextView) itemView.findViewById(R.id.tv_slogan_text);
            tvSloganRating = (TextView) itemView.findViewById(R.id.tv_slogan_rating);
            tvSloganNumVotes = (TextView) itemView.findViewById(R.id.tv_slogan_num_votes);
            btnPromoteSlogan = itemView.findViewById(R.id.btn_promote_slogan);

            rootView = itemView;

            btnPromoteSlogan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onPromoteSloganClick(getAdapterPosition());
                    }
                }
            });

        }

    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onPromoteSloganClick(int position);
    }
}


