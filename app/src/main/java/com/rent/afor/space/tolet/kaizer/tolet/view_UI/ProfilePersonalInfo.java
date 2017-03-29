package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.rent.afor.space.tolet.kaizer.tolet.R;

/**
 * Created by kaizer on 3/29/17.
 */

public class ProfilePersonalInfo extends Fragment implements View.OnClickListener {

    private ImageView personalInfoEdit, personalInfoSave, passwordEdit, passwordSave;
    private EditText userName, phoneNumber, oldPassword, newPassword, confirmPassword;

    public ProfilePersonalInfo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_personal_info_layout, container, false);

        initLayout(rootView);


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

        } else if (v == passwordEdit) {

            passwordEdit.setVisibility(View.GONE);
            passwordSave.setVisibility(View.VISIBLE);
            editTextPasswordSelection(true);

        } else if (v == passwordSave) {

            passwordEdit.setVisibility(View.VISIBLE);
            passwordSave.setVisibility(View.GONE);
            editTextPasswordSelection(false);

        }

    }
}