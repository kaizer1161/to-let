package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.afor.space.tolet.kaizer.tolet.R;

/**
 * Created by kaizer on 3/29/17.
 */

public class ProfilePersonalInfo extends Fragment {

    public ProfilePersonalInfo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_personal_info_layout, container, false);

        return rootView;
    }
}
