package org.halloweenalcala.app.ui.zombiselfie;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import org.halloweenalcala.app.R;

import java.util.List;

public class FramesAdapter extends RecyclerView.Adapter<FramesAdapter.ViewHolder> {


    private List<Integer> imageIds;
    private Context context;
    private OnItemClickListener itemClickListener;


    public FramesAdapter(Context context, List<Integer> imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_zombie_selfie_frame_thumb, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Integer imageId = getItemAtPosition(position);

        holder.imgFrameThumb.setImageResource(imageId);


    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    public Integer getItemAtPosition(int position) {
        return imageIds.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgFrameThumb;
        public View rootView;

        public ViewHolder(View itemView) {

            super(itemView);

            imgFrameThumb = (ImageView) itemView.findViewById(R.id.img_frame_thumb);

            rootView = itemView;

            rootView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}


