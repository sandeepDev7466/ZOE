package com.ztp.app.View.Fragment.Student.Extra;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.VolunteerDashboardCombineRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.VolunteerDashboardCombineViewModel;

public class UpcomingEventsFragment extends Fragment {

    Context context;
    LinearLayout mainLayout;
    MyTextView noData;
    ListView lv_upcoming_event;
    MyToast myToast;
    VolunteerDashboardCombineViewModel volunteerDashboardCombineViewModel;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    public UpcomingEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        noData = view.findViewById(R.id.noData);
        lv_upcoming_event = view.findViewById(R.id.lv_upcoming_event);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        volunteerDashboardCombineViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerDashboardCombineViewModel.class);
        if(Utility.isNetworkAvailable(context))
        {
            myProgressDialog.show(getString(R.string.please_wait));
            VolunteerDashboardCombineRequest volunteerDashboardCombineRequest = new VolunteerDashboardCombineRequest();
            volunteerDashboardCombineRequest.setUserId(/*"V201905241i2UEEkNxIF"*/sharedPref.getUserId());
            volunteerDashboardCombineRequest.setListDate(/*"05-01-2019"*/Utility.getCurrentDate());

            Log.i("REQUEST",""+new Gson().toJson(volunteerDashboardCombineRequest));

            volunteerDashboardCombineViewModel.getVolunteerDashboardCombinedResponse(volunteerDashboardCombineRequest).observe((LifecycleOwner) context, volunteerDashboardCombineResponse -> {

                if(volunteerDashboardCombineResponse != null)
                {
                    Log.i("REQUEST",""+new Gson().toJson(volunteerDashboardCombineResponse));
                    if(volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        if(volunteerDashboardCombineResponse.getResData()!=null && volunteerDashboardCombineResponse.getResData().getEventData().size()>0)
                        {
                            mainLayout.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);

                            VolunteerUpcomingEventAdapter adapter = new VolunteerUpcomingEventAdapter(context, volunteerDashboardCombineResponse.getResData().getEventData());
                            lv_upcoming_event.setAdapter(adapter);

                        }
                        else
                        {
                            mainLayout.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }
                    }
                    else if(volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        mainLayout.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    mainLayout.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    //myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT,false);
                }
                myProgressDialog.dismiss();

            });
        }
        else
        {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT,false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
