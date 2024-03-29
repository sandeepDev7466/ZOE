package com.ztp.app.View.Fragment.CSO.Students;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCSOAllRequest;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CSOAllRequest;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.GetAllNotificationResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CsoAllRequestViewModel;

public class TabStudentsVolunteersFragment extends Fragment {

    Context context;
    ListView listView;
    CsoAllRequestViewModel csoAllRequestViewModel;
    MyTextView noData;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    DBGetCSOAllRequest dbGetCSOAllRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbGetCSOAllRequest = new DBGetCSOAllRequest(context);
        View view = inflater.inflate(R.layout.fragment_volunteers, container, false);
        listView = view.findViewById(R.id.lv_volunteers);
        noData = view.findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        csoAllRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoAllRequestViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            CSOAllRequest csoAllRequest = new CSOAllRequest(sharedPref.getUserId());
            Log.i("", "" + new Gson().toJson(csoAllRequest));
            csoAllRequestViewModel.getCsoAllRequetResponse(context, csoAllRequest).observe((LifecycleOwner) context, new Observer<CSOAllResponse>() {
                @Override
                public void onChanged(@Nullable CSOAllResponse csoAllResponse) {

                    if (csoAllResponse != null) {
                        if (csoAllResponse.getResStatus().equalsIgnoreCase("200")) {
                            if (csoAllResponse.getResData() != null && csoAllResponse.getResData().size() > 0) {
                                Log.i("", "" + new Gson().toJson(csoAllResponse.getResData()));
                                listView.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.INVISIBLE);

                                VolunteerAdapter adapter = new VolunteerAdapter(context, csoAllResponse.getResData());
                                listView.setAdapter(adapter);
                            } else {
                                listView.setVisibility(View.INVISIBLE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } else if (csoAllResponse.getResStatus().equalsIgnoreCase("401")) {
                            listView.setVisibility(View.INVISIBLE);
                            noData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        listView.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                    myProgressDialog.dismiss();
                }
            });
        } else {

            if (dbGetCSOAllRequest.getCSOAllRequestDataList() != null && dbGetCSOAllRequest.getCSOAllRequestDataList().size() > 0) {
                listView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.INVISIBLE);
                VolunteerAdapter adapter = new VolunteerAdapter(context, dbGetCSOAllRequest.getCSOAllRequestDataList());
                listView.setAdapter(adapter);
            } else {
                listView.setVisibility(View.INVISIBLE);
                noData.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
