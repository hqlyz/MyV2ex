package com.example.lyz.myv2ex.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.MySingleton;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.adapters.ReplyViewAdapter;
import com.example.lyz.myv2ex.models.MemberModel;
import com.example.lyz.myv2ex.models.ReplyModel;
import com.example.lyz.myv2ex.models.TopicModel;
import com.example.lyz.myv2ex.utils.DateUtils;
import com.example.lyz.myv2ex.widgets.RelativeTimeTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends Fragment {

    private ListView topicListView;
    private ProgressBar topicLoadingProgressBar;

    private ArrayList<ReplyModel> replyModelArrayList;
    private ReplyViewAdapter replyViewAdapter;

    public TopicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        topicListView = (ListView)view.findViewById(R.id.topic_list_view);
        topicLoadingProgressBar = (ProgressBar)view.findViewById(R.id.topic_loading_progress_bar);
        replyModelArrayList = new ArrayList<>();

        topicListView.setEmptyView(topicLoadingProgressBar);
        Bundle bundle = getArguments();
        if(bundle != null) {
            TopicModel topicModel = bundle.getParcelable(AppConfig.PARCEL_TOPIC_MODEL_KEY);
            View headerView = inflater.inflate(R.layout.topic_view, null, false);
            TextView topicTitleTextView = (TextView)headerView.findViewById(R.id.topic_title_text_view);
            TextView topicContentTextView = (TextView)headerView.findViewById(R.id.topic_content_text_view);
            NetworkImageView topicAvatarImageView = (NetworkImageView)headerView.findViewById(R.id.topic_avatar_image_view);
            TextView topicUserNameTextView = (TextView)headerView.findViewById(R.id.topic_user_name_text_view);
            RelativeTimeTextView topicTimeAgoTextView = (RelativeTimeTextView)headerView.findViewById(R.id.topic_time_ago_text_view);
            TextView topicReplyTextView = (TextView)headerView.findViewById(R.id.topic_replies_text_view);
            topicTitleTextView.setText(topicModel.getTitle());
            topicContentTextView.setMaxLines(Integer.MAX_VALUE);
            topicContentTextView.setText(topicModel.getContent());
            topicAvatarImageView.setImageUrl(AppConfig.HTTPS + topicModel.getMemberModel().getAvatarNormal(), MySingleton.getInstance(getActivity()).getImageLoader());
            topicUserNameTextView.setText(topicModel.getMemberModel().getUserName());
            topicTimeAgoTextView.setText(DateUtils.convertDistTimeStampToString(topicModel.getCreated()));
            int replies = topicModel.getReplies();
            topicReplyTextView.setText(replies <= 1 ? replies + " reply" : replies + " replies");
            headerView.setClickable(false);
            topicListView.addHeaderView(headerView);

            getRepliesData(topicModel.getId());
            replyViewAdapter = new ReplyViewAdapter(getActivity(), R.layout.reply_view, replyModelArrayList);
            topicListView.setAdapter(replyViewAdapter);
        }

        return view;
    }

    private void getRepliesData(int id) {
        replyModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.API_URL + AppConfig.API_REPLIES + "?topic_id=" + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for(int i = 0; i < jsonArray.length(); ++i) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ReplyModel replyModel = new ReplyModel();
                        replyModel.setId(jsonObject.getInt("id"));
                        replyModel.setThanks(jsonObject.getInt("thanks"));
                        replyModel.setContent(jsonObject.getString("content"));
                        replyModel.setContentRendered(jsonObject.getString("content_rendered"));

                        MemberModel memberModel = new MemberModel();
                        JSONObject memberObject = jsonObject.getJSONObject("member");
                        memberModel.setId(memberObject.getInt("id"));
                        memberModel.setUserName(memberObject.getString("username"));
                        memberModel.setTagLine(memberObject.getString("tagline"));
                        memberModel.setAvatarMini(memberObject.getString("avatar_mini"));
                        memberModel.setAvatarNormal(memberObject.getString("avatar_normal"));
                        memberModel.setAvatarLarge(memberObject.getString("avatar_large"));
                        replyModel.setMemberModel(memberModel);

                        replyModel.setCreated(jsonObject.getLong("created"));
                        replyModel.setLastModified(jsonObject.getLong("last_modified"));

                        replyModelArrayList.add(replyModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                replyViewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DebugLog.e(volleyError.getMessage());
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }
}
