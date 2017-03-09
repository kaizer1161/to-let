package com.rent.afor.space.tolet.kaizer.tolet.view_UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rent.afor.space.tolet.kaizer.tolet.R;

/**
 * Created by kaizer on 3/7/17.
 */

public class PostFragment extends android.support.v4.app.Fragment {

    EditText askingPrice, sizeOfFlat, noOfBed, noOfBath, floor, locationOfFlat, additionalInfo;

    public PostFragment() {
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
}


