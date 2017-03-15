package com.rent.afor.space.tolet.kaizer.tolet.controller_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaizer on 3/12/17.
 */

class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentAdapterHolder> {

    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<CommentContent> comment;
    private View rootView;
    private String postId;

    public CommentAdapter(Activity context, ArrayList<CommentContent> comment, View rootView, String postId) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.comment = comment;
        this.rootView = rootView;
        this.postId = postId;

    }

    @Override
    public CommentAdapter.CommentAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentAdapter.CommentAdapterHolder(inflater.inflate(R.layout.comment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentAdapterHolder holder, int position) {

        CommentContent item = comment.get(position);

        holder.userName.setText(item.getUserName());
        holder.dateAndTime.setText(item.getDateAndTime());
        holder.comment.setText(item.getComment());
        holder.postId = this.postId;

    }

    @Override
    public int getItemCount() {
        return comment.size();
    }

    public void itemUpdated(CommentContent[] items) {

        comment.clear();
        for (int i = 0; i < items.length; i++)
            comment.add(items[i]);

        notifyDataSetChanged();

    }

    public class CommentAdapterHolder extends RecyclerView.ViewHolder {


        private ImageView userPic;
        private TextView userName, dateAndTime, comment;
        private EditText commentEditText;
        private ImageView commentSendBtn;
        private String postId;

        public CommentAdapterHolder(View itemView) {
            super(itemView);

            initFeedViews(itemView);

            commentSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!commentEditText.getText().toString().equals(""))
                        addComment("1st@gmail.com", commentEditText.getText().toString(), postId);

                }
            });

        }

        private void initFeedViews(View itemView) {

            userPic = (ImageView) itemView.findViewById(R.id.user_pro_pic_id);
            userName = (TextView) itemView.findViewById(R.id.feed_comment_user_name_feed_id);
            dateAndTime = (TextView) itemView.findViewById(R.id.feed_comment_date_and_time_of_feed_id);
            comment = (TextView) itemView.findViewById(R.id.feed_comment_content_text_view_id);
            commentEditText = (EditText) rootView.findViewById(R.id.comment_edit_text_id);
            commentSendBtn = (ImageView) rootView.findViewById(R.id.comment_send_btn_id);

        }

        private void addComment(final String email, final String comment, final String postId) {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Config.POST_COMMENT_URL;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.

                            Toast.makeText(context, " " + response, Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.v("Volly return : ", " " + error);
                }

            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(Config.KEY_USER_EMAIL, email);
                    params.put(Config.COMMENT_CONTENT, comment);
                    params.put(Config.KEY_COMMENT_POST_ID, postId);
                    return params;
                }
            };


            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }

    }
}
