package org.halloweenalcala.app.ui.news.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.model.News;

import java.util.List;

/**
 * Created by julio on 9/10/17.
 */


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    private List<News> newsList;
    private Context context;
    private OnItemClickListener itemClickListener;


    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_news, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        final int safePosition = holder.getAdapterPosition();

        final News newsList = getItemAtPosition(safePosition);

        holder.tvNewsTitle.setText(newsList.getTitle());
        holder.tvNewsText.setText(newsList.getText());
        holder.tvNewsDate.setText(newsList.getDatetime());

        Picasso.with(context)
                .load(newsList.getImage_url())
                //                .placeholder(R.mipmap.img_default_grid)
//                         .error(R.mipmap.ic_mes_v2_144)
                .resizeDimen(R.dimen.width_image_small, R.dimen.height_image_small)
                .into(holder.imgNews);

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
        return newsList.size();
    }

    public News getItemAtPosition(int position) {
        return newsList.get(position);
    }

    public void updateData(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        private ImageView imgNews;
        private TextView tvNewsTitle;
        private TextView tvNewsText;
        private TextView tvNewsDate;


        public ViewHolder(View itemView) {

            super(itemView);

            imgNews = (ImageView) itemView.findViewById(R.id.img_news);
            tvNewsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            tvNewsText = (TextView) itemView.findViewById(R.id.tv_news_text);
            tvNewsDate = (TextView) itemView.findViewById(R.id.tv_news_date);

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
  
 

