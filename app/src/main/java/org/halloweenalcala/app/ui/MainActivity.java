package org.halloweenalcala.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.ui.map.MapsPresenter;
import org.halloweenalcala.app.ui.news.list.NewsFragment;
import org.halloweenalcala.app.ui.participants.list.ParticipantsPresenter;
import org.halloweenalcala.app.ui.performances.list.PerformancesFragment;
import org.halloweenalcala.app.ui.poems.PoemsFragment;
import org.halloweenalcala.app.ui.static_info.TextHtmlActivity;
import org.halloweenalcala.app.ui.static_info.WebViewActivity;
import org.halloweenalcala.app.ui.zombiselfie.ZombiSelfieFragment;
import org.halloweenalcala.app.util.SoftKeyboardManager;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainView, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private BottomNavigationView bottomNavView;
    private DrawerLayout drawerLayout;
    private NavigationView leftNavigacionView;
    private LinearLayout viewUpdateApp;
    private TextView btnUpdateApp;
    private ImageView btnCloseUpdateApp;
    private MainPresenter presenter;
    private Place placeToSelect;
    private View iconNewNewsBadge;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    private void findViews() {
        bottomNavView = (BottomNavigationView) findViewById(R.id.navigation_bottom_view);
        leftNavigacionView = (NavigationView) findViewById(R.id.navigation_view);
        viewUpdateApp = (LinearLayout) findViewById(R.id.view_update_app);
        btnUpdateApp = (TextView) findViewById(R.id.btn_update_app);
        btnCloseUpdateApp = (ImageView) findViewById(R.id.btn_close_update_app);

        bottomNavView.setOnNavigationItemSelectedListener(this);
        leftNavigacionView.setNavigationItemSelectedListener(this);

        btnUpdateApp.setOnClickListener(this);
        btnCloseUpdateApp.setOnClickListener(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        presenter = MainPresenter.newInstance(this, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        configureToolbar();
        configureDrawerLayout();
        configureBottomNavView();

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PerformancesFragment()).commit();

        presenter.onCreate(getIntent());

        SoftKeyboardManager.newInstance().configureSoftKeyboardVisibilityBehaviour(this, new SoftKeyboardManager.OnSoftKeyboardChangedListener() {
            @Override
            public void onSoftKeyboardVisible(boolean visible) {
                bottomNavView.setVisibility(visible ? View.GONE : View.VISIBLE);
            }
        });

//        WebViewActivity.start(this, "https://www.loomio.org/p/PsUuLI1p/nota-de-prensa-para-solidarizarse");

//        showSection(new SloganContestFragment());

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    private void configureBottomNavView() {

        View v = bottomNavView.findViewById(R.id.navigation_news);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        iconNewNewsBadge = LayoutInflater.from(this)
                .inflate(R.layout.view_new_news_badge, bottomNavView, false);

        itemView.addView(iconNewNewsBadge);

//        showNewNewsMessage();
    }

    // CONFIGURATIONS

    private void configureDrawerLayout() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    // INTERACTIONS


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (bottomNavView.getSelectedItemId() == item.getItemId()) {
            return false;
        }

        // Bottom Navigation
        try {
            switch (item.getItemId()) {
                case R.id.navigation_agenda:
                    showSection(new PerformancesFragment());
                    setToolbarTitle(R.string.app_name);
                    return true;
                case R.id.navigation_map:
                    showSection(MapsPresenter.newFragment(placeToSelect));
                    setToolbarTitle(R.string.app_name);
                    return true;
                case R.id.navigation_zombiselfie:
                    showSection(new ZombiSelfieFragment());
                    setToolbarTitle(R.string.zombiecuadro);
                    return true;
                case R.id.navigation_poems:
                    showSection(new PoemsFragment());
                    setToolbarTitle(R.string.poems);
                    return true;
                case R.id.navigation_news:
                    presenter.onNewsButtonClick();
                    showSection(new NewsFragment());
                    setToolbarTitle(R.string.news);
                    return true;
            }
        } finally {
            placeToSelect = null;
        }


        // Left Navigation
        switch (item.getItemId()) {
            case R.id.menu_participants:
                startActivity(ParticipantsPresenter.newParticipantsActivity(this));
                break;

            case R.id.menu_twitter_halloween_alcala_17:
                String hashtagAlcalaHalloween = getString(R.string.hashtag_alcalahalloween).replace("#", "");
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(String.format("https://twitter.com/hashtag/%s?lang=es", hashtagAlcalaHalloween))));
                break;

            case R.id.menu_twitter_7mz:
                String hashtag7mz = getString(R.string.hashtag_7mz).replace("#", "");
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(String.format("https://twitter.com/hashtag/%s?lang=es", hashtag7mz))));
                break;

            case R.id.menu_about_alcala_halloween:
                TextHtmlActivity.start(this, TextHtmlActivity.TYPE_ALCALA_HALLOWEEN);
                break;

            case R.id.menu_about_marcha_zombie:
                TextHtmlActivity.start(this, TextHtmlActivity.TYPE_7MZ);
                break;

            case R.id.menu_contest_and_raffle:
                showContestDecorationInfo();
                break;

            case R.id.menu_credits:
                WebViewActivity.start(this, "http://www.marchazombiealcala.com/creditos");https://www.loomio.org/
                break;
        }

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return false;
    }

    private void showContestDecorationInfo() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        ab.setTitle(R.string.contests_and_raffle);
        ab.setMessage(R.string.contest_and_raffle_info);
        ab.setPositiveButton(R.string.more_info_web_contest, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.marchazombiealcala.com/concursos")));
            }
        });
        ab.setNeutralButton(R.string.return_back, null);
        ab.show();
    }

    private void showSection(BaseFragment fragment) {

        getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_update_app:
                viewUpdateApp.setVisibility(View.GONE);
                break;

            case R.id.btn_update_app:
                presenter.onUpdateVersionClick();
                break;
        }
    }

    public void onGoToMapButtonClick(Place place) {
        placeToSelect = place;
        bottomNavView.setSelectedItemId(R.id.navigation_map);
    }


    // PRESENTER CALLBACKS

    @Override
    public void showUpdateAppView() {
        viewUpdateApp.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshFragmentsData() {

        if (bottomNavView.getSelectedItemId() == R.id.navigation_agenda) {
            showSection(new PerformancesFragment());
        }
    }

    @Override
    public void showNewNewsIcon(boolean show) {
//        AlertDialog.Builder ab = new AlertDialog.Builder(this);
//        ab.setTitle(R.string.fresh_news);
//        ab.setMessage(R.string.fresh_news_message);
//        ab.setPositiveButton(R.string.go_to_news, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                bottomNavView.setSelectedItemId(R.id.navigation_news);
//            }
//        });
//        ab.setNegativeButton(R.string.no_thanks, null);
//        ab.show();

        if (show) {

            iconNewNewsBadge.setVisibility(View.VISIBLE);

            try {
                Animation anim = new ScaleAnimation(
                        1, 2, // Start and end values for the X axis scaling
                        1, 2, // Start and end values for the Y axis scaling
                        Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                        Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
//        anim.setFillAfter(true); // Needed to keep the result of the animation
                anim.setDuration(300);
                anim.setRepeatCount(3);
                anim.setRepeatMode(Animation.REVERSE);
                iconNewNewsBadge.startAnimation(anim);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
            }
        } else {
            iconNewNewsBadge.setVisibility(View.GONE);
        }

    }

}
