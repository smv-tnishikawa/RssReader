package com.t_nishikawa.internrssreaderapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RssListAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initListView();

        requestRss();
    }

    private void initListView() {
        final ListView rssListView = (ListView) findViewById(R.id.rss_list_view);

        final ArrayList<RssListItem> rssListItems = new ArrayList<>();
        adapter = new RssListAdapter(this, R.layout.rss_list_item, rssListItems);
        rssListView.setAdapter(adapter);
    }

    private void requestRss() {
        final AsyncWebAccess asyncWebAccess = new AsyncWebAccess() {
            @Override
            protected void onPostExecute(String result) {
                RssParser rssParser = new RssParser();
                List<RssListItem> rssListItems = rssParser.parse(result);
                adapter.update(rssListItems);
            }
        };
        String[] params = {"http://feeds.feedburner.com/hatena/b/hotentry"};
        asyncWebAccess.execute(params);
    }
}