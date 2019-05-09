package com.ztp.app.View.Fragment.CSO.Dashboard;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetMonthEventDateRequest;
import com.ztp.app.Data.Remote.Model.Response.GetMonthEventDateResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.Viewmodel.GetMonthEventDateViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {


    ListView lv_upcoming_event;
    UpcomingEventModel upcomingEventModel;
    ArrayList<UpcomingEventModel> upcomingEventModelArrayList = new ArrayList<>();
    MyBoldTextView tv_days, tv_hours, tv_minutes, tv_seconds;
    Context context;
    private Handler handler;
    MyHeadingTextView upcoming_text;
    LinearLayout bottomLayout;
    boolean theme;
    SharedPref sharedPref;
    ImageView vol_img, con_img;
    MyTextView vol_txt, con_txt;
    List<EventDay> events;
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    LinearLayout parentLayout;
    View childLayout;
    GetMonthEventDateViewModel getMonthEventDateViewModel;
    MyToast myToast;
    List<GetMonthEventDateResponse.ResData> eventsList = new ArrayList<>();

    public DashboardFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard_cso, container, false);


        lv_upcoming_event = v.findViewById(R.id.lv_upcoming_event);
        tv_days = v.findViewById(R.id.days);
        tv_hours = v.findViewById(R.id.hours);
        tv_minutes = v.findViewById(R.id.minutes);
        tv_seconds = v.findViewById(R.id.seconds);
        upcoming_text = v.findViewById(R.id.upcoming_text);
        bottomLayout = v.findViewById(R.id.bottomLayout);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
       /* getMonthEventDateViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetMonthEventDateViewModel.class);


        if(Utility.isNetworkAvailable(context))
        {
            getMonthEventDateViewModel.getMonthEventDateResponse(new GetMonthEventDateRequest()).observe((LifecycleOwner) context, getMonthEventDateResponse -> {

                if(getMonthEventDateResponse!=null)
                {
                    if(getMonthEventDateResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        eventsList = getMonthEventDateResponse.getResData();
                    }
                    else
                    {
                        myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT,false);
                    }
                }
                else
                {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT,false);
                }

            });
        }
        else
        {
          myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT,false);
        }*/


        vol_img = v.findViewById(R.id.vol_img);
        con_img = v.findViewById(R.id.con_img);
        vol_txt = v.findViewById(R.id.vol_txt);
        con_txt = v.findViewById(R.id.con_txt);
        parentLayout = v.findViewById(R.id.custom_calendar_container);
        theme = sharedPref.getTheme();
        events = new ArrayList<>();

        if (theme) {
            bottomLayout.setBackgroundColor(getResources().getColor(R.color.white));
            upcoming_text.setBackgroundColor(getResources().getColor(R.color.white));
            upcoming_text.setTextColor(getResources().getColor(R.color.black));
            vol_img.setColorFilter(getResources().getColor(R.color.white));
            con_img.setColorFilter(getResources().getColor(R.color.white));
            vol_txt.setTextColor(getResources().getColor(R.color.black));
            con_txt.setTextColor(getResources().getColor(R.color.black));

            childLayout = inflater.inflate(R.layout.calendarview_night,
                    (ViewGroup) v.findViewById(R.id.cal));
            parentLayout.addView(childLayout);
            mCalendarView = (CalendarView) childLayout.findViewById(R.id.calendarViews);
            for (int i = 1; i < 10; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2019, 0, i);
                events.add(new EventDay(calendar, R.drawable.ic_calendars));
                mCalendarView.setEvents(events);
            }

            mCalendarView.setOnDayClickListener(new OnDayClickListener() {
                @Override
                public void onDayClick(EventDay eventDay) {

                }
            });


        } else {

            bottomLayout.setBackgroundColor(getResources().getColor(R.color.black));
            upcoming_text.setBackgroundColor(getResources().getColor(R.color.black));
            upcoming_text.setTextColor(getResources().getColor(R.color.white));
            vol_img.setColorFilter(getResources().getColor(R.color.black));
            con_img.setColorFilter(getResources().getColor(R.color.black));
            vol_txt.setTextColor(getResources().getColor(R.color.white));
            con_txt.setTextColor(getResources().getColor(R.color.white));

            childLayout = inflater.inflate(R.layout.calendarview_day,
                    (ViewGroup) v.findViewById(R.id.cal));
            parentLayout.addView(childLayout);
            mCalendarView = (CalendarView) childLayout.findViewById(R.id.calendarViews);
            for (int i = 1; i < 10; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2019, 0, i);
                events.add(new EventDay(calendar, R.drawable.ic_calendars));
                mCalendarView.setEvents(events);
            }

            mCalendarView.setOnDayClickListener(new OnDayClickListener() {
                @Override
                public void onDayClick(EventDay eventDay) {

                }
            });
        }


        countDownStart();

        upcomingEventModel = new UpcomingEventModel();

        upcomingEventModel.setName("Atlanta Mission Food Drive");
        upcomingEventModel.setDate("2");
        upcomingEventModel.setDay("Tuesday");
        upcomingEventModel.setTime("4pm - 5pm");
        upcomingEventModel.setMonth("APR");

        upcomingEventModelArrayList.add(upcomingEventModel);

        upcomingEventModel = new UpcomingEventModel();

        upcomingEventModel.setName("Atlanta Mission Food Drive");
        upcomingEventModel.setDate("10");
        upcomingEventModel.setDay("Wednesday");
        upcomingEventModel.setTime("4pm - 5pm");
        upcomingEventModel.setMonth("APR");

        upcomingEventModelArrayList.add(upcomingEventModel);

        upcomingEventModel = new UpcomingEventModel();

        upcomingEventModel.setName("Atlanta Mission Food Drive");
        upcomingEventModel.setDate("28");
        upcomingEventModel.setDay("Sunday");
        upcomingEventModel.setTime("4pm - 5pm");
        upcomingEventModel.setMonth("APR");

        upcomingEventModelArrayList.add(upcomingEventModel);
        UpcomingEventAdapter adapter = new UpcomingEventAdapter(upcomingEventModelArrayList, getActivity());
        lv_upcoming_event.setAdapter(adapter);


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CsoDashboardActivity.ADD_NOTE && resultCode == RESULT_OK) {
            try {
                MyEventDay myEventDay = data.getParcelableExtra(CsoDashboardActivity.RESULT);
                mCalendarView.setDate(myEventDay.getCalendar());
                mEventDays.add(myEventDay);
                mCalendarView.setEvents(mEventDays);
            } catch (Exception e) {
                System.out.print(e);
            }
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void countDownStart() {
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date futureDate = dateFormat.parse("2019-5-30");//event date
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        tv_days.setText(String.format(Locale.ENGLISH, "%02d", days));
                        tv_hours.setText(String.format(Locale.ENGLISH, "%02d", hours));
                        tv_minutes.setText(String.format(Locale.ENGLISH, "%02d", minutes));
                        tv_seconds.setText(String.format(Locale.ENGLISH, "%02d", seconds));
                    } else {
                        /*tvEventStart.setVisibility(View.VISIBLE);
                        tvEventStart.setText("The event started!");
                        textViewGone();*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }


}
