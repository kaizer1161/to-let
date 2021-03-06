package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaizer on 3/29/17.
 */

public class ProfilePersonalInfo extends Fragment implements View.OnClickListener {

    private ImageView personalInfoEdit, personalInfoSave, passwordEdit, passwordSave;
    private EditText userName, phoneNumber, oldPassword, newPassword, confirmPassword;
    private String email;

    public ProfilePersonalInfo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_personal_info_layout, container, false);

        initLayout(rootView);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SP_TOLET_APP, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(Config.SP_EMAIL, "");

        fetchUserInfo(email);

        return rootView;
    }

    private void initLayout(View rootView) {

        personalInfoEdit = (ImageView) rootView.findViewById(R.id.personal_info_edit_image_view);
        personalInfoSave = (ImageView) rootView.findViewById(R.id.personal_info_save_image_view);
        passwordEdit = (ImageView) rootView.findViewById(R.id.password_info_edit_image_view);
        passwordSave = (ImageView) rootView.findViewById(R.id.password_info_save_image_view);

        userName = (EditText) rootView.findViewById(R.id.profile_personal_info_user_name_id);
        phoneNumber = (EditText) rootView.findViewById(R.id.profile_personal_info_phone_number_id);
        oldPassword = (EditText) rootView.findViewById(R.id.personal_info_old_Password_EditText_id);
        newPassword = (EditText) rootView.findViewById(R.id.personal_info_new_Password_EditText_id);
        confirmPassword = (EditText) rootView.findViewById(R.id.personal_info_confirm_Password_EditText_id);

        personalInfoSave.setVisibility(View.GONE);
        passwordSave.setVisibility(View.GONE);

        editTextPersonalInfoSelection(false);
        editTextPasswordSelection(false);

        personalInfoEdit.setOnClickListener(this);
        personalInfoSave.setOnClickListener(this);
        passwordEdit.setOnClickListener(this);
        passwordSave.setOnClickListener(this);

    }

    private void editTextPersonalInfoSelection(boolean state) {

        userName.setEnabled(state);
        phoneNumber.setEnabled(state);

    }

    private void editTextPasswordSelection(boolean state) {

        oldPassword.setEnabled(state);
        newPassword.setEnabled(state);
        confirmPassword.setEnabled(state);

    }

    @Override
    public void onClick(View v) {

        if (v == personalInfoEdit) {

            personalInfoEdit.setVisibility(View.GONE);
            personalInfoSave.setVisibility(View.VISIBLE);
            editTextPersonalInfoSelection(true);

        } else if (v == personalInfoSave) {

            personalInfoEdit.setVisibility(View.VISIBLE);
            personalInfoSave.setVisibility(View.GONE);
            editTextPersonalInfoSelection(false);
            updateUserInfo(email, userName.getText().toString(), phoneNumber.getText().toString());

        } else if (v == passwordEdit) {

            passwordEdit.setVisibility(View.GONE);
            passwordSave.setVisibility(View.VISIBLE);
            editTextPasswordSelection(true);

        } else if (v == passwordSave) {

            passwordEdit.setVisibility(View.VISIBLE);
            passwordSave.setVisibility(View.GONE);
            editTextPasswordSelection(false);

            if (newPassword.getText().toString().equals(confirmPassword.getText().toString()) && newPassword.getText().toString().length() >= 6 && confirmPassword.getText().toString().length() >= 6) {

                updatePassword(email, oldPassword.getText().toString(), newPassword.getText().toString());

            } else {

                oldPassword.setError("Password miss match/min 6 length");
                newPassword.setError("Password miss match/min 6 length");
                confirmPassword.setError("Password miss match/min 6 length");

            }



        }

    }

    private void fetchUserInfo(final String email) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.PROFILE_FETCH_PERSONAL_INFO;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {

                        if (!response.trim().equals("fail")) {

                            try {
                                userInfo(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error " + error, Toast.LENGTH_LONG).show();

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USER_EMAIL, email);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void userInfo(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray result = jsonObject.getJSONArray(Config.USER_LOGIN_INFO);

        /*Log.v("result", "value : " + response);*/

        for (int i = 0; i < result.length(); i++) {

            JSONObject json = result.getJSONObject(i);

            userName.setText(json.optString(Config.KEY_USERNAME));
            phoneNumber.setText(json.optString(Config.KEY_USER_PHONE));

        }

    }

    private void updateUserInfo(final String email, final String userName, final String phone) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.PROFILE_UPDATE_PERSONAL_INFO;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (response.equals("success")) {

                            Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_LONG).show();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USER_EMAIL, email);
                params.put(Config.KEY_USER_PHONE, phone);
                params.put(Config.KEY_USERNAME, userName);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void updatePassword(final String email, final String oldPassword, final String newPassword) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = Config.PROFILE_UPDATE_PASSWORD;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (response.equals("success")) {

                            Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(getContext(), "Password mismatch", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_LONG).show();
            }

        })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.KEY_USER_EMAIL, email);
                params.put(Config.KEY_USER_PASSWORD, oldPassword);
                params.put(Config.KEY_USER_NEW_PASSWORD, newPassword);
                return params;
            }

        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
