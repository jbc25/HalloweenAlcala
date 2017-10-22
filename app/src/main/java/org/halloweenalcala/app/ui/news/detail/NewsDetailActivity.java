package org.halloweenalcala.app.ui.news.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.News;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String EXTRA_NEWS = "extra_participant";
    private News news;
    private TextView tvNewsTitle;
    private AppCompatImageView imgNews;
    private TextView tvNewsDescription;
    private AppCompatButton btnLink;

    private void findViews() {
        tvNewsTitle = (TextView) findViewById(R.id.tv_news_title);
        imgNews = (AppCompatImageView) findViewById(R.id.img_news);
        tvNewsDescription = (TextView) findViewById(R.id.tv_news_description);
        btnLink = (AppCompatButton) findViewById(R.id.btn_link);

        btnLink.setOnClickListener(this);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public static void start(Context context, News news) {
        Intent starter = new Intent(context, NewsDetailActivity.class);
        starter.putExtra(EXTRA_NEWS, news);
        context.startActivity(starter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        configureSecondLevelActivity();
        findViews();

        news = (News) getIntent().getSerializableExtra(EXTRA_NEWS);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void loadData() {

        setToolbarTitle(R.string.news);

        tvNewsTitle.setText(news.getTitle());
        tvNewsDescription.setText(news.getText());

        if (news.hasImage()) {
            Picasso.with(this)
                    .load(news.getImage_url())
                    .into(imgNews);
        }

        if (news.hasValidLink()) {
            btnLink.setText(news.getBtn_text());
            btnLink.setVisibility(View.VISIBLE);
        }

    }


    // INTERACTIONS


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                shareNews();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareNews() {

        String poemShareMessage = String.format(getString(R.string.news_share_message),
                news.getTitle(), news.getText(), App.URL_GOOGLE_PLAY_APP);

//        if (true) {
//            alert(poemShareMessage);
//            return;
//        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, poemShareMessage);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_link:
                openLink(news.getBtn_link());
                break;
        }
    }

    private void openLink(String link) {

        if (!link.startsWith("http")) {
            link = "http://" + link;
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (ActivityNotFoundException e) {
            toast(R.string.cannot_open_link);
        }
    }

}
