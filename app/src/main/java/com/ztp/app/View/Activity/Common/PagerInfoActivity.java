package com.ztp.app.View.Activity.Common;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.eftimoff.viewpagertransformers.TabletTransformer;
import com.ztp.app.R;
import com.ztp.app.View.Pager.ViewInfoPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PagerInfoActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<HashMap<String, Object>> data = new ArrayList<>();
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_info);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        count = getData().size();
        ViewInfoPager viewInfoPager = new ViewInfoPager(getSupportFragmentManager(), count, getData());
        viewPager.setAdapter(viewInfoPager);
        viewPager.setPageTransformer(true, new TabletTransformer());
        tabLayout.setupWithViewPager(viewPager, true);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private List<HashMap<String, Object>> getData() {

        data.clear();

        HashMap<String, Object> map1 = new HashMap<>();
        //map1.put("image", R.drawable.clock);
        map1.put("image", R.drawable.track_hours);
        map1.put("title", "TRACK YOUR HOURS");
        map1.put("color", "#59ED96");
        map1.put("description", "One centralized location to track your community service hours and manage your progress to put target GPA. ");
        data.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        //map2.put("image", R.drawable.document);
        map2.put("image", R.drawable.documents);
        map2.put("title", "A PLACE FOR DOCUMENTS");
        map2.put("color", "#3FDDD0");
        map2.put("description", "Load and store files to keep you organized and on track. Managing your hours quick and easy. ");
        data.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        //map3.put("image", R.drawable.event);
        map3.put("image", R.drawable.cso_events);
        map3.put("title", "BOOK YOUR CSO EVENTS");
        map3.put("color", "#0F96F9");
        map3.put("description", "It's so easy to manage your volunteer Calendar and connect with community service organizations. ");
        data.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        //map4.put("image", R.drawable.hangout);
        map4.put("image", R.drawable.hangout);
        map4.put("title", "SOMEWHERE TO HANGOUT");
        map4.put("color", "#63F5B7");
        map4.put("description", "Keep the conversation going! Chat with your friends and keep in the loop with the CSO announcements.");
        data.add(map4);

        HashMap<String, Object> map5 = new HashMap<>();
        //map5.put("image", R.drawable.connected);
        map5.put("image", R.drawable.chat_connected);
        map5.put("title", "YOU'RE CONNECTED");
        map5.put("color", "#3FDDD0");
        map5.put("description", "Have direct access to school and CSO administrators. Ask questions,get feedback and keep up to date. ");
        data.add(map5);

        return data;
    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() > 0) {

            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        } else {
            super.onBackPressed();
        }

    }
}
