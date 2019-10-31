package com.ztp.app.View.Fragment.Volunteer.Hangout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.ztp.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimelineFragment extends Fragment {

    Context context;
    ListView listView;
    List<Map<String, Object>> dataList = new ArrayList<>();
    ImageView edit;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        edit = view.findViewById(R.id.edit);
        listView = view.findViewById(R.id.listView);

        TimelineAdapter timelineAdapter = new TimelineAdapter(context, getData());
        listView.setAdapter(timelineAdapter);

        return view;
    }

    private List<Map<String, Object>> getData() {

        Map<String, Object> map1 = new HashMap<>();
        map1.put("user_image",R.drawable.user);
        map1.put("name", "Ron Swanson");
        map1.put("text", "Hours log for october");
        map1.put("msg_image",R.drawable.scene_2);
        dataList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("user_image",R.drawable.user);
        map2.put("name", "Ron Swanson");
        map2.put("text", "Hours log for october");
        map2.put("msg_image",R.drawable.scene_2);
        dataList.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("user_image",R.drawable.user);
        map3.put("name", "Ron Swanson");
        map3.put("text", "Hours log for october");
        map3.put("msg_image",R.drawable.scene_2);
        dataList.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("user_image",R.drawable.user);
        map4.put("name", "Ron Swanson");
        map4.put("text", "Hours log for october");
        map4.put("msg_image",R.drawable.scene_2);
        dataList.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("user_image",R.drawable.user);
        map5.put("name", "Ron Swanson");
        map5.put("text", "Hours log for october");
        map5.put("msg_image",R.drawable.scene_2);
        dataList.add(map5);

        return dataList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
