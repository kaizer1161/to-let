package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_FEED_FRAGMENT = "feed_fragment";

    private FeedFragment feedFragment = new FeedFragment();

    private FloatingActionButton fab;

    private SharedPreferences preferences;

    @Override
    protected void onStart() {
        super.onStart();

        //see if the user is logged in shared memory
        //then redirect to dashboard
        if (!(getSharedPreferences(Config.SP_TOLET_APP, MODE_PRIVATE).getBoolean(Config.SP_LOGED_IN, false))) {
            Intent intent = new Intent(this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getSupportFragmentManager().beginTransaction().add(R.id.content_dash_board, feedFragment, TAG_FEED_FRAGMENT)
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new PostFragment()).commit();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    public void hideFloatingActionButton() {
        fab.hide();
    }

    public void showFloatingActionButton() {
        fab.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            feedFragment = (FeedFragment) getSupportFragmentManager().findFragmentByTag(TAG_FEED_FRAGMENT);

            if (feedFragment != null && feedFragment.isVisible()) {

                if (feedFragment.commentBottomSheetStateIsExpanded()) {

                    feedFragment.commentBottomSheetCollapse();
                    showFloatingActionButton();

                } else
                    super.onBackPressed();
            } else
                getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new FeedFragment(), TAG_FEED_FRAGMENT)
                        .commit();

        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.post_fragment_action_bar_post_id) {

            Toast.makeText(DashBoard.this, "Post button selected", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bar_rent_feed_id) {
            // Handle the camera action
        } else if (id == R.id.nav_bar_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new ProfileFragment())
                    .commit();
        } /*else if (id == R.id.logout_nav_id) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void logoutClicked(View view) {

        preferences = getSharedPreferences(Config.SP_TOLET_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Config.SP_LOGED_IN, false);
        editor.apply();

        Intent intent = new Intent(this, LoginSignupActivity.class);
        startActivity(intent);
        finish();

    }
}
