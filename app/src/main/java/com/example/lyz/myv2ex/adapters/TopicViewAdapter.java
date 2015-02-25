package com.example.lyz.myv2ex.adapters;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.lyz.myv2ex.models.TopicModel;

import java.util.ArrayList;
import java.util.List;

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
