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

public class TabStudentsVolunteersFragment extends Fragment {

    Context context;
    ListView listView;

    List<VolunteerModel> dataList;
    VolunteerModel volunteerModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_volunteers, container, false);
        listView = view.findViewById(R.id.lv_volunteers);


        dataList = new ArrayList<>();

        for(int i=0;i<=10;i++)
        {
            volunteerModel=new VolunteerModel();
            volunteerModel.setName("Gospel in the park: Helping the Homeless night");
            volunteerModel.setDescrip("Music,food and clothing,shoes,blanket to be distributed at Williams park St petersburg-focus and talking with homeless");
            volunteerModel.setDate("10-04-19");
            volunteerModel.setTime("24:15");
            volunteerModel.setStudent_name("Abhishek Mishra");
            volunteerModel.setRank("5");
            volunteerModel.setChanges_status("yes");

            dataList.add(volunteerModel);
        }


        VolunteerAdapter adapter = new VolunteerAdapter(context,dataList);
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
