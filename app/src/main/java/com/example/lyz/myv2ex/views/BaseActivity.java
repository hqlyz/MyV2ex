package com.example.lyz.myv2ex.views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.lyz.myv2ex.R;


public class BaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
    }
}
