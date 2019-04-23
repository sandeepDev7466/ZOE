package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ztp.app.R;

import java.util.List;
import java.util.Map;

public class PotentialFriendAndPotentialCSOFragment extends Fragment {

    Context context;
    List<Map<String, Object>> dataList;
    ListView listView;

    public PotentialFriendAndPotentialCSOFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_potential_friend, container, false);
        listView = view.findViewById(R.id.listView);

        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            dataList = (List<Map<String, Object>>) bundle.getSerializable("potentialFriendData");

            PotentialFriendAndPotentialCSOAdapter potentialFriendAndPotentialCSOAdapter = new PotentialFriendAndPotentialCSOAdapter(context,dataList);
            listView.setAdapter(potentialFriendAndPotentialCSOAdapter);


        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
