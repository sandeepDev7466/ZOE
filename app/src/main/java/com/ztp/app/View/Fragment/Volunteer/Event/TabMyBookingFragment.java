package com.ztp.app.View.Fragment.Volunteer.Event;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.sendbird.android.GroupChannel;
import com.sendbird.android.SendBirdException;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetAllVolunteer;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.Model.EventDayModel;
import com.ztp.app.R;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.EventDecorator;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;
import com.ztp.app.View.Fragment.Common.EventDescriptionFragment;
import com.ztp.app.Viewmodel.ChangeVolunteerStatusViewModel;
import com.ztp.app.Viewmodel.VolunteerAllRequestViewModel;
import com.ztp.app.Viewmodel.VolunteerMarkHoursViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TabMyBookingFragment extends Fragment {

    Context context;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    VolunteerAllRequestViewModel volunteerAllRequestViewModel;
    LinearLayout parentLayout;
    boolean theme;
    private MaterialCalendarView mCalendarView;
    View childLayout;
    ChangeVolunteerStatusViewModel changeVolunteerStatusViewModel;
    VolunteerMarkHoursViewModel volunteerMarkHoursViewModel;
    private List<EventDayModel> mEventDays = new ArrayList<>();
    private List<CalendarDay> calendarDayList = new ArrayList<>();
    List<VolunteerAllResponse.VolunteerResponse> volunteerResponseList = new ArrayList<>();
    List<HashMap<String, String>> shiftDataList = new ArrayList<>();
    String event_id, event_heading;
    int pos;
    boolean flag = false;
    String startTime, endTime;
    String map_status, map_id;
    public static final String EXTRA_NEW_CHANNEL_URL = "EXTRA_NEW_CHANNEL_URL";
    public static final String EXTRA_Name = "EXTRA_NAME";
    private List<String> mSelectedIds;
    DBGetAllVolunteer dbGetAllVolunteer;
    EventDecorator eventDecorator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbGetAllVolunteer = new DBGetAllVolunteer(context);
        View view = inflater.inflate(R.layout.fragment_tab_my_booking, container, false);
        sharedPref = SharedPref.getInstance(context);
        changeVolunteerStatusViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeVolunteerStatusViewModel.class);
        volunteerAllRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerAllRequestViewModel.class);
        volunteerMarkHoursViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerMarkHoursViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        parentLayout = view.findViewById(R.id.custom_calendar_container);
        theme = sharedPref.getTheme();

        if (theme) {
            childLayout = inflater.inflate(R.layout.calendarview_night,
                    (ViewGroup) view.findViewById(R.id.cal));
        } else {
            childLayout = inflater.inflate(R.layout.calendarview_day,
                    (ViewGroup) view.findViewById(R.id.cal));
        }
        mCalendarView = (MaterialCalendarView) childLayout.findViewById(R.id.calendarViews);
        Calendar instance = Calendar.getInstance();
        mCalendarView.setSelectedDate(instance.getTime());

        getBooking(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isAdded())
            this.context = context;
        else
            this.context = getActivity();

    }

    public void getBooking(boolean show) {

        if(isAdded() && context != null) {
            if (parentLayout.getChildCount() > 0)
                parentLayout.removeAllViews();
            parentLayout.addView(childLayout);
            if (Utility.isNetworkAvailable(context)) {
                if (show)
                    myProgressDialog.show(context.getString(R.string.please_wait));
                volunteerAllRequestViewModel.getVolunteerAllRequestResponse(context, new VolunteerAllRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, new Observer<VolunteerAllResponse>() {
                    @Override
                    public void onChanged(@Nullable VolunteerAllResponse volunteerAllResponse) {

                        if (volunteerAllResponse != null) {

                            Log.i("", "" + new Gson().toJson(volunteerAllResponse));
                            if (volunteerAllResponse.getResStatus().equalsIgnoreCase("200")) {

                                if (volunteerAllResponse.getResData() != null && volunteerAllResponse.getResData().size() > 0) {

                                    volunteerResponseList = volunteerAllResponse.getResData();
                                    setCalendar(volunteerAllResponse.getResData());
                                } else {
                                    myToast.show(context.getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);
                                }
                            } else if (volunteerAllResponse.getResStatus().equalsIgnoreCase("401")) {
                                //myToast.show(getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);
                                //setCalendar(volunteerAllResponse.getShiftDetail());
                            }

                        } else {
                            myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        }
                    }
                });
                myProgressDialog.dismiss();
            } else {
                //myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

                if (dbGetAllVolunteer.getAllVolunteerList() != null && dbGetAllVolunteer.getAllVolunteerList().size() > 0) {
                    volunteerResponseList = dbGetAllVolunteer.getAllVolunteerList();
                    setCalendar(dbGetAllVolunteer.getAllVolunteerList());
                } else {
                    //myToast.show(getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);
                    myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                }
            }
        }
    }

    private void setCalendar(List<VolunteerAllResponse.VolunteerResponse> resData) {

        mEventDays = new ArrayList<>();

        mCalendarView = (MaterialCalendarView) childLayout.findViewById(R.id.calendarViews);

        for (int i = 0; i < resData.size(); i++) {
            VolunteerAllResponse.VolunteerResponse data = resData.get(i);
            Calendar calendar = Calendar.getInstance();
            Date date = Utility.convertStringToDateWithoutTime(data.getShiftDate());
            String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
            int day = Integer.parseInt(String.valueOf(DateFormat.format("dd", date))); // 20
            String monthString = (String) DateFormat.format("MMM", date); // Jun
            int month = Integer.parseInt(String.valueOf(DateFormat.format("MM", date))); // 06
            int year = Integer.parseInt(String.valueOf(DateFormat.format("yyyy", date))); // 2013

            EventDayModel eventDayModel = new EventDayModel();
            eventDayModel.setCalendarDay(new CalendarDay(year, month - 1, day));
            eventDayModel.setEventId(resData.get(i).getEventId());
            eventDayModel.setEventHeading(resData.get(i).getEventHeading());
            eventDayModel.setShiftDate(resData.get(i).getShiftDate());
            eventDayModel.setShiftTask(resData.get(i).getShiftTask());
            eventDayModel.setShiftStartTime(resData.get(i).getShiftStartTime());
            eventDayModel.setShiftEndTime(resData.get(i).getShiftEndTime());
            eventDayModel.setShiftId(resData.get(i).getShiftId());
            eventDayModel.setMapId(resData.get(i).getMapId());
            eventDayModel.setMapStatus(resData.get(i).getMapStatus());
            eventDayModel.setUserName(resData.get(i).getCso_f_name() + " " + resData.get(i).getCso_l_name());
            eventDayModel.setUserEmail(resData.get(i).getCso_email());
            if (resData.get(i).getMapStatusComment() != null && !resData.get(i).getMapStatusComment().trim().equalsIgnoreCase(""))
                eventDayModel.setComment(resData.get(i).getMapStatusComment());
            else
                eventDayModel.setComment("");

            if (resData.get(i).getMapRankComment() != null && !resData.get(i).getMapRankComment().trim().equalsIgnoreCase(""))
                eventDayModel.setRankComment(resData.get(i).getMapRankComment());
            else
                eventDayModel.setRankComment("");

            mEventDays.add(eventDayModel);

        }

        setEventsOnCalendar(mEventDays);


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
                        map.put("map_id", mEventDays.get(i).getMapId());
                        map.put("map_status", mEventDays.get(i).getMapStatus());
                        map.put("userName", mEventDays.get(i).getUserName());
                        map.put("userEmail", mEventDays.get(i).getUserEmail());
                        map.put("comment", mEventDays.get(i).getComment());
                        map.put("rankComment", mEventDays.get(i).getRankComment());
                        shiftDataList.add(map);
                        pos = i;
                        flag = true;
                        //break;
                    }
                }

                if (flag) {
                    flag = false;
                    openDateDialog(shiftDataList);
                    /*if (shiftDataList.size() > 1)
                        openDateDialog(shiftDataList);
                    else
                        openDialog(event_id, pos, null, resData.get(pos).getCso_f_name() + " " + resData.get(pos).getCso_l_name(), resData.get(pos).getCso_email());*/
                }


            }
        });
    }

    private void openDateDialog(List<HashMap<String, String>> shiftDataList) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.shift_list_for_event_dialog, null);
        dialog.setContentView(view);
        // dialog.setCancelable(false);


        ListView shiftList = view.findViewById(R.id.shiftList);
        //Button close = view.findViewById(R.id.close);

        CalendarAdapter calendarAdapter = new CalendarAdapter(context, shiftDataList, "MyBooking", dialog, TabMyBookingFragment.this);
        shiftList.setAdapter(calendarAdapter);
//        ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataList,"MyBooking",dialog,TabMyBookingFragment.this);
//        shiftList.setAdapter(shiftListForCalendarAdapter);

        /*if (shiftDataList.size() > 1) {
            shiftDataListTimeSorted = sortAccordingToTime(shiftDataList);
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataListTimeSorted);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        } else {
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataList);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        }*/
        //Utility.setListViewHeightBasedOnChildren(shiftList);

        /*close.setOnClickListener(v -> {
            dialog.dismiss();
        });*/

        dialog.show();
    }

    private void setEventsOnCalendar(List<EventDayModel> eventDayModelList) {
       /* for (int i = 0; i < calendarDayList.size(); i++) {
            mCalendarView.addDecorator(new EventDecorator(getResources().getColor(R.color.transparent), calendarDayList));
        }*/

        for (int i = 0; i < eventDayModelList.size(); i++) {
            CalendarDay day = eventDayModelList.get(i).getCalendarDay();
            calendarDayList.add(day);
            eventDecorator = new EventDecorator(context.getResources().getColor(R.color.blue_light), calendarDayList);
            mCalendarView.addDecorator(eventDecorator);
        }
    }

    private void openDialog(String event_id, int pos, HashMap<String, String> map, String username, String userEmail) {

        Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.booking_action_layout, null);
        dialog.setContentView(view1);
        dialog.show();

        LinearLayout view_event = view1.findViewById(R.id.view_event);
        LinearLayout change_status = view1.findViewById(R.id.change_status);
        final String shift_id;

        if (map != null) {
            //shift_id = map.get("shift_id");
            map_status = map.get("map_status");
            map_id = map.get("map_id");
        } else {
            VolunteerAllResponse.VolunteerResponse data = volunteerResponseList.get(pos);
            //shift_id = data.getShiftId();
            map_status = data.getMapStatus();
            map_id = data.getMapId();
        }
        view_event.setOnClickListener(vs -> {
            dialog.dismiss();

            /*EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id", event_id);
            bundle.putSerializable("shift_id", shift_id);
            bundle.putBoolean("booking", true);
            eventDescriptionFragment.setArguments(bundle);
            Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");*/

            ShiftListFragment shiftListFragment = new ShiftListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id", event_id);
            shiftListFragment.setArguments(bundle);
            Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");

        });

        change_status.setOnClickListener(v1 -> {
            dialog.dismiss();
            openDialog(Integer.parseInt(map_status), map_id, username, userEmail);
        });
    }

    private void openDialog(int status, String MapID, String username, String userEmail) {

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.volunteer_change_status_popup, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        LinearLayout volunteer_layout = view.findViewById(R.id.volunteer_layout);
        LinearLayout withdraw_layout = view.findViewById(R.id.withdraw_layout);
        LinearLayout initiateDM_layout = view.findViewById(R.id.initiateDM_layout);
        LinearLayout complete_layout = view.findViewById(R.id.complete_layout);
        View volunteer_view = view.findViewById(R.id.volunteer_view);
        View withdraw_view = view.findViewById(R.id.withdraw_view);
        View initiate_view = view.findViewById(R.id.initiate_view);
        ImageView imv_status = view.findViewById(R.id.imv_status);
        ImageView imv_chat = view.findViewById(R.id.imv_chat);
        MyTextView status_txt = view.findViewById(R.id.status_txt);
        MyHeadingTextView change_status = view.findViewById(R.id.change_status);

        switch (status) {

            case 10:
                withdraw_layout.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.ic_pending);
                status_txt.setText("Pending");
                break;
            case 20:
                withdraw_layout.setVisibility(View.VISIBLE);
                withdraw_view.setVisibility(View.VISIBLE);
                complete_layout.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.ic_accepted_cso);
                status_txt.setText("Accepted");
                break;
            case 30:
                //initiateDM_layout.setVisibility(View.VISIBLE);
                imv_chat.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.ic_declined_cso);
                status_txt.setText("Declined");
                change_status.setVisibility(View.GONE);
                break;
            case 40:
                change_status.setVisibility(View.GONE);
                imv_status.setImageResource(R.drawable.ic_completed_volunteer);
                status_txt.setText("Completed");
                change_status.setVisibility(View.GONE);
                break;
            case 50:
                volunteer_layout.setVisibility(View.VISIBLE);
                volunteer_view.setVisibility(View.VISIBLE);
                withdraw_layout.setVisibility(View.VISIBLE);
                withdraw_view.setVisibility(View.VISIBLE);
                complete_layout.setVisibility(View.VISIBLE);
                imv_chat.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.ic_more_info);
                status_txt.setText("More Info");
                break;
            case 51:
                withdraw_layout.setVisibility(View.VISIBLE);
                imv_chat.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.ic_more_info);
                status_txt.setText("More Info");
                break;
            case 60:
                //initiateDM_layout.setVisibility(View.VISIBLE);
                imv_chat.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.ic_rejected_cso);
                status_txt.setText("Rejected");
                change_status.setVisibility(View.GONE);
                break;
            case 70:
                imv_status.setImageResource(R.drawable.ic_complete_verified);
                status_txt.setText("Verified");
                change_status.setVisibility(View.GONE);
                break;
            case 90:
                volunteer_layout.setVisibility(View.VISIBLE);
                imv_status.setImageResource(R.drawable.not_available);
                status_txt.setText("Not Available");
                break;
        }

        volunteer_layout.setOnClickListener(v -> {
            changeStatus(Constants.NewRequestVol, MapID, mEventDays);
            dialog.dismiss();

        });

        withdraw_layout.setOnClickListener(v -> {
            changeStatus(Constants.WithdrawnVol, MapID, mEventDays);
            dialog.dismiss();
        });
        initiateDM_layout.setOnClickListener(v -> {
            dialog.dismiss();
        });
        complete_layout.setOnClickListener(v -> {
            openDialogHrs(Constants.CompletedByVol, MapID);
//            changeStatus(Constants.CompletedByVol, MapID);
            dialog.dismiss();
        });
        imv_chat.setOnClickListener(v -> {
//            Intent intent = new Intent(context, CreateGroupChannelActivity.class);
//            intent.putExtra("email","");
//            startActivity(intent);
//            CreateGroupChannelFragment fragment = new CreateGroupChannelFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("email", userEmail);
//            bundle.putString("name", username);
//            fragment.setArguments(bundle);
//            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.body, fragment, "CreateGroupChannelFragment");
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();


            mSelectedIds = new ArrayList<>();
            mSelectedIds.add(userEmail);

            createGroupChannel(username, mSelectedIds, true);

            ((VolunteerDashboardActivity) context).setHangoutProps();
            dialog.dismiss();
        });
        dialog.show();

    }

    private void createGroupChannel(String nick_name, List<String> userIds, boolean distinct) {
        GroupChannel.createChannelWithUserIds(userIds, distinct, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                Constants.backFromChat = false;
                GroupChannelListFragment fragment = new GroupChannelListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                bundle.putString(EXTRA_Name, nick_name);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.body, fragment, "GroupChannelListFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

  /*  @SuppressLint("ClickableViewAccessibility")
    private void openDialogHrs(int status, String mapID) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.volunteer_mark_hours_popup, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        MyTextInputEditText et_clockIn = view.findViewById(R.id.et_clockIn);
        MyTextInputEditText et_clockOut = view.findViewById(R.id.et_clockOut);

        MyButton done = view.findViewById(R.id.done);
        MyButton cancel = view.findViewById(R.id.cancel);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        startTime = String.format(Locale.ENGLISH, "%02d:%02d", hour, minute);

        if(Integer.parseInt(startTime.split(":")[0]) > 12)
        {
            int hr = Integer.parseInt(startTime.split(":")[0]) - 12;
            int min = Integer.parseInt(startTime.split(":")[1]);
            startTimeAmPm = String.format(Locale.ENGLISH, "%02d:%02d", hr, min)+" PM";
        }
        else
        {
            int hr = Integer.parseInt(startTime.split(":")[0]);
            int min = Integer.parseInt(startTime.split(":")[1]);
            startTimeAmPm = String.format(Locale.ENGLISH, "%02d:%02d", hr, min)+" AM";
        }
        et_clockIn.setText(startTimeAmPm);



        et_clockIn.setOnTouchListener((v1, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                et_clockOut.setText("");

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime = String.format(Locale.ENGLISH, "%02d:%02d", selectedHour, selectedMinute);

                        if(Integer.parseInt(startTime.split(":")[0]) > 12)
                        {
                            int hr = Integer.parseInt(startTime.split(":")[0]) - 12;
                            int min = Integer.parseInt(startTime.split(":")[1]);
                            startTimeAmPm = String.format(Locale.ENGLISH, "%02d:%02d", hr, min)+" PM";
                        }
                        else
                        {
                            int hr = Integer.parseInt(startTime.split(":")[0]);
                            int min = Integer.parseInt(startTime.split(":")[1]);
                            startTimeAmPm = String.format(Locale.ENGLISH, "%02d:%02d", hr, min)+" AM";
                        }
                        et_clockIn.setText(startTimeAmPm);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

                return true;
            }
            return false;
        });


        et_clockOut.setOnTouchListener((v13, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (et_clockIn.getText() != null && !et_clockIn.getText().toString().isEmpty()) {

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {

                        endTime = String.format(Locale.ENGLISH, "%02d:%02d", selectedHour, selectedMinute);

                        int hour1 = Integer.parseInt(startTime.split(":")[0]);
                        int minute1 = Integer.parseInt(startTime.split(":")[1]);

                        int tempStart = (60 * minute1) + (3600 * hour1);


                        int hour2 = Integer.parseInt(endTime.split(":")[0]);
                        int minute2 = Integer.parseInt(endTime.split(":")[1]);

                        int tempEnd = (60 * minute2) + (3600 * hour2);

                        if (tempStart > tempEnd) {
                            myToast.show(getString(R.string.err_outtime_notbefore_intime), Toast.LENGTH_SHORT, false);
                            et_clockOut.setText("");
                        } else {

                            if(Integer.parseInt(endTime.split(":")[0]) > 12)
                            {
                                int hr = Integer.parseInt(endTime.split(":")[0]) - 12;
                                int min = Integer.parseInt(endTime.split(":")[1]);
                                endTimeAmPm = String.format(Locale.ENGLISH, "%02d:%02d", hr, min)+" PM";
                            }
                            else
                            {
                                int hr = Integer.parseInt(endTime.split(":")[0]);
                                int min = Integer.parseInt(endTime.split(":")[1]);
                                endTimeAmPm = String.format(Locale.ENGLISH, "%02d:%02d", hr, min)+" AM";
                            }

                            et_clockOut.setText(endTimeAmPm);
                        }

                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle(getString(R.string.select_time));
                    mTimePicker.show();

                } else {
                    myToast.show(getString(R.string.err_enter_in_time_first), Toast.LENGTH_SHORT, false);
                }

                return true;
            }
            return false;
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_clockIn.getText().toString().trim())) {
                    myToast.show(getString(R.string.err_enter_in_time), Toast.LENGTH_SHORT, false);
                    return;
                }
                if (TextUtils.isEmpty(et_clockOut.getText().toString().trim())) {
                    myToast.show(getString(R.string.err_enter_out_time), Toast.LENGTH_SHORT, false);
                    return;
                }
                markHoursCompleted(status, mapID, et_clockIn.getText().toString().trim(), et_clockOut.getText().toString().trim());
                dialog.dismiss();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }*/

    @SuppressLint("ClickableViewAccessibility")
    private void openDialogHrs(int status, String mapID) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.volunteer_mark_hours_popup, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        MyTextInputEditText et_clockIn = view.findViewById(R.id.et_clockIn);
        MyTextInputEditText et_clockOut = view.findViewById(R.id.et_clockOut);

        MyButton done = view.findViewById(R.id.done);
        MyButton cancel = view.findViewById(R.id.cancel);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        startTime = String.format(Locale.ENGLISH, "%02d:%02d", hour, minute);
        et_clockIn.setText(Utility.getTimeAmPm(startTime));

        et_clockIn.setOnTouchListener((v1, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                et_clockOut.setText("");

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime = String.format(Locale.ENGLISH, "%02d:%02d", selectedHour, selectedMinute);

                        et_clockIn.setText(Utility.getTimeAmPm(startTime));
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(context.getString(R.string.select_time));
                mTimePicker.show();

                return true;
            }
            return false;
        });


        et_clockOut.setOnTouchListener((v13, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (et_clockIn.getText() != null && !et_clockIn.getText().toString().isEmpty()) {

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {

                        endTime = String.format(Locale.ENGLISH, "%02d:%02d", selectedHour, selectedMinute);

                        int hour1 = Integer.parseInt(startTime.split(":")[0]);
                        int minute1 = Integer.parseInt(startTime.split(":")[1]);

                        int tempStart = (60 * minute1) + (3600 * hour1);


                        int hour2 = Integer.parseInt(endTime.split(":")[0]);
                        int minute2 = Integer.parseInt(endTime.split(":")[1]);

                        int tempEnd = (60 * minute2) + (3600 * hour2);

                        if (tempStart > tempEnd) {
                            myToast.show(context.getString(R.string.err_outtime_notbefore_intime), Toast.LENGTH_SHORT, false);
                            et_clockOut.setText("");
                        } else {
                            et_clockOut.setText(Utility.getTimeAmPm(endTime));
                        }

                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle(context.getString(R.string.select_time));
                    mTimePicker.show();

                } else {
                    myToast.show(context.getString(R.string.err_enter_in_time_first), Toast.LENGTH_SHORT, false);
                }

                return true;
            }
            return false;
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_clockIn.getText().toString().trim())) {
                    myToast.show(context.getString(R.string.err_enter_in_time), Toast.LENGTH_SHORT, false);
                    return;
                }
                if (TextUtils.isEmpty(et_clockOut.getText().toString().trim())) {
                    myToast.show(context.getString(R.string.err_enter_out_time), Toast.LENGTH_SHORT, false);
                    return;
                }
                markHoursCompleted(status, mapID, et_clockIn.getText().toString().trim(), et_clockOut.getText().toString().trim());
                dialog.dismiss();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void changeStatus(int map_status, String mapID, List<EventDayModel> eventDayModelList) {
        ChangeVolunteerStatusRequest changeVolunteerStatusRequest = new ChangeVolunteerStatusRequest();
        changeVolunteerStatusRequest.setUser_id(sharedPref.getUserId());
        changeVolunteerStatusRequest.setUser_type(sharedPref.getUserType());
        changeVolunteerStatusRequest.setUser_device(Utility.getDeviceId(context));
        changeVolunteerStatusRequest.setMap_id(mapID);
        changeVolunteerStatusRequest.setMap_status(String.valueOf(map_status));

        Log.i("", "" + new Gson().toJson(changeVolunteerStatusRequest));

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            changeVolunteerStatusViewModel.changeStatus(changeVolunteerStatusRequest).observe((FragmentActivity) context, changeVolunteerStatusResponse -> {
                if (changeVolunteerStatusResponse != null) {
                    if (changeVolunteerStatusResponse.getResStatus().equalsIgnoreCase("200")) {
                        switch (map_status) {
                            case 10:
                                new MyToast(context).show(context.getString(R.string.toast_volunteer_request_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 40:
                                new MyToast(context).show(context.getString(R.string.toast_volunteer_complete_success), Toast.LENGTH_SHORT, true);
                                break;
                            case 90:
                                new MyToast(context).show(context.getString(R.string.toast_volunteer_withdraw_success), Toast.LENGTH_SHORT, true);

                                try {
                                    for (int i = 0; i < eventDayModelList.size(); i++) {
                                        if (eventDayModelList.get(i).getMapId().equalsIgnoreCase(mapID)) {
                                            CalendarDay day = eventDayModelList.get(i).getCalendarDay();
                                            calendarDayList.remove(day);
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                        }
                        mCalendarView.removeDecorators();
                        mCalendarView.invalidateDecorators();
                        eventDecorator = new EventDecorator(context.getResources().getColor(R.color.blue_light), calendarDayList);
                        mCalendarView.addDecorator(eventDecorator);
                        mCalendarView.invalidateDecorators();
                        myProgressDialog.dismiss();
                    } else if (changeVolunteerStatusResponse.getResStatus().equalsIgnoreCase("401")) {
                        myProgressDialog.dismiss();
                        new MyToast(context).show(context.getString(R.string.toast_volunteer_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myProgressDialog.dismiss();
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    public void markHoursCompleted(int map_status, String mapID, String inTime, String outTime) {
        VolunteerMarkHoursRequest volunteerMarkHoursRequest = new VolunteerMarkHoursRequest();
        volunteerMarkHoursRequest.setUserId(sharedPref.getUserId());
        volunteerMarkHoursRequest.setUser_type(sharedPref.getUserType());
        volunteerMarkHoursRequest.setUser_device(Utility.getDeviceId(context));
        volunteerMarkHoursRequest.setMap_id(mapID);
        volunteerMarkHoursRequest.setAttend_in_time(inTime);
        volunteerMarkHoursRequest.setAttend_out_time(outTime);
        volunteerMarkHoursRequest.setMap_status(String.valueOf(map_status));

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            volunteerMarkHoursViewModel.getMarkHoursResponse(volunteerMarkHoursRequest).observe((FragmentActivity) context, volunteerMarkHoursResponse -> {
                if (volunteerMarkHoursResponse != null) {
                    if (volunteerMarkHoursResponse.getResStatus().equalsIgnoreCase("200")) {

                        new MyToast(context).show(context.getString(R.string.toast_volunteer_complete_success), Toast.LENGTH_SHORT, true);
                        getBooking(false);
                    } else if (volunteerMarkHoursResponse.getResStatus().equalsIgnoreCase("401")) {
                        myProgressDialog.dismiss();
                        new MyToast(context).show(context.getString(R.string.toast_volunteer_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myProgressDialog.dismiss();
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    public class CalendarAdapter extends BaseAdapter {

        Context context;
        List<HashMap<String, String>> shiftDataList;
        String type;
        SharedPref sharedPref;
        MyProgressDialog myProgressDialog;
        MyToast myToast;
        ChangeVolunteerStatusViewModel changeVolunteerStatusViewModel;
        Dialog dialog;
        TabMyBookingFragment fragment;

        public CalendarAdapter(Context context, List<HashMap<String, String>> shiftDataList, String type, Dialog dialog, TabMyBookingFragment fragment) //type = MyBooking,Dashboard
        {
            this.context = context;
            this.shiftDataList = shiftDataList;
            this.type = type;
            this.dialog = dialog;
            this.fragment = fragment;
            myProgressDialog = new MyProgressDialog(context);
            myToast = new MyToast(context);
            sharedPref = SharedPref.getInstance(context);
            changeVolunteerStatusViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeVolunteerStatusViewModel.class);
        }

        @Override
        public int getCount() {
            return shiftDataList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return shiftDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = new Holder();
            HashMap<String, String> map = getItem(position);
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.shift_data_list_item, null);
                holder.date = view.findViewById(R.id.date);
                holder.shiftTask = view.findViewById(R.id.shiftTask);
                holder.month = view.findViewById(R.id.month);
                holder.day = view.findViewById(R.id.day);
                holder.shiftTime = view.findViewById(R.id.shiftTime);
                holder.shiftTime = view.findViewById(R.id.shiftTime);
                holder.comment = view.findViewById(R.id.comment);
                holder.rankComment = view.findViewById(R.id.rankComment);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            Date date = Utility.convertStringToDateWithoutTime(map.get("shift_date"));

            String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
            String day = (String) DateFormat.format("dd", date); // 20
            String monthString = (String) DateFormat.format("MMM", date); // Jun
            String monthNumber = (String) DateFormat.format("MM", date); // 06
            String year = (String) DateFormat.format("yyyy", date); // 2013

            holder.date.setText(day);
            holder.day.setText(dayOfTheWeek);
            holder.month.setText(monthString);
            if (!Objects.requireNonNull(map.get("comment")).equalsIgnoreCase("")) {
                holder.comment.setVisibility(View.VISIBLE);
                holder.comment.setText("Decline : " + map.get("comment"));
            } else
                holder.comment.setVisibility(View.GONE);

            if (!Objects.requireNonNull(map.get("rankComment")).equalsIgnoreCase("")) {
                holder.rankComment.setVisibility(View.VISIBLE);
                holder.rankComment.setText("Rank : " + map.get("rankComment"));
            } else
                holder.rankComment.setVisibility(View.GONE);

            holder.shiftTask.setText(map.get("event_heading"));
            holder.shiftTime.setText("Time : " + map.get("shift_start_time") + " - " + map.get("shift_end_time"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                    if (type.equalsIgnoreCase("MyBooking")) {
                        openDialog(map.get("event_id"), position, getItem(position), map.get("userName"), map.get("userEmail"));
                    } else {


                       /* EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", map.get("event_id"));
                        bundle.putString("shift_id", map.get("shift_id"));
                        bundle.putBoolean("booking", true);
                        eventDetailFragment.setArguments(bundle);
                        Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");*/

                        EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", map.get("event_id"));
                        bundle.putSerializable("shift_id", map.get("shift_id"));
                        bundle.putBoolean("booking", true);
                        eventDescriptionFragment.setArguments(bundle);
                        Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");
                    }
                }
            });

            return view;
        }


        private class Holder {
            MyBoldTextView date, shiftTask;
            MyTextView month, day, shiftTime, comment, rankComment;
        }
    }
}
