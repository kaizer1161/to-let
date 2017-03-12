package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rent.afor.space.tolet.kaizer.tolet.R;

/**
 * Created by kaizer on 3/12/17.
 */

public class CommentFragment extends Fragment {

    public CommentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comment_bottom_sheet_layout, container, false);

        return rootView;
    }
}
