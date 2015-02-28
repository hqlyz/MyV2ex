package com.example.lyz.myv2ex.adapters;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.MySingleton;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.models.TopicModel;
import com.example.lyz.myv2ex.widgets.RelativeTimeTextView;

import java.util.ArrayList;

public class TopicViewAdapter extends ArrayAdapter<TopicModel>{
    private Context context;
    private int resourceId;
    private ArrayList<TopicModel> topicModelList;
    private int lastPosition = -1;

    public TopicViewAdapter(Context context, int resourceId, ArrayList<TopicModel> topicModelList) {
        super(context, resourceId, topicModelList);
        this.context = context;
        this.resourceId = resourceId;
        this.topicModelList = topicModelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceId, parent, false);
        }

        TextView titleTextView = (TextView)convertView.findViewById(R.id.topic_title_text_view);
        TextView contentTextView = (TextView)convertView.findViewById(R.id.topic_content_text_view);
        NetworkImageView avatarImageView = (NetworkImageView)convertView.findViewById(R.id.topic_avatar_image_view);
        TextView userNameTextView = (TextView)convertView.findViewById(R.id.topic_user_name_text_view);
        RelativeTimeTextView timeAgoTextView = (RelativeTimeTextView)convertView.findViewById(R.id.topic_time_ago_text_view);
        TextView repliesTextView = (TextView)convertView.findViewById(R.id.topic_replies_text_view);

        final TopicModel topicModel = topicModelList.get(position);
        titleTextView.setText(topicModel.getTitle());
        contentTextView.setText(topicModel.getContent());
        avatarImageView.setImageUrl(AppConfig.HTTPS + topicModel.getMemberModel().getAvatarNormal(), MySingleton.getInstance(context).getImageLoader());
        avatarImageView.setClickable(true);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO open user profile activity
                DebugLog.i(topicModel.getMemberModel().getUserName() + "'s avatar");
            }
        });
        userNameTextView.setText(topicModel.getMemberModel().getUserName());
        timeAgoTextView.setReferenceTime(topicModel.getCreated());
        int replies = topicModel.getReplies();
        repliesTextView.setText(replies <= 1 ? replies + " reply" : replies + " replies");

        if(position > lastPosition) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(400).playTogether(
                    ObjectAnimator.ofFloat(convertView, View.TRANSLATION_Y, 150, 0),
                    ObjectAnimator.ofFloat(convertView, View.ROTATION_X, 8, 0)
            );
            animatorSet.start();
        }
        lastPosition = position;

        return convertView;
    }

    @Override
    public int getCount() {
        return topicModelList.size();
    }
}
