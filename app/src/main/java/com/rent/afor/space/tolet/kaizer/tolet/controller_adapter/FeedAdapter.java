package com.rent.afor.space.tolet.kaizer.tolet.controller_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.FeedContent;

import java.util.ArrayList;

/**
 * Created by kaizer on 3/6/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedAdapterHolder> {

    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<FeedContent> feed;

    public FeedAdapter(Activity context, ArrayList<FeedContent> feed) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.feed = feed;

    }

    @Override
    public FeedAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedAdapterHolder(inflater.inflate(R.layout.dash_board_feed_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(FeedAdapterHolder holder, int position) {

        FeedContent item = feed.get(position);

        holder.userName.setText(item.getUserName());
        holder.dateAndTime.setText(item.getDateAndTime());
        holder.priceOfFlat.setText(item.getPriceOfFlat());
        holder.sizeOfFlat.setText(item.getSizeOfFlat());
        holder.noOfBed.setText(item.getNoOfBed());
        holder.noOfBath.setText(item.getNoOfBath());
        holder.floorNo.setText(item.getFloorNo());
        holder.addressOfFlat.setText(item.getAddressOfFlat());
        holder.otherInfo.setText(item.getOtherInfo());

    }

    @Override
    public int getItemCount() {
        return feed.size();
    }

    public void itemUpdated(FeedContent[] items) {

        feed.clear();
        for (int i = 0; i < items.length; i++)
            feed.add(items[i]);

        notifyDataSetChanged();

    }

    public class FeedAdapterHolder extends RecyclerView.ViewHolder {


        private ImageView userPic;
        private TextView userName, dateAndTime, priceOfFlat, sizeOfFlat, noOfBed, noOfBath, floorNo, addressOfFlat, otherInfo;

        public FeedAdapterHolder(View itemView) {
            super(itemView);

            initFeedViews(itemView);

        }

        private void initFeedViews(View itemView) {

            userPic = (ImageView) itemView.findViewById(R.id.user_pro_pic_id);
            userName = (TextView) itemView.findViewById(R.id.user_name_feed_id);
            dateAndTime = (TextView) itemView.findViewById(R.id.date_and_time_of_feed_id);
            priceOfFlat = (TextView) itemView.findViewById(R.id.price_of_flat_feed_id);
            sizeOfFlat = (TextView) itemView.findViewById(R.id.size_of_flat_feed_id);
            noOfBed = (TextView) itemView.findViewById(R.id.no_of_bed_feed_id);
            noOfBath = (TextView) itemView.findViewById(R.id.no_of_bath_feed_id);
            floorNo = (TextView) itemView.findViewById(R.id.floor_feed_id);
            addressOfFlat = (TextView) itemView.findViewById(R.id.location_feed_id);
            otherInfo = (TextView) itemView.findViewById(R.id.other_info_feed_id);

        }

    }
}
