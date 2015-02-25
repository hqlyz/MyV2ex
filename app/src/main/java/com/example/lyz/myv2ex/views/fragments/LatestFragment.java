package com.example.lyz.myv2ex.views.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.adapters.TopicViewAdapter;
import com.example.lyz.myv2ex.models.TopicModel;
import com.example.lyz.myv2ex.views.MainActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {

    private ListView latestListView;
    private ArrayList<TopicModel> topicModelList;
    private Activity activity;

    public LatestFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        activity = getActivity();
        Bundle bundle = getArguments();
        if(bundle != null) {
            topicModelList = bundle.getParcelableArrayList(AppConfig.PARCEL_TOPIC_MODEL_KEY);
        }
        latestListView = (ListView)view.findViewById(R.id.latestListView);
        TopicViewAdapter topicViewAdapter = new TopicViewAdapter(getActivity(), R.layout.topic_view, topicModelList);
        latestListView.setAdapter(topicViewAdapter);
        latestListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(activity == null)
                    return;
                if(activity instanceof MainActivity) {
                    int topRowVerticalPosition = (latestListView == null || latestListView.getChildCount() == 0) ? 0 : latestListView.getChildAt(0).getTop();
                    ((MainActivity) activity).setSwipeRefreshEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            }
        });

        return view;
    }
}
