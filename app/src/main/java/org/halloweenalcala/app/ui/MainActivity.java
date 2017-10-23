package org.halloweenalcala.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainView, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private BottomNavigationView bottomNavView;
    private DrawerLayout drawerLayout;
    private NavigationView leftNavigacionView;
    private LinearLayout viewUpdateApp;
    private TextView btnUpdateApp;
    private ImageView btnCloseUpdateApp;
    private MainPresenter presenter;
    private Place placeToSelect;

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

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new PerformancesFragment()).commit();

        presenter.onCreate();

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

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
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
                case R.id.navigation_poems:
                    showSection(new PoemsFragment());
                    setToolbarTitle(R.string.zinemazombies);
                    return true;
                case R.id.navigation_news:
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
                TextHtmlActivity.start(this, WebViewActivity.TYPE_ALCALA_HALLOWEEN);
                break;

            case R.id.menu_about_marcha_zombie:
                TextHtmlActivity.start(this, WebViewActivity.TYPE_7MZ);
                break;

            case R.id.menu_contest_and_raffle:
                showContestDecorationInfo();
                break;

            case R.id.menu_credits:
                WebViewActivity.start(this, "http://www.marchazombiealcala.es/creditos");
                break;
        }

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }

        return false;
    }

    private void showContestDecorationInfo() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(R.string.contests_and_raffle);
        ab.setMessage(R.string.contest_and_raffle_info);
        ab.setPositiveButton(R.string.go_to_web_contest, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.marchazombiealcala.es/concursos")));
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
        showSection(new PerformancesFragment());
    }

    @Override
    public void showNewNewsMessage() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(R.string.fresh_news);
        ab.setMessage(R.string.fresh_news_message);
        ab.setPositiveButton(R.string.go_to_news, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bottomNavView.setSelectedItemId(R.id.navigation_news);
            }
        });
        ab.setNegativeButton(R.string.no_thanks, null);
        ab.show();
    }

}
