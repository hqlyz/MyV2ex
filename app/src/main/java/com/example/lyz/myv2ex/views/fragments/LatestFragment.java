package com.example.lyz.myv2ex.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.adapters.TopicViewAdapter;
import com.example.lyz.myv2ex.models.TopicModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {

    private ListView latestListView;
    private ArrayList<TopicModel> topicModelList;

    public LatestFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        Bundle bundle = getArguments();
        if(bundle != null) {
            topicModelList = bundle.getParcelableArrayList(AppConfig.PARCEL_TOPIC_MODEL_KEY);
        }
        latestListView = (ListView)view.findViewById(R.id.latestListView);
        TopicViewAdapter topicViewAdapter = new TopicViewAdapter(getActivity(), R.layout.topic_view, topicModelList);
        latestListView.setAdapter(topicViewAdapter);

        return view;
    }
}
