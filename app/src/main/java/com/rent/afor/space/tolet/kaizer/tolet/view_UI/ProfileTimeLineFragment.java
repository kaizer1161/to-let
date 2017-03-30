package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.rent.afor.space.tolet.kaizer.tolet.controller_adapter.ProfileTimeLineAdapter;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.FeedContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kaizer on 3/10/17.
 */

public class ProfileTimeLineFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProfileTimeLineAdapter profileTimeLineAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private ImageView userImage;
    private TextView userName;
    private SharedPreferences sharedPreferences;

    public ProfileTimeLineFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.prolife_timeline_layout, container, false);

        initLayout(rootView);

        sharedPreferences = getContext().getSharedPreferences(Config.SP_TOLET_APP, Context.MODE_PRIVATE);
        userName.setText(sharedPreferences.getString(Config.SP_USERNAME, ""));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFeedData();
            }
        });

        ArrayList<FeedContent> feed = new ArrayList<>();
        feed.add(new FeedContent("", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "));

        showFeed(feed);

        recyclerView.setVisibility(View.GONE);

        fetchFeedData();

        ((DashBoard) getActivity()).showFloatingActionButton();

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });

        return rootView;
    }

    private void initLayout(View rootView) {

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.profile_swipe_refresh_layout);
        userImage = (ImageView) rootView.findViewById(R.id.user_Image_View_id);
        userName = (TextView) rootView.findViewById(R.id.text_View_User_Name_id);

    }

    private void showFeed(ArrayList<FeedContent> feed) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.profile_RecyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        profileTimeLineAdapter = new ProfileTimeLineAdapter(getActivity(), feed);
        recyclerView.setAdapter(profileTimeLineAdapter);

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        recyclerView.addItemDecoration(new ItemDecorator(dividerDrawable));

    }

    private void fetchFeedData() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.PROFILE_TIMELINE_FEED_URL;

        final SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SP_TOLET_APP, Context.MODE_PRIVATE);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (!response.trim().equals("")) {

                            try {
                                profileTimeLineAdapter.itemUpdated(statusValue(response));
                                recyclerView.setVisibility(View.VISIBLE);
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

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USER_EMAIL, sharedPreferences.getString(Config.SP_EMAIL, ""));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private FeedContent[] statusValue(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray result = jsonObject.getJSONArray(Config.PROFILE_TIMELINE_ARRAY);

        //Log.v("result", "value : " + result.length());

        FeedContent[] feed = new FeedContent[result.length()];

        for (int i = 0; i < result.length(); i++) {

            JSONObject json = result.getJSONObject(i);

            feed[i] = new FeedContent("", json.optString(Config.KEY_USERNAME), json.optString(Config.STATUS_TIME), json.optString(Config.KEY_PRICE), json.optString(Config.KEY_SIZE_OF_FLAT), json.optString(Config.KEY_NO_OF_BED), json.optString(Config.KEY_NO_OF_BATH), json.optString(Config.KEY_FLOOR), json.optString(Config.KEY_LOCATION), json.optString(Config.KEY_OTHER_INFORMATION), json.optString(Config.KEY_POST_ID));

        }

        return feed;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && null != data && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                userImage.setImageBitmap(bitmap);

                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage() {
        final String image = getStringImage(bitmap);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.UPLOAD_USER_PIC_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (response.equals("success"))
                            Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("Volly return : ", " " + error);
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USER_EMAIL, sharedPreferences.getString(Config.KEY_USER_EMAIL, ""));
                params.put(Config.KEY_USER_IMAGE, image);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


}
