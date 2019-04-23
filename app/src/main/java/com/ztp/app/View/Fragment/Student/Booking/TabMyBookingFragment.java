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

public class TabMyBookingFragment extends Fragment {

    Context context;
    ListView listView;
    List<Map<String, String>> dataList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_my_booking, container, false);
        listView = view.findViewById(R.id.listView);

        MyBookingAdapter myBookingAdapter = new MyBookingAdapter(context,getDataList());
        listView.setAdapter(myBookingAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    public List<Map<String, String>> getDataList() {

        Map<String, String> map1 = new HashMap<>();
        map1.put("text","Atlanta Mission Food Drive");
        map1.put("date","12");
        map1.put("time","4pm - 8pm");
        map1.put("month","APR");
        dataList.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("text","Atlanta Mission Food Drive");
        map2.put("date","14");
        map2.put("time","4pm - 8pm");
        map2.put("month","APR");
        dataList.add(map2);

        Map<String, String> map3 = new HashMap<>();
        map3.put("text","Atlanta Mission Food Drive");
        map3.put("date","23");
        map3.put("time","4pm - 8pm");
        map3.put("month","APR");
        dataList.add(map3);

        Map<String, String> map4 = new HashMap<>();
        map4.put("text","Atlanta Mission Food Drive");
        map4.put("date","29");
        map4.put("time","4pm - 8pm");
        map4.put("month","APR");
        dataList.add(map4);

        Map<String, String> map5 = new HashMap<>();
        map5.put("text","Atlanta Mission Food Drive");
        map5.put("date","19");
        map5.put("time","4pm - 8pm");
        map5.put("month","APR");
        dataList.add(map5);

        return dataList;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList = new ArrayList<>();
    }
}
