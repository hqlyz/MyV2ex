package com.example.lyz.myv2ex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.MySingleton;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.models.ReplyModel;
import com.example.lyz.myv2ex.utils.DateUtils;
import com.example.lyz.myv2ex.widgets.RelativeTimeTextView;

import java.util.ArrayList;

public class ReplyViewAdapter extends ArrayAdapter<ReplyModel> {

    private Context context;
    private int resourceId;
    private ArrayList<ReplyModel> replyModels;

    public ReplyViewAdapter(Context context, int resourceId, ArrayList<ReplyModel> replyModels) {
        super(context, resourceId, replyModels);
        this.context = context;
        this.resourceId = resourceId;
        this.replyModels = replyModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceId, parent, false);
        }
        NetworkImageView replyAvatarImageView = (NetworkImageView)convertView.findViewById(R.id.reply_avatar_image_view);
        TextView replyUserNameTextView = (TextView)convertView.findViewById(R.id.reply_user_name_text_view);
        TextView replyContentTextView = (TextView)convertView.findViewById(R.id.reply_content_text_view);
        RelativeTimeTextView replyTimeAgoTextView = (RelativeTimeTextView)convertView.findViewById(R.id.reply_time_ago_text_view);

        ReplyModel replyModel = replyModels.get(position);
        replyAvatarImageView.setImageUrl(AppConfig.HTTPS + replyModel.getMemberModel().getAvatarNormal(), MySingleton.getInstance(context).getImageLoader());
        replyTimeAgoTextView.setText(DateUtils.convertDistTimeStampToString(replyModel.getCreated()));
        replyUserNameTextView.setText(replyModel.getMemberModel().getUserName());
        replyContentTextView.setText(replyModel.getContent());

        return convertView;
    }
}
