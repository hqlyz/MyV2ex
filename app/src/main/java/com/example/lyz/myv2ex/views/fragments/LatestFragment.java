package com.example.lyz.myv2ex.views.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.FragmentData;
import com.example.lyz.myv2ex.GetDataCallback;
import com.example.lyz.myv2ex.MySingleton;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.adapters.TopicViewAdapter;
import com.example.lyz.myv2ex.models.MemberModel;
import com.example.lyz.myv2ex.models.NodeModel;
import com.example.lyz.myv2ex.models.TopicModel;
import com.example.lyz.myv2ex.views.MainActivity;
import com.example.lyz.myv2ex.views.TopicActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment implements FragmentData {

    private ListView latestListView;
    private ProgressBar loadingProgressBar;

    private static ArrayList<TopicModel> topicModelList;
    private TopicViewAdapter topicViewAdapter;
    private Activity activity;
    private GetDataCallback callback;

    public LatestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        latestListView = (ListView)view.findViewById(R.id.latest_list_view);
        loadingProgressBar = (ProgressBar)view.findViewById(R.id.latest_loading_progress_bar);
        activity = getActivity();
        if(topicModelList == null) {
            topicModelList = new ArrayList<>();
            getData();
        }

        topicViewAdapter = new TopicViewAdapter(getActivity(), R.layout.topic_view, topicModelList);
        latestListView.setAdapter(topicViewAdapter);
        latestListView.setEmptyView(loadingProgressBar);
        latestListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(activity != null && activity instanceof MainActivity) {
                    int topRowVerticalPosition = (latestListView == null || latestListView.getChildCount() == 0) ? 0 : latestListView.getChildAt(0).getTop();
                    ((MainActivity) activity).setSwipeRefreshEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            }
        });
        latestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TopicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConfig.PARCEL_TOPIC_MODEL_KEY, topicModelList.get(position));
                intent.putExtra(AppConfig.TOPIC_MODEL_BUNDLE_KEY, bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (GetDataCallback)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement GetDataCallback");
        }
    }

    private void getData() {
        topicModelList.clear();
//        latestListView.setVisibility(View.GONE);
//        loadingProgressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.API_URL + AppConfig.API_LATEST,
            new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                topicModelList.clear();
                int count = jsonArray.length();
                DebugLog.i("json array's count: " + count);
                for(int i = 0; i < count; ++i) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TopicModel topicModel = new TopicModel();
                        MemberModel memberModel = new MemberModel();
                        NodeModel nodeModel = new NodeModel();
                        topicModel.setId(jsonObject.getInt("id"));
                        topicModel.setTitle(jsonObject.getString("title"));
                        topicModel.setContent(jsonObject.getString("content"));
                        topicModel.setContentRendered(jsonObject.getString("content_rendered"));
                        topicModel.setReplies(jsonObject.getInt("replies"));

                        JSONObject memberObject = jsonObject.getJSONObject("member");
                        memberModel.setId(memberObject.getInt("id"));
                        memberModel.setUserName(memberObject.getString("username"));
                        memberModel.setTagLine(memberObject.getString("tagline"));
                        memberModel.setAvatarMini(memberObject.getString("avatar_mini"));
                        memberModel.setAvatarNormal(memberObject.getString("avatar_normal"));
                        memberModel.setAvatarLarge(memberObject.getString("avatar_large"));
                        topicModel.setMemberModel(memberModel);

                        JSONObject nodeObject = jsonObject.getJSONObject("node");
                        nodeModel.setId(nodeObject.getInt("id"));
                        nodeModel.setName(nodeObject.getString("name"));
                        nodeModel.setTitle(nodeObject.getString("title"));
                        nodeModel.setTitleAlternative(nodeObject.getString("title_alternative"));
                        nodeModel.setUrl(nodeObject.getString("url"));
                        nodeModel.setTopics(nodeObject.getInt("topics"));
                        nodeModel.setAvatarMini(nodeObject.getString("avatar_mini"));
                        nodeModel.setAvatarNormal(nodeObject.getString("avatar_normal"));
                        nodeModel.setAvatarLarge(nodeObject.getString("avatar_large"));
                        topicModel.setNodeModel(nodeModel);

                        topicModel.setCreated(jsonObject.getLong("created"));
                        topicModel.setLastModified(jsonObject.getLong("last_modified"));
                        topicModel.setLastTouched(jsonObject.getLong("last_touched"));

                        topicModelList.add(topicModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    topicViewAdapter.notifyDataSetChanged();
                    callback.updateDataCompleted();
//                    latestListView.setVisibility(View.VISIBLE);
//                    loadingProgressBar.setVisibility(View.GONE);
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DebugLog.e(volleyError.getMessage());
            }
        });
        MySingleton.getInstance(activity).getRequestQueue().add(jsonArrayRequest);
    }

    @Override
    public void updateData() {
        getData();
    }


}
