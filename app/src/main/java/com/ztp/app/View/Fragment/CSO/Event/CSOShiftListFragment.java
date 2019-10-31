package com.ztp.app.View.Fragment.CSO.Event;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBCSOGetShiftList;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetCSOEventShiftViewModel;

public class CSOShiftListFragment extends Fragment {

    public CSOShiftListFragment() {
        // Required empty public constructor
    }

    ListView lv_shift_list;
    String event_id = "";
    Context context;
    GetCSOEventShiftViewModel getShiftListViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    MyTextView noData;
    SharedPref sharedPref;
    String startDate,endDate,event_status;
    DBCSOGetShiftList DBCSOGetShiftList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            Bundle b = getArguments();
            event_id = b.getString("event_id");
            startDate = getArguments().getString("event_start_date");
            endDate = getArguments().getString("event_end_date");
            event_status = getArguments().getString("event_status");
        }
        DBCSOGetShiftList = new DBCSOGetShiftList(context,event_id);
        View v = inflater.inflate(R.layout.fragment_shift_list, container, false);
        sharedPref = SharedPref.getInstance(context);
        getShiftListViewModel = ViewModelProviders.of(this).get(GetCSOEventShiftViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        noData = v.findViewById(R.id.noData);
        init(v);
        return v;
    }

    public void init(View v) {
        lv_shift_list = v.findViewById(R.id.lv_shift_list);
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            Log.i("REQUEST",""+new Gson().toJson(new GetCSOShiftRequest(event_id)));
            getShiftListViewModel.getCSOShiftLiveData(context,new GetCSOShiftRequest(event_id)).observe(this, getCSOShiftResponse -> {
                if(getCSOShiftResponse != null) {
                    Log.i("RESPONSE",""+new Gson().toJson(getCSOShiftResponse));
                    if(getCSOShiftResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (getCSOShiftResponse.getResData() != null && getCSOShiftResponse.getResData().size()>0) {

                            noData.setVisibility(View.INVISIBLE);
                            lv_shift_list.setVisibility(View.VISIBLE);
                            CSOShiftListAdapter adapter = new CSOShiftListAdapter(context, getCSOShiftResponse.getResData(), event_id,startDate,endDate,event_status);
                            lv_shift_list.setAdapter(adapter);
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            lv_shift_list.setVisibility(View.INVISIBLE);
                        }
                    }
                    else if(getCSOShiftResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        noData.setVisibility(View.VISIBLE);
                        lv_shift_list.setVisibility(View.INVISIBLE);
                       // myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                    }
                }
                else
                {
                    noData.setVisibility(View.VISIBLE);
                    lv_shift_list.setVisibility(View.INVISIBLE);
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();

            });
        } else {

            //new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

            if (DBCSOGetShiftList.getShiftList() != null && DBCSOGetShiftList.getShiftList().size() > 0) {
                noData.setVisibility(View.INVISIBLE);
                lv_shift_list.setVisibility(View.VISIBLE);
                CSOShiftListAdapter adapter = new CSOShiftListAdapter(context, DBCSOGetShiftList.getShiftList(), event_id,startDate,endDate,event_status);
                lv_shift_list.setAdapter(adapter);
            } else {
                noData.setVisibility(View.VISIBLE);
                lv_shift_list.setVisibility(View.INVISIBLE);
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
