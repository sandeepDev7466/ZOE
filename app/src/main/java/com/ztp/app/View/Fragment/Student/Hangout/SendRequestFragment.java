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
import com.ztp.app.Data.Remote.Model.Request.StudentSendRequest;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.StudentSendRequestViewModel;

public class SendRequestFragment extends Fragment {

    Context context;
    ListView listView;
    TextView noData;
    StudentSendRequestViewModel studentSendRequestViewModel;
    SharedPref sharedPref;
    MyToast myToast;
    ProgressBar progress;

    public SendRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = SharedPref.getInstance(context);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        myToast = new MyToast(context);
        progress = view.findViewById(R.id.progress);
        studentSendRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(StudentSendRequestViewModel.class);
        progress.setVisibility(View.VISIBLE);
        StudentSendRequest studentSendRequest = new StudentSendRequest();
        studentSendRequest.setUserDevice(Utility.getDeviceId(context));
        studentSendRequest.setUserId(sharedPref.getUserId());
        studentSendRequest.setUserType(sharedPref.getUserType());
        Log.i("","" +new Gson().toJson(studentSendRequest));
        studentSendRequestViewModel.getStudentSendRequestResponse(studentSendRequest).observe((LifecycleOwner) context, studentSendResponse -> {
            if(studentSendResponse != null)
            {
                if(studentSendResponse.getResStatus().equalsIgnoreCase("200"))
                {
                    if(studentSendResponse.getResData() !=null && studentSendResponse.getResData().size()>0)
                    {
                        listView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                        SendRequestAdapter sendRequestAdapter = new SendRequestAdapter(context,studentSendResponse.getResData());
                        listView.setAdapter(sendRequestAdapter);
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
