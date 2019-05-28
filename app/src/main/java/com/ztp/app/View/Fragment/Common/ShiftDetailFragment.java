package com.ztp.app.View.Fragment.Common;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Remote.Model.Request.GetShiftDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
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
    String shift_id = null;
    MyToast myToast;
    MyProgressDialog myProgressDialog;

    public ShiftDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shift_detail, container, false);
        init(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.get("shift_id") != null)

                shift_id = bundle.getString("shift_id");

        }

        if (shift_id != null) {
            if(Utility.isNetworkAvailable(context)) {
                myProgressDialog.show(getString(R.string.please_wait));
                getShiftDetailViewModel.getShiftDetailResponseLiveData(new GetShiftDetailRequest(shift_id)).observe((LifecycleOwner) context, getShiftDetailResponse -> {
                    if (getShiftDetailResponse != null) {
                        if (getShiftDetailResponse.getResStatus().equalsIgnoreCase("200")) {
                            setData(getShiftDetailResponse);
                        } else if(getShiftDetailResponse.getResStatus().equalsIgnoreCase("401")){
                            myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();

                });
            }
            else
            {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
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
    private void setData(GetShiftDetailResponse getShiftDetailResponse)
    {
        if(getShiftDetailResponse.getResData() != null) {
            shift_task.setText(getShiftDetailResponse.getResData().getShiftTask());
            if (getShiftDetailResponse.getResData().getShiftRank().equalsIgnoreCase("1"))
                Picasso.with(context).load(R.drawable.rank_baby).fit().into(rankImage);
            else if (getShiftDetailResponse.getResData().getShiftRank().equalsIgnoreCase("2"))
                Picasso.with(context).load(R.drawable.rank_grownup).fit().into(rankImage);
            else if (getShiftDetailResponse.getResData().getShiftRank().equalsIgnoreCase("3"))
                Picasso.with(context).load(R.drawable.rank_knight).fit().into(rankImage);
            else if (getShiftDetailResponse.getResData().getShiftRank().equalsIgnoreCase("4"))
                Picasso.with(context).load(R.drawable.rank_royalty).fit().into(rankImage);
            else if (getShiftDetailResponse.getResData().getShiftRank().equalsIgnoreCase("5"))
                Picasso.with(context).load(R.drawable.rank_warrior).fit().into(rankImage);

            date.setText(getShiftDetailResponse.getResData().getShiftDate());
            time.setText(getShiftDetailResponse.getResData().getShiftStartTime() + " - " + getShiftDetailResponse.getResData().getShiftEndTime());
            vol_req.setText(getShiftDetailResponse.getResData().getShiftVolReq());
        }

    }
}
