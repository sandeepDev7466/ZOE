package com.ztp.app.View.Fragment.CSO.Dashboard;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;
import com.ztp.app.Viewmodel.ChangeVolunteerStatusViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShiftListForCalendarAdapter extends BaseAdapter {

    Context context;
    List<HashMap<String, String>> shiftDataList;
    String type;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    ChangeVolunteerStatusViewModel changeVolunteerStatusViewModel;
    Dialog dialog;
    TabMyBookingFragment fragment;

    public ShiftListForCalendarAdapter(Context context, List<HashMap<String, String>> shiftDataList, String type, Dialog dialog) //type = MyBooking,Dashboard
    {
        this.context = context;
        this.shiftDataList = shiftDataList;
        this.type = type;
        this.dialog = dialog;
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        changeVolunteerStatusViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeVolunteerStatusViewModel.class);
    }

    @Override
    public int getCount() {
        return shiftDataList.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return shiftDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        HashMap<String, String> map = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dashboard_cal_item, null);
            holder.date = view.findViewById(R.id.date);
            holder.shiftTask = view.findViewById(R.id.shiftTask);
            holder.month = view.findViewById(R.id.month);
            holder.day = view.findViewById(R.id.day);
            holder.shiftTime = view.findViewById(R.id.shiftTime);
            holder.shiftTaskName = view.findViewById(R.id.shiftTaskName);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Date date = Utility.convertStringToDateWithoutTime(map.get("shift_date"));

        String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
        String day = (String) DateFormat.format("dd", date); // 20
        String monthString = (String) DateFormat.format("MMM", date); // Jun
        String monthNumber = (String) DateFormat.format("MM", date); // 06
        String year = (String) DateFormat.format("yyyy", date); // 2013

        holder.date.setText(day);
        holder.day.setText(dayOfTheWeek);
        holder.month.setText(monthString);

        holder.shiftTask.setText(map.get("event_heading"));
        holder.shiftTime.setText("Time : " + map.get("shift_start_time") + " - " + map.get("shift_end_time"));
        if(map.get("shift_task_name") != null && !map.get("shift_task_name").isEmpty())
        holder.shiftTaskName.setText(map.get("shift_task_name"));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                if (type.equalsIgnoreCase("MyBooking")) {
                    openDialog(map.get("event_id"), position);
                } else {
                    EventDetailFragment eventDetailFragment = new EventDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", map.get("event_id"));
                    bundle.putString("shift_id", map.get("shift_id"));
                    bundle.putBoolean("booking", true);
                    eventDetailFragment.setArguments(bundle);
                    Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");
                }
            }
        });

        return view;
    }

    private void openDialog(String event_id, int pos) {

        Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.booking_action_layout, null);
        dialog.setContentView(view1);
        dialog.show();

        LinearLayout view_event = view1.findViewById(R.id.view_event);
        LinearLayout change_status = view1.findViewById(R.id.change_status);

        HashMap<String, String> map = shiftDataList.get(pos);

        //VolunteerAllResponse.Event data = resDataList.get(pos);
        view_event.setOnClickListener(vs -> {
            dialog.dismiss();
            //String id = map.get(eventDay);

            EventDetailFragment eventDetailFragment = new EventDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id", event_id);
            bundle.putSerializable("shift_id", map.get("shift_id"));
            bundle.putBoolean("booking", true);
            eventDetailFragment.setArguments(bundle);
            Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");
        });


        change_status.setOnClickListener(v1 -> {
            dialog.dismiss();
            openDialog(Integer.parseInt(map.get("map_status")), map.get("map_id"));
        });
    }

    private void openDialog(int status, String MapID) {

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.volunteer_change_status_popup, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        LinearLayout volunteer_layout = view.findViewById(R.id.volunteer_layout);
        LinearLayout withdraw_layout = view.findViewById(R.id.withdraw_layout);
        LinearLayout initiateDM_layout = view.findViewById(R.id.initiateDM_layout);
        LinearLayout complete_layout = view.findViewById(R.id.complete_layout);
        View volunteer_view = view.findViewById(R.id.volunteer_view);
        View withdraw_view = view.findViewById(R.id.withdraw_view);
        View initiate_view = view.findViewById(R.id.initiate_view);

        switch (status) {

            case 10:
                withdraw_layout.setVisibility(View.VISIBLE);
                break;
            case 20:
                withdraw_layout.setVisibility(View.VISIBLE);
                withdraw_view.setVisibility(View.VISIBLE);
                complete_layout.setVisibility(View.VISIBLE);
                break;
            case 30:
                initiateDM_layout.setVisibility(View.VISIBLE);
                break;
            case 50:
                volunteer_layout.setVisibility(View.VISIBLE);
                volunteer_view.setVisibility(View.VISIBLE);
                withdraw_layout.setVisibility(View.VISIBLE);
                withdraw_view.setVisibility(View.VISIBLE);
                complete_layout.setVisibility(View.VISIBLE);
                break;
            case 60:
                initiateDM_layout.setVisibility(View.VISIBLE);
                break;
            case 90:
                volunteer_layout.setVisibility(View.VISIBLE);
                break;
        }

        volunteer_layout.setOnClickListener(v -> {
            changeStatus(Constants.NewRequestVol, MapID);
            dialog.dismiss();

        });

        withdraw_layout.setOnClickListener(v -> {
            changeStatus(Constants.WithdrawnVol, MapID);
            dialog.dismiss();
        });
        initiateDM_layout.setOnClickListener(v -> {
            dialog.dismiss();
        });
        complete_layout.setOnClickListener(v -> {
            changeStatus(Constants.CompletedByVol, MapID);
            dialog.dismiss();
        });
        dialog.show();

    }

    public void changeStatus(int map_status, String mapID) {
        ChangeVolunteerStatusRequest changeVolunteerStatusRequest = new ChangeVolunteerStatusRequest();
        changeVolunteerStatusRequest.setUser_id(sharedPref.getUserId());
        changeVolunteerStatusRequest.setUser_type(sharedPref.getUserType());
        changeVolunteerStatusRequest.setUser_device(Utility.getDeviceId(context));
        changeVolunteerStatusRequest.setMap_id(mapID);
        changeVolunteerStatusRequest.setMap_status(String.valueOf(map_status));

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            changeVolunteerStatusViewModel.changeStatus(changeVolunteerStatusRequest).observe((FragmentActivity) context, changeVolunteerStatusResponse -> {
                if (changeVolunteerStatusResponse != null) {
                    if (changeVolunteerStatusResponse.getResStatus().equalsIgnoreCase("200")) {
                        switch (map_status) {
                            case 10:
                                new MyToast(context).show(context.getString(R.string.toast_volunteer_request_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 40:
                                new MyToast(context).show(context.getString(R.string.toast_volunteer_complete_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 90:
                                new MyToast(context).show(context.getString(R.string.toast_volunteer_withdraw_success), Toast.LENGTH_SHORT, true);
                                break;
                        }
                        //getBooking(false);
                        myProgressDialog.dismiss();
                        if (type.equalsIgnoreCase("MyBooking"))
                            fragment.getBooking(false);
                    } else if (changeVolunteerStatusResponse.getResStatus().equalsIgnoreCase("401")) {
                        myProgressDialog.dismiss();
                        new MyToast(context).show(context.getString(R.string.toast_volunteer_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myProgressDialog.dismiss();
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private class Holder {
        MyBoldTextView date, shiftTask;
        MyTextView month, day, shiftTime,shiftTaskName;
    }
}
