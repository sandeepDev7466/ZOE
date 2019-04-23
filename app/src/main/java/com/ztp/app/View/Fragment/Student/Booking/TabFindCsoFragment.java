package com.ztp.app.View.Fragment.Student.Booking;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ztp.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabFindCsoFragment extends Fragment {

    Context context;
    ListView listView;
    List<Map<String, Object>> dataList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_find_cso, container, false);
        listView = view.findViewById(R.id.listView);

        FindCsoAdapter findCsoAdapter = new FindCsoAdapter(context,getDataList());
        listView.setAdapter(findCsoAdapter);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public List<Map<String, Object>> getDataList() {

        Map<String, Object> map1 = new HashMap<>();
        map1.put("text","Atlanta Mission Food Drive");
        map1.put("status","Donations dropped off at the school.");
        map1.put("time",new String[]{"2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)"});
        dataList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("text","Atlanta Mission Food Drive");
        map2.put("status","Donations dropped off at the school.");
        map2.put("time",new String[]{"2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)"});
        dataList.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("text","Atlanta Mission Food Drive");
        map3.put("status","Donations dropped off at the school.");
        map3.put("time",new String[]{"2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)"});
        dataList.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("text","Atlanta Mission Food Drive");
        map4.put("status","Donations dropped off at the school.");
        map4.put("time",new String[]{"2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)",
                "2016-03-05:4:00PM-8PM(6/6)"});
        dataList.add(map4);

        return dataList;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList = new ArrayList<>();
    }
}
