package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.controller_adapter.CommentAdapter;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.CommentContent;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_FEED_FRAGMENT = "feed_fragment";
    private static final String TAG_PROFILE_TAB_FRAGMENT = "profile_tab_fragment";

    private BottomSheetBehavior<View> behaviorBottomSheet;

    private CommentAdapter commentAdapter;

    private FeedFragment feedFragment = new FeedFragment();

    private FloatingActionButton fab;

    private String postId;

    private SharedPreferences sharedPreferences;

    private NavigationView navigationView;

    private EditText commentEditText;

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

        /*Local variables*/
        sharedPreferences = getSharedPreferences(Config.SP_TOLET_APP, MODE_PRIVATE);
        TextView userName = (TextView) findViewById(R.id.nav_bar_user_name_id);
        commentEditText = (EditText) findViewById(R.id.comment_edit_text_id);
        ImageView commentSendBtn = (ImageView) findViewById(R.id.comment_send_btn_id);

        CoordinatorLayout commentCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.dash_board_coordinator_layout);
        View bottomSheet = commentCoordinatorLayout.findViewById(R.id.comment_bottomSheet);
        behaviorBottomSheet = BottomSheetBehavior.from(bottomSheet);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_bar_rent_feed_id);

        behaviorBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        userName.setText(sharedPreferences.getString(Config.SP_USERNAME, ""));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().add(R.id.content_dash_board, feedFragment, TAG_FEED_FRAGMENT)
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new PostFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_bar_post);

            }
        });

        commentSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!commentEditText.getText().toString().equals(""))
                    addComment(sharedPreferences.getString(Config.SP_EMAIL, ""), commentEditText.getText().toString(), postId);

            }
        });

        behaviorBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    showFloatingActionButton();
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

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

            if (behaviorBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                behaviorBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);

            } else if (feedFragment != null && feedFragment.isVisible()) {

                super.onBackPressed();

            } else {

                navigationView.setCheckedItem(R.id.nav_bar_rent_feed_id);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new FeedFragment(), TAG_FEED_FRAGMENT)
                        .commit();

            }


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

            getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new FeedFragment(), TAG_FEED_FRAGMENT)
                    .commit();

        } else if (id == R.id.nav_bar_profile) {

            getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new ProfileTabFragment(), TAG_PROFILE_TAB_FRAGMENT)
                    .commit();

        } else if (id == R.id.nav_bar_post) {

            getSupportFragmentManager().beginTransaction().replace(R.id.content_dash_board, new PostFragment()).commit();

        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void logoutClicked(View view) {

        SharedPreferences preferences = getSharedPreferences(Config.SP_TOLET_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Config.SP_LOGED_IN, false);
        editor.apply();

        Intent intent = new Intent(this, LoginSignupActivity.class);
        startActivity(intent);
        finish();

    }

    public void commentBtnIsClicked(String postId) {

        this.postId = postId;
        fetchCommentData(postId);
        behaviorBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        hideFloatingActionButton();

        ArrayList<CommentContent> comment = new ArrayList<>();
        comment.add(new CommentContent("", " ", " ", " "));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comment_bottomSheet_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashBoard.this));
        commentAdapter = new CommentAdapter(DashBoard.this, comment, postId);
        recyclerView.setAdapter(commentAdapter);

    }

    private void fetchCommentData(final String postId) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(DashBoard.this);
        String url = Config.FETCH_COMMENT_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.trim().equals("")) {

                            try {

                                commentAdapter.itemUpdated(statusValue(response));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Volly return : ", "" + error);
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_COMMENT_POST_ID, postId);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private CommentContent[] statusValue(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray result = jsonObject.getJSONArray(Config.COMMENT_ARRAY);

        //Log.v("result", "value : " + result.length());

        CommentContent[] feed = new CommentContent[result.length()];

        for (int i = 0; i < result.length(); i++) {

            JSONObject json = result.getJSONObject(i);

            feed[i] = new CommentContent("", json.optString(Config.KEY_USERNAME), json.optString(Config.STATUS_TIME),
                    json.optString(Config.COMMENT_CONTENT));

        }

        return feed;

    }

    private void addComment(final String email, final String comment, final String postId) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(DashBoard.this);
        String url = Config.POST_COMMENT_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        Toast.makeText(DashBoard.this, " " + response, Toast.LENGTH_LONG).show();

                        if (response.equals("success")) {

                            fetchCommentData(postId);
                            commentEditText.setText("");
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DashBoard.this, " " + error, Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USER_EMAIL, email);
                params.put(Config.COMMENT_CONTENT, comment);
                params.put(Config.KEY_COMMENT_POST_ID, postId);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
