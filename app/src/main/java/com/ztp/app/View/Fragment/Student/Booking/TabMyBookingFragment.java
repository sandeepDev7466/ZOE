package com.ztp.app.View.Fragment.Student.Booking;

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
import android.widget.ListView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.VolunteerAllRequestViewModel;

public class TabMyBookingFragment extends Fragment {

    Context context;
    ListView listView;
    MyTextView noData;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    VolunteerAllRequestViewModel volunteerAllRequestViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_my_booking, container, false);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        volunteerAllRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerAllRequestViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            volunteerAllRequestViewModel.getVolunteerAllRequetResponse(new VolunteerAllRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, new Observer<VolunteerAllResponse>() {
                @Override
                public void onChanged(@Nullable VolunteerAllResponse volunteerAllResponse) {

                    if (volunteerAllResponse != null) {
                        if (volunteerAllResponse.getResStatus().equalsIgnoreCase("200")) {
                            if (volunteerAllResponse.getResData() != null && volunteerAllResponse.getResData().size() > 0) {
                                listView.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.INVISIBLE);
                                MyBookingAdapter myBookingAdapter = new MyBookingAdapter(context, volunteerAllResponse.getResData());
                                listView.setAdapter(myBookingAdapter);
                            } else {
                                listView.setVisibility(View.INVISIBLE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        } else {
                            listView.setVisibility(View.INVISIBLE);
                            noData.setVisibility(View.VISIBLE);
                            // myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT,false);
                        }
                    } else {
                        listView.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                    myProgressDialog.dismiss();
                }
            });
        } else {
            listView.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.VISIBLE);
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }
}
