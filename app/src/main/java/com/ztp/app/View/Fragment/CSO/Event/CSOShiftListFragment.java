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
import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetCSOEventShiftViewModel;
import com.ztp.app.Viewmodel.GetVolunteerShiftListViewModel;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shift_list, container, false);
        sharedPref = SharedPref.getInstance(context);
        getShiftListViewModel = ViewModelProviders.of(this).get(GetCSOEventShiftViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        noData = v.findViewById(R.id.noData);
        if (getArguments() != null) {
            Bundle b = getArguments();
            event_id = b.getString("event_id");
        }

        init(v);
        return v;
    }

    public void init(View v) {
        myProgressDialog.show(getString(R.string.please_wait));
        lv_shift_list = v.findViewById(R.id.lv_shift_list);
        if (Utility.isNetworkAvailable(context)) {
            getShiftListViewModel.getCSOShiftLiveData(new GetCSOShiftRequest(event_id)).observe(this, getCSOShiftResponse -> {
                if (getCSOShiftResponse != null && getCSOShiftResponse.getResData() != null) {

                    noData.setVisibility(View.INVISIBLE);
                    lv_shift_list.setVisibility(View.VISIBLE);
                    CSOShiftListAdapter adapter = new CSOShiftListAdapter(context, getCSOShiftResponse.getResData(), event_id);
                    lv_shift_list.setAdapter(adapter);
                } else {
                    noData.setVisibility(View.VISIBLE);
                    lv_shift_list.setVisibility(View.INVISIBLE);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


}
