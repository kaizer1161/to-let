package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.afor.space.tolet.kaizer.tolet.R;

/**
 * Created by kaizer on 3/7/17.
 */

public class PostFragment extends android.support.v4.app.Fragment {

    public PostFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.post_layout, container, false);

        ((DashBoard) getActivity()).hideFloatingActionButton();

        return rootView;
    }
}


