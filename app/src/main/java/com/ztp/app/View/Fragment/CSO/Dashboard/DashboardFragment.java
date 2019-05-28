package com.ztp.app.View.Fragment.CSO.Dashboard;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoDashboardCombinedRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.Model.EventDayModel;
import com.ztp.app.R;
import com.ztp.app.Utils.EventDecorator;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.Viewmodel.GetCsoDashoardCombinedViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment implements View.OnClickListener {


    ListView lv_upcoming_event;
    MyBoldTextView tv_days, tv_hours, tv_minutes, tv_seconds;
    Context context;
    private Handler handler;
    private Runnable runnable;
    MyHeadingTextView upcoming_text;
    LinearLayout bottomLayout;
    boolean theme;
    SharedPref sharedPref;
    ImageView vol_img, con_img;
    MyTextView vol_txt, con_txt;
    MaterialCalendarView mCalendarView;
    private List<EventDayModel> mEventDays = new ArrayList<>();
    private List<CalendarDay> calendarDayList = new ArrayList<>();
    LinearLayout parentLayout;
    View childLayout;
    GetCsoDashoardCombinedViewModel getCsoDashoardCombinedViewModel;
    MyToast myToast;
    List<CsoDashboardCombinedResponse.CalendarData> calendarDataList = new ArrayList<>();
    List<CsoDashboardCombinedResponse.EventData> eventDataList = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    ScrollView scrollView;
    LinearLayout vol_req,timerLayout;
    String event_id, event_heading;
    List<HashMap<String, String>> shiftDataList = new ArrayList<>();
    boolean flag = false;
    int countDown = 0;

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
        timerLayout = v.findViewById(R.id.timerLayout);
        vol_req.setOnClickListener(this);

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
            mCalendarView = (MaterialCalendarView) childLayout.findViewById(R.id.calendarViews);
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
            mCalendarView = (MaterialCalendarView) childLayout.findViewById(R.id.calendarViews);
        }

        Calendar instance = Calendar.getInstance();
        mCalendarView.setSelectedDate(instance.getTime());


        getCsoDashoardCombinedViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetCsoDashoardCombinedViewModel.class);

        if (Utility.isNetworkAvailable(context)) {

            myProgressDialog.show(getString(R.string.please_wait));

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;

            CsoDashboardCombinedRequest csoDashboardCombinedRequest = new CsoDashboardCombinedRequest();
            csoDashboardCombinedRequest.setUserId(sharedPref.getUserId());
            csoDashboardCombinedRequest.setEventMonth(String.valueOf(month));
            csoDashboardCombinedRequest.setEventYear(String.valueOf(year));
            csoDashboardCombinedRequest.setCountdownDate(new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).format(new Date()));

            Log.i("REQUEST", "" + new Gson().toJson(csoDashboardCombinedRequest));

            getCsoDashoardCombinedViewModel.getCsoDashboardCombinedResponse(csoDashboardCombinedRequest).observe((LifecycleOwner) context, new Observer<CsoDashboardCombinedResponse>() {
                @Override
                public void onChanged(@Nullable CsoDashboardCombinedResponse csoDashboardCombinedResponse) {

                    if (csoDashboardCombinedResponse != null) {
                        Log.i("RESPONSE", "" + new Gson().toJson(csoDashboardCombinedResponse));
                        if (csoDashboardCombinedResponse.getResStatus().equalsIgnoreCase("200")) {

                            if(csoDashboardCombinedResponse.getResData()!=null) {

                                upcoming_text.setVisibility(View.VISIBLE);
                                lv_upcoming_event.setVisibility(View.VISIBLE);
                                timerLayout.setVisibility(View.VISIBLE);
                                eventDataList = csoDashboardCombinedResponse.getResData().getEventData();
                                calendarDataList = csoDashboardCombinedResponse.getResData().getCalendarData();
                                //countDownDataList = csoDashboardCombinedResponse.getResData().getCountDownData();

                                //startCountDown(eventDataList);

                                if(calendarDataList!=null) {
                                    mEventDays = new ArrayList<>();
                                    for (int i = 0; i < calendarDataList.size(); i++) {
                                        int year = Integer.parseInt(calendarDataList.get(i).getYr());
                                        int month = Integer.parseInt(calendarDataList.get(i).getMn());
                                        int date = Integer.parseInt(calendarDataList.get(i).getDt());

                                        EventDayModel eventDayModel = new EventDayModel();
                                        eventDayModel.setCalendarDay(new CalendarDay(year, month - 1, date));
                                        eventDayModel.setEventId(calendarDataList.get(i).getEventId());
                                        eventDayModel.setEventHeading(calendarDataList.get(i).getEventHeading());
                                        eventDayModel.setShiftDate(calendarDataList.get(i).getShiftDate());
                                        eventDayModel.setShiftTask(calendarDataList.get(i).getShiftTask());
                                        eventDayModel.setShiftStartTime(calendarDataList.get(i).getShiftStartTime());
                                        eventDayModel.setShiftEndTime(calendarDataList.get(i).getShiftEndTime());
                                        eventDayModel.setShiftId(calendarDataList.get(i).getShiftId());

                                        mEventDays.add(eventDayModel);

                                    }

                                    setEventsOnCalendar(mEventDays);
                                }

                                if(eventDataList!=null) {
                                    UpcomingEventAdapter adapter = new UpcomingEventAdapter(context, eventDataList);
                                    lv_upcoming_event.setAdapter(adapter);
                                    if (eventDataList.size() > 3) {
                                        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) lv_upcoming_event.getLayoutParams();
                                        lp.height = 800;
                                        lv_upcoming_event.setLayoutParams(lp);
                                    } else {
                                        Utility.setListViewHeightBasedOnChildren(lv_upcoming_event);
                                    }

                                    countDownStart(eventDataList.get(countDown).getShiftDate() + " " + eventDataList.get(countDown).getShiftStartTime());

                                }
                                else
                                {
                                    tv_days.setText("00");
                                    tv_hours.setText("00");
                                    tv_minutes.setText("00");
                                    tv_seconds.setText("00");
                                    upcoming_text.setVisibility(View.GONE);
                                    lv_upcoming_event.setVisibility(View.GONE);
                                }



                                mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                                    @Override
                                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                                        HashMap<String, String> map;
                                        shiftDataList = new ArrayList<>();
                                        for (int i = 0; i < mEventDays.size(); i++) {
                                            if (mEventDays.get(i).getCalendarDay().getDay() == date.getDay() && mEventDays.get(i).getCalendarDay().getMonth() == date.getMonth() && mEventDays.get(i).getCalendarDay().getYear() == date.getYear()) {
                                                map = new HashMap<>();
                                                event_id = mEventDays.get(i).getEventId();
                                                event_heading = mEventDays.get(i).getEventHeading();
                                                map.put("shift_task", mEventDays.get(i).getShiftTask());
                                                map.put("shift_date", mEventDays.get(i).getShiftDate());
                                                map.put("shift_start_time", mEventDays.get(i).getShiftStartTime());
                                                map.put("shift_end_time", mEventDays.get(i).getShiftEndTime());
                                                map.put("event_heading", event_heading);
                                                map.put("event_id", event_id);
                                                map.put("shift_id", mEventDays.get(i).getShiftId());
                                                shiftDataList.add(map);
                                                flag = true;
                                                //break;
                                            }
                                        }

                                        if (flag) {
                                            flag = false;
                                            openDateDialog(shiftDataList);
                                        }

                                    /*if(event_id!=null && event_heading!=null) {
                                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("event_id", event_id);
                                        eventDetailFragment.setArguments(bundle);
                                        Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");
                                    }*/

                                    }
                                });
                            }
                            else
                            {
                                myToast.show(getString(R.string.no_events_found), Toast.LENGTH_SHORT, false);
                                upcoming_text.setVisibility(View.GONE);
                                lv_upcoming_event.setVisibility(View.GONE);
                                timerLayout.setVisibility(View.GONE);
                            }

                        } else if(csoDashboardCombinedResponse.getResStatus().equalsIgnoreCase("401")){
                            myToast.show(getString(R.string.no_events_found), Toast.LENGTH_SHORT, false);
                            upcoming_text.setVisibility(View.GONE);
                            lv_upcoming_event.setVisibility(View.GONE);
                            timerLayout.setVisibility(View.GONE);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        upcoming_text.setVisibility(View.GONE);
                        lv_upcoming_event.setVisibility(View.GONE);
                        timerLayout.setVisibility(View.GONE);
                    }

                    myProgressDialog.dismiss();

                }
            });

        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            upcoming_text.setVisibility(View.GONE);
            lv_upcoming_event.setVisibility(View.GONE);
        }

        return v;
    }

    /*private void startCountDown(List<CsoDashboardCombinedResponse.EventData> eventDataList) {

        countDownStart(countDownDataList.get(0).getShiftDate() + " " + countDownDataList.get(0).getShiftStartTime());

    }
*/
    private void openDateDialog(List<HashMap<String, String>> shiftDataList) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.shift_list_for_event_dialog, null);
        dialog.setContentView(view);
        //dialog.setCancelable(false);


        ListView shiftList = view.findViewById(R.id.shiftList);
        // Button close = view.findViewById(R.id.close);


        if(shiftDataList!=null) {
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataList, "Dashboard", dialog);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        }


        /*if (shiftDataList.size() > 1) {
            shiftDataListTimeSorted = sortAccordingToTime(shiftDataList);
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataListTimeSorted);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        } else {
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataList);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        }*/
        //Utility.setListViewHeightBasedOnChildren(shiftList);

           /* close.setOnClickListener(v -> {
                dialog.dismiss();
            });
*/
        dialog.show();
    }

   /* private List<HashMap<String, String>> sortAccordingToTime(List<HashMap<String, String>> shiftDataList) {

        List<HashMap<String, String>> shiftDataListSorted = new ArrayList<>();

       for(int i=0;i<shiftDataList.size();i++)
       {
           for(int j=i+1;j<shiftDataList.size();j++)
           {
               Date low = Utility.convertTimeToDate(shiftDataList.get(i).get("shift_start_time"));
               Date high = Utility.convertTimeToDate(shiftDataList.get(j).get("shift_end_time"));

               if(low.before(high))
               {
                   shiftDataListSorted.add(shiftDataList.get(i));
               }
           }
       }


       *//* for (int i = 0; i < shiftDataList.size()-1; i++) {
            for (int j = 0; j < shiftDataList.size() - i - 1; j++) {
                Date low = Utility.convertTimeToDate(shiftDataList.get(j).get("shift_start_time"));
                Date high = Utility.convertTimeToDate(shiftDataList.get(j+1).get("shift_end_time"));

                int hour1 = Integer.parseInt(shiftDataList.get(j).get("shift_start_time").split(":")[0]);
                int minute1 = Integer.parseInt(shiftDataList.get(j).get("shift_start_time").split(":")[1]);
                int seconds1 = Integer.parseInt(shiftDataList.get(j).get("shift_start_time").split(":")[2]);

                int tempStart = (60 * minute1) + (3600 * hour1) + seconds1;

                int hour2 = Integer.parseInt(shiftDataList.get(j+1).get("shift_end_time").split(":")[0]);
                int minute2 = Integer.parseInt(shiftDataList.get(j+1).get("shift_end_time").split(":")[1]);
                int seconds2 = Integer.parseInt(shiftDataList.get(j+1).get("shift_end_time").split(":")[2]);

                int tempEnd= (60 * minute2) + (3600 * hour2) + seconds2;

                if (tempStart > tempEnd) {
                    *//**//*HashMap<String, String> map  = shiftDataList.get(j);
                    shiftDataList.set(j,shiftDataList.get(j+1));
                    shiftDataList.set(j+1,map);*//**//*

                    Collections.swap(shiftDataList, j, j+1);
                }
            }
        }*//*

        return shiftDataListSorted;
    }*/

    private void setEventsOnCalendar(List<EventDayModel> eventDayModelList) {

        for (int i = 0; i < eventDayModelList.size(); i++) {
            CalendarDay day = eventDayModelList.get(i).getCalendarDay();
            calendarDayList.add(day);
            mCalendarView.addDecorator(new EventDecorator(getResources().getColor(R.color.blue_light), calendarDayList));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CsoDashboardActivity.ADD_NOTE && resultCode == RESULT_OK) {
           /* try {
                MyEventDay myEventDay = data.getParcelableExtra(CsoDashboardActivity.RESULT);
                mCalendarView.setDate(myEventDay.getCalendar());
                mEventDays.add(myEventDay);
                mCalendarView.setEvents(mEventDays);
            } catch (Exception e) {
                System.out.print(e);
            }*/
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void countDownStart(String future) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date futureDate = Utility.convertStringToDateTimer(future);//event date
                    Date currentDate = Utility.convertStringToDateTimer(Utility.getCurrentTime());
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
                        handler.removeCallbacks(runnable);
                        countDown++;
                        if(eventDataList != null && eventDataList.get(countDown) != null)
                        countDownStart(eventDataList.get(countDown).getShiftDate() + " " + eventDataList.get(countDown).getShiftStartTime());
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
        switch (v.getId()) {
            case R.id.vol_req:
                ((CsoDashboardActivity) context).setStudentFragmentFrom();
                break;
        }
    }
}
