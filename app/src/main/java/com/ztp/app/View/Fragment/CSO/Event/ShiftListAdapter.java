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
import android.widget.Toast;

import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Request.DeleteShiftRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftListResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.DeleteShiftViewModel;
import com.ztp.app.Viewmodel.EventDeleteViewModel;
import com.ztp.app.Viewmodel.GetShiftDetailViewModel;

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



    public ShiftListAdapter(Context context, List<GetShiftListResponse.ShiftData> shiftDataList) {
        this.context = context;
        this.shiftDataList = shiftDataList;
        myProgressDialog = new MyProgressDialog(context);
        deleteShiftViewModel = ViewModelProviders.of((FragmentActivity) context).get(DeleteShiftViewModel.class);

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
           Holder holder = new Holder();
          GetShiftListResponse.ShiftData shiftData= getItem(position);
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
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }


            holder.imv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AddNewShiftFragment updateShiftFragment = new AddNewShiftFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("shiftData", shiftData);
                    bundle.putString("status", "update");
                    updateShiftFragment.setArguments(bundle);
                    Utility.replaceFragment(context,updateShiftFragment,"AddNewShiftFragment");
                }
            });


            holder.imv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DeleteShiftRequest deleteShiftRequest = new DeleteShiftRequest();
                    deleteShiftRequest.setShiftId(shiftData.getShift_id());

                    if(Utility.isNetworkAvailable(context)) {
                        myProgressDialog.show(context.getString(R.string.deleting_shift));
                        deleteShiftViewModel.getDeleteShiftResponse(deleteShiftRequest).observe((LifecycleOwner) context, deleteShiftResponse -> {

                            if(deleteShiftResponse != null) {
                                if (deleteShiftResponse.getResStatus().equalsIgnoreCase("200")) {

                                    new MyToast(context).show(context.getString(R.string.shift_deleted_successfully), Toast.LENGTH_SHORT, true);

                                    shiftDataList.remove(shiftData);

                                    notifyDataSetChanged();

                                } else {

                                    new MyToast(context).show(context.getString(R.string.failed), Toast.LENGTH_SHORT, false);
                                }
                            }
                            else {
                                new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                            }

                            myProgressDialog.dismiss();
                        });
                    }else
                    {
                        new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                    }


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


            Date ds = Utility.convertStringToDate(shiftData.getShift_add_date());
            holder.shift_add_date.setText(Utility.formatDateFullTime(ds));

            Date eds = Utility.convertStringToDate(shiftData.getShift_update_date());
            holder.shift_update_date.setText(Utility.formatDateFullTime(eds));


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return view;
    }


    public class Holder
    {
        MyTextView shift_id,shift_date,shift_vol_req,shift_start_time,shift_end_time,shift_rank,shift_task,shift_status,shift_add_date,shift_update_date;
        ImageView imv_edit,imv_delete;
    }
}
