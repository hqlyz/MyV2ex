package com.example.lyz.myv2ex.views.fragments;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.MySingleton;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.adapters.NodeViewAdapter;
import com.example.lyz.myv2ex.models.NodeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllNodesFragment extends Fragment {
    private ProgressBar nodeLoadingProgressBar;

    private ArrayList<NodeModel> nodeModelArrayList;
    private NodeViewAdapter nodeViewAdapter;

    public AllNodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_nodes, container, false);
        GridView allNodesGridView = (GridView)view.findViewById(R.id.all_nodes_grid_view);
        nodeLoadingProgressBar = (ProgressBar)view.findViewById(R.id.node_loading_progress_bar);
        nodeModelArrayList = new ArrayList<>();
        nodeViewAdapter = new NodeViewAdapter(getActivity(), R.layout.node_view, nodeModelArrayList);
        getAllNodesData();
        allNodesGridView.setAdapter(nodeViewAdapter);
        allNodesGridView.setEmptyView(nodeLoadingProgressBar);
        return view;
    }

    private void getAllNodesData() {
        nodeModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.API_URL + AppConfig.API_ALL_NODE,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i = 0; i < jsonArray.length(); ++i) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                NodeModel nodeModel = new NodeModel();
                                nodeModel.setId(jsonObject.getInt("id"));
                                nodeModel.setName(jsonObject.getString("name"));
                                nodeModel.setTitle(jsonObject.getString("title"));
                                nodeModel.setTitleAlternative(jsonObject.getString("title_alternative"));
                                nodeModel.setTopics(jsonObject.getInt("topics"));
                                nodeModel.setHeader(jsonObject.getString("header"));
                                nodeModel.setFooter(jsonObject.getString("footer"));
                                nodeModel.setCreated(jsonObject.getLong("created"));

                                nodeModelArrayList.add(nodeModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        nodeViewAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        DebugLog.e(volleyError.getMessage());
                    }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }
}
