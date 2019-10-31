package com.ztp.app.View.Fragment.CSO.Students;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;
import com.ztp.app.Viewmodel.CsoMarkHoursViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class FollowerAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<CsoMyFollowerResponse.SeeFollower> dataList;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    CsoMarkHoursViewModel csoMarkHoursViewModel;
    SharedPref sharedPref;
    private List<String> mSelectedIds;
    public static final String EXTRA_NEW_CHANNEL_URL = "EXTRA_NEW_CHANNEL_URL";

    public FollowerAdapter(Context context, List<CsoMyFollowerResponse.SeeFollower> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        csoMarkHoursViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoMarkHoursViewModel.class);
        sharedPref = SharedPref.getInstance(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CsoMyFollowerResponse.SeeFollower getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CsoMyFollowerResponse.SeeFollower dataModel = getItem(position);

        Holder holder = new Holder();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_student_row, null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_date = view.findViewById(R.id.tv_date);
            holder.tv_hrs = view.findViewById(R.id.tv_hrs);
            holder.rank = view.findViewById(R.id.rank);
            holder.imv_chat = view.findViewById(R.id.imv_chat);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }


        holder.tv_name.setText(dataModel.getUserFName() + " " + dataModel.getUserLName());


        if (!dataModel.getUserGradDateFormat().isEmpty()) {
            Date d = Utility.convertStringToDateWithoutTime(dataModel.getUserGradDateFormat());
            holder.tv_date.setText(Utility.formatDateFull(d));
        }


        holder.tv_hrs.setText(dataModel.getUserHours()+" "+context.getString(R.string.hrs_small));

        if (dataModel.getUserRank().equalsIgnoreCase("1"))
            Picasso.with(context).load(R.drawable.rank_one_vol).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("2"))
            Picasso.with(context).load(R.drawable.rank_two_vol).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("3"))
            Picasso.with(context).load(R.drawable.rank_three_vol).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("4"))
            Picasso.with(context).load(R.drawable.rank_four_vol).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("5"))
            Picasso.with(context).load(R.drawable.rank_five_vol).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("") || dataModel.getUserRank().equalsIgnoreCase("0"))
            Picasso.with(context).load(R.drawable.rank_five_vol).fit().into(holder.rank);


        holder.imv_chat.setTag(dataModel);
        holder.imv_chat.setOnClickListener(this);

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHoursChangeDialog(dataModel);

            }
        });*/

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_chat:

                CsoMyFollowerResponse.SeeFollower dataModel= (CsoMyFollowerResponse.SeeFollower) v.getTag();
                mSelectedIds = new ArrayList<>();
                mSelectedIds.add(dataModel.getUserEmail());
                createGroupChannel(dataModel.getUserFName()+ " "+dataModel.getUserLName(),mSelectedIds, true);
                ((CsoDashboardActivity) context).setHangoutProps();

                break;

        }
    }

    private class Holder {
        MyTextView tv_name, tv_date, tv_hrs;
        //ScaleRatingBar rb_rank;
        ImageView imv_chat, rank;
    }

    private void createGroupChannel(String nick_name,List<String> userIds, boolean distinct) {
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

  /*  private void openHoursChangeDialog(CsoMyFollowerResponse.Event dataModel) {

        String userHours, reqiredHours;
        userHours = dataModel.getUserHours();
        reqiredHours = dataModel.getUserHoursReq();

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.change_hours_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        Button cancel = view.findViewById(R.id.cancel);
        Button done = view.findViewById(R.id.done);
        MyTextView currentHours = view.findViewById(R.id.currentHours);
        MyTextView requiredHours = view.findViewById(R.id.requiredHours);
        MyTextInputEditText etHours = view.findViewById(R.id.etHours);

        dialog.show();

        currentHours.setText(userHours);
        requiredHours.setText(reqiredHours);

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

    private void hitUpdateHoursApi(String userHours, CsoMyFollowerResponse.Event dataModel) {
        myProgressDialog.show(context.getString(R.string.please_wait));
        csoMarkHoursViewModel.getCsoMarkHoursResponse(new CsoMarkHoursRequest(sharedPref.getUserId(), sharedPref.getUserType(), Utility.getDeviceId(context), "", userHours, dataModel.getUserId())).observe((LifecycleOwner) context, csoMarkHoursResponse -> {

            if (csoMarkHoursResponse != null) {
                if (csoMarkHoursResponse.getResStatus().equalsIgnoreCase("200")) {
                    dataModel.setUserHours(userHours);
                    notifyDataSetChanged();
                } else {
                    myToast.show(context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }
            myProgressDialog.dismiss();
        });
    }*/
}
