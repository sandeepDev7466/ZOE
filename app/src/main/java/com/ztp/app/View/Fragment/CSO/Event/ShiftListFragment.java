package com.ztp.app.View.Fragment.CSO.Event;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ztp.app.Data.Remote.Model.Request.GetShiftListRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.R;
import com.ztp.app.Viewmodel.GetShiftListViewModel;

import java.util.ArrayList;

public class ShiftListFragment extends Fragment {

    public ShiftListFragment() {
        // Required empty public constructor
    }

    ListView lv_shift_list;
    String event_id = "";
    Context context;

    ArrayList<String> siftList = new ArrayList<>();
    GetShiftListViewModel getShiftListViewModel;
    MyProgressDialog myProgressDialog;
    GetEventsResponse.EventData eventData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shift_list, container, false);
        getShiftListViewModel = ViewModelProviders.of(this).get(GetShiftListViewModel.class);
        myProgressDialog = new MyProgressDialog(context);

        if(getArguments()!=null) {
            Bundle b = getArguments();
            event_id =b.getString("event_id");
        }

        init(v);
        return v;
    }

    public void init(View v) {
        myProgressDialog.show("Please wait...");
        lv_shift_list = v.findViewById(R.id.lv_shift_list);
        getShiftListViewModel.getShiftResponseLiveData(new GetShiftListRequest(event_id)).observe(this, getShiftListResponse -> {
            if (getShiftListResponse.getShiftData()!=null) {

                ShiftListAdapter adapter = new ShiftListAdapter(context, getShiftListResponse.getShiftData());
                lv_shift_list.setAdapter(adapter);
            }
            else
            {
                //no data found
            }
            myProgressDialog.dismiss();

        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


}
