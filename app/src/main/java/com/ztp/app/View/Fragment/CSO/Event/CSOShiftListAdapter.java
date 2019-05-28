package com.ztp.app.View.Fragment.CSO.Event;

import android.app.Dialog;
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
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.ShiftDetailFragment;
import com.ztp.app.Viewmodel.DeleteShiftViewModel;
import com.ztp.app.Viewmodel.VolunteerEventRequestViewModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CSOShiftListAdapter extends BaseAdapter {

    private Context context;
    private List<GetCSOShiftResponse.ResData> shiftDataList;
    private MyProgressDialog myProgressDialog;
    DeleteShiftViewModel deleteShiftViewModel;
    SharedPref sharedPref;
    String event_id;
    VolunteerEventRequestViewModel volunteerEventRequestViewModel;
    Holder holder;
    String eventStartDate,eventEndDate;

    public CSOShiftListAdapter(Context context, List<GetCSOShiftResponse.ResData> shiftDataList, String event_id,String eventStartDate,String eventEndDate) {
        this.context = context;
        sharedPref = SharedPref.getInstance(context);
        this.shiftDataList = shiftDataList;
        this.event_id = event_id;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        myProgressDialog = new MyProgressDialog(context);
        deleteShiftViewModel = ViewModelProviders.of((FragmentActivity) context).get(DeleteShiftViewModel.class);
        volunteerEventRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerEventRequestViewModel.class);
    }

    @Override
    public int getCount() {
        return shiftDataList.size();
    }

    @Override
    public GetCSOShiftResponse.ResData getItem(int position) {
        return shiftDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        holder = new Holder();
        GetCSOShiftResponse.ResData shiftData = getItem(position);
        try {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.shift_list_item, null);

                holder.shift_date = view.findViewById(R.id.shift_date);
                holder.title = view.findViewById(R.id.title);
                holder.description = view.findViewById(R.id.description);
                holder.shift_day = view.findViewById(R.id.shift_day);
                holder.shift_month = view.findViewById(R.id.shift_month);

                holder.imv_volunteer = view.findViewById(R.id.imv_volunteer);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.title.setText(shiftData.getShiftTask());
            holder.description.setText(shiftData.getShiftStartTime() + " - "+shiftData.getShiftEndTime());

            Date date = Utility.convertStringToDateWithoutTime(shiftData.getShiftDate());

            String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
            String day = (String) DateFormat.format("dd", date); // 20
            String monthString = (String) DateFormat.format("MMM", date); // Jun
            String monthNumber = (String) DateFormat.format("MM", date); // 06
            String year = (String) DateFormat.format("yyyy", date); // 2013

            holder.shift_date.setText(day);
            holder.shift_month.setText(monthString);
            holder.shift_day.setText(dayOfTheWeek);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    View view1 = LayoutInflater.from(context).inflate(R.layout.shift_action_layout, null);
                    dialog.setContentView(view1);
                    dialog.show();

                    LinearLayout edit = view1.findViewById(R.id.edit_event);
                    LinearLayout delete = view1.findViewById(R.id.delete_event);
                    LinearLayout view = view1.findViewById(R.id.view_event);

                    edit.setOnClickListener(vs -> {
                        dialog.dismiss();
                        AddNewShiftFragment updateShiftFragment = new AddNewShiftFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shiftData", shiftData);
                        bundle.putString("status", "update");
                        bundle.putString("event_start_date",eventStartDate);
                        bundle.putString("event_end_date",eventEndDate);
                        updateShiftFragment.setArguments(bundle);
                        Utility.replaceFragment(context, updateShiftFragment, "AddNewShiftFragment");
                    });

                    delete.setOnClickListener(vs -> {
                        dialog.dismiss();


                        Dialog dialog1 = new Dialog(context);
                        View vw = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                        dialog1.setContentView(vw);
                        dialog1.setCancelable(false);

                        LinearLayout yes = vw.findViewById(R.id.yes);
                        LinearLayout no = vw.findViewById(R.id.no);

                        yes.setOnClickListener(v12 -> {
                            dialog1.dismiss();

                            DeleteShiftRequest deleteShiftRequest = new DeleteShiftRequest();
                            deleteShiftRequest.setShiftId(shiftData.getShiftId());

                            if (Utility.isNetworkAvailable(context)) {
                                myProgressDialog.show(context.getString(R.string.deleting_shift));
                                deleteShiftViewModel.getDeleteShiftResponse(deleteShiftRequest).observe((LifecycleOwner) context, deleteShiftResponse -> {

                                    if (deleteShiftResponse != null) {
                                        if (deleteShiftResponse.getResStatus().equalsIgnoreCase("200")) {

                                            new MyToast(context).show(context.getString(R.string.shift_deleted_successfully), Toast.LENGTH_SHORT, true);

                                            shiftDataList.remove(shiftData);

                                            notifyDataSetChanged();

                                        } else if(deleteShiftResponse.getResStatus().equalsIgnoreCase("401")){

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

                        });

                        no.setOnClickListener(v12 -> {
                            dialog1.dismiss();
                        });

                        dialog1.show();

                    });
                    view.setOnClickListener(v1 -> {
                        dialog.dismiss();

                        ShiftDetailFragment shiftDetailFragment = new ShiftDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("shift_id", shiftData.getShiftId());
                        shiftDetailFragment.setArguments(bundle);
                        Utility.replaceFragment(context, shiftDetailFragment, "ShiftDetailFragment");

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public class Holder {
//        MyTextView shift_id, shift_date, shift_vol_req, shift_start_time, shift_end_time, shift_rank, shift_task, shift_status, shift_add_date, shift_update_date;
//        ImageView imv_edit, imv_delete, imv_volunteer;
//        LinearLayout volunteer_layout, cso_layout;

        MyBoldTextView title, shift_date, shift_month, shift_day;
        ImageView  imv_volunteer;
        MyTextView description;
    }


}
