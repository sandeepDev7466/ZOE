package com.ztp.app.View.Fragment.Volunteer.Event;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBVolunteerGetShiftList;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.ReadNotificationRequest;
import com.ztp.app.Data.Remote.Model.Response.GetAllNotificationResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Data.Remote.Model.Response.ReadNotificationResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetVolunteerShiftListViewModel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShiftListActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lv_shift_list;
    String event_id = "";
    Context context;
    GetVolunteerShiftListViewModel getVolunteerShiftListViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    MyTextView noData;
    SharedPref sharedPref;
    DBVolunteerGetShiftList dbVolunteerGetShiftList;
    GetAllNotificationResponse.ResData responseData;
    List<GetVolunteerShiftListResponse.ShiftData> shiftDataList = new ArrayList<>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_list);
        context = this;
        dbVolunteerGetShiftList = new DBVolunteerGetShiftList(context, event_id);
        sharedPref = SharedPref.getInstance(context);
        getVolunteerShiftListViewModel = ViewModelProviders.of(this).get(GetVolunteerShiftListViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        noData = findViewById(R.id.noData);
        lv_shift_list = findViewById(R.id.lv_shift_list);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hitShiftAPI();
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

                            if (getIntent().getExtras() != null) {
                                responseData = (GetAllNotificationResponse.ResData) getIntent().getExtras().getSerializable("model");
                                if(responseData != null) {
                                    hitReadNotificationAPI(responseData.getNotificationId());
                                    if (responseData.getMapId() != null) {
                                        for (int i = 0; i < getShiftListResponse.getShiftData().size(); i++) {
                                            if (responseData.getMapId().equalsIgnoreCase(getShiftListResponse.getShiftData().get(i).getMapId()))
                                                shiftDataList.add(getShiftListResponse.getShiftData().get(i));
                                        }
                                        ShiftListAdapter adapter = new ShiftListAdapter(context, shiftDataList, event_id);
                                        lv_shift_list.setAdapter(adapter);
                                    } else {
                                        noData.setVisibility(View.VISIBLE);
                                        lv_shift_list.setVisibility(View.INVISIBLE);
                                    }
                                }
                            } else {
                                ShiftListAdapter adapter = new ShiftListAdapter(context, getShiftListResponse.getShiftData(), event_id);
                                lv_shift_list.setAdapter(adapter);
                            }

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

    private void hitReadNotificationAPI(String notification_id)
    {

        if(Utility.isNetworkAvailable(context)) {
            Api.getClient().readNotification(new ReadNotificationRequest(notification_id)).enqueue(new Callback<ReadNotificationResponse>() {
                @Override
                public void onResponse(Call<ReadNotificationResponse> call, Response<ReadNotificationResponse> response) {
                    if(response.isSuccessful())
                    {
                        if(response.body().getResStatus().equalsIgnoreCase("200"))
                        {
                            // DONE
                        }
                        else
                        {
                            myToast.show(getString(R.string.err_server),Toast.LENGTH_SHORT,false);
                        }
                    }
                    else
                    {
                        myToast.show(getString(R.string.err_server),Toast.LENGTH_SHORT,false);
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
