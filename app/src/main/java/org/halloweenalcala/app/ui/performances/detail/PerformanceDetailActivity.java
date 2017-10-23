package org.halloweenalcala.app.ui.performances.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Performance;

public class PerformanceDetailActivity extends BaseActivity {

    private static final String EXTRA_PERFORMANCE = "extra_performance";
    private Performance performance;
    private ImageView imgPerformance;
    private TextView tvPlacePerformance;
    private TextView tvTimePerformance;
    private TextView tvDescriptionPerformance;

    private void findViews() {
        imgPerformance = (ImageView)findViewById( R.id.img_performance );
        tvPlacePerformance = (TextView)findViewById( R.id.tv_place_performance );
        tvTimePerformance = (TextView)findViewById( R.id.tv_time_performance );
        tvDescriptionPerformance = (TextView)findViewById( R.id.tv_description_performance );
    }



    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public static void launch(Context context, Performance performance) {
        Intent intent = new Intent(context, PerformanceDetailActivity.class);
        intent.putExtra(EXTRA_PERFORMANCE, performance);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_detail);

        configureSecondLevelActivity();
        findViews();

        performance = (Performance) getIntent().getSerializableExtra(EXTRA_PERFORMANCE);

        fillData();

    }

    private void fillData() {

        setToolbarTitle(performance.getTitle());

        tvDescriptionPerformance.setMovementMethod(LinkMovementMethod.getInstance());

        tvPlacePerformance.setText(performance.getPlaceInfo());
        tvTimePerformance.setText(performance.getDateTimeHumanFriendly());
        tvDescriptionPerformance.setText(Html.fromHtml(performance.getInfo()));

        if (performance.getImage_url() != null && !performance.getImage_url().isEmpty()) {
            Picasso.with(this)
                    .load(performance.getImage_url())
                    .placeholder(R.mipmap.img_placeholder_performance)
                    .error(R.mipmap.img_placeholder_performance)
                    .into(imgPerformance);
        }
    }

}
