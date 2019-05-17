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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoDashboardCombinedRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Model.EventDayModel;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;
import com.ztp.app.Viewmodel.GetCsoDashoardCombinedViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment implements View.OnClickListener {


    ListView lv_upcoming_event;
    MyBoldTextView tv_days, tv_hours, tv_minutes, tv_seconds;
    Context context;
    private Handler handler;
    MyHeadingTextView upcoming_text;
    LinearLayout bottomLayout;
    boolean theme;
    SharedPref sharedPref;
    ImageView vol_img, con_img;
    MyTextView vol_txt, con_txt;
    List<EventDay> events = new ArrayList<>();
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    LinearLayout parentLayout;
    View childLayout;
    GetCsoDashoardCombinedViewModel getCsoDashoardCombinedViewModel;
    MyToast myToast;
    List<CsoDashboardCombinedResponse.CalendarData> calendarDataList = new ArrayList<>();
    List<CsoDashboardCombinedResponse.EventData> eventDataList = new ArrayList<>();
    List<CsoDashboardCombinedResponse.CountDownData> countDownDataList = new ArrayList<>();
    List<EventDayModel> eventDayModelList = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    ScrollView scrollView;
    LinearLayout vol_req;
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
        scrollView = v.findViewById(R.id.scrollView);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        vol_img = v.findViewById(R.id.vol_img);
        con_img = v.findViewById(R.id.con_img);
        vol_txt = v.findViewById(R.id.vol_txt);
        con_txt = v.findViewById(R.id.con_txt);
        parentLayout = v.findViewById(R.id.custom_calendar_container);
        theme = sharedPref.getTheme();
        vol_req = v.findViewById(R.id.vol_req);
        vol_req.setOnClickListener(this);

        getCsoDashoardCombinedViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetCsoDashoardCombinedViewModel.class);

        if (Utility.isNetworkAvailable(context)) {

            myProgressDialog.show(getString(R.string.please_wait));

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);

            getCsoDashoardCombinedViewModel.getCsoDashboardCombinedResponse(new CsoDashboardCombinedRequest(sharedPref.getUserId(), String.valueOf(month), String.valueOf(year), new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).format(new Date()))).observe((LifecycleOwner) context, new Observer<CsoDashboardCombinedResponse>() {
                @Override
                public void onChanged(@Nullable CsoDashboardCombinedResponse csoDashboardCombinedResponse) {

                    if (csoDashboardCombinedResponse != null) {
                        if (csoDashboardCombinedResponse.getResStatus().equalsIgnoreCase("200")) {
                            eventDataList = csoDashboardCombinedResponse.getResData().getEventData();
                            calendarDataList = csoDashboardCombinedResponse.getResData().getCalendarData();
                            countDownDataList = csoDashboardCombinedResponse.getResData().getCountDownData();

                            countDownStart(countDownDataList.get(0).getShiftDate() + " " + countDownDataList.get(0).getShiftStartTime());

                            if (theme) {
                                bottomLayout.setBackgroundColor(getResources().getColor(R.color.white));
                                upcoming_text.setBackgroundColor(getResources().getColor(R.color.white));
                                upcoming_text.setTextColor(getResources().getColor(R.color.black));
                                vol_img.setColorFilter(getResources().getColor(R.color.black));
                                con_img.setColorFilter(getResources().getColor(R.color.black));
                                vol_txt.setTextColor(getResources().getColor(R.color.black));
                                con_txt.setTextColor(getResources().getColor(R.color.black));

                                childLayout = inflater.inflate(R.layout.calendarview_night,
                                        (ViewGroup) v.findViewById(R.id.cal));
                                parentLayout.addView(childLayout);
                                mCalendarView = (CalendarView) childLayout.findViewById(R.id.calendarViews);
                            } else {

                                bottomLayout.setBackgroundColor(getResources().getColor(R.color.black));
                                upcoming_text.setBackgroundColor(getResources().getColor(R.color.black));
                                upcoming_text.setTextColor(getResources().getColor(R.color.white));
                                vol_img.setColorFilter(getResources().getColor(R.color.white));
                                con_img.setColorFilter(getResources().getColor(R.color.white));
                                vol_txt.setTextColor(getResources().getColor(R.color.white));
                                con_txt.setTextColor(getResources().getColor(R.color.white));

                                childLayout = inflater.inflate(R.layout.calendarview_day,
                                        (ViewGroup) v.findViewById(R.id.cal));
                                parentLayout.addView(childLayout);
                                mCalendarView = (CalendarView) childLayout.findViewById(R.id.calendarViews);
                            }

                            for (int i = 0; i < calendarDataList.size(); i++) {
                                Calendar calendar = Calendar.getInstance();
                                String year = calendarDataList.get(i).getYr();
                                String month = calendarDataList.get(i).getMn();
                                String date = calendarDataList.get(i).getDt();

                                calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
                                EventDay eventDay = new EventDay(calendar, R.drawable.ic_event);
                                events.add(eventDay);
                                mCalendarView.setEvents(events);

                                eventDayModelList.add(new EventDayModel(eventDay, calendarDataList.get(i).getEventId(), calendarDataList.get(i).getEventHeading()));

                            }

                            try {
                                mCalendarView.setDate(new Date());
                            } catch (OutOfDateRangeException e) {
                                e.printStackTrace();
                            }

                            mCalendarView.setOnDayClickListener(eventDay -> {

                                String event_id = "";
                                for (int i = 0; i < eventDayModelList.size(); i++) {
                                    EventDayModel eventDayModel = eventDayModelList.get(i);
                                    if (eventDayModel.getEventDay() == eventDay) {
                                        event_id = eventDayModel.getEventId();
                                        break;
                                    }
                                }


                                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("event_id", event_id);
                                eventDetailFragment.setArguments(bundle);
                                Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");


                            });


                            UpcomingEventAdapter adapter = new UpcomingEventAdapter(context, eventDataList);
                            lv_upcoming_event.setAdapter(adapter);

                            if(eventDataList.size()>3)
                            {
                                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) lv_upcoming_event.getLayoutParams();
                                lp.height = 800;
                                lv_upcoming_event.setLayoutParams(lp);
                            }
                            else
                            {
                                Utility.setListViewHeightBasedOnChildren(lv_upcoming_event);
                            }

                        } else {
                            myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();

                }
            });

        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }

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

    public void countDownStart(String future) {
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date futureDate = Utility.convertStringToDate(future);//event date
                    Date currentDate = Utility.convertStringToDate(Utility.getCurrentTime());
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


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.vol_req:
                ((CsoDashboardActivity) context).setStudentFragmentFrom();
                break;
        }
    }
}
