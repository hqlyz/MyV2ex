package com.example.lyz.myv2ex.adapters;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.models.TopicModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TopicViewAdapter extends ArrayAdapter<TopicModel> {
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

        TextView title_text_view = (TextView)convertView.findViewById(R.id.topic_title_text_view);
        TextView content_text_view = (TextView)convertView.findViewById(R.id.topic_content_text_view);
        ImageView avatar_image_view = (ImageView)convertView.findViewById(R.id.topic_avatar_image_view);
        TextView name_text_view = (TextView)convertView.findViewById(R.id.topic_name_text_view);
        TextView time_ago_text_view = (TextView)convertView.findViewById(R.id.topic_time_ago_text_view);
        TextView replies_text_view = (TextView)convertView.findViewById(R.id.topic_replies_text_view);

        TopicModel topicModel = topicModelList.get(position);
        title_text_view.setText(topicModel.getTitle());
        content_text_view.setText(topicModel.getContent());
        name_text_view.setText(topicModel.getMemberModel().getUserName());
        time_ago_text_view.setText(topicModel.getCreated() + "");
        int replies = topicModel.getReplies();
        replies_text_view.setText(replies <= 1 ? replies + " reply" : replies + " replies");

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
