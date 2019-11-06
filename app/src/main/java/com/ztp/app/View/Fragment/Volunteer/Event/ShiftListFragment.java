package com.ztp.app.View.Fragment.Volunteer.Event;

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
import com.ztp.app.Data.Local.Room.Async.Get.DBVolunteerGetShiftList;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetVolunteerShiftListViewModel;

public class ShiftListFragment extends Fragment {

    ListView lv_shift_list;
    String event_id = "";
    Context context;
    GetVolunteerShiftListViewModel getVolunteerShiftListViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    MyTextView noData;
    SharedPref sharedPref;
    DBVolunteerGetShiftList dbVolunteerGetShiftList;

    public ShiftListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            Bundle b = getArguments();
            event_id = b.getString("event_id");
        }
        dbVolunteerGetShiftList = new DBVolunteerGetShiftList(context, event_id);
        View v = inflater.inflate(R.layout.fragment_shift_list, container, false);
        sharedPref = SharedPref.getInstance(context);
        getVolunteerShiftListViewModel = ViewModelProviders.of(this).get(GetVolunteerShiftListViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        noData = v.findViewById(R.id.noData);
        lv_shift_list = v.findViewById(R.id.lv_shift_list);
        hitShiftAPI();
        return v;
    }

    public void hitShiftAPI() {

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            Log.i("REQUEST", "" + new Gson().toJson(new GetSearchShiftListRequest(event_id, sharedPref.getUserId())));
            getVolunteerShiftListViewModel.getSearchShiftResponseLiveData(context, new GetSearchShiftListRequest(event_id, sharedPref.getUserId())).observe(this, getShiftListResponse -> {

                if (getShiftListResponse != null) {
                    Log.i("RESPONSE", "" + new Gson().toJson(getShiftListResponse));
                    if (getShiftListResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (getShiftListResponse.getShiftData() != null && getShiftListResponse.getShiftData().size() > 0) {

                            noData.setVisibility(View.INVISIBLE);
                            lv_shift_list.setVisibility(View.VISIBLE);
                            ShiftListAdapter adapter = new ShiftListAdapter(context, getShiftListResponse.getShiftData(), event_id);
                            lv_shift_list.setAdapter(adapter);
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            lv_shift_list.setVisibility(View.INVISIBLE);
                        }
                    } else if (getShiftListResponse.getResStatus().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                        lv_shift_list.setVisibility(View.INVISIBLE);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

                myProgressDialog.dismiss();

            });
        } else {

            if (dbVolunteerGetShiftList.getShiftList() != null && dbVolunteerGetShiftList.getShiftList().size() > 0) {
                noData.setVisibility(View.INVISIBLE);
                lv_shift_list.setVisibility(View.VISIBLE);
                ShiftListAdapter adapter = new ShiftListAdapter(context, dbVolunteerGetShiftList.getShiftList(), event_id);
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
