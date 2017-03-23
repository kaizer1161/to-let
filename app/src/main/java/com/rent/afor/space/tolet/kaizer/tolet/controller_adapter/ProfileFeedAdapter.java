package com.rent.afor.space.tolet.kaizer.tolet.controller_adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.CommentContent;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.Config;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.FeedContent;
import com.rent.afor.space.tolet.kaizer.tolet.view_UI.DashBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaizer on 3/23/17.
 */

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ProfileFeedAdapterHolder> {

    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<FeedContent> feed;
    private View rootView;
    private ProfileFeedAdapter.ProfileFeedAdapterHolder holder;

    public ProfileFeedAdapter(Activity context, ArrayList<FeedContent> feed, View rootView) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.feed = feed;
        this.rootView = rootView;

    }

    @Override
    public ProfileFeedAdapter.ProfileFeedAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileFeedAdapter.ProfileFeedAdapterHolder(inflater.inflate(R.layout.dash_board_feed_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ProfileFeedAdapter.ProfileFeedAdapterHolder holder, int position) {

        FeedContent item = feed.get(position);

        this.holder = holder;

        holder.userName.setText(item.getUserName());
        holder.dateAndTime.setText(item.getDateAndTime());
        holder.priceOfFlat.setText(item.getPriceOfFlat());
        holder.sizeOfFlat.setText(item.getSizeOfFlat());
        holder.noOfBed.setText(item.getNoOfBed());
        holder.noOfBath.setText(item.getNoOfBath());
        holder.floorNo.setText(item.getFloorNo());
        holder.addressOfFlat.setText(item.getAddressOfFlat());
        holder.otherInfo.setText(item.getOtherInfo());
        holder.postId = item.getPostId();

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

    public boolean behaviorBottomSheetStateExpanded() {

        return holder.behaviorBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED;

    }

    public void changeBehaviorBottomSheetToCollapse() {
        holder.behaviorBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public class ProfileFeedAdapterHolder extends RecyclerView.ViewHolder {


        private ImageView userPic;
        private TextView userName, dateAndTime, priceOfFlat, sizeOfFlat, noOfBed, noOfBath, floorNo, addressOfFlat, otherInfo;
        private LinearLayout callBtn, commentBtn;

        private CoordinatorLayout commentCoordinatorLayout;
        private View bottomSheet;

        private BottomSheetBehavior<View> behaviorBottomSheet;

        private CommentAdapter commentAdapter;
        private RecyclerView recyclerView;
        private String postId;

        public ProfileFeedAdapterHolder(View itemView) {
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

                    fetchCommentData();
                    behaviorBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                    ((DashBoard) context).hideFloatingActionButton();
                    ((DashBoard) context).getSupportActionBar().hide();

                    ArrayList<CommentContent> comment = new ArrayList<>();
                    comment.add(new CommentContent("", " ", " ", " "));

                    recyclerView = (RecyclerView) rootView.findViewById(R.id.comment_bottomSheet_recycler_view_id);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    commentAdapter = new CommentAdapter(context, comment, rootView, postId);
                    recyclerView.setAdapter(commentAdapter);

                }
            });

            behaviorBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {


                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        ((DashBoard) context).showFloatingActionButton();
                        ((DashBoard) context).getSupportActionBar().show();
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });


        }

        private void fetchCommentData() {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Config.FETCH_COMMENT_URL;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (!response.trim().equals("")) {

                                try {

                                    commentAdapter.itemUpdated(statusValue(response));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

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
                    params.put(Config.KEY_COMMENT_POST_ID, postId);
                    return params;
                }
            };


            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }

        private CommentContent[] statusValue(String response) throws JSONException {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.COMMENT_ARRAY);

            //Log.v("result", "value : " + result.length());

            CommentContent[] feed = new CommentContent[result.length()];

            for (int i = 0; i < result.length(); i++) {

                JSONObject json = result.getJSONObject(i);

                feed[i] = new CommentContent("", json.optString(Config.KEY_USERNAME), json.optString(Config.STATUS_TIME),
                        json.optString(Config.COMMENT_CONTENT));

            }

            return feed;

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

            commentCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.profile_coordinator_layout);
            bottomSheet = commentCoordinatorLayout.findViewById(R.id.comment_bottomSheet);
            behaviorBottomSheet = BottomSheetBehavior.from(bottomSheet);

            behaviorBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }


    }

}
