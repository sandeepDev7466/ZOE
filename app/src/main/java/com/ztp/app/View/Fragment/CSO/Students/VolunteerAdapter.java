package com.ztp.app.View.Fragment.CSO.Students;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.willy.ratingbar.ScaleRatingBar;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CSOAllRequest;
import com.ztp.app.Data.Remote.Model.Request.ChangeStatusByCSORequest;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ChangeStatusByCSOViewModel;
import com.ztp.app.Viewmodel.ChangeVolunteerStatusViewModel;

import java.util.Date;
import java.util.List;

class VolunteerAdapter extends BaseAdapter {
    private Context context;
    List<CSOAllResponse.ResData> dataList;
    MyProgressDialog myProgressDialog;
    SharedPref sharedPref;
    MyToast myToast;
    ChangeStatusByCSOViewModel changeStatusByCSOViewModel;

    public VolunteerAdapter(Context context, List<CSOAllResponse.ResData> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        changeStatusByCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeStatusByCSOViewModel.class);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CSOAllResponse.ResData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        CSOAllResponse.ResData dataModel = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_volunteers_row, null);
            holder.date = view.findViewById(R.id.tv_date);
            holder.time = view.findViewById(R.id.tv_time);
            holder.name = view.findViewById(R.id.tv_name);
            holder.tv_heading = view.findViewById(R.id.tv_heading);
            holder.tv_desc = view.findViewById(R.id.tv_description);
            holder.rb_rank = view.findViewById(R.id.rb_rank);
            holder.imv_status = view.findViewById(R.id.imv_status);
            holder.tv_event_name = view.findViewById(R.id.tv_event_name);
            holder.tv_shift_task = view.findViewById(R.id.tv_shift_task);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_heading.setText(dataModel.getUser_f_name() + " " + dataModel.getUser_l_name());


        //Date d = Utility.convertStringToDateWithoutTime(dataModel.getDate());
        holder.date.setText(dataModel.getShiftDate());


        holder.time.setText(dataModel.getShiftStartTime() + " - " + dataModel.getShiftEndTime());
        holder.name.setText(dataModel.getUser_email());
        holder.tv_desc.setText(dataModel.getUser_email());
        holder.tv_event_name.setText(dataModel.getEventHeading());
        holder.tv_shift_task.setText(dataModel.getShiftTask());
        holder.rb_rank.setRating(Float.parseFloat(dataModel.getShiftRank()));

        if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.WithdrawnVol))) {
            holder.imv_status.setImageResource(R.drawable.blocked);
        } else if (dataModel.getMapStatus().equalsIgnoreCase("")) {
            holder.imv_status.setImageResource(R.drawable.blocked);
        }
        if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol))) {
            holder.imv_status.setImageResource(R.drawable.vol_pending);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_approved);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.DeclinedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_declined);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol))) {
            holder.imv_status.setImageResource(R.drawable.vol_completed);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO))) {
            holder.imv_status.setImageResource(R.drawable.more_info);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.RejectedCompleteByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_rejected);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedVerifiedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.cso_verified);

        }
        holder.imv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.DeclinedByCSO)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.RejectedCompleteByCSO))
                )
                    openDialog(Integer.parseInt(dataModel.getMapStatus()), dataModel.getMapId(), position);
            }
        });

        return view;
    }

    private class Holder {
        MyTextView date, time, name, tv_heading, tv_desc, tv_shift_task, tv_event_name;
        ScaleRatingBar rb_rank;
        ImageView imv_status;
    }

    private void openDialog(int status, String MapID, int position) {

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.cso_change_status_popup, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        LinearLayout accept_layout = view.findViewById(R.id.accept_layout);
        LinearLayout reject_layout = view.findViewById(R.id.reject_layout);
        LinearLayout decline_layout = view.findViewById(R.id.decline_layout);
        LinearLayout verify_layout = view.findViewById(R.id.verify_layout);
        LinearLayout info_layout = view.findViewById(R.id.info_layout);
        View accept_view = view.findViewById(R.id.accept_view);
        View reject_view = view.findViewById(R.id.reject_view);
        View decline_view = view.findViewById(R.id.decline_view);
        View info_view = view.findViewById(R.id.info_view);

        switch (status) {

            case 10:
                decline_view.setVisibility(View.VISIBLE);
                decline_layout.setVisibility(View.VISIBLE);
                accept_layout.setVisibility(View.VISIBLE);
                break;
            case 20:
                decline_layout.setVisibility(View.VISIBLE);
                break;
            case 30:
                accept_layout.setVisibility(View.VISIBLE);
                break;
            case 40:
                info_layout.setVisibility(View.VISIBLE);
                info_view.setVisibility(View.VISIBLE);
                reject_layout.setVisibility(View.VISIBLE);
                reject_view.setVisibility(View.VISIBLE);
                verify_layout.setVisibility(View.VISIBLE);
                break;
            case 50:
                decline_layout.setVisibility(View.VISIBLE);
                decline_view.setVisibility(View.VISIBLE);
                accept_layout.setVisibility(View.VISIBLE);
                accept_view.setVisibility(View.VISIBLE);
                reject_layout.setVisibility(View.VISIBLE);
                reject_view.setVisibility(View.VISIBLE);
                verify_layout.setVisibility(View.VISIBLE);
                break;
            case 60:
                info_layout.setVisibility(View.VISIBLE);
                info_view.setVisibility(View.VISIBLE);
                verify_layout.setVisibility(View.VISIBLE);
                break;

        }

        accept_layout.setOnClickListener(v -> {
            changeStatus(Constants.AcceptedByCSO, MapID, position);
            dialog.dismiss();

        });

        decline_layout.setOnClickListener(v -> {
            changeStatus(Constants.DeclinedByCSO, MapID, position);
            dialog.dismiss();
        });
        reject_layout.setOnClickListener(v -> {
            changeStatus(Constants.RejectedCompleteByCSO, MapID, position);
            dialog.dismiss();
        });
        verify_layout.setOnClickListener(v -> {
            changeStatus(Constants.CompletedVerifiedByCSO, MapID, position);
            dialog.dismiss();
        });
        info_layout.setOnClickListener(v -> {
            changeStatus(Constants.MoreInfoByCSO, MapID, position);
            dialog.dismiss();
        });
        dialog.show();

    }

    public void changeStatus(int map_status, String mapID, int position) {
        ChangeStatusByCSORequest changeVolunteerStatusRequest = new ChangeStatusByCSORequest();
        changeVolunteerStatusRequest.setUser_id(sharedPref.getUserId());
        changeVolunteerStatusRequest.setUser_type(sharedPref.getUserType());
        changeVolunteerStatusRequest.setUser_device(Utility.getDeviceId(context));
        changeVolunteerStatusRequest.setMap_id(mapID);
        changeVolunteerStatusRequest.setMap_status(String.valueOf(map_status));

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            changeStatusByCSOViewModel.changeStatus(changeVolunteerStatusRequest).observe((FragmentActivity) context, changeStatusByCSOResponse -> {
                if (changeStatusByCSOResponse != null) {
                    if (changeStatusByCSOResponse.getResStatus().equalsIgnoreCase("200")) {
                        switch (map_status) {

                            case 20:
                                new MyToast(context).show(context.getString(R.string.toast_accept_request_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 30:
                                new MyToast(context).show(context.getString(R.string.toast_decline_request_success), Toast.LENGTH_SHORT, true);
                                break;

                            case 50:
                                new MyToast(context).show(context.getString(R.string.toast_info_cso_request_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 60:
                                new MyToast(context).show(context.getString(R.string.toast_reject_request_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 70:
                                new MyToast(context).show(context.getString(R.string.toast_verify_cso_request_success), Toast.LENGTH_SHORT, true);
                                break;
                        }
                        refreshVolunteers(position, map_status);
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

    private void refreshVolunteers(int position, int status) {
        CSOAllResponse.ResData dataModel = dataList.get(position);
        dataModel.setMapStatus(String.valueOf(status));
        this.notifyDataSetChanged();
    }
}
