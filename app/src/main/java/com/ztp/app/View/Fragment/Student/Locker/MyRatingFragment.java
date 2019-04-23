package com.ztp.app.View.Fragment.Student.Locker;

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

public class MyRatingFragment extends Fragment {

    Context context;
    ListView listView;
    List<Map<String,Object>> dataList = new ArrayList<>();

    public MyRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rating, container, false);

        listView = view.findViewById(R.id.listView);

        RatingAdapter ratingAdapter = new RatingAdapter(context,getData());
        listView.setAdapter(ratingAdapter);

        return view;
    }

    private List<Map<String,Object>> getData()
    {
        Map<String,Object> map1 = new HashMap<>();
        map1.put("",R.drawable.user);
        map1.put("title","Atlanta Mission");
        map1.put("rating","4");
        dataList.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("",R.drawable.user);
        map2.put("title","Pawnee Parks Department");
        map2.put("rating","3");
        dataList.add(map2);

        return dataList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onResume() {
        super.onResume();

        dataList = new ArrayList<>();
    }
}
