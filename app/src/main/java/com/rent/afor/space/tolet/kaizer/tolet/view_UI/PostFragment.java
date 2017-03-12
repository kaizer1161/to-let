package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaizer on 3/7/17.
 */

public class PostFragment extends android.support.v4.app.Fragment {


    EditText askingPrice, sizeOfFlat, noOfBed, noOfBath, floor, locationOfFlat, additionalInfo;

    public PostFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post_fragment_action_bar, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.post_layout, container, false);

        ((DashBoard) getActivity()).hideFloatingActionButton();

        initPostLayout(rootView);

        return rootView;
    }

    private void initPostLayout(View rootView) {

        askingPrice = (EditText) rootView.findViewById(R.id.asking_price_post_Edit_Text_id);
        sizeOfFlat = (EditText) rootView.findViewById(R.id.size_of_flat_post_edit_text_id);
        noOfBed = (EditText) rootView.findViewById(R.id.no_of_bed_post_edit_text_id);
        noOfBath = (EditText) rootView.findViewById(R.id.no_of_bath_post_edit_text_id);
        floor = (EditText) rootView.findViewById(R.id.floor_post_edit_text_id);
        locationOfFlat = (EditText) rootView.findViewById(R.id.location_post_edit_text_id);
        additionalInfo = (EditText) rootView.findViewById(R.id.additional_info_post_edit_text_id);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.post_fragment_action_bar_post_id) {

            validatePostUserInput();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void validatePostUserInput() {

        String price = askingPrice.getText().toString().trim();
        String size = sizeOfFlat.getText().toString().trim();
        String bed = noOfBed.getText().toString().trim();
        String bath = noOfBath.getText().toString().trim();
        String floorNo = floor.getText().toString().trim();
        String location = locationOfFlat.getText().toString().trim();
        String additional = additionalInfo.getText().toString().trim();

        //condition for validation error message;
        if (price.trim().equals("")) {

            /*createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);*/
            askingPrice.setError("Asking price required!");

        } else if (size.trim().equals("")) {

            /*createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);*/
            sizeOfFlat.setError("Size Of flat required!");

        } else if (bed.trim().equals("")) {

            /*createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);*/
            noOfBed.setError("No Of bed Required!");

        } else if (bath.trim().equals("")) {

            /*createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);*/
            noOfBath.setError("No Of bath required!");

        } else if (floorNo.trim().equals("")) {

            /*createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);*/
            floor.setError("Floor required!");

        } else if (location.trim().equals("")) {

            /*createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);*/
            locationOfFlat.setError("Location Of flat required!");

        } else
            makePost("3rd@gmail.com", price, size, bed, bath, floorNo, location, additional);
    }

    private void makePost(final String email, final String price, final String size, final String bed, final String bath, final String floorNo, final String location, final String additional) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.POST_FEED_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USER_EMAIL, email);
                params.put(Config.KEY_PRICE, price);
                params.put(Config.KEY_SIZE_OF_FLAT, size);
                params.put(Config.KEY_NO_OF_BED, bed);
                params.put(Config.KEY_NO_OF_BATH, bath);
                params.put(Config.KEY_FLOOR, floorNo);
                params.put(Config.KEY_LOCATION, location);
                params.put(Config.KEY_OTHER_INFORMATION, additional);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

}


