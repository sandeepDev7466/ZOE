package com.ztp.app.View.Fragment.CSO.Message;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ztp.app.R;

import java.util.ArrayList;


public class TapGroupsFragment extends Fragment {

    public TapGroupsFragment() {
        // Required empty public constructor
    }

    ListView lv_group_list;
    MessageModel messageModel;
    ArrayList<MessageModel> messageModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_groups, container, false);
        lv_group_list=v.findViewById(R.id.lv_groups_list);

        messageModelArrayList=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            messageModel=new MessageModel();
            messageModel.setName("Lesile Knope");
            messageModel.setDescrip("Hey There! Looking Forward to this weeks events!");
            messageModelArrayList.add(messageModel);

        }

        MessageListAdapter adapter = new MessageListAdapter(messageModelArrayList,getActivity());
        lv_group_list.setAdapter(adapter);

       return v;
    }


}
