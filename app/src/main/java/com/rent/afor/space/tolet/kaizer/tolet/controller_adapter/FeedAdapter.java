package com.rent.afor.space.tolet.kaizer.tolet.controller_adapter;

import android.app.Activity;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        private LinearLayout callBtn, commentBtn;

        private CoordinatorLayout commentCoordinatorLayout;
        private View bottomSheet;
        private BottomSheetBehavior<View> behaviorBottomSheet;

        public FeedAdapterHolder(View itemView) {
            super(itemView);

            initFeedViews(itemView);

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    manager.beginTransaction().add(R.id.content_dash_board, new CommentFragment())
                            .commit();*/

                    behaviorBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

                }
            });

            /*behaviorBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                    *//*Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.TOP);

                    ViewGroup root = (ViewGroup) findViewById(R.id.login_signup_coordinator_layout);
                    TransitionManager.beginDelayedTransition(root, slide);*//*


                    if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                        bottomSheetEditTextVisibility("gone");

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });*/


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
            callBtn = (LinearLayout) itemView.findViewById(R.id.call_feed_id);
            commentBtn = (LinearLayout) itemView.findViewById(R.id.comment_feed_id);

            commentCoordinatorLayout = (CoordinatorLayout) itemView.findViewById(R.id.feed_coordinator_layout);
            bottomSheet = commentCoordinatorLayout.findViewById(R.id.comment_bottomSheet);
            behaviorBottomSheet = BottomSheetBehavior.from(bottomSheet);

            behaviorBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }


    }
}
