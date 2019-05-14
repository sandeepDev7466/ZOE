package com.ztp.app.View.Fragment.CSO.Event;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
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
import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Request.DeleteShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.PostVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftListResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.DeleteShiftViewModel;
import com.ztp.app.Viewmodel.EventDeleteViewModel;
import com.ztp.app.Viewmodel.GetShiftDetailViewModel;
import com.ztp.app.Viewmodel.VolunteerEventRequestViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShiftListAdapter extends BaseAdapter {

    private Context context;
    private List<GetShiftListResponse.ShiftData> shiftDataList;
    private MyProgressDialog myProgressDialog;
    DeleteShiftViewModel deleteShiftViewModel;
    SharedPref sharedPref;
    String event_id;
    VolunteerEventRequestViewModel volunteerEventRequestViewModel;
    Holder holder;

    public ShiftListAdapter(Context context, List<GetShiftListResponse.ShiftData> shiftDataList, String event_id) {
        this.context = context;
        sharedPref = SharedPref.getInstance(context);
        this.shiftDataList = shiftDataList;
        this.event_id = event_id;
        myProgressDialog = new MyProgressDialog(context);
        deleteShiftViewModel = ViewModelProviders.of((FragmentActivity) context).get(DeleteShiftViewModel.class);
        volunteerEventRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerEventRequestViewModel.class);
    }

    @Override
    public int getCount() {
        return shiftDataList.size();
    }

    @Override
    public GetShiftListResponse.ShiftData getItem(int position) {
        return shiftDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        holder = new Holder();
        GetShiftListResponse.ShiftData shiftData = getItem(position);
        try {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_shift_list, null);

                holder.shift_date = view.findViewById(R.id.shift_date);
                holder.shift_vol_req = view.findViewById(R.id.shift_vol_req);
                holder.shift_start_time = view.findViewById(R.id.shift_start_time);
                holder.shift_end_time = view.findViewById(R.id.shift_end_time);
                holder.shift_rank = view.findViewById(R.id.shift_rank);
                holder.shift_status = view.findViewById(R.id.shift_status);
                holder.shift_add_date = view.findViewById(R.id.shift_add_date);
                holder.shift_update_date = view.findViewById(R.id.shift_update_date);
                holder.shift_task = view.findViewById(R.id.shift_task);
                holder.imv_edit = view.findViewById(R.id.imv_edit);
                holder.imv_delete = view.findViewById(R.id.imv_delete);
                holder.volunteer_layout = view.findViewById(R.id.volunteer_layout);
                holder.cso_layout = view.findViewById(R.id.cso_layout);
                holder.imv_volunteer = view.findViewById(R.id.imv_volunteer);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            if (!sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso)) ;
            {
                holder.cso_layout.setVisibility(View.GONE);
                holder.volunteer_layout.setVisibility(View.VISIBLE);
            }

            holder.imv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AddNewShiftFragment updateShiftFragment = new AddNewShiftFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("shiftData", shiftData);
                    bundle.putString("status", "update");
                    updateShiftFragment.setArguments(bundle);
                    Utility.replaceFragment(context, updateShiftFragment, "AddNewShiftFragment");
                }
            });


            holder.imv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DeleteShiftRequest deleteShiftRequest = new DeleteShiftRequest();
                    deleteShiftRequest.setShiftId(shiftData.getShift_id());

                    if (Utility.isNetworkAvailable(context)) {
                        myProgressDialog.show(context.getString(R.string.deleting_shift));
                        deleteShiftViewModel.getDeleteShiftResponse(deleteShiftRequest).observe((LifecycleOwner) context, deleteShiftResponse -> {

                            if (deleteShiftResponse != null) {
                                if (deleteShiftResponse.getResStatus().equalsIgnoreCase("200")) {

                                    new MyToast(context).show(context.getString(R.string.shift_deleted_successfully), Toast.LENGTH_SHORT, true);

                                    shiftDataList.remove(shiftData);

                                    notifyDataSetChanged();

                                } else {

                                    new MyToast(context).show(context.getString(R.string.failed), Toast.LENGTH_SHORT, false);
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
            });
            holder.imv_volunteer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postVolunteerRequest(shiftData);
                }
            });

            Date d = Utility.convertStringToDateWithoutTime(shiftData.getShift_date());

            holder.shift_date.setText(Utility.formatDateFull(d));

            holder.shift_vol_req.setText(shiftData.getShift_vol_req());
            holder.shift_start_time.setText(shiftData.getShift_start_time());
            holder.shift_end_time.setText(shiftData.getShift_end_time());
            holder.shift_rank.setText(shiftData.getShift_rank());
            holder.shift_status.setText(shiftData.getShift_status());
            holder.shift_task.setText(shiftData.getShift_task());

            if (shiftData.getVolunteer_apply().equalsIgnoreCase(Constants.Volunter_apply_0)) {
                holder.imv_volunteer.setImageResource(R.drawable.vol_request);
            }else{
                holder.imv_volunteer.setImageResource(R.drawable.blocked);
                holder.imv_volunteer.setEnabled(false);
            }

            Date ds = Utility.convertStringToDate(shiftData.getShift_add_date());
            holder.shift_add_date.setText(Utility.formatDateFullTime(ds));

            Date eds = Utility.convertStringToDate(shiftData.getShift_update_date());
            holder.shift_update_date.setText(Utility.formatDateFullTime(eds));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public class Holder {
        MyTextView shift_id, shift_date, shift_vol_req, shift_start_time, shift_end_time, shift_rank, shift_task, shift_status, shift_add_date, shift_update_date;
        ImageView imv_edit, imv_delete, imv_volunteer;
        LinearLayout volunteer_layout, cso_layout;
    }

    public void postVolunteerRequest(GetShiftListResponse.ShiftData shiftData) {
        PostVolunteerRequest postVolunteerRequest = new PostVolunteerRequest();
        postVolunteerRequest.setUser_id(sharedPref.getUserId());
        postVolunteerRequest.setUser_type(sharedPref.getUserType());
        postVolunteerRequest.setUser_device(Utility.getDeviceId(context));
        postVolunteerRequest.setEvent_id(event_id);
        postVolunteerRequest.setShift_id(shiftData.getShift_id());
        postVolunteerRequest.setCso_id("");

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            volunteerEventRequestViewModel.getPostVolunteerRequestResponseLiveData(postVolunteerRequest).observe((FragmentActivity) context, postVolunteerRequestResponse -> {
                if (postVolunteerRequestResponse != null && postVolunteerRequestResponse.getResData() != null) {
                    holder.imv_volunteer.setEnabled(false);
                    new MyToast(context).show(context.getString(R.string.toast_volunteer_request_success), Toast.LENGTH_SHORT, true);
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
