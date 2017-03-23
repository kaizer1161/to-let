package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.controller_adapter.ProfileFeedAdapter;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.FeedContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kaizer on 3/10/17.
 */

public class ProfileFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    ProfileFeedAdapter profileFeedAdapter;
    RecyclerView recyclerView;
    View rootView;

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_layout, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.profile_swipe_refresh_layout_id);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFeedData();
            }
        });

        ArrayList<FeedContent> feed = new ArrayList<>();
        feed.add(new FeedContent("", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "));

        showFeed(feed);
        fetchFeedData();

        ((DashBoard) getActivity()).showFloatingActionButton();

        return rootView;
    }

    private void showFeed(ArrayList<FeedContent> feed) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.profile_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileFeedAdapter = new ProfileFeedAdapter(getActivity(), feed, rootView);
        recyclerView.setAdapter(profileFeedAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        recyclerView.addItemDecoration(new ItemDecorator(dividerDrawable));

    }

    private void fetchFeedData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.FETCH_FEED_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.trim().equals("")) {

                            try {
                                profileFeedAdapter.itemUpdated(statusValue(response));
                                swipeRefreshLayout.setRefreshing(false);

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

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private FeedContent[] statusValue(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

        //Log.v("result", "value : " + result.length());

        FeedContent[] feed = new FeedContent[result.length()];

        for (int i = 0; i < result.length(); i++) {

            JSONObject json = result.getJSONObject(i);

            feed[i] = new FeedContent("", json.optString(Config.KEY_USERNAME), json.optString(Config.STATUS_TIME), json.optString(Config.KEY_PRICE), json.optString(Config.KEY_SIZE_OF_FLAT), json.optString(Config.KEY_NO_OF_BED), json.optString(Config.KEY_NO_OF_BATH), json.optString(Config.KEY_FLOOR), json.optString(Config.KEY_LOCATION), json.optString(Config.KEY_OTHER_INFORMATION), json.optString(Config.KEY_POST_ID));

        }

        return feed;

    }

    public boolean commentBottomSheetStateIsExpanded() {

        return profileFeedAdapter.behaviorBottomSheetStateExpanded();

    }

    public void commentBottomSheetCollapse() {

        profileFeedAdapter.changeBehaviorBottomSheetToCollapse();

    }

}
