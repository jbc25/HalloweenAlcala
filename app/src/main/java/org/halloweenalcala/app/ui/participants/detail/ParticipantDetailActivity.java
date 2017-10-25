package org.halloweenalcala.app.ui.participants.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.squareup.picasso.Picasso;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Participant;
import org.halloweenalcala.app.ui.image_full.ImageFullActivity;

public class ParticipantDetailActivity extends BaseActivity implements View.OnClickListener, YouTubePlayer.OnInitializedListener {

    private static final String EXTRA_PARTICIPANT = "extra_participant";
    private Participant participant;
    private AppCompatImageView imgParticipantBackground;
    private TextView tvParticipantModality;
    private AppCompatImageView imgParticipant;
    private TextView tvParticipantDescription;
    private YouTubePlayerSupportFragment youtubePlayerFragment;
    private ImageView imgSocialTwitter;
    private ImageView imgSocialFacebook;
    private ImageView imgSocialWeb;

    private void findViews() {
        imgParticipantBackground = (AppCompatImageView)findViewById( R.id.img_participant_background );
        tvParticipantModality = (TextView)findViewById( R.id.tv_participant_modality );
        imgParticipant = (AppCompatImageView)findViewById( R.id.img_participant );
        tvParticipantDescription = (TextView)findViewById( R.id.tv_participant_description );

        imgSocialWeb = (ImageView) findViewById(R.id.img_social_web);
        imgSocialFacebook = (ImageView) findViewById(R.id.img_social_facebook);
        imgSocialTwitter = (ImageView) findViewById(R.id.img_social_twitter);

        imgParticipant.setOnClickListener(this);
        imgSocialWeb.setOnClickListener(this);
        imgSocialFacebook.setOnClickListener(this);
        imgSocialTwitter.setOnClickListener(this);
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }
    
    public static void start(Context context, Participant participant) {
        Intent starter = new Intent(context, ParticipantDetailActivity.class);
        starter.putExtra(EXTRA_PARTICIPANT, participant);
        context.startActivity(starter);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_detail);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        configureSecondLevelActivity();
        findViews();

        participant = (Participant) getIntent().getSerializableExtra(EXTRA_PARTICIPANT);

        youtubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_youtube_player);

        if (participant.hasValidYoutubeVideo()) {
            youtubePlayerFragment.initialize(getString(R.string.google_maps_key), this);
        } else {
            youtubePlayerFragment.getView().setVisibility(View.GONE);
        }

        sendAnalyticsParticipant();


        loadData();
    }

    private void sendAnalyticsParticipant() {

        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, participant.getId_server());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, participant.getName());

        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM+"_participant", bundle);
    }

    private void loadData() {

        setToolbarTitle(participant.getName());

        tvParticipantModality.setText(participant.getArtistic_modality());
        tvParticipantDescription.setText(Html.fromHtml(participant.getDescription()));

        if (participant.hasImage1()) {
            Picasso.with(this)
                    .load(participant.getImage1())
                    .into(imgParticipantBackground);
        }

        if (participant.hasImage2()) {
            Picasso.with(this)
                    .load(participant.getImage2())
                    .into(imgParticipant);
        }

        imgSocialWeb.setVisibility(participant.hasValidLinkWeb() ? View.VISIBLE : View.GONE);
        imgSocialFacebook.setVisibility(participant.hasValidLinkFacebook() ? View.VISIBLE : View.GONE);
        imgSocialTwitter.setVisibility(participant.hasValidLinkTwitter() ? View.VISIBLE : View.GONE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_participant:
                startActivity(ImageFullActivity.newImageFullActivity(this, participant.getImage2()));
                break;

            case R.id.img_social_web:
                openLink(participant.getWeb());
                break;
            case R.id.img_social_facebook:
                openLink(participant.getFacebook());
                break;
            case R.id.img_social_twitter:
                openLink(participant.getTwitter());
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
//        youTubePlayer.addFullscreenControlFlag();
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.cueVideo(participant.getYoutube_video_ID());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

//        toast("Error en Youtube Player");
        FirebaseCrash.report(new Error("Error en Youtube player"));
        youtubePlayerFragment.getView().setVisibility(View.GONE);
    }
}
