package com.ztp.app.View.Fragment.CSO.Students;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoMyVolunteerResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CsoMarkHoursViewModel;

import java.util.Date;
import java.util.List;

class FollowerAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<CsoMyVolunteerResponse.ResData> dataList;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    CsoMarkHoursViewModel csoMarkHoursViewModel;
    SharedPref sharedPref;

    public FollowerAdapter(Context context, List<CsoMyVolunteerResponse.ResData> dataList) {
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
    public CsoMyVolunteerResponse.ResData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CsoMyVolunteerResponse.ResData dataModel = getItem(position);

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


        if (!dataModel.getUserGradDate().isEmpty()) {
            Date d = Utility.convertStringToDateWithoutTime(dataModel.getUserGradDate());
            holder.tv_date.setText(Utility.formatDateFull(d));
        }


        holder.tv_hrs.setText(dataModel.getUserHours()+" "+context.getString(R.string.hrs_small));

        if (dataModel.getUserRank().equalsIgnoreCase("1"))
            Picasso.with(context).load(R.drawable.rank_baby).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("2"))
            Picasso.with(context).load(R.drawable.rank_grownup).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("3"))
            Picasso.with(context).load(R.drawable.rank_knight).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("4"))
            Picasso.with(context).load(R.drawable.rank_royalty).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase("5"))
            Picasso.with(context).load(R.drawable.rank_warrior).fit().into(holder.rank);
        else if (dataModel.getUserRank().equalsIgnoreCase(""))
            Picasso.with(context).load(R.drawable.not_available).fit().into(holder.rank);


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

                break;

        }
    }

    private class Holder {
        MyTextView tv_name, tv_date, tv_hrs;
        //ScaleRatingBar rb_rank;
        ImageView imv_chat, rank;
    }

  /*  private void openHoursChangeDialog(CsoMyVolunteerResponse.ResData dataModel) {

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

    private void hitUpdateHoursApi(String userHours, CsoMyVolunteerResponse.ResData dataModel) {
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
