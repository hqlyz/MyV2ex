package com.example.lyz.myv2ex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.models.NodeModel;

import java.util.ArrayList;

public class NodeViewAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    private ArrayList<NodeModel> nodeModelArrayList;

    public NodeViewAdapter(Context context, int resourceId, ArrayList<NodeModel> nodeModelArrayList) {
        this.context = context;
        this.resourceId = resourceId;
        this.nodeModelArrayList = nodeModelArrayList;
    }

    @Override
    public int getCount() {
        return nodeModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return nodeModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceId, parent, false);
        }

        TextView nodeTitleTextView = (TextView)convertView.findViewById(R.id.node_title_text_view);
        TextView nodeContentTextView = (TextView)convertView.findViewById(R.id.node_content_text_view);

        NodeModel nodeModel = nodeModelArrayList.get(position);
        nodeTitleTextView.setText(nodeModel.getTitle());
        nodeContentTextView.setText(nodeModel.getHeader().equals("null") ? "NO description" : nodeModel.getHeader());

        return convertView;
    }
}
