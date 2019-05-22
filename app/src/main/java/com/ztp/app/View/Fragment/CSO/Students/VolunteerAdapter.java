package com.ztp.app.View.Fragment.CSO.Students;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangeStatusByCSORequest;
import com.ztp.app.Data.Remote.Model.Request.CsoMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Request.MarkRankRequest;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoMyVolunteerResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ChangeStatusByCSOViewModel;
import com.ztp.app.Viewmodel.CsoMarkHoursViewModel;
import com.ztp.app.Viewmodel.MarkRankViewModel;

import java.util.List;

class VolunteerAdapter extends BaseAdapter {
    private Context context;
    List<CSOAllResponse.ResData> dataList;
    MyProgressDialog myProgressDialog;
    SharedPref sharedPref;
    MyToast myToast;
    ChangeStatusByCSOViewModel changeStatusByCSOViewModel;
    String setRank;
    CSOAllResponse.ResData dataModel;
    MarkRankViewModel markRankViewModel;
    CsoMarkHoursViewModel csoMarkHoursViewModel;

    public VolunteerAdapter(Context context, List<CSOAllResponse.ResData> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        changeStatusByCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeStatusByCSOViewModel.class);
        markRankViewModel = ViewModelProviders.of((FragmentActivity) context).get(MarkRankViewModel.class);
        csoMarkHoursViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoMarkHoursViewModel.class);
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
        dataModel = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_volunteers_row, null);
            holder.date = view.findViewById(R.id.tv_date);
            holder.time = view.findViewById(R.id.tv_time);
            holder.name = view.findViewById(R.id.tv_name);
            holder.tv_heading = view.findViewById(R.id.tv_heading);
            holder.tv_desc = view.findViewById(R.id.tv_description);
            //holder.rb_rank = view.findViewById(R.id.rb_rank);
            holder.imv_status = view.findViewById(R.id.imv_status);
            holder.tv_event_name = view.findViewById(R.id.tv_event_name);
            holder.tv_shift_task = view.findViewById(R.id.tv_shift_task);
            holder.rank = view.findViewById(R.id.rank);
            holder.tv_hrs = view.findViewById(R.id.tv_hrs);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_heading.setText(dataModel.getUserFName() + " " + dataModel.getUserLName());


        holder.date.setText(dataModel.getShiftDate());
        holder.time.setText(dataModel.getShiftStartTime() + " - " + dataModel.getShiftEndTime());
        holder.name.setText(dataModel.getUserEmail());
        holder.tv_desc.setText(dataModel.getUserEmail());
        holder.tv_event_name.setText(dataModel.getEventHeading());
        holder.tv_shift_task.setText(dataModel.getShiftTask());
        holder.tv_hrs.setText(dataModel.getAttendHours()+" "+context.getString(R.string.hrs_small));


        if (dataModel.getAttendRank().equalsIgnoreCase("1"))
            Picasso.with(context).load(R.drawable.rank_baby).fit().into(holder.rank);
        else if (dataModel.getAttendRank().equalsIgnoreCase("2"))
            Picasso.with(context).load(R.drawable.rank_grownup).fit().into(holder.rank);
        else if (dataModel.getAttendRank().equalsIgnoreCase("3"))
            Picasso.with(context).load(R.drawable.rank_knight).fit().into(holder.rank);
        else if (dataModel.getAttendRank().equalsIgnoreCase("4"))
            Picasso.with(context).load(R.drawable.rank_royalty).fit().into(holder.rank);
        else if (dataModel.getAttendRank().equalsIgnoreCase("5"))
            Picasso.with(context).load(R.drawable.rank_warrior).fit().into(holder.rank);
        else if (dataModel.getAttendRank().equalsIgnoreCase(""))
            Picasso.with(context).load(R.drawable.not_available).fit().into(holder.rank);


       /* if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.WithdrawnVol))) {
            holder.imv_status.setImageResource(R.drawable.blocked);
        } else if (dataModel.getMapStatus().equalsIgnoreCase("")) {
            holder.imv_status.setImageResource(R.drawable.blocked);
        }*/
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

        holder.imv_status.setTag(dataModel);
        holder.imv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSOAllResponse.ResData dataModel = (CSOAllResponse.ResData) v.getTag();

                if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.DeclinedByCSO)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO)) ||
                                dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.RejectedCompleteByCSO))
                )
                    openDialog(Integer.parseInt(dataModel.getMapStatus()), dataModel.getMapId(), position);
            }
        });

        holder.rank.setTag(dataModel);

        holder.rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRankChangeDialog(v.getTag());
            }
        });

        holder.tv_hrs.setTag(dataModel);
        holder.tv_hrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CSOAllResponse.ResData dataModel = (CSOAllResponse.ResData) v.getTag();
                openHoursChangeDialog(dataModel);


            }
        });

        return view;
    }

    private class Holder {
        MyTextView date, time, name, tv_heading, tv_desc, tv_shift_task, tv_event_name,tv_hrs;
        //ScaleRatingBar rb_rank;
        ImageView imv_status, rank;
    }


    private void openHoursChangeDialog(CSOAllResponse.ResData dataModel) {

        String userHours, reqiredHours;
        userHours = dataModel.getAttendHours();
        reqiredHours = "10";

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.change_hours_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        Button cancel = view.findViewById(R.id.cancel);
        Button done = view.findViewById(R.id.done);
        MyTextView currentHours = view.findViewById(R.id.currentHours);
       // MyTextView requiredHours = view.findViewById(R.id.requiredHours);
        MyTextInputEditText etHours = view.findViewById(R.id.etHours);

        dialog.show();

        currentHours.setText(userHours+" "+context.getString(R.string.hrs_small));
        //requiredHours.setText(reqiredHours);

        etHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            if(s.length()>0)
            {
                currentHours.setText(s+" "+context.getString(R.string.hrs_small));
            }
            else
            {
                currentHours.setText(userHours+" "+context.getString(R.string.hrs_small));
            }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etHours.getText() != null && !etHours.getText().toString().isEmpty()) {
                    if (!userHours.equalsIgnoreCase(etHours.getText() != null ? etHours.getText().toString().trim() : "0")) {
                        if (Utility.isNetworkAvailable(context)) {
                            dialog.dismiss();
                            hitUpdateHoursApi(etHours.getText().toString().trim(), dataModel);
                        }
                        else
                            myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(context.getString(R.string.err_enter_hours), Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void hitUpdateHoursApi(String userHours, CSOAllResponse.ResData dataModel) {
        myProgressDialog.show(context.getString(R.string.please_wait));
        csoMarkHoursViewModel.getCsoMarkHoursResponse(new CsoMarkHoursRequest(sharedPref.getUserId(), sharedPref.getUserType(), Utility.getDeviceId(context), dataModel.getMapId(), userHours, dataModel.getUserId())).observe((LifecycleOwner) context, csoMarkHoursResponse -> {

            if (csoMarkHoursResponse != null) {
                if (csoMarkHoursResponse.getResStatus().equalsIgnoreCase("200")) {
                    dataModel.setAttendHours(userHours);
                    notifyDataSetChanged();
                } else {
                    myToast.show(context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }
            myProgressDialog.dismiss();
        });
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

    private void openRankChangeDialog(Object object) {

        CSOAllResponse.ResData dataModel = (CSOAllResponse.ResData) object;
        String current = dataModel.getAttendRank();

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.change_rank_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        ImageView currentRank = view.findViewById(R.id.currentRank);
        ImageView rank1 = view.findViewById(R.id.rank1);
        ImageView rank2 = view.findViewById(R.id.rank2);
        ImageView rank3 = view.findViewById(R.id.rank3);
        ImageView rank4 = view.findViewById(R.id.rank4);
        ImageView rank5 = view.findViewById(R.id.rank5);
        Button cancel = view.findViewById(R.id.cancel);
        Button done = view.findViewById(R.id.done);

        dialog.show();

        if (current.equalsIgnoreCase("1"))
            Picasso.with(context).load(R.drawable.rank_baby).fit().into(currentRank);
        else if (current.equalsIgnoreCase("2"))
            Picasso.with(context).load(R.drawable.rank_grownup).fit().into(currentRank);
        else if (current.equalsIgnoreCase("3"))
            Picasso.with(context).load(R.drawable.rank_knight).fit().into(currentRank);
        else if (current.equalsIgnoreCase("4"))
            Picasso.with(context).load(R.drawable.rank_royalty).fit().into(currentRank);
        else if (current.equalsIgnoreCase("5"))
            Picasso.with(context).load(R.drawable.rank_warrior).fit().into(currentRank);
        else if (current.equalsIgnoreCase(""))
            Picasso.with(context).load(R.drawable.not_available).fit().into(currentRank);

        rank1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_baby).fit().into(currentRank);
                setRank = v.getTag().toString();
            }
        });

        rank2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_grownup).fit().into(currentRank);
                setRank = v.getTag().toString();
            }
        });

        rank3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_knight).fit().into(currentRank);
                setRank = v.getTag().toString();
            }
        });

        rank4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_royalty).fit().into(currentRank);
                setRank = v.getTag().toString();
            }
        });

        rank5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(context).load(R.drawable.rank_warrior).fit().into(currentRank);
                setRank = v.getTag().toString();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setRank = "";
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (!setRank.equalsIgnoreCase(current)) {
                    if (Utility.isNetworkAvailable(context))
                        hitMarkRankApi(setRank, dataModel);
                    else
                        myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void hitMarkRankApi(String rank, CSOAllResponse.ResData dataModel) {
        myProgressDialog.show(context.getString(R.string.please_wait));

        MarkRankRequest markRankRequest = new MarkRankRequest(sharedPref.getUserId(), sharedPref.getUserType(), Utility.getDeviceId(context), dataModel.getUserId(), rank, dataModel.getMapId());
        Log.i("REQUEST", "" + new Gson().toJson(markRankRequest));


        markRankViewModel.getMarkRankResponse(markRankRequest).observe((LifecycleOwner) context, markRankResponse -> {


            if (markRankResponse != null) {
                Log.i("RESPONSE", "" + new Gson().toJson(markRankResponse));
                if (markRankResponse.getResStatus().equalsIgnoreCase("200")) {
                    dataModel.setAttendRank(rank);
                    notifyDataSetChanged();
                } else {
                    myToast.show(context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }
            myProgressDialog.dismiss();
        });
    }
}
