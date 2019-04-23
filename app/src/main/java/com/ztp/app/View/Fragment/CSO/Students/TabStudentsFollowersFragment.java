package com.ztp.app.View.Fragment.CSO.Students;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ztp.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabStudentsFollowersFragment extends Fragment {

    Context context;
    ListView listView;
    List<FollowerModel> dataList;
    FollowerModel dataModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student, container, false);
        listView = view.findViewById(R.id.lv_student);

        dataList = new ArrayList<>();

        for(int i=0;i<=10;i++)
        {
            dataModel=new FollowerModel();
            dataModel.setName("shivam");
            dataModel.setDate("10-04-19");
            dataModel.setHours("24");
            dataModel.setRank("5");

            dataList.add(dataModel);
        }

        FollowerAdapter adapter = new FollowerAdapter(context,dataList);
        listView.setAdapter(adapter);

        return view;
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
