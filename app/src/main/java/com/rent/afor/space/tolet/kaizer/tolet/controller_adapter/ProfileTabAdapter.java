package com.rent.afor.space.tolet.kaizer.tolet.controller_adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.view_UI.ProfilePersonalInfo;
import com.rent.afor.space.tolet.kaizer.tolet.view_UI.ProfileTimeLineFragment;

/**
 * Created by kaizer on 3/23/17.
 */

public class ProfileTabAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public ProfileTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ProfileTimeLineFragment();
        } else {
            return new ProfilePersonalInfo();
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.profile_time_line);
        } else {
            return context.getString(R.string.personalInfo);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

}
