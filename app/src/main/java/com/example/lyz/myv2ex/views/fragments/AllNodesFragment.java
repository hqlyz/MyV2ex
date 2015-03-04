package com.example.lyz.myv2ex.views.fragments;


import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
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
import com.example.lyz.myv2ex.adapters.NodeViewAdapter;
import com.example.lyz.myv2ex.models.NodeModel;
import com.example.lyz.myv2ex.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.CallableStatement;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllNodesFragment extends Fragment implements FragmentData {
    private ProgressBar nodeLoadingProgressBar;

    private ArrayList<NodeModel> nodeModelArrayList;
    private NodeViewAdapter nodeViewAdapter;
    private Activity activity;
    private GetDataCallback callback;

    public AllNodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_nodes, container, false);
        final GridView allNodesGridView = (GridView)view.findViewById(R.id.all_nodes_grid_view);
        activity = getActivity();
        nodeLoadingProgressBar = (ProgressBar)view.findViewById(R.id.node_loading_progress_bar);
        nodeModelArrayList = new ArrayList<>();
        nodeViewAdapter = new NodeViewAdapter(getActivity(), R.layout.node_view, nodeModelArrayList);
        getAllNodesData();
        allNodesGridView.setAdapter(nodeViewAdapter);
        allNodesGridView.setEmptyView(nodeLoadingProgressBar);
        allNodesGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (allNodesGridView == null || allNodesGridView.getChildCount() == 0) ? 0 : allNodesGridView.getChildAt(0).getTop();
                ((MainActivity)activity).setSwipeRefreshEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
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
            throw new ClassCastException(activity.toString() + " must implement GetDataCallBack");
        }
    }

    private void getAllNodesData() {
        nodeModelArrayList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.API_URL + AppConfig.API_ALL_NODE,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        DebugLog.i("The size of nodes: " + jsonArray.length());
                        for(int i = 0; i < jsonArray.length(); ++i) {
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
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
                        callback.updateDataCompleted();
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

    @Override
    public void updateData() {
        getAllNodesData();
    }
}
