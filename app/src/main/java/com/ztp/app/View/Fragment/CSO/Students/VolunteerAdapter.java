package com.ztp.app.View.Fragment.CSO.Students;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.AddCommentRequest;
import com.ztp.app.Data.Remote.Model.Request.ChangeStatusByCSORequest;
import com.ztp.app.Data.Remote.Model.Request.CsoMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Request.MarkRankRequest;
import com.ztp.app.Data.Remote.Model.Response.AddCommentResponse;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;
import com.ztp.app.Viewmodel.AddCommentViewModel;
import com.ztp.app.Viewmodel.ChangeStatusByCSOViewModel;
import com.ztp.app.Viewmodel.CsoMarkHoursViewModel;
import com.ztp.app.Viewmodel.MarkRankViewModel;

import java.util.ArrayList;
import java.util.List;

class VolunteerAdapter extends BaseAdapter {
    private Context context;
    List<CSOAllResponse.CSOAllRequest> dataList;
    MyProgressDialog myProgressDialog;
    SharedPref sharedPref;
    MyToast myToast;
    ChangeStatusByCSOViewModel changeStatusByCSOViewModel;
    String setRank;
    CSOAllResponse.CSOAllRequest dataModel;
    MarkRankViewModel markRankViewModel;
    CsoMarkHoursViewModel csoMarkHoursViewModel;
    AddCommentViewModel addCommentViewModel;
    int x = 0;
    private List<String> mSelectedIds;
    public static final String EXTRA_NEW_CHANNEL_URL = "EXTRA_NEW_CHANNEL_URL";

    public VolunteerAdapter(Context context, List<CSOAllResponse.CSOAllRequest> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        changeStatusByCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeStatusByCSOViewModel.class);
        markRankViewModel = ViewModelProviders.of((FragmentActivity) context).get(MarkRankViewModel.class);
        csoMarkHoursViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoMarkHoursViewModel.class);
        addCommentViewModel = ViewModelProviders.of((FragmentActivity) context).get(AddCommentViewModel.class);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CSOAllResponse.CSOAllRequest getItem(int position) {
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
            holder.imv_status = view.findViewById(R.id.imv_status);
            holder.tv_event_name = view.findViewById(R.id.tv_event_name);
            holder.tv_shift_task = view.findViewById(R.id.tv_shift_task);
            holder.rank = view.findViewById(R.id.rank);
            holder.tv_hrs = view.findViewById(R.id.tv_hrs);
            holder.imv_chat = view.findViewById(R.id.imv_chat);
            holder.rankText = view.findViewById(R.id.rankText);
            holder.statusText = view.findViewById(R.id.statusText);
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
        holder.tv_shift_task.setText(dataModel.getShiftTaskName());

        if (!dataModel.getAttendHours().isEmpty() && !dataModel.getAttendHours().equalsIgnoreCase("0"))
            holder.tv_hrs.setText(dataModel.getAttendHours() + " " + context.getString(R.string.hrs_small));
        else {
            // holder.tv_hrs.setText(" 0 " + context.getString(R.string.hrs_small));

            holder.tv_hrs.setText(dataModel.getAttendHoursVol() + " " + context.getString(R.string.hrs_small));
        }


        if (dataModel.getAttendRank().equalsIgnoreCase("1")) {
            Picasso.with(context).load(R.drawable.rank_one_vol).fit().into(holder.rank);
            holder.rankText.setText(context.getString(R.string.RANK_ONE_NAME));
        }
        else if (dataModel.getAttendRank().equalsIgnoreCase("2")) {
            Picasso.with(context).load(R.drawable.rank_two_vol).fit().into(holder.rank);
            holder.rankText.setText(context.getString(R.string.RANK_TWO_NAME));
        }
        else if (dataModel.getAttendRank().equalsIgnoreCase("3")) {
            Picasso.with(context).load(R.drawable.rank_three_vol).fit().into(holder.rank);
            holder.rankText.setText(context.getString(R.string.RANK_THREE_NAME));
        }
        else if (dataModel.getAttendRank().equalsIgnoreCase("4")) {
            Picasso.with(context).load(R.drawable.rank_four_vol).fit().into(holder.rank);
            holder.rankText.setText(context.getString(R.string.RANK_FOUR_NAME));
        }
        else if (dataModel.getAttendRank().equalsIgnoreCase("5")) {
            Picasso.with(context).load(R.drawable.rank_five_vol).fit().into(holder.rank);
            holder.rankText.setText(context.getString(R.string.RANK_FIVE_NAME));
        }
        else if (dataModel.getAttendRank().equalsIgnoreCase("") || dataModel.getAttendRank().equalsIgnoreCase("0")) {
            Picasso.with(context).load(R.drawable.no_rank).fit().into(holder.rank);
            holder.rankText.setText("No rank");
        }

        if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.WithdrawnVol))) {
            holder.imv_status.setImageResource(R.drawable.ic_withdrawn_volunteer);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.WITHDRAWN);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconOrange));
        } else if (dataModel.getMapStatus().equalsIgnoreCase("")) {
            holder.imv_status.setImageResource(R.drawable.ic_not_available);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.NOT_AVAILABLE);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconGray));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol))) {
            holder.imv_status.setImageResource(R.drawable.ic_pending);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.PENDING);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconGray));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.ic_accepted_cso);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.ACCEPTED);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconGreen));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.DeclinedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.ic_declined_cso);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.DECLINED);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconOrange));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol))) {
            holder.imv_status.setImageResource(R.drawable.ic_completed_volunteer);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.COMPLETED);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconGreen));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO))) {
            holder.imv_status.setImageResource(R.drawable.ic_more_info);
            holder.imv_chat.setVisibility(View.VISIBLE);
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoCSO))) {
            holder.imv_status.setImageResource(R.drawable.ic_more_info);
            holder.imv_chat.setVisibility(View.VISIBLE);
            holder.statusText.setText(Constants.MORE_INFO);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconOrange));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.RejectedCompleteByCSO))) {
            holder.imv_status.setImageResource(R.drawable.ic_rejected_cso);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.REJECTED);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconRed));
        } else if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedVerifiedByCSO))) {
            holder.imv_status.setImageResource(R.drawable.ic_complete_verified);
            holder.imv_chat.setVisibility(View.GONE);
            holder.statusText.setText(Constants.VERIFIED);
            holder.statusText.setTextColor(context.getResources().getColor(R.color.iconGreen));
        }

        holder.imv_status.setTag(dataModel);
        holder.imv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSOAllResponse.CSOAllRequest dataModel = (CSOAllResponse.CSOAllRequest) v.getTag();

                if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.NewRequestVol)) ||
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.DeclinedByCSO)) ||
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.AcceptedByCSO)) ||
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedByVol)) ||
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoByCSO)) ||
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.MoreInfoCSO)) ||
                        dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.RejectedCompleteByCSO))
                )
                    openDialog(dataModel, Integer.parseInt(dataModel.getMapStatus()), dataModel.getMapId(), position);
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

               /* CSOAllResponse.CSOAllRequest dataModel = (CSOAllResponse.CSOAllRequest) v.getTag();
                if (dataModel.getMapStatus().equalsIgnoreCase(String.valueOf(Constants.CompletedVerifiedByCSO)))
                    openHoursChangeDialog(dataModel);
                else
                    myToast.show("Firstly verify the request", Toast.LENGTH_SHORT, false);*/


            }
        });
        holder.imv_chat.setTag(dataModel);
        holder.imv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSOAllResponse.CSOAllRequest dataModel = (CSOAllResponse.CSOAllRequest) v.getTag();
                mSelectedIds = new ArrayList<>();
                mSelectedIds.add(dataModel.getUserEmail());
                createGroupChannel(dataModel.getUserFName() + " " + dataModel.getUserLName(), mSelectedIds, true);
                ((CsoDashboardActivity) context).setHangoutProps();
            }
        });
        return view;
    }

    private class Holder {
        MyTextView date, time, name, tv_heading, tv_desc, tv_shift_task, tv_event_name, tv_hrs,rankText,statusText;
        ImageView imv_status, rank, imv_chat;
    }


    private void openHoursChangeDialog(CSOAllResponse.CSOAllRequest dataModel, String map_id, int position) {

        String userHours, reqiredHours;
        if (!dataModel.getAttendHours().isEmpty())
            userHours = dataModel.getAttendHours();
        else
            userHours = dataModel.getAttendHoursVol();

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

        if (!userHours.isEmpty())
            currentHours.setText(userHours + " " + context.getString(R.string.hrs_small));
        else
            currentHours.setText(" 0 " + context.getString(R.string.hrs_small));
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
                if (s.length() > 0) {
                    currentHours.setText(s + " " + context.getString(R.string.hrs_small));
                } else {
                    if (!userHours.isEmpty())
                        currentHours.setText(userHours + " " + context.getString(R.string.hrs_small));
                    else
                        currentHours.setText(" 0 " + context.getString(R.string.hrs_small));
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
                            hitUpdateHoursApi(etHours.getText().toString().trim(), dataModel, map_id, position);
                            dialog.dismiss();
                        } else {
                            myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                            dialog.dismiss();
                        }
                    }
                } else {
                    myToast.show(context.getString(R.string.err_enter_hours), Toast.LENGTH_SHORT, false);
                }

            }
        });
    }

    private void hitUpdateHoursApi(String userHours, CSOAllResponse.CSOAllRequest dataMode, String MapID, int position) {
        myProgressDialog.show(context.getString(R.string.please_wait));
        CsoMarkHoursRequest csoMarkHoursRequest = new CsoMarkHoursRequest();
        csoMarkHoursRequest.setUserId(sharedPref.getUserId());
        csoMarkHoursRequest.setUserType(sharedPref.getUserType());
        csoMarkHoursRequest.setUserDevice(Utility.getDeviceId(context));
        csoMarkHoursRequest.setMapId(dataModel.getMapId());
        csoMarkHoursRequest.setAttendHours(userHours);
        csoMarkHoursRequest.setVolId(dataModel.getUserId());

        Log.i("REQUEST", "" + new Gson().toJson(csoMarkHoursRequest));

        csoMarkHoursViewModel.getCsoMarkHoursResponse(csoMarkHoursRequest).observe((LifecycleOwner) context, csoMarkHoursResponse -> {

            if (csoMarkHoursResponse != null) {
                Log.i("RESPONSE", "" + new Gson().toJson(csoMarkHoursResponse));
                if (csoMarkHoursResponse.getResStatus().equalsIgnoreCase("200")) {
                    dataModel.setAttendHours(userHours);
                    notifyDataSetChanged();
                    myToast.show(context.getString(R.string.hours_updated_success), Toast.LENGTH_SHORT, true);

                    changeStatus(Constants.CompletedVerifiedByCSO, MapID, position);

                } else if (csoMarkHoursResponse.getResStatus().equalsIgnoreCase("401")) {
                    myToast.show(context.getString(R.string.hours_update_failed), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }
            myProgressDialog.dismiss();
        });
    }

    private void openDialog(CSOAllResponse.CSOAllRequest dataModel, int status, String MapID, int position) {

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
                accept_view.setVisibility(View.VISIBLE);
                info_layout.setVisibility(View.VISIBLE);
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
            case 51:
                decline_layout.setVisibility(View.VISIBLE);
                decline_view.setVisibility(View.VISIBLE);
                accept_layout.setVisibility(View.VISIBLE);
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

            dialog.dismiss();

            Dialog dialog1 = new Dialog(context);
            View vw = LayoutInflater.from(context).inflate(R.layout.comment_dialog, null);
            dialog1.setContentView(vw);
            dialog1.setCancelable(false);

            EditText comment = vw.findViewById(R.id.comment);
            LinearLayout add = vw.findViewById(R.id.add);
            LinearLayout cancel = vw.findViewById(R.id.cancel);

            add.setOnClickListener(v1 -> {
                if(!comment.getText().toString().trim().isEmpty()) {
                    dialog1.dismiss();
                    if (Utility.isNetworkAvailable(context)) {
                        myProgressDialog.show(context.getString(R.string.please_wait));
                        AddCommentRequest addCommentRequest = new AddCommentRequest();
                        addCommentRequest.setUserId(sharedPref.getUserId());
                        addCommentRequest.setUserType(sharedPref.getUserType());
                        addCommentRequest.setUserDevice(Utility.getDeviceId(context));
                        addCommentRequest.setMapStatusComment(comment.getText().toString().trim());
                        addCommentRequest.setMapStatus(String.valueOf(status));
                        addCommentRequest.setMapId(MapID);

                        Log.i("", "" + new Gson().toJson(addCommentRequest));

                        addCommentViewModel.addCommentResponse(addCommentRequest).observe((LifecycleOwner) context, new Observer<AddCommentResponse>() {
                            @Override
                            public void onChanged(@Nullable AddCommentResponse addCommentResponse) {
                                if (addCommentResponse != null) {
                                    Log.i("", "" + new Gson().toJson(addCommentResponse));
                                    if (addCommentResponse.getResStatus().equalsIgnoreCase("200")) {
                                        changeStatus(Constants.DeclinedByCSO, MapID, position); //decline call
                                    } else if (addCommentResponse.getResStatus().equalsIgnoreCase("401")) {
                                        new MyToast(context).show(context.getString(R.string.req_failed), Toast.LENGTH_SHORT, false);
                                    } else {
                                        new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                                    }
                                } else {
                                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                                }
                                myProgressDialog.dismiss();
                            }
                        });
                    } else {
                        new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                    }
                }
                else
                {
                    new MyToast(context).show(context.getString(R.string.err_enter_comment), Toast.LENGTH_SHORT, false);
                }
            });

            cancel.setOnClickListener(v12 -> {
                dialog1.dismiss();
            });

            dialog1.show();

            /*Dialog dialog1 = new Dialog(context);
            View vw = LayoutInflater.from(context).inflate(R.layout.common_dialog, null);
            dialog1.setContentView(vw);
            dialog1.setCancelable(false);

            LinearLayout yes = vw.findViewById(R.id.yes);
            LinearLayout no = vw.findViewById(R.id.no);
            MyHeadingTextView title = vw.findViewById(R.id.title);
            MyTextView message = vw.findViewById(R.id.message);

            title.setText("CONFIRM DECLINE ?");
            message.setText("Do you want to decline ?");

            yes.setOnClickListener(v1 -> {
                changeStatus(Constants.DeclinedByCSO, MapID, position);
                dialog1.dismiss();
            });

            no.setOnClickListener(v12 -> dialog1.dismiss());

            dialog1.show();*/

        });
        reject_layout.setOnClickListener(v -> {
            changeStatus(Constants.RejectedCompleteByCSO, MapID, position);
            dialog.dismiss();
        });
        verify_layout.setOnClickListener(v -> {
            openHoursChangeDialog(dataModel, MapID, position);

            dialog.dismiss();
        });
        info_layout.setOnClickListener(v -> {
            if (x == 0) {
                changeStatus(Constants.MoreInfoCSO, MapID, position);
                x++;
            } else {
                changeStatus(Constants.MoreInfoByCSO, MapID, position);
                x++;
            }

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

        Log.i("", "" + new Gson().toJson(changeVolunteerStatusRequest));

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

                    } else if (changeStatusByCSOResponse.getResStatus().equalsIgnoreCase("401")) {
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
        CSOAllResponse.CSOAllRequest dataModel = dataList.get(position);
        dataModel.setMapStatus(String.valueOf(status));
        this.notifyDataSetChanged();
    }

    private void openRankChangeDialog(Object object) {

        CSOAllResponse.CSOAllRequest dataModel = (CSOAllResponse.CSOAllRequest) object;
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
        EditText comment = view.findViewById(R.id.comment);
        MyTextView currentRankText = view.findViewById(R.id.currentRankText);

        dialog.show();

        if (current.equalsIgnoreCase("1")) {
            Picasso.with(context).load(R.drawable.rank_one_vol).fit().into(currentRank);
            currentRankText.setText(context.getString(R.string.RANK_ONE_NAME));
        }
        else if (current.equalsIgnoreCase("2")) {
            Picasso.with(context).load(R.drawable.rank_two_vol).fit().into(currentRank);
            currentRankText.setText(context.getString(R.string.RANK_TWO_NAME));
        }
        else if (current.equalsIgnoreCase("3")) {
            Picasso.with(context).load(R.drawable.rank_three_vol).fit().into(currentRank);
            currentRankText.setText(context.getString(R.string.RANK_THREE_NAME));
        }
        else if (current.equalsIgnoreCase("4")) {
            Picasso.with(context).load(R.drawable.rank_four_vol).fit().into(currentRank);
            currentRankText.setText(context.getString(R.string.RANK_FOUR_NAME));
        }
        else if (current.equalsIgnoreCase("5")) {
            Picasso.with(context).load(R.drawable.rank_five_vol).fit().into(currentRank);
            currentRankText.setText(context.getString(R.string.RANK_FIVE_NAME));
        }
        else if (current.equalsIgnoreCase("") || current.equalsIgnoreCase("0")) {
            Picasso.with(context).load(R.drawable.no_rank).fit().into(currentRank);
            currentRankText.setText("No rank");
        }

        rank1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_one_vol).fit().into(currentRank);
                currentRankText.setText(context.getString(R.string.RANK_ONE_NAME));
                setRank = v.getTag().toString();
            }
        });

        rank2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_two_vol).fit().into(currentRank);
                currentRankText.setText(context.getString(R.string.RANK_TWO_NAME));
                setRank = v.getTag().toString();
            }
        });

        rank3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_three_vol).fit().into(currentRank);
                currentRankText.setText(context.getString(R.string.RANK_THREE_NAME));
                setRank = v.getTag().toString();
            }
        });

        rank4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(context).load(R.drawable.rank_four_vol).fit().into(currentRank);
                currentRankText.setText(context.getString(R.string.RANK_FOUR_NAME));
                setRank = v.getTag().toString();
            }
        });

        rank5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(context).load(R.drawable.rank_five_vol).fit().into(currentRank);
                currentRankText.setText(context.getString(R.string.RANK_FIVE_NAME));
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

                if (Utility.isNetworkAvailable(context)) {
                    if(!comment.getText().toString().trim().isEmpty()) {
                        if (setRank != null && !setRank.equalsIgnoreCase(current)) {
                            hitMarkRankApi(setRank, dataModel, comment.getText().toString().trim());
                            dialog.dismiss();
                        }
                        else {
                            hitMarkRankApi(current, dataModel, comment.getText().toString().trim());
                            dialog.dismiss();
                        }
                    }
                    else
                    {
                        myToast.show(context.getString(R.string.err_enter_comment), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                }
            }
        });
    }

    private void hitMarkRankApi(String rank, CSOAllResponse.CSOAllRequest dataModel,String comment) {
        myProgressDialog.show(context.getString(R.string.please_wait));

        MarkRankRequest markRankRequest = new MarkRankRequest(sharedPref.getUserId(), sharedPref.getUserType(), Utility.getDeviceId(context), dataModel.getUserId(), rank, dataModel.getMapId(),comment);
        Log.i("REQUEST", "" + new Gson().toJson(markRankRequest));

        markRankViewModel.getMarkRankResponse(markRankRequest).observe((LifecycleOwner) context, markRankResponse -> {

            if (markRankResponse != null) {
                Log.i("RESPONSE", "" + new Gson().toJson(markRankResponse));
                if (markRankResponse.getResStatus().equalsIgnoreCase("200")) {
                    dataModel.setAttendRank(rank);
                    notifyDataSetChanged();
                    myToast.show(context.getString(R.string.rank_updated), Toast.LENGTH_SHORT, true);
                } else if (markRankResponse.getResStatus().equalsIgnoreCase("401")) {
                    myToast.show(context.getString(R.string.err_rank_not_updated), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }
            myProgressDialog.dismiss();
        });
    }

    /**
     * Creates a new Group Channel.
     * <p>
     * Note that if you have not included empty channels in your GroupChannelListQuery,
     * the channel will not be shown in the user's channel list until at least one message
     * has been sent inside.
     *
     * @param userIds  The users to be members of the new channel.
     * @param distinct Whether the channel is unique for the selected members.
     *                 If you attempt to create another Distinct channel with the same members,
     *                 the existing channel instance will be returned.
     */
    private void createGroupChannel(String nick_name, List<String> userIds, boolean distinct) {
        GroupChannel.createChannelWithUserIds(userIds, distinct, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                Constants.backFromChat = false;
                GroupChannelListFragment fragment = new GroupChannelListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                bundle.putString(TabMyBookingFragment.EXTRA_Name, nick_name);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.body, fragment, "GroupChannelListFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }
}
