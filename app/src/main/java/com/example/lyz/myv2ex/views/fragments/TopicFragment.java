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

import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.R;

import junit.framework.Test;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends Fragment {

    private ListView topicListView;
    private ProgressBar topicLoadingProgressBar;


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

        topicListView.setEmptyView(topicLoadingProgressBar);
        View headerView = inflater.inflate(R.layout.topic_view, null, false);
        headerView.setClickable(true);
        topicListView.addHeaderView(headerView);

        String[] nameList = {"111", "222", "333", "444", "555", "666", "777", "888", "999", "111", "222", "333", "444", "555", "666", "777", "888", "999"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                nameList
        );
        topicListView.setAdapter(adapter);
        topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.i("list view item clicked");
            }
        });

        return view;
    }
}
