package com.ztp.app.View.Fragment.CSO.Event;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetEventsRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;
import com.ztp.app.Viewmodel.GetEventsViewModel;

import java.util.ArrayList;

public class TabMyEventFragment extends Fragment {

    ListView lv_event_list;
    Context context;
    GetEventsViewModel getEventsViewModel;
    SharedPref sharedPref;
    MyTextView noData;

    public TabMyEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_my_event, container, false);
        lv_event_list = v.findViewById(R.id.lv_event_list);
        noData = v.findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        getEventsViewModel = ViewModelProviders.of(this).get(GetEventsViewModel.class);

        getEventsViewModel.getEventsResponseLiveData(new GetEventsRequest(sharedPref.getUserId())).observe(this, getEventsResponse -> {
            if(getEventsResponse!=null) {
                lv_event_list.setVisibility(View.VISIBLE);
                EventListAdapter adapter = new EventListAdapter(context, getEventsResponse.getEventData());
                lv_event_list.setAdapter(adapter);
            }
            else
            {
                lv_event_list.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
