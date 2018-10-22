package org.halloweenalcala.app.ui.news.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.News;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener, YouTubePlayer.OnInitializedListener {

    private static final String EXTRA_NEWS = "extra_participant";
    private News news;
    private TextView tvNewsTitle;
    private AppCompatImageView imgNews;
    private TextView tvNewsDescription;
    private AppCompatButton btnLink;
    private YouTubePlayerSupportFragment youtubePlayerFragment;

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

        youtubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_youtube_player);

        if (news.hasValidYoutubeVideo()) {
            youtubePlayerFragment.initialize(getString(R.string.google_maps_key), this);
        } else {
            youtubePlayerFragment.getView().setVisibility(View.GONE);
        }

        loadData();

        sendAnalyticsNews();
    }

    private void sendAnalyticsNews() {

        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, news.getId_server());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, news.getTitle());

        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM+"_news", bundle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void loadData() {

        setToolbarTitle(R.string.news);

        tvNewsDescription.setMovementMethod(LinkMovementMethod.getInstance());

        tvNewsTitle.setText(news.getTitle());
        tvNewsDescription.setText(Html.fromHtml(news.getText()));

        if (news.hasImage()) {
            Picasso.get()
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.cueVideo(news.getYoutube_video_ID());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

//        toast("Error en Youtube Player");
        Crashlytics.logException(new Error("Error en Youtube player"));
        youtubePlayerFragment.getView().setVisibility(View.GONE);
    }
}
