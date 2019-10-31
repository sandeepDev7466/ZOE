package com.ztp.app.View.Fragment.CSO.Event;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetEvents;
import com.ztp.app.Data.Local.Room.Async.Save.DBSaveEvent;
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
    DBGetEvents dbGetEvents;

    public TabMyEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbGetEvents = new DBGetEvents(context);
        return inflater.inflate(R.layout.fragment_tab_my_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        lv_event_list = v.findViewById(R.id.lv_event_list);
        noData = v.findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        getEventsViewModel = ViewModelProviders.of(this).get(GetEventsViewModel.class);
        getData();
    }

    public void getData() {
        if (Utility.isNetworkAvailable(context)) {

            Log.i("",""+ new Gson().toJson(new GetEventsRequest(sharedPref.getUserId())));
            getEventsViewModel.getEventsResponseLiveData(new GetEventsRequest(sharedPref.getUserId())).observe(this, getEventsResponse -> {

                if (getEventsResponse != null) {
                    if (getEventsResponse.getResStatus().equalsIgnoreCase("200")) {
                        Log.i("",""+ new Gson().toJson(getEventsResponse));
                        if (getEventsResponse.getResData() != null) {
                            lv_event_list.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.INVISIBLE);
                            adapter = new EventListAdapter(context, getEventsResponse.getResData(),TabMyEventFragment.this);
                            lv_event_list.setAdapter(adapter);
                            if(getEventsResponse.getResData().size() > 0)
                            new DBSaveEvent(context, getEventsResponse.getResData()).execute(); // Save Offline
                        } else {
                            lv_event_list.setVisibility(View.INVISIBLE);
                            noData.setVisibility(View.VISIBLE);
                            new MyToast(context).show(context.getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                        }
                    } else if (getEventsResponse.getResStatus().equalsIgnoreCase("401")) {
                        lv_event_list.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                        new MyToast(context).show(context.getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

            });
        } else {
            //new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

            if (dbGetEvents.getEventsList() != null && dbGetEvents.getEventsList().size() > 0) {
                lv_event_list.setVisibility(View.VISIBLE);
                noData.setVisibility(View.INVISIBLE);
                adapter = new EventListAdapter(context,dbGetEvents.getEventsList(), TabMyEventFragment.this);
                lv_event_list.setAdapter(adapter);
            } else {
                lv_event_list.setVisibility(View.INVISIBLE);
                noData.setVisibility(View.VISIBLE);
                new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
