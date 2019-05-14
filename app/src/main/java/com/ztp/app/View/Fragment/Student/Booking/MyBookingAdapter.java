package com.ztp.app.View.Fragment.Student.Booking;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ChangeVolunteerStatusViewModel;

import java.util.Date;
import java.util.List;

class MyBookingAdapter extends BaseAdapter {
    private Context context;
    List<VolunteerAllResponse.ResData> dataList;
    MyToast myToast;
    //    int status = 0;
    MyProgressDialog myProgressDialog;
    SharedPref sharedPref;
    ChangeVolunteerStatusViewModel changeVolunteerStatusViewModel;


    public MyBookingAdapter(Context context, List<VolunteerAllResponse.ResData> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        changeVolunteerStatusViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeVolunteerStatusViewModel.class);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public VolunteerAllResponse.ResData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        VolunteerAllResponse.ResData data = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_booking_list, null);
            holder.day = view.findViewById(R.id.day);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.time = view.findViewById(R.id.time);
            holder.month = view.findViewById(R.id.month);
            holder.imv_status = view.findViewById(R.id.imv_status);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.WithdrawnVol))) {
            holder.imv_status.setImageResource(R.drawable.ic_edit);
//            status = 1;
        } else if (data.getMapStatus().equalsIgnoreCase("")) {
            holder.imv_status.setImageResource(R.drawable.ic_edit);
//            status = 1;
        }
        if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol))) {
            holder.imv_status.setImageResource(R.drawable.vol_pending);
//            status = 2;
        } else if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_approved);
//            status = 3;
        } else if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.DeclinedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_declined);
//            status = 4;
        } else if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol))) {
            holder.imv_status.setImageResource(R.drawable.vol_completed);
//            status = 5;
        } else if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO))) {
            holder.imv_status.setImageResource(R.drawable.more_info);
//            status = 6;
        } else if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.RejectedCompleteByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_rejected);
//            status = 7;
        } else if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedVerifiedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_verified);
//            status = 8;

        }
        holder.title.setText(data.getEventHeading());
        holder.description.setText(data.getShiftTask());
        holder.time.setText(data.getShiftStartTime() + " - " + data.getShiftEndTime());


        Date date = Utility.convertStringToDateWithoutTime(data.getShiftDate());

        String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
        String day = (String) DateFormat.format("dd", date); // 20
        String monthString = (String) DateFormat.format("MMM", date); // Jun
        String monthNumber = (String) DateFormat.format("MM", date); // 06
        String year = (String) DateFormat.format("yyyy", date); // 2013

        holder.month.setText(monthString.toUpperCase());
        holder.day.setText(day);

        holder.imv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol)) ||
                        data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.WithdrawnVol)) ||
                        data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO)) ||
                        data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol)) ||
                        data.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO))
                )
                    openDialog(Integer.parseInt(data.getMapStatus()), data.getMapId(), position);
            }
        });

        return view;
    }

    private void openDialog(int status, String MapID, int position) {

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
            changeStatus(Constants.NewRequestVol, MapID, position);
            dialog.dismiss();

        });

        withdraw_layout.setOnClickListener(v -> {
            changeStatus(Constants.WithdrawnVol,MapID, position);
            dialog.dismiss();
        });
        initiateDM_layout.setOnClickListener(v -> {
            dialog.dismiss();
        });
        complete_layout.setOnClickListener(v -> {
            changeStatus(Constants.CompletedByVol,MapID, position);
            dialog.dismiss();
        });
        dialog.show();

    }

    private class Holder {
        MyTextView day, time, description;
        MyBoldTextView month, title;
        ImageView imv_status;
    }

    public void changeStatus(int map_status, String mapID, int position) {
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
                        refreshStatus(position, map_status);
                    } else {
                        new MyToast(context).show(context.getString(R.string.toast_volunteer_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
    private void refreshStatus(int position, int status) {
        VolunteerAllResponse.ResData data = dataList.get(position);
        data.setMapStatus(String.valueOf(status));
        this.notifyDataSetChanged();
    }
}
