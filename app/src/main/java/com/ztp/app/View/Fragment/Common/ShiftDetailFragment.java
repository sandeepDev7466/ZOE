package com.ztp.app.View.Fragment.Common;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Request.GetShiftDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetShiftDetailViewModel;

public class ShiftDetailFragment extends Fragment {

    Context context;
    ImageView rankImage;
    MyTextView shift_task, vol_req, date, time;
    GetShiftDetailViewModel getShiftDetailViewModel;
    String shift_id = null, endTimeAmPm, startTimeAmPm;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    RoomDB roomDB;

    public ShiftDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shift_detail, container, false);
        roomDB = RoomDB.getInstance(context);
        init(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.get("shift_id") != null)

                shift_id = bundle.getString("shift_id");

        }

        if (shift_id != null) {
            if (Utility.isNetworkAvailable(context)) {
                myProgressDialog.show(getString(R.string.please_wait));
                getShiftDetailViewModel.getShiftDetailResponseLiveData(context, new GetShiftDetailRequest(shift_id)).observe((LifecycleOwner) context, getShiftDetailResponse -> {
                    if (getShiftDetailResponse != null) {
                        if (getShiftDetailResponse.getResStatus().equalsIgnoreCase("200")) {
                            setData(getShiftDetailResponse.getShiftDetail());
                        } else if (getShiftDetailResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();

                });
            } else {
                //myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

                new AsyncTask<Void, Void, GetShiftDetailResponse.ShiftDetail>() {
                    @Override
                    protected void onPostExecute(GetShiftDetailResponse.ShiftDetail shiftDetail) {
                        if (shiftDetail != null)
                            setData(shiftDetail);
                    }

                    @Override
                    protected GetShiftDetailResponse.ShiftDetail doInBackground(Void... voids) {
                        return roomDB.getShiftDetailResponseDao().getShiftDetailFromId(shift_id);
                    }
                }.execute();
            }
        }

        return view;
    }

    private void init(View view) {

        getShiftDetailViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetShiftDetailViewModel.class);
        rankImage = view.findViewById(R.id.rankImage);
        shift_task = view.findViewById(R.id.shift_task);
        vol_req = view.findViewById(R.id.vol_req);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        //add_date = view.findViewById(R.id.add_date);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setData(GetShiftDetailResponse.ShiftDetail shiftDetail) {
        if (shiftDetail != null) {
            shift_task.setText(shiftDetail.getShiftTaskName());

            if (shiftDetail.getShiftRank().equalsIgnoreCase("1"))
                Picasso.with(context).load(R.drawable.rank_one_vol).into(rankImage);
            else if (shiftDetail.getShiftRank().equalsIgnoreCase("2"))
                Picasso.with(context).load(R.drawable.rank_two_vol).into(rankImage);
            else if (shiftDetail.getShiftRank().equalsIgnoreCase("3"))
                Picasso.with(context).load(R.drawable.rank_three_vol).into(rankImage);
            else if (shiftDetail.getShiftRank().equalsIgnoreCase("4"))
                Picasso.with(context).load(R.drawable.rank_four_vol).into(rankImage);
            else if (shiftDetail.getShiftRank().equalsIgnoreCase("5"))
                Picasso.with(context).load(R.drawable.rank_five_vol).into(rankImage);
            else if(shiftDetail.getShiftRank().equalsIgnoreCase(""))
                Picasso.with(context).load(R.drawable.rank_five_vol).into(rankImage);

            date.setText(shiftDetail.getShiftDate());
            time.setText(shiftDetail.getShiftStartTimeFormat() + " - " + shiftDetail.getShiftEndTimeFormat());
           // time.setText(Utility.getTimeAmPm(shiftDetail.getShiftStartTime()) + " - " + Utility.getTimeAmPm(shiftDetail.getShiftEndTime()));
            vol_req.setText(shiftDetail.getShiftVolReq());
        }
    }
}
