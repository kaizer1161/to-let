package com.rent.afor.space.tolet.kaizer.tolet.controller_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rent.afor.space.tolet.kaizer.tolet.R;
import com.rent.afor.space.tolet.kaizer.tolet.model_data.CommentContent;

import java.util.ArrayList;

/**
 * Created by kaizer on 3/12/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentAdapterHolder> {

    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<CommentContent> comment;

    public CommentAdapter(Activity context, ArrayList<CommentContent> comment, String postId) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.comment = comment;

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

        public CommentAdapterHolder(View itemView) {
            super(itemView);

            initFeedViews(itemView);

        }

        private void initFeedViews(View itemView) {

            userPic = (ImageView) itemView.findViewById(R.id.user_pro_pic_id);
            userName = (TextView) itemView.findViewById(R.id.feed_comment_user_name_feed_id);
            dateAndTime = (TextView) itemView.findViewById(R.id.feed_comment_date_and_time_of_feed_id);
            comment = (TextView) itemView.findViewById(R.id.feed_comment_content_text_view_id);

        }

    }
}
