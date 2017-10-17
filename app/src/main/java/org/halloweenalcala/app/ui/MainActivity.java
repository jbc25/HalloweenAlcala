package org.halloweenalcala.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.api.ApiClient;
import org.halloweenalcala.app.api.GoogleSheetApiCsv;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.ui.map.MapsFragment;
import org.halloweenalcala.app.ui.news.list.NewsFragment;
import org.halloweenalcala.app.ui.participants.list.ParticipantsPresenter;
import org.halloweenalcala.app.ui.poems.PoemsFragment;
import org.halloweenalcala.app.ui.performances.PerformancesFragment;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainView, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private BottomNavigationView bottomNavView;
    private DrawerLayout drawerLayout;
    private NavigationView leftNavigacionView;
    private LinearLayout viewUpdateApp;
    private TextView btnUpdateApp;
    private ImageView btnCloseUpdateApp;
    private MainPresenter presenter;

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    private void findViews() {
        bottomNavView = (BottomNavigationView) findViewById(R.id.navigation_bottom_view);
        leftNavigacionView = (NavigationView) findViewById(R.id.navigation_view);
        viewUpdateApp = (LinearLayout)findViewById( R.id.view_update_app );
        btnUpdateApp = (TextView)findViewById( R.id.btn_update_app );
        btnCloseUpdateApp = (ImageView)findViewById( R.id.btn_close_update_app );

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (bottomNavView.getSelectedItemId() == item.getItemId()) {
            return false;
        }

        // Bottom Navigation
        switch (item.getItemId()) {
            case R.id.navigation_agenda:
                showSection(new PerformancesFragment());
                return true;
            case R.id.navigation_map:
                showSection(new MapsFragment());
                return true;
            case R.id.navigation_poems:
                showSection(new PoemsFragment());
                return true;
            case R.id.navigation_news:
                showSection(new NewsFragment());
                return true;
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
        }

        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }

        return false;
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

    private void getDataFromApi() {

        showProgressDialog("Cargando...");
        ApiClient.getInstance().create(GoogleSheetApiCsv.class).getPlaces()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(actionTerminate)
                .subscribe(new Observer<List<Place>>() {

                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {

//                        callback.onError(handleError(e));
                                   hideProgressDialog();
                                   toast("error");
                               }

                               @Override
                               public void onNext(List<Place> places) {

                                   hideProgressDialog();

                                   toast("Lugares size: " + places.size());


                               }
                           }


                );
    }


    // PRESENTER CALLBACKS

    @Override
    public void showUpdateAppView() {
        viewUpdateApp.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshFragmentsData() {

    }

}
