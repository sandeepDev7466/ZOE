package com.ztp.app.View.Fragment.CSO.Event;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetEventsRequest;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetEventsViewModel;

public class TabMyEventFragment extends Fragment {

    ListView lv_event_list;
    Context context;
    GetEventsViewModel getEventsViewModel;
    SharedPref sharedPref;
    MyTextView noData;
    EventListAdapter adapter;

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
        getData();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void getData() {
        if(Utility.isNetworkAvailable(context)) {
            getEventsViewModel.getEventsResponseLiveData(new GetEventsRequest(sharedPref.getUserId())).observe(this, getEventsResponse -> {
                if (getEventsResponse != null && getEventsResponse.getEventData() != null) {
                    lv_event_list.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.INVISIBLE);
                    adapter = new EventListAdapter(context, getEventsResponse.getEventData());
                    lv_event_list.setAdapter(adapter);
                } else {
                    lv_event_list.setVisibility(View.INVISIBLE);
                    noData.setVisibility(View.VISIBLE);
                }
            });
        }
        else
        {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
