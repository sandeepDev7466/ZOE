package com.ztp.app.View.Fragment.Student.Hangout;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ApprovedCSORequest;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ApprovedCSOViewModel;

import java.util.List;

public class Stu_MyCsoFragment extends Fragment {

    Context context;
    ListView listView;
    TextView noData;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    SharedPref sharedPref;
    ApprovedCSOViewModel approvedCSOViewModel;
    List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList;

    public Stu_MyCsoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu__my_cso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        approvedCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(ApprovedCSOViewModel.class);
        getRegisteredCSO();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void getRegisteredCSO() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            ApprovedCSORequest request = new ApprovedCSORequest();
            request.setUserId(sharedPref.getUserId());
            request.setSeachRowNumber("0");
            request.setSearchCity("");
            request.setSearchEventType("");
            request.setSearchPostcode("");
            request.setSearchState("");
            request.setSearchKeyword("");
            request.setSearchPageSize("10");
            request.setSearchOrg("");

            Log.i("", "" + new Gson().toJson(request));

            approvedCSOViewModel.getApprovedCSOResponse(request).observe((LifecycleOwner) context, approvedCSOResponse -> {

                if (approvedCSOResponse != null) {
                    if (approvedCSOResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (approvedCSOResponse.getResData() != null) {

                            approvedCSOList = approvedCSOResponse.getResData();
                            noData.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);

                            StudentMyCSOAdapter studentMyCSOAdapter = new StudentMyCSOAdapter(context,approvedCSOList);
                            listView.setAdapter(studentMyCSOAdapter);

                            /*LinearSnapHelper snapHelper = new LinearSnapHelper();
                            ApprovedCSOAdapter approvedCSOAdapter = new ApprovedCSOAdapter(context, approvedCSOList);
                            recyclerView.setAdapter(approvedCSOAdapter);*/

                        }
                    } else if (approvedCSOResponse.getResStatus().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
