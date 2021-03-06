package com.awave.apps.droider.Main;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.awave.apps.droider.Elements.MainScreen.AboutFragment;
import com.awave.apps.droider.Elements.MainScreen.Feed;
import com.awave.apps.droider.Elements.MainScreen.Preferences;
import com.awave.apps.droider.R;
import com.awave.apps.droider.Utils.Helper;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static int mainOrientation;
    Toolbar toolbar;
    String mTitle = "Главная";
    int theme;
    private String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawer;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /** Проверяем какая тема выбрана в настройках **/
        String themeName = PreferenceManager.getDefaultSharedPreferences(this).getString("theme", "Светлая");
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        switch (themeName) {
            case "Светлая":

                theme = R.style.LightTheme;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark_light));
                }
                break;

            case "Тёмная":

                theme = R.style.DarkTheme;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark_dark));
                }
                break;

            case "В зависимости от времени суток":
                theme = R.style.DayNightAuto;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        super.onCreate(savedInstanceState);
        /** Затем "включаем" нужную тему **/
        setTheme(theme);

        setContentView(R.layout.main);

        MainActivity.mainOrientation = this.getResources().getConfiguration().orientation;
        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(mTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        drawer = (DrawerLayout) findViewById(R.id.nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d(TAG, "onCreate: isOnline = " + Helper.isOnline(this));

        if (!Helper.isOnline(this))
            Helper.initInternetConnectionDialog(this);
        else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_main, Feed.instance(Helper.HOME_URL))
                    .commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                Log.d(TAG, "onCreate: Night mode is not active, we're in day time ");


                break;
            case Configuration.UI_MODE_NIGHT_YES:
                Log.d(TAG, "onCreate: Night mode is active, we're at night! ");

                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Log.d(TAG, "onCreate: We don't know what mode we're in, assume notnight ");
                break;
        }

    }


    @Override
    protected void onStart() {
        stopService(new Intent(this, NotifyService.class));
        super.onStart();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.apps.wow.droider.main/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        startService(new Intent(this, NotifyService.class));
        super.onStop();
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.apps.wow.droider.main/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.disconnect();
    }

    public void restoreActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        restoreActionBar();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        android.app.Fragment fragment = null;

        switch (menuItem.getItemId()) {

            case R.id.home_page_tab:
                fragment = Feed.instance(Helper.HOME_URL);
                getSupportActionBar().setTitle(getString(R.string.drawer_item_home));
                if (!Helper.isOnline(this))
                    Helper.initInternetConnectionDialog(this);
                break;
            case R.id.news_tab:
                fragment = Feed.instance(Helper.NEWS_URL);
                getSupportActionBar().setTitle(getString(R.string.drawer_item_news));
                if (!Helper.isOnline(this))
                    Helper.initInternetConnectionDialog(this);
                break;
            case R.id.apps_tab:
                fragment = Feed.instance(Helper.APPS_URL);
                getSupportActionBar().setTitle(getString(R.string.drawer_item_apps));
                if (!Helper.isOnline(this))
                    Helper.initInternetConnectionDialog(this);
                break;
            case R.id.games_tab:
                fragment = Feed.instance(Helper.GAMES_URL);
                getSupportActionBar().setTitle(getString(R.string.drawer_item_games));
                if (!Helper.isOnline(this))
                    Helper.initInternetConnectionDialog(this);
                break;
            case R.id.video_tab:
                fragment = Feed.instance(Helper.VIDEOS_URL);
                getSupportActionBar().setTitle(getString(R.string.drawer_item_videos));
                if (!Helper.isOnline(this))
                    Helper.initInternetConnectionDialog(this);
                break;
            case R.id.droider_cast_tab:
                fragment = Feed.instance(Helper.DROIDER_CAST_URL);
                getSupportActionBar().setTitle(getString(R.string.drawer_item_drcast));
                if (!Helper.isOnline(this))
                    Helper.initInternetConnectionDialog(this);
                break;

            case R.id.settings_tab:
                fragment = new Preferences();
                getSupportActionBar().setTitle(R.string.drawer_item_settings);
                break;
            case R.id.info_tab:
                fragment = new AboutFragment();
                getSupportActionBar().setTitle(getString(R.string.drawer_item_about));
                break;
        }

        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
                    .replace(R.id.container_main, fragment)
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(menuItem);
    }
}