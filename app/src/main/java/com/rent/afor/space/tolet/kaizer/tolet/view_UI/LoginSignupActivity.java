package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by kaizer on 2/26/17.
 */

public class LoginSignupActivity extends FragmentActivity {

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_USERNAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DEVICE_ID = "device_id";

    TextView createNewAccountBottomSheet;
    CoordinatorLayout loginSignupCoordinatorLayout;
    View bottomSheet;
    BottomSheetBehavior<View> behaviorBottomSheet;
    EditText emailLogin, passwordLogin, userNameSignup, emailSignup, passwordSignup, rePasswordSignup, phoneNumberSighup;
    Button loginBtn, createAccountSignup;
    ProgressBar loginProgressBar, sighupProgressBar;

    LinearLayout loginContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_login_signup);

        initLoginViews();
        initSignUpViews();

        behaviorBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.TOP);

                ViewGroup root = (ViewGroup) findViewById(R.id.login_signup_coordinator_layout);
                TransitionManager.beginDelayedTransition(root, slide);

                bottomSheetEditTextVisibility("visible");
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetEditTextVisibility("gone");

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (behaviorBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED)
            behaviorBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();

    }

    private void bottomSheetEditTextVisibility(String state) {

        if (state.equals("visible")) {

            userNameSignup.setVisibility(View.VISIBLE);
            emailSignup.setVisibility(View.VISIBLE);
            passwordSignup.setVisibility(View.VISIBLE);
            rePasswordSignup.setVisibility(View.VISIBLE);
            phoneNumberSighup.setVisibility(View.VISIBLE);

            loginContainer.setVisibility(View.GONE);

        } else if (state.equals("gone")) {

            userNameSignup.setVisibility(View.GONE);
            emailSignup.setVisibility(View.GONE);
            passwordSignup.setVisibility(View.GONE);
            rePasswordSignup.setVisibility(View.GONE);
            phoneNumberSighup.setVisibility(View.GONE);

            loginContainer.setVisibility(View.VISIBLE);

        }


    }

    private void initSignUpViews() {

        userNameSignup = (EditText) findViewById(R.id.asking_price_post_Edit_Text_id);
        emailSignup = (EditText) findViewById(R.id.signup_Email_EditText_id);
        passwordSignup = (EditText) findViewById(R.id.signup_Password_EditText_id);
        rePasswordSignup = (EditText) findViewById(R.id.signup_Password_ReEnter_EditText_id);
        phoneNumberSighup = (EditText) findViewById(R.id.size_of_flat_post_edit_text_id);

        createAccountSignup = (Button) findViewById(R.id.create_account_Btn_id);

        sighupProgressBar = (ProgressBar) findViewById(R.id.sign_up_progress_bar_id);

        bottomSheetEditTextVisibility("gone");
        sighupProgressBar.setVisibility(View.GONE);

    }

    private void initLoginViews() {

        loginContainer = (LinearLayout) findViewById(R.id.login_view_container_id);

        loginBtn = (Button) findViewById(R.id.loginBtn_id);

        emailLogin = (EditText) findViewById(R.id.email_login_EditText_id);
        passwordLogin = (EditText) findViewById(R.id.password_login_edit_text_id);

        createNewAccountBottomSheet = (TextView) findViewById(R.id.login_create_new_account_bottom_sheet);
        loginSignupCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.login_signup_coordinator_layout);
        bottomSheet = loginSignupCoordinatorLayout.findViewById(R.id.fl_bottomSheet);
        behaviorBottomSheet = BottomSheetBehavior.from(bottomSheet);

        loginProgressBar = (ProgressBar) findViewById(R.id.login_progress_view_id);

        loginProgressBar.setVisibility(View.GONE);

    }

    public void loginSignupBtnClicked(View view) {

        if (view == createNewAccountBottomSheet) {

            behaviorBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else if (view == loginBtn) {

            loginProgressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);

            String email = emailLogin.getText().toString().trim();
            String password = passwordLogin.getText().toString().trim();

            if (isValidEmailId(email))
                loginUser(email, password);
            else {

                loginProgressBar.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);

                emailLogin.setError("Invalid Email Address");

            }

        } else if (view == createAccountSignup) {

            sighupProgressBar.setVisibility(View.VISIBLE);
            createAccountSignup.setVisibility(View.GONE);
            validateSignUpUserInput();

        }

    }

    private void loginUser(final String email, final String password) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Config.LOGIN_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {

                            loginProgressBar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(LoginSignupActivity.this, DashBoard.class);
                            startActivity(intent);
                            finish();

                        } else {

                            loginProgressBar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(getBaseContext(), "Invalid Email or password", Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loginProgressBar.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), "Error " + error, Toast.LENGTH_LONG).show();

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD, password);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void validateSignUpUserInput() {

        String username = userNameSignup.getText().toString().trim();
        String phone = phoneNumberSighup.getText().toString().trim();
        String password = passwordSignup.getText().toString().trim();
        String email = emailSignup.getText().toString().trim();
        String rePass = rePasswordSignup.getText().toString().trim();
        String deviceId = "asd";

        //condition for validation error message;
        if (username.trim().equals("")) {

            createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);
            userNameSignup.setError("username required!");

        } else if (!isValidEmailId(email)) {

            createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);
            emailSignup.setError("Invalid Email Address");

        } else if (email.trim().equals("")) {

            createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);
            emailSignup.setError("email is missing!");

        } else if (password.trim().equals("") || password.length() < 5) {

            createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);
            passwordSignup.setError("password length should be greater than 4 or can't be blank");

        } else if (rePass.trim().equals("")) {

            createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);
            rePasswordSignup.setError("can't be blank");

        } else if (!password.equals(rePass)) {

            createAccountSignup.setVisibility(View.VISIBLE);
            sighupProgressBar.setVisibility(View.GONE);
            rePasswordSignup.setError("password doesn't matched");

        } else
            signUpNewUser(email, phone, username, password, deviceId);
    }


    private void signUpNewUser(final String email, final String phone, final String userName, final String password, final String device_id) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Config.SIGNUP_URL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v("Volly return : ", "" + response);
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
                params.put(KEY_EMAIL, email);
                params.put(KEY_PHONE, phone);
                params.put(KEY_USERNAME, userName);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_DEVICE_ID, device_id);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    //function for email validation;
    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

}
