package org.halloweenalcala.app.ui.slogan_contest.denounces;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.cloud.Slogan;

import java.util.List;

public class DenouncesAdapter extends RecyclerView.Adapter<DenouncesAdapter.ViewHolder> {


    private List<Slogan> slogans;
    private Context context;
    private OnItemClickListener itemClickListener;


    public DenouncesAdapter(Context context, List<Slogan> slogans) {
        this.context = context;
        this.slogans = slogans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_denounced_slogan, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        final Slogan slogan = getItemAtPosition(holder.getAdapterPosition());

        holder.tvSloganText.setText(slogan.getText());


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
        private final View btnDeleteSlogan;
        private final View btnBanUser;
        private final View btnValidateSlogan;
        public View rootView;

        public ViewHolder(View itemView) {

            super(itemView);

            tvSloganText = (TextView) itemView.findViewById(R.id.tv_slogan_text);
            btnValidateSlogan = itemView.findViewById(R.id.btn_validate_slogan);
            btnDeleteSlogan = itemView.findViewById(R.id.btn_delete_slogan);
            btnBanUser = itemView.findViewById(R.id.btn_ban_user);

            rootView = itemView;

            btnValidateSlogan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onValidateClick(getAdapterPosition());
                    }
                }
            });

            btnDeleteSlogan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onDeleteSloganClick(getAdapterPosition());
                    }
                }
            });

            btnBanUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onBanUserClick(getAdapterPosition());
                    }
                }
            });

        }

    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onValidateClick(int position);
        void onDeleteSloganClick(int position);
        void onBanUserClick(int position);
    }
}


