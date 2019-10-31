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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.StudentPendingRequest;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.StudentPendingRequestViewModel;

public class ReceivedRequestFragment extends Fragment {

    Context context;
    ListView listView;
    TextView noData;
    ProgressBar progress;
    SharedPref sharedPref;
    MyToast myToast;
    StudentPendingRequestViewModel studentPendingRequestViewModel;

    public ReceivedRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        progress = view.findViewById(R.id.progress);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        studentPendingRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(StudentPendingRequestViewModel.class);
        progress.setVisibility(View.VISIBLE);
        StudentPendingRequest studentPendingRequest = new StudentPendingRequest();
        studentPendingRequest.setUserDevice(Utility.getDeviceId(context));
        studentPendingRequest.setUserId(sharedPref.getUserId());
        studentPendingRequest.setUserType(sharedPref.getUserType());
        Log.i("","" +new Gson().toJson(studentPendingRequest));
        studentPendingRequestViewModel.getStudentPendingRequestResponse(studentPendingRequest).observe((LifecycleOwner) context, studentPendingResponse -> {
            if(studentPendingResponse != null)
            {
                if(studentPendingResponse.getResStatus().equalsIgnoreCase("200"))
                {
                    if(studentPendingResponse.getResData() !=null && studentPendingResponse.getResData().size()>0)
                    {
                        listView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                        PendingRequestAdapter pendingRequestAdapter = new PendingRequestAdapter(context,studentPendingResponse.getResData());
                        listView.setAdapter(pendingRequestAdapter);
                    }
                    else
                    {
                        listView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    listView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                listView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT,false);
            }
            progress.setVisibility(View.GONE);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
