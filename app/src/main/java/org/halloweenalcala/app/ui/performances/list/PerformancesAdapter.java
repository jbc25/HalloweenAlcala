package org.halloweenalcala.app.ui.performances.list;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.Performance;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by julio on 7/07/16.
 */
public class PerformancesAdapter extends RecyclerView.Adapter<PerformancesAdapter.ViewHolder>
        implements StickyHeaderAdapter<PerformancesAdapter.HeaderHolder> {


    private List<Performance> performances;
    private Context context;
    private OnItemClickListener itemClickListener;

    private Integer selectedNumber = -1;


    public PerformancesAdapter(Context context, List<Performance> performances) {
        this.context = context;
        this.performances = performances;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_performance, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Performance performance = getItemAtPosition(position);

        holder.tvTimePerformance.setText(performance.getTime_begin());
        holder.tvTitlePerformance.setText(performance.getTitle());
        holder.tvSubtitlePerformance.setText(performance.getSubtitle());
        holder.tvSubtitlePerformance.setVisibility(performance.getSubtitle().isEmpty() ? View.GONE : View.VISIBLE);
        holder.tvPlacePerformance.setText(performance.getPlaceInfo());

        if (performance.isHighlight()) {
            holder.tvTitlePerformance.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimensionPixelSize(R.dimen.text_size_performance_highlight));
            holder.tvTitlePerformance.setTextColor(ContextCompat.getColor(context, R.color.red_halloween));
        } else {
            holder.tvTitlePerformance.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimensionPixelSize(R.dimen.text_size_performance_normal));
            holder.tvTitlePerformance.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

//        Picasso.get()
//                .load(band.getImageLogoUrlFull())
//                .placeholder(R.mipmap.img_default_grid)
//                .error(R.mipmap.img_default_grid)
//                .resizeDimen(R.dimen.width_image_small, R.dimen.height_image_small)
//                .into(holder.imgBand);

        addClickListener(holder.rootView, position);

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
        return performances.size();
    }

    public Performance getItemAtPosition(int position) {
        return performances.get(position);
    }

    public void updateData(List<Performance> performances) {
        this.performances = performances;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        private TextView tvTimePerformance;
        private TextView tvTitlePerformance;
        private TextView tvSubtitlePerformance;
        private TextView tvPlacePerformance;


        public ViewHolder(View itemView) {

            super(itemView);

            tvTimePerformance = itemView.findViewById( R.id.tv_time_performance );
            tvTitlePerformance = itemView.findViewById( R.id.tv_title_performance );
            tvSubtitlePerformance = itemView.findViewById( R.id.tv_subtitle_performance );
            tvPlacePerformance = itemView.findViewById( R.id.tv_place_performance );

            rootView = itemView;
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public long getHeaderId(int position) {
        return performances.get(position).getDayId();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {

        View headerView = LayoutInflater.from(context).inflate(R.layout.row_performance_header, parent, false);

        // Return a new holder instance
        HeaderHolder viewHolder = new HeaderHolder(headerView);
        return viewHolder;
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {

        Performance performance = performances.get(position);
        viewholder.tvHeaderDay.setText(performance.getDayHeaderFormat());
    }


    public class HeaderHolder extends RecyclerView.ViewHolder {

        private final TextView tvHeaderDay;

        public HeaderHolder(View headerView) {
            super(headerView);
            tvHeaderDay = headerView.findViewById(R.id.tv_header_performance_day);
        }
    }
}
