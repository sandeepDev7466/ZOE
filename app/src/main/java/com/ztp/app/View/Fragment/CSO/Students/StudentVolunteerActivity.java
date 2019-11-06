package com.ztp.app.View.Fragment.CSO.Students;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCSOAllRequest;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CSOAllRequest;
import com.ztp.app.Data.Remote.Model.Request.ReadNotificationRequest;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.GetAllNotificationResponse;
import com.ztp.app.Data.Remote.Model.Response.ReadNotificationResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CsoAllRequestViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentVolunteerActivity extends AppCompatActivity implements View.OnClickListener{

    Context context;
    ListView listView;
    CsoAllRequestViewModel csoAllRequestViewModel;
    MyTextView noData;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    DBGetCSOAllRequest dbGetCSOAllRequest;
    GetAllNotificationResponse.ResData responseData;
    List<CSOAllResponse.CSOAllRequest> csoAllRequestList = new ArrayList<>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_volunteer);
        context = this;
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        Utility.makeStatusBarTransparent(context);
        dbGetCSOAllRequest = new DBGetCSOAllRequest(context);
        listView = findViewById(R.id.lv_volunteers);
        noData = findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        csoAllRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoAllRequestViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

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

                                if (getIntent().getExtras() != null) {
                                    responseData = (GetAllNotificationResponse.ResData) getIntent().getExtras().getSerializable("model");

                                    if(responseData!= null) {

                                        hitReadNotificationAPI(responseData.getNotificationId());

                                        if (responseData.getMapId() != null) {
                                            for (int i = 0; i < csoAllResponse.getResData().size(); i++) {
                                                if (responseData.getMapId().equalsIgnoreCase(csoAllResponse.getResData().get(i).getMapId()))
                                                    csoAllRequestList.add(csoAllResponse.getResData().get(i));
                                            }
                                            VolunteerAdapter adapter = new VolunteerAdapter(context, csoAllRequestList);
                                            listView.setAdapter(adapter);
                                        } else {
                                            listView.setVisibility(View.INVISIBLE);
                                            noData.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                else
                                {
                                    VolunteerAdapter adapter = new VolunteerAdapter(context, csoAllResponse.getResData());
                                    listView.setAdapter(adapter);
                                }
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
    }

    private void hitReadNotificationAPI(String notification_id)
    {
        if(Utility.isNetworkAvailable(context)) {
            Api.getClient().readNotification(new ReadNotificationRequest(notification_id)).enqueue(new Callback<ReadNotificationResponse>() {
                @Override
                public void onResponse(Call<ReadNotificationResponse> call, Response<ReadNotificationResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResStatus().equalsIgnoreCase("200")) {
                            // DONE
                        } else {
                            myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                }

                @Override
                public void onFailure(Call<ReadNotificationResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        else
        {
            myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT,false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            onBackPressed();
        }
    }
}
