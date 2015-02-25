package com.example.lyz.myv2ex.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyz.myv2ex.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllNodesFragment extends Fragment {


    public AllNodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_nodes, container, false);
    }


}
