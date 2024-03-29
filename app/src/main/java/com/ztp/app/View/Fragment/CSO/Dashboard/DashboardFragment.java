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
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCalendarData;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetUpcomingEvent;
import com.ztp.app.Data.Local.Room.Async.Save.DBSaveCalendarData;
import com.ztp.app.Data.Local.Room.Async.Save.DBSaveUpcomingEvent;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoDashboardCombinedRequest;
import com.ztp.app.Data.Remote.Model.Request.InsertFirebaseIdRequest;
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
import com.ztp.app.Viewmodel.InsertFirebaseIdViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment implements View.OnClickListener {


    ListView lv_upcoming_event;
    TextView tv_days, tv_hours, tv_minutes, tv_seconds,vol_txt;
    Context context;
    private Handler handler;
    private Runnable runnable;
    TextView upcoming_text;
    LinearLayout bottomLayout;
    boolean theme;
    SharedPref sharedPref;
    ImageView vol_img,con_img;
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
    LinearLayout vol_req, timerLayout,con_layout;
    String event_id, event_heading;
    List<HashMap<String, String>> shiftDataList = new ArrayList<>();
    boolean flag = false;
    int countDown;
    DBGetUpcomingEvent dbGetUpcomingEvent;
    DBGetCalendarData dbGetCalendarData;
    InsertFirebaseIdViewModel insertFirebaseIdViewModel;
    TextView con_txt;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public DashboardFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbGetCalendarData = new DBGetCalendarData(context);
        dbGetUpcomingEvent = new DBGetUpcomingEvent(context);
        View v = inflater.inflate(R.layout.fragment_dashboard_cso, container, false);
        countDown = 0;
        handler = new Handler();
        lv_upcoming_event = v.findViewById(R.id.lv_upcoming_event);
        tv_days = v.findViewById(R.id.days);
        tv_hours = v.findViewById(R.id.hours);
        tv_minutes = v.findViewById(R.id.minutes);
        tv_seconds = v.findViewById(R.id.seconds);
        upcoming_text = v.findViewById(R.id.upcoming_text);
        bottomLayout = v.findViewById(R.id.bottomLayout);
        sharedPref = SharedPref.getInstance(context);
        scrollView = v.findViewById(R.id.scrollView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeToRefresh);
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
        con_layout = v.findViewById(R.id.con_layout);
        vol_req.setOnClickListener(this);
        insertFirebaseIdViewModel = ViewModelProviders.of((FragmentActivity) context).get(InsertFirebaseIdViewModel.class);

        if (theme) {
            bottomLayout.setBackgroundColor(getResources().getColor(R.color.white));
            upcoming_text.setBackgroundColor(getResources().getColor(R.color.white));
            upcoming_text.setTextColor(getResources().getColor(R.color.black));
            vol_img.setColorFilter(getResources().getColor(R.color.black));
            con_img.setColorFilter(getResources().getColor(R.color.black));
            vol_txt.setTextColor(getResources().getColor(R.color.black));
            con_txt.setTextColor(getResources().getColor(R.color.black));
            con_txt.setBackgroundColor(getResources().getColor(R.color.white));
            //con_layout.setBackgroundColor(getResources().getColor(R.color.black));

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
            con_txt.setBackgroundColor(getResources().getColor(R.color.black));
           // con_layout.setBackgroundColor(getResources().getColor(R.color.white));

            childLayout = inflater.inflate(R.layout.calendarview_day,
                    (ViewGroup) v.findViewById(R.id.cal));
            parentLayout.addView(childLayout);
            mCalendarView = (MaterialCalendarView) childLayout.findViewById(R.id.calendarViews);
        }

        Calendar instance = Calendar.getInstance();
        mCalendarView.setSelectedDate(instance.getTime());
        getCsoDashoardCombinedViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetCsoDashoardCombinedViewModel.class);

        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                try {
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
                            map.put("shift_task_name", mEventDays.get(i).getShiftTaskName());
                            shiftDataList.add(map);
                            flag = true;
                        }
                    }
                    if (flag) {
                        flag = false;
                        openDateDialog(shiftDataList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hitDashboardAPI();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return v;
    }


    public void hitInsertFirebaseId() {
        if (Utility.isNetworkAvailable(context)) {
            InsertFirebaseIdRequest insertFirebaseIdRequest = new InsertFirebaseIdRequest();
            insertFirebaseIdRequest.setUserDevice(FirebaseInstanceId.getInstance().getToken());
            insertFirebaseIdRequest.setUserEmail(sharedPref.getEmail());
            insertFirebaseIdRequest.setUserId(sharedPref.getUserId());
            insertFirebaseIdRequest.setUserType(sharedPref.getUserType());

            insertFirebaseIdViewModel.getInsertFirebaseIdResponse(insertFirebaseIdRequest).observe(this, insertFirebaseIdResponse -> {
                if (insertFirebaseIdResponse != null) {
                    if (insertFirebaseIdResponse.getResStatus().equalsIgnoreCase("200")) {
                        //myToast.show("Firebase registered successfully", Toast.LENGTH_SHORT, true);
                    } else if (insertFirebaseIdResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show("Firebase register failed", Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                }
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void openDateDialog(List<HashMap<String, String>> shiftDataList) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.shift_list_for_event_dialog, null);
        dialog.setContentView(view);
        //dialog.setCancelable(false);


        ListView shiftList = view.findViewById(R.id.shiftList);
        // Button close = view.findViewById(R.id.close);


        if (shiftDataList != null) {
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

    @Override
    public void onResume() {
        super.onResume();

        hitInsertFirebaseId();
        hitDashboardAPI();

       /* if (!sharedPref.getProfileImage().isEmpty()) {

            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(CsoDashboardActivity.user);

            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(CsoDashboardActivity.userNav);
        } else {
            Picasso.with(context).load(R.drawable.user_png).fit().into(CsoDashboardActivity.user);
            Picasso.with(context).load(R.drawable.user_png).fit().into(CsoDashboardActivity.userNav);

        }

        if (!sharedPref.getCoverImage().isEmpty()) {
            *//*Picasso.with(context).load(sharedPref.getCoverImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.back)
                    .error(R.drawable.back)
                    .into(CsoDashboardActivity.cover);*//*

            Picasso.with(context).load(sharedPref.getCoverImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.back)
                    .error(R.drawable.back)
                    .into(CsoDashboardActivity.coverNav);

        } else {
            //Picasso.with(context).load(R.drawable.back).fit().into(CsoDashboardActivity.cover);
            Picasso.with(context).load(R.drawable.back).fit().into(CsoDashboardActivity.coverNav);

        }*/


    }

    private void hitDashboardAPI()
    {
        if (Utility.isNetworkAvailable(context)) {

            myProgressDialog.show(getString(R.string.please_wait));

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;

            CsoDashboardCombinedRequest csoDashboardCombinedRequest = new CsoDashboardCombinedRequest();
            csoDashboardCombinedRequest.setUserId(sharedPref.getUserId());
            csoDashboardCombinedRequest.setEventMonth(String.valueOf(month));
            csoDashboardCombinedRequest.setEventYear(String.valueOf(year));
            csoDashboardCombinedRequest.setCountdownDate(Utility.getDashboardCurrentDateTime());

            Log.i("REQUEST", "" + new Gson().toJson(csoDashboardCombinedRequest));

            getCsoDashoardCombinedViewModel.getCsoDashboardCombinedResponse(csoDashboardCombinedRequest).observe((LifecycleOwner) context, new Observer<CsoDashboardCombinedResponse>() {
                @Override
                public void onChanged(@Nullable CsoDashboardCombinedResponse csoDashboardCombinedResponse) {

                    if (csoDashboardCombinedResponse != null) {
                        Log.i("RESPONSE", "" + new Gson().toJson(csoDashboardCombinedResponse));
                        if (csoDashboardCombinedResponse.getResStatus().equalsIgnoreCase("200")) {

                            if (csoDashboardCombinedResponse.getResData() != null) {

                                upcoming_text.setVisibility(View.VISIBLE);
                                lv_upcoming_event.setVisibility(View.VISIBLE);
                                timerLayout.setVisibility(View.VISIBLE);
                                //con_txt.setVisibility(View.VISIBLE);
                                con_layout.setVisibility(View.VISIBLE);
                                eventDataList = csoDashboardCombinedResponse.getResData().getEventData();
                                calendarDataList = csoDashboardCombinedResponse.getResData().getCalendarData();

                                if (csoDashboardCombinedResponse.getResData().getUserCoverPic() != null) {

                                    if (!sharedPref.getCoverImage().equalsIgnoreCase(csoDashboardCombinedResponse.getResData().getUserCoverPic())) {
                                        sharedPref.setCoverImage(csoDashboardCombinedResponse.getResData().getUserCoverPic());

                                        Picasso.with(context).load(sharedPref.getCoverImage())
                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                .placeholder(R.drawable.back)
                                                .error(R.drawable.back)
                                                .into(CsoDashboardActivity.cover);

                                        Picasso.with(context).load(sharedPref.getCoverImage())
                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                .placeholder(R.drawable.back)
                                                .error(R.drawable.back)
                                                .into(CsoDashboardActivity.coverNav);
                                    }
                                }

                                if (csoDashboardCombinedResponse.getResData().getUserProfilePic() != null) {
                                    if (!sharedPref.getProfileImage().equalsIgnoreCase(csoDashboardCombinedResponse.getResData().getUserProfilePic())) {
                                        sharedPref.setProfileImage(csoDashboardCombinedResponse.getResData().getUserProfilePic());

                                        Picasso.with(context).load(sharedPref.getProfileImage())
                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                .placeholder(R.drawable.user_png)
                                                .error(R.drawable.user_png)
                                                .into(CsoDashboardActivity.user);

                                        Picasso.with(context).load(sharedPref.getProfileImage())
                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                .placeholder(R.drawable.user_png)
                                                .error(R.drawable.user_png)
                                                .into(CsoDashboardActivity.userNav);
                                    }
                                }


                                if (calendarDataList != null && calendarDataList.size() > 0) {
                                    new DBSaveCalendarData(context, calendarDataList).execute();
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
                                        eventDayModel.setShiftTaskName(calendarDataList.get(i).getShiftTaskName());
                                        mEventDays.add(eventDayModel);
                                    }

                                    setEventsOnCalendar(mEventDays);
                                }
                                if (eventDataList != null) {
                                    new DBSaveUpcomingEvent(context, eventDataList).execute();
                                    UpcomingEventAdapter adapter = new UpcomingEventAdapter(context, eventDataList);
                                    lv_upcoming_event.setAdapter(adapter);
                                    if (eventDataList.size() > 3) {
                                        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) lv_upcoming_event.getLayoutParams();
                                        lp.height = 800;
                                        lv_upcoming_event.setLayoutParams(lp);
                                    } else {
                                        Utility.setListViewHeightBasedOnChildren(lv_upcoming_event);
                                    }
                                    handler.removeCallbacks(runnable);
                                    if (countDown < eventDataList.size())
                                        countDownStart(eventDataList.get(countDown).getShiftDate() + " " + eventDataList.get(countDown).getShiftStartTimeTimer());

                                } else {
                                    timerLayout.setVisibility(View.GONE);
                                    // con_txt.setVisibility(View.GONE);
                                    con_layout.setVisibility(View.GONE);
                                    upcoming_text.setVisibility(View.GONE);
                                    lv_upcoming_event.setVisibility(View.GONE);
                                }

                            } else {
                                myToast.show(getString(R.string.no_events_found), Toast.LENGTH_SHORT, false);
                                upcoming_text.setVisibility(View.GONE);
                                lv_upcoming_event.setVisibility(View.GONE);
                                timerLayout.setVisibility(View.GONE);
                                //con_txt.setVisibility(View.GONE);
                                con_layout.setVisibility(View.GONE);
                            }

                        } else if (csoDashboardCombinedResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(getString(R.string.no_events_found), Toast.LENGTH_SHORT, false);
                            upcoming_text.setVisibility(View.GONE);
                            lv_upcoming_event.setVisibility(View.GONE);
                            timerLayout.setVisibility(View.GONE);
                            //con_txt.setVisibility(View.GONE);
                            con_layout.setVisibility(View.GONE);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        upcoming_text.setVisibility(View.GONE);
                        lv_upcoming_event.setVisibility(View.GONE);
                        timerLayout.setVisibility(View.GONE);
                        //con_txt.setVisibility(View.GONE);
                        con_layout.setVisibility(View.GONE);
                    }
                    myProgressDialog.dismiss();
                }
            });

        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

            if (dbGetUpcomingEvent.getUpcomingEventList() != null) {

                for (int i = 0; i < dbGetUpcomingEvent.getUpcomingEventList().size(); i++) {
                    Date shiftStartDate = Utility.convertStringToDateTimer(dbGetUpcomingEvent.getUpcomingEventList().get(i).getShiftDate() + " " + dbGetUpcomingEvent.getUpcomingEventList().get(i).getShiftStartTime());
                    Date currentDate = Utility.convertStringToDateTimer(Utility.getCurrentTime());

                    if (shiftStartDate.before(currentDate)) {
                        dbGetUpcomingEvent.getUpcomingEventList().remove(i);
                    }
                }

                if (dbGetUpcomingEvent.getUpcomingEventList() != null) {
                    upcoming_text.setVisibility(View.VISIBLE);
                    timerLayout.setVisibility(View.VISIBLE);
                    //con_txt.setVisibility(View.VISIBLE);
                    con_layout.setVisibility(View.VISIBLE);
                    UpcomingEventAdapter adapter = new UpcomingEventAdapter(context, dbGetUpcomingEvent.getUpcomingEventList());
                    lv_upcoming_event.setAdapter(adapter);
                    if (dbGetUpcomingEvent.getUpcomingEventList().size() > 3) {
                        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) lv_upcoming_event.getLayoutParams();
                        lp.height = 700;
                        lv_upcoming_event.setLayoutParams(lp);
                    } else {
                        Utility.setListViewHeightBasedOnChildren(lv_upcoming_event);
                    }
                    handler.removeCallbacks(runnable);
                    if (dbGetUpcomingEvent.getUpcomingEventList().size() > 0)
                        countDownStart(dbGetUpcomingEvent.getUpcomingEventList().get(countDown).getShiftDate() + " " + dbGetUpcomingEvent.getUpcomingEventList().get(countDown).getShiftStartTime());
                } else {
                    upcoming_text.setVisibility(View.GONE);
                }

            } else {
                upcoming_text.setVisibility(View.GONE);
            }

            if (dbGetCalendarData.getCalendarDataList() != null) {

                mEventDays = new ArrayList<>();
                for (int i = 0; i < dbGetCalendarData.getCalendarDataList().size(); i++) {
                    int year = Integer.parseInt(dbGetCalendarData.getCalendarDataList().get(i).getYr());
                    int month = Integer.parseInt(dbGetCalendarData.getCalendarDataList().get(i).getMn());
                    int date = Integer.parseInt(dbGetCalendarData.getCalendarDataList().get(i).getDt());

                    EventDayModel eventDayModel = new EventDayModel();
                    eventDayModel.setCalendarDay(new CalendarDay(year, month - 1, date));
                    eventDayModel.setEventId(dbGetCalendarData.getCalendarDataList().get(i).getEventId());
                    eventDayModel.setEventHeading(dbGetCalendarData.getCalendarDataList().get(i).getEventHeading());
                    eventDayModel.setShiftDate(dbGetCalendarData.getCalendarDataList().get(i).getShiftDate());
                    eventDayModel.setShiftTask(dbGetCalendarData.getCalendarDataList().get(i).getShiftTask());
                    eventDayModel.setShiftStartTime(dbGetCalendarData.getCalendarDataList().get(i).getShiftStartTime());
                    eventDayModel.setShiftEndTime(dbGetCalendarData.getCalendarDataList().get(i).getShiftEndTime());
                    eventDayModel.setShiftId(dbGetCalendarData.getCalendarDataList().get(i).getShiftId());
                    mEventDays.add(eventDayModel);
                }
                setEventsOnCalendar(mEventDays);
            } else {
                lv_upcoming_event.setVisibility(View.GONE);
                upcoming_text.setVisibility(View.GONE);
            }

        }

        if (!sharedPref.getProfileImage().isEmpty()) {

            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(CsoDashboardActivity.user);

            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(CsoDashboardActivity.userNav);
        } else {
            Picasso.with(context).load(R.drawable.user_png).fit().into(CsoDashboardActivity.user);
            Picasso.with(context).load(R.drawable.user_png).fit().into(CsoDashboardActivity.userNav);

        }

        if (!sharedPref.getCoverImage().isEmpty()) {
           /* Picasso.with(context).load(sharedPref.getCoverImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.back)
                    .error(R.drawable.back)
                    .into(CsoDashboardActivity.cover);*/

            Picasso.with(context).load(sharedPref.getCoverImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.back)
                    .error(R.drawable.back)
                    .into(CsoDashboardActivity.coverNav);

        } else {
            //Picasso.with(context).load(R.drawable.back).fit().into(CsoDashboardActivity.cover);
            Picasso.with(context).load(R.drawable.back).fit().into(CsoDashboardActivity.coverNav);
        }
    }

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
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date futureDate = Utility.convertStringToDateTimerNew(future);//event date
                    Date currentDate = Utility.convertStringToDateTimerNew(Utility.getCurrentTime());

                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime() - currentDate.getTime();
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
                        if (eventDataList != null && eventDataList.get(countDown) != null)
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
