package com.example.lyz.myv2ex.views;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lyz.myv2ex.AppConfig;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.views.fragments.TopicFragment;

public class TopicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        if(savedInstanceState == null) {
            TopicFragment topicFragment = new TopicFragment();
            Bundle bundle = getIntent().getBundleExtra(AppConfig.TOPIC_MODEL_BUNDLE_KEY);
            topicFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.topic_detail_container, topicFragment).commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
    }
}
