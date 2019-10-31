package com.ztp.app.View.Fragment.Volunteer.Extra;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.util.List;

public class UpcomingEventsFragment extends Fragment {

    Context context;
    LinearLayout mainLayout, upcoming_event_layout;
    MyTextView noData;
    MyHeadingTextView upcoming_event;
    ListView lv_upcoming_event;
    MyToast myToast;
    SharedPref sharedPref;

    public UpcomingEventsFragment() {
        // Required empty public constructor
    }

    boolean theme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        upcoming_event_layout = view.findViewById(R.id.upcoming_event_layout);
        noData = view.findViewById(R.id.noData);
        lv_upcoming_event = view.findViewById(R.id.lv_upcoming_event);
        upcoming_event = view.findViewById(R.id.upcoming_event);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        if (theme) {
            upcoming_event_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            upcoming_event.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            upcoming_event_layout.setBackgroundColor(context.getResources().getColor(R.color.black));
            upcoming_event.setTextColor(context.getResources().getColor(R.color.white));
        }


        Bundle bundle = getArguments();
        if (bundle != null) {
            List<VolunteerDashboardCombineResponse.EventData> eventDataList = (List<VolunteerDashboardCombineResponse.EventData>) bundle.getSerializable("event_list");

            if (eventDataList != null && eventDataList.size() > 0) {
                mainLayout.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
                VolunteerUpcomingEventAdapter adapter = new VolunteerUpcomingEventAdapter(context, eventDataList);
                lv_upcoming_event.setAdapter(adapter);
            } else {
                mainLayout.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }
        } else {
            mainLayout.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }

       /* myProgressDialog = new MyProgressDialog(context);
        volunteerDashboardCombineViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerDashboardCombineViewModel.class);
        if(Utility.isNetworkAvailable(context))
        {
            myProgressDialog.show(getString(R.string.please_wait));
            VolunteerDashboardCombineRequest volunteerDashboardCombineRequest = new VolunteerDashboardCombineRequest();
            volunteerDashboardCombineRequest.setUserId(*//*"V201905241i2UEEkNxIF"*//*sharedPref.getUserId());
            volunteerDashboardCombineRequest.setListDate(*//*"05-01-2019"*//*Utility.getCurrentDate());

            Log.i("REQUEST",""+new Gson().toJson(volunteerDashboardCombineRequest));

            volunteerDashboardCombineViewModel.getVolunteerDashboardCombinedResponse(volunteerDashboardCombineRequest).observe((LifecycleOwner) context, volunteerDashboardCombineResponse -> {

                if(volunteerDashboardCombineResponse != null)
                {
                    Log.i("REQUEST",""+new Gson().toJson(volunteerDashboardCombineResponse));
                    if(volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        if(volunteerDashboardCombineResponse.getShiftDetail()!=null && volunteerDashboardCombineResponse.getShiftDetail().getEventData().size()>0)
                        {
                            mainLayout.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);

                            VolunteerUpcomingEventAdapter adapter = new VolunteerUpcomingEventAdapter(context, volunteerDashboardCombineResponse.getShiftDetail().getEventData());
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
        }*/

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
