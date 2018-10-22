package org.halloweenalcala.app.ui.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.halloweenalcala.app.BuildConfig;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.interactor.ConfigurationInteractor;
import org.halloweenalcala.app.model.Configuration;

import static org.halloweenalcala.app.App.URL_DIRECT_GOOGLE_PLAY_APP;
import static org.halloweenalcala.app.App.URL_GOOGLE_PLAY_APP;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitleSplash;
    private View viewEyesAnim;
    private ImageView imgRomanZombie;
    private TextView tvSplashMessage;
    private Button btnUpdateAppSplash;

    private void findViews() {
        tvTitleSplash = (TextView)findViewById( R.id.tv_title_splash );
        viewEyesAnim = (View)findViewById( R.id.view_eyes_anim );
        imgRomanZombie = (ImageView)findViewById( R.id.img_roman_zombie );
        tvSplashMessage = (TextView)findViewById( R.id.tv_splash_message );
        btnUpdateAppSplash = (Button)findViewById( R.id.btn_update_app_splash );

        btnUpdateAppSplash.setOnClickListener( this );
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViews();

        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(1500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);

        viewEyesAnim.startAnimation(animation);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();


        new ConfigurationInteractor(this, this).getConfiguration(new ConfigurationInteractor.ConfigurationCallback() {
            @Override
            public void onSuccess(Configuration configuration) {
                checkAppVersion(configuration.getLast_app_version());
            }

            @Override
            public void onError(String message) {
//                toast(message);
            }
        });
    }

    private void checkAppVersion(int lastAppVersion) {
        int currentAppVersion = BuildConfig.VERSION_CODE;
        if (currentAppVersion < lastAppVersion) {
            btnUpdateAppSplash.setVisibility(View.VISIBLE);
            tvSplashMessage.setText(R.string.splash_message_update);
        }

    }


    @Override
    public void onClick(View v) {
        if ( v == btnUpdateAppSplash ) {
            onUpdateVersionClick();
        }
    }

    public void onUpdateVersionClick() {

        Intent directPlayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_DIRECT_GOOGLE_PLAY_APP));
        if (directPlayIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(directPlayIntent);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL_GOOGLE_PLAY_APP)));
        }
    }

}
