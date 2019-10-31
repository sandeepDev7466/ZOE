package com.ztp.app.View.Activity.Common;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetTimeZone;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Response.GetUserTimezoneResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateUserTimezoneResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetUserTimezoneViewModel;
import com.ztp.app.Viewmodel.TimeZoneViewModel;
import com.ztp.app.Viewmodel.UpdateUserTimezoneViewModel;

import java.util.ArrayList;
import java.util.List;

public class TimezoneActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ImageView back;
    Spinner sp_timezone,sp_daylight;
    String daylight_id,timezone_id;
    List<TimeZoneResponse.Timezone> timezoneListData = new ArrayList<>();
    TimeZoneViewModel timeZoneViewModel;
    List<String> timezoneList = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    GetUserTimezoneViewModel getUserTimezoneViewModel;
    UpdateUserTimezoneViewModel updateUserTimezoneViewModel;
    SharedPref sharedPref;
    MyButton submit;
    DBGetTimeZone dbGetTimeZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timezone);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        dbGetTimeZone = new DBGetTimeZone(context);
        sharedPref = SharedPref.getInstance(context);
        back = findViewById(R.id.back);
        sp_timezone = findViewById(R.id.sp_timezone);
        sp_daylight = findViewById(R.id.sp_daylight);
        submit = findViewById(R.id.submit);
        myProgressDialog = new MyProgressDialog(context);
        updateUserTimezoneViewModel = ViewModelProviders.of((FragmentActivity) context).get(UpdateUserTimezoneViewModel.class);
        getUserTimezoneViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetUserTimezoneViewModel.class);
        myToast = new MyToast(context);
        timeZoneViewModel = ViewModelProviders.of(this).get(TimeZoneViewModel.class);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        setDaylightSpinner();
        getTimeZonelist();


        sp_daylight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    daylight_id = getResources().getStringArray(R.array.daylight)[position];
                } else if (position == 0) {
                    daylight_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });

        sp_timezone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    timezone_id = timezoneListData.get(position - 1).getTimezoneCode();
                } else if (position == 0) {
                    timezone_id = "";
                }
                sharedPref.setTimezone(timezone_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private void setDaylightSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.daylight));
        sp_daylight.setAdapter(adapter);
    }

    public void getTimeZonelist() {
        timezoneList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            timeZoneViewModel.getTimezoneResponse(context).observe((LifecycleOwner) context, timeZoneResponse -> {

                if (timeZoneResponse != null) {
                    if (timeZoneResponse.getResStatus().equalsIgnoreCase("200")) {
                        timezoneListData = timeZoneResponse.getResData();
                        if (timezoneListData.size() > 0) {
                            for (int i = 0; i < timezoneListData.size(); i++) {
                                timezoneList.add(timezoneListData.get(i).getTimezoneName()+" ["+timezoneListData.get(i).getTimezoneCode()+"]");
                            }
                            timezoneList.add(0, getString(R.string.select_time_zone));
                            setTimezoneSpinner(timezoneList);
                        }
                    } else if (timeZoneResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_timezone_found), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }
                } else {

                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
               // myProgressDialog.dismiss();
            });
        } else {

            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);


            timezoneListData = dbGetTimeZone.getTimeZoneList();
            if (timezoneListData.size() > 0) {
                for (int i = 0; i < timezoneListData.size(); i++) {
                    timezoneList.add(timezoneListData.get(i).getTimezoneName());
                }
                timezoneList.add(0, getString(R.string.select_time_zone));
                setTimezoneSpinner(timezoneList);
            }
        }
    }


    private void setTimezoneSpinner(List<String> timezoneList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, timezoneList);
        sp_timezone.setAdapter(adapter);
        getUserTimeZone();
    }

    private void getUserTimeZone()
    {
        if(Utility.isNetworkAvailable(context))
        {
           // myProgressDialog.show(getString(R.string.please_wait));
            getUserTimezoneViewModel.getUserTimezoneResponse(new GetUserTimezoneRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, getUserTimezoneResponse -> {
                if(getUserTimezoneResponse != null)
                {
                    if(getUserTimezoneResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        if(getUserTimezoneResponse.getResData().get(0).getLoginDaylight().equalsIgnoreCase("0"))
                        {
                            sp_daylight.setSelection(2,true);
                        }
                        else if(getUserTimezoneResponse.getResData().get(0).getLoginDaylight().equalsIgnoreCase("1"))
                        {
                             sp_daylight.setSelection(1,true);
                        }
                        else
                        {
                            sp_daylight.setSelection(0,true);
                        }


                        String timezone = getUserTimezoneResponse.getResData().get(0).getLoginTimezone();
                        sp_timezone.setSelection(getTimezonePosition(timezone),true);


                    }
                    else if(getUserTimezoneResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show(getString(R.string.err_no_data_found),Toast.LENGTH_SHORT,false);
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
                myProgressDialog.dismiss();
            });
        }
        else
        {
            myToast.show(getString(R.string.no_internet_connection),Toast.LENGTH_SHORT,false);
        }
    }

    public int getTimezonePosition(String timezone_code) {
        int position = -1;
        for (int i = 0; i < timezoneListData.size(); i++) {
            if (timezoneListData.get(i).getTimezoneCode().equalsIgnoreCase(timezone_code)) {
                position = i + 1;
                break;
            }
        }
        return position;
    }

    private void submitClicked()
    {
        if(Utility.isNetworkAvailable(context))
        {
            myProgressDialog.show(getString(R.string.please_wait));
            UpdateUserTimezoneRequest updateUserTimezoneRequest = new UpdateUserTimezoneRequest();
            updateUserTimezoneRequest.setUserId(sharedPref.getUserId());
            if(daylight_id.equalsIgnoreCase("ON"))
                updateUserTimezoneRequest.setLoginDaylight("1");
            else
                updateUserTimezoneRequest.setLoginDaylight("0");
            updateUserTimezoneRequest.setLoginTimezone(timezone_id);

            Log.i("",""+ new Gson().toJson(updateUserTimezoneRequest));

            updateUserTimezoneViewModel.updateUserTimezoneResponse(updateUserTimezoneRequest).observe((LifecycleOwner) context, updateUserTimezoneResponse -> {
                if(updateUserTimezoneResponse != null)
                {
                    if(updateUserTimezoneResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        myToast.show(getString(R.string.timezone_updated),Toast.LENGTH_SHORT,true);
                        finish();
                    }
                    else if(updateUserTimezoneResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show(getString(R.string.err_timezone_update_failed),Toast.LENGTH_SHORT,false);
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
            });
            myProgressDialog.dismiss();
        }
        else
        {
            myToast.show(getString(R.string.no_internet_connection),Toast.LENGTH_SHORT,false);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                if(timezone_id == null || timezone_id.isEmpty())
                    myToast.show(getString(R.string.select_time_zone),Toast.LENGTH_SHORT,false);
                if(daylight_id == null || daylight_id.isEmpty())
                    myToast.show(getString(R.string.select_daylight),Toast.LENGTH_SHORT,false);
                submitClicked();
                break;
        }
    }
}
