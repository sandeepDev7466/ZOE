package com.ztp.app.View.Fragment.Volunteer.Target;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Local.Room.Async.Get.DBGetTarget;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.VolunteerTargetRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;
import com.ztp.app.Helper.CircleProgressBar;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.VolunteerTargetViewModel;

public class MyTargetFragment extends Fragment {

    Context context;
    MyTextView tv_hrs, tv_csoHrs, tv_gpa, tv_gpaTarget;
    LinearLayout gpaLayout;
    SharedPref sharedPref;
    VolunteerTargetViewModel volunteerTargetViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    CircleProgressBar hrs_progress;
    DBGetTarget dbGetTarget;

    public MyTargetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbGetTarget = new DBGetTarget(context);
        View view = inflater.inflate(R.layout.fragment_mytarget, container, false);
        tv_hrs = view.findViewById(R.id.tv_hrs);
        tv_csoHrs = view.findViewById(R.id.tv_csoHrs);
        hrs_progress = view.findViewById(R.id.hrs_progress);
        tv_gpa = view.findViewById(R.id.tv_gpa);
        tv_gpaTarget = view.findViewById(R.id.tv_gpaTarget);
        gpaLayout = view.findViewById(R.id.gpaLayout);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        volunteerTargetViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerTargetViewModel.class);
        //callAPI();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void callAPI() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            volunteerTargetViewModel.getVolunteerTarget(context, new VolunteerTargetRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, new Observer<VolunteerTargetResponse>() {
                @Override
                public void onChanged(@Nullable VolunteerTargetResponse volunteerTargetResponse) {

                    if (volunteerTargetResponse != null) {
                        if (volunteerTargetResponse.getResStatus().equalsIgnoreCase("200")) {

                            if (volunteerTargetResponse.getTargetData() != null && volunteerTargetResponse.getTargetData().size() > 0) {
                                for (VolunteerTargetResponse.TargetData data : volunteerTargetResponse.getTargetData()) {
                                    setVolunteer(data.getVol_hours(), data.getVol_hours_req());
                                }
                                myProgressDialog.dismiss();
                            } else {

                                myProgressDialog.dismiss();
                            }
                        } else if (volunteerTargetResponse.getResStatus().equalsIgnoreCase("401")) {

                            myProgressDialog.dismiss();
                        }

                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }
                }
            });

        } else {

            if(dbGetTarget.getTargetList()!=null && dbGetTarget.getTargetList().size()>0)
            {
                for (VolunteerTargetResponse.TargetData data : dbGetTarget.getTargetList()) {
                    setVolunteer(data.getVol_hours(), data.getVol_hours_req());
                }
            }
            else
            {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }
        }
    }

    private void setVolunteer(String current, String target) {
        gpaLayout.setAlpha(Constants.alpha);
        String hrs = String.format(getString(R.string.CSO_Target), current, target);
        tv_csoHrs.setText(hrs);
        tv_hrs.setText(current + "%");
        hrs_progress.setMax(Integer.parseInt(target));
        hrs_progress.setProgress(Integer.parseInt(current));

        String gpa = String.format(getString(R.string.GPA_Target), String.valueOf(0), String.valueOf(0));
        tv_gpaTarget.setText(gpa);
    }
}
