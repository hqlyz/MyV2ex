package com.example.lyz.myv2ex.views;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lyz.myv2ex.DebugLog;
import com.example.lyz.myv2ex.R;
import com.example.lyz.myv2ex.models.TopicModel;
import com.example.lyz.myv2ex.views.fragments.AllNodesFragment;
import com.example.lyz.myv2ex.views.fragments.LatestFragment;
import com.example.lyz.myv2ex.views.fragments.SettingFragment;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements LatestFragment.Callback {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String[] fragmentTitles;
    private Resources resources;
    private FragmentManager fragmentManager;
    private ActionBar.TabListener tabListener;
    private ArrayList<TopicModel> topicModelList;
    private LatestFragment latestFragment;
    private AllNodesFragment allNodesFragment;
    private SettingFragment settingFragment;
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVars();
        initViews();
    }

    private void initVars() {
        resources = getResources();
        actionBar = getSupportActionBar();
        fragmentManager = getSupportFragmentManager();
        fragmentTitles = resources.getStringArray(R.array.fragment_titles);
        topicModelList = new ArrayList<>();
    }

    private void initViews() {
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setIcon(R.mipmap.ic_launcher);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentManager);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
                currentPage = tab.getPosition();
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText(fragmentTitles[0]).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(fragmentTitles[1]).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(fragmentTitles[2]).setTabListener(tabListener));
        swipeRefreshLayout.setColorSchemeColors(
                resources.getColor(R.color.refresh_progress_color_1),
                resources.getColor(R.color.refresh_progress_color_2),
                resources.getColor(R.color.refresh_progress_color_3)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected void onPreExecute() {
//                        swipeRefreshLayout.setRefreshing(true);
//
//                    }
//
//                    @Override
//                    protected Void doInBackground(Void... params) {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        swipeRefreshLayout.setRefreshing(false);
//
//                    }
//                }.execute();
                switch(currentPage) {
                    case 0:
                        if(latestFragment != null) {
                            latestFragment.updateData();
                        }
                        break;
                    case 1:
                        DebugLog.i("Page two refreshed.");
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSwipeRefreshEnabled(boolean enabled) {
        swipeRefreshLayout.setEnabled(enabled);
    }

    @Override
    public void updateDataCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(latestFragment == null) {
                        latestFragment = new LatestFragment();
                    }
                    return latestFragment;
                case 1:
                    return new AllNodesFragment();
                case 2:
                    return new SettingFragment();
                default:
                    if(latestFragment == null) {
                        latestFragment = new LatestFragment();
                    }
                    return latestFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles[position];
        }
    }
}
