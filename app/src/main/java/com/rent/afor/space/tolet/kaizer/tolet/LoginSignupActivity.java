package com.rent.afor.space.tolet.kaizer.tolet;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by kaizer on 2/26/17.
 */

public class LoginSignupActivity extends Activity {

    TextView createNewAccountBottomSheet;
    CoordinatorLayout loginSignupCoordinatorLayout;
    View bottomSheet;
    BottomSheetBehavior<View> behavior;
    EditText emailLogin, passwordLogin, userNameSignup, emailSignup, passwordSignup, rePasswordSignup, phoneNumberSighup;
    Button loginBtn, createAccountSignup;
    ProgressBar loginProgressBar, sighupProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_activity_slide_up_panel);

        initLoginViews();
        initSignUpViews();

    }

    private void initSignUpViews() {

        userNameSignup = (EditText) findViewById(R.id.signup_Username_EditText_id);
        emailSignup = (EditText) findViewById(R.id.signup_Email_EditText_id);
        passwordSignup = (EditText) findViewById(R.id.signup_Password_EditText_id);
        rePasswordSignup = (EditText) findViewById(R.id.signup_Password_ReEnter_EditText_id);
        phoneNumberSighup = (EditText) findViewById(R.id.signup_Phone_EditText_id);

        createAccountSignup = (Button) findViewById(R.id.create_account_Btn_id);

        sighupProgressBar = (ProgressBar) findViewById(R.id.sign_up_progress_bar_id);

    }

    private void initLoginViews() {

        loginBtn = (Button) findViewById(R.id.loginBtn_id);

        emailLogin = (EditText) findViewById(R.id.email_login_EditText_id);
        passwordLogin = (EditText) findViewById(R.id.password_login_edit_text_id);

        createNewAccountBottomSheet = (TextView) findViewById(R.id.login_create_new_account_bottom_sheet);
        loginSignupCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.login_signup_coordinator_layout);
        bottomSheet = loginSignupCoordinatorLayout.findViewById(R.id.fl_bottomSheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        loginProgressBar = (ProgressBar) findViewById(R.id.login_progress_view_id);

    }

    public void loginSignupBtnClicked(View view) {

        if (view == createNewAccountBottomSheet) {

            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        } else if (view == loginBtn) {


        } else if (view == createAccountSignup) {

        }
    }
}
