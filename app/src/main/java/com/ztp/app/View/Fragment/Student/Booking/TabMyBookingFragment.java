package com.ztp.app.View.Fragment.Student.Booking;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.Model.EventDayModel;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.EventDecorator;
import com.ztp.app.Utils.TodayDateDecorator;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.CSO.Dashboard.ShiftListForCalendarAdapter;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;
import com.ztp.app.Viewmodel.ChangeVolunteerStatusViewModel;
import com.ztp.app.Viewmodel.VolunteerAllRequestViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private List<EventDayModel> mEventDays = new ArrayList<>();
    private List<CalendarDay> calendarDayList = new ArrayList<>();
    List<VolunteerAllResponse.ResData> resDataList = new ArrayList<>();
    List<HashMap<String, String>> shiftDataList = new ArrayList<>();
    String event_id, event_heading;
    int pos;
    boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_my_booking, container, false);
        sharedPref = SharedPref.getInstance(context);
        changeVolunteerStatusViewModel = ViewModelProviders.of((FragmentActivity) context).get(ChangeVolunteerStatusViewModel.class);
        volunteerAllRequestViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerAllRequestViewModel.class);
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
        this.context = context;

    }

    public void getBooking(boolean show) {
        if (parentLayout.getChildCount() > 0)
            parentLayout.removeAllViews();
        parentLayout.addView(childLayout);
        if (Utility.isNetworkAvailable(context)) {
            if (show)
                myProgressDialog.show(getString(R.string.please_wait));
            volunteerAllRequestViewModel.getVolunteerAllRequestResponse(new VolunteerAllRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, new Observer<VolunteerAllResponse>() {
                @Override
                public void onChanged(@Nullable VolunteerAllResponse volunteerAllResponse) {

                    if (volunteerAllResponse != null) {
                        if (volunteerAllResponse.getResStatus().equalsIgnoreCase("200")) {

                            if (volunteerAllResponse.getResData() != null && volunteerAllResponse.getResData().size() > 0) {
//                                listView.setVisibility(View.VISIBLE);
//                                noData.setVisibility(View.INVISIBLE);
//                                MyBookingAdapter myBookingAdapter = new MyBookingAdapter(context, volunteerAllResponse.getResData());
//                                listView.setAdapter(myBookingAdapter);
                                resDataList = volunteerAllResponse.getResData();
                                setCalendar(volunteerAllResponse.getResData());
                            } else {
                                myToast.show(getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);
                        }

                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                }
            });
            myProgressDialog.dismiss();
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setCalendar(List<VolunteerAllResponse.ResData> resData) {

        mEventDays = new ArrayList<>();

        mCalendarView = (MaterialCalendarView) childLayout.findViewById(R.id.calendarViews);

        for (int i = 0; i < resData.size(); i++) {
            VolunteerAllResponse.ResData data = resData.get(i);
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

            mEventDays.add(eventDayModel);

        }

        setEventsOnCalendar(mEventDays);


        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


                /*for(int i=0;i<mEventDays.size();i++)
                {
                    if(mEventDays.get(i).getCalendarDay().getDay() == date.getDay() && mEventDays.get(i).getCalendarDay().getMonth() == date.getMonth() && mEventDays.get(i).getCalendarDay().getYear() == date.getYear())
                    {
                        event_id = mEventDays.get(i).getEventId();
                        event_heading = mEventDays.get(i).getEventHeading();
                        pos = i;
                        flag = true;
                        break;
                    }
                }

                if(flag) {
                    flag = false;
                    openDialog(event_id,pos);

                   //openDateDialog()
                }
*/

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
                        map.put("shift_id",mEventDays.get(i).getShiftId());
                        map.put("map_id",mEventDays.get(i).getMapId());
                        map.put("map_status",mEventDays.get(i).getMapStatus());
                        shiftDataList.add(map);
                        flag = true;
                        //break;
                    }
                }

                if (flag) {
                    flag = false;
                    if (shiftDataList.size() > 1)
                        openDateDialog(shiftDataList);
                    else
                        openDialog(event_id, pos);
                }




               /* EventDetailFragment eventDetailFragment = new EventDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id", event_id);
                eventDetailFragment.setArguments(bundle);
                Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");*/

            }
        });
    }

    private void openDateDialog(List<HashMap<String, String>> shiftDataList) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.shift_list_for_event_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);


        ListView shiftList = view.findViewById(R.id.shiftList);
        Button close = view.findViewById(R.id.close);


        ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataList,"MyBooking",dialog,TabMyBookingFragment.this);
        shiftList.setAdapter(shiftListForCalendarAdapter);

        /*if (shiftDataList.size() > 1) {
            shiftDataListTimeSorted = sortAccordingToTime(shiftDataList);
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataListTimeSorted);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        } else {
            ShiftListForCalendarAdapter shiftListForCalendarAdapter = new ShiftListForCalendarAdapter(context, shiftDataList);
            shiftList.setAdapter(shiftListForCalendarAdapter);
        }*/
        //Utility.setListViewHeightBasedOnChildren(shiftList);

        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setEventsOnCalendar(List<EventDayModel> eventDayModelList) {

        for (int i = 0; i < eventDayModelList.size(); i++) {
            CalendarDay day = eventDayModelList.get(i).getCalendarDay();
            calendarDayList.add(day);
            mCalendarView.addDecorator(new EventDecorator(getResources().getColor(R.color.blue_light), calendarDayList));
        }
    }

    private void openDialog(String event_id, int pos) {

        Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.booking_action_layout, null);
        dialog.setContentView(view1);
        dialog.show();

        LinearLayout view_event = view1.findViewById(R.id.view_event);
        LinearLayout change_status = view1.findViewById(R.id.change_status);
        VolunteerAllResponse.ResData data = resDataList.get(pos);
        view_event.setOnClickListener(vs -> {
            dialog.dismiss();
            //String id = map.get(eventDay);
            EventDetailFragment eventDetailFragment = new EventDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id", event_id);
            bundle.putSerializable("shift_id", data.getShiftId());
            bundle.putBoolean("booking", true);
            eventDetailFragment.setArguments(bundle);
            Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");
        });


        change_status.setOnClickListener(v1 -> {
            dialog.dismiss();
            openDialog(Integer.parseInt(data.getMapStatus()), data.getMapId());
        });
    }


    private void openDialog(int status, String MapID) {

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

        switch (status) {

            case 10:
                withdraw_layout.setVisibility(View.VISIBLE);
                break;
            case 20:
                withdraw_layout.setVisibility(View.VISIBLE);
                withdraw_view.setVisibility(View.VISIBLE);
                complete_layout.setVisibility(View.VISIBLE);
                break;
            case 30:
                initiateDM_layout.setVisibility(View.VISIBLE);
                break;
            case 50:
                volunteer_layout.setVisibility(View.VISIBLE);
                volunteer_view.setVisibility(View.VISIBLE);
                withdraw_layout.setVisibility(View.VISIBLE);
                withdraw_view.setVisibility(View.VISIBLE);
                complete_layout.setVisibility(View.VISIBLE);
                break;
            case 60:
                initiateDM_layout.setVisibility(View.VISIBLE);
                break;
            case 90:
                volunteer_layout.setVisibility(View.VISIBLE);
                break;
        }

        volunteer_layout.setOnClickListener(v -> {
            changeStatus(Constants.NewRequestVol, MapID);
            dialog.dismiss();

        });

        withdraw_layout.setOnClickListener(v -> {
            changeStatus(Constants.WithdrawnVol, MapID);
            dialog.dismiss();
        });
        initiateDM_layout.setOnClickListener(v -> {
            dialog.dismiss();
        });
        complete_layout.setOnClickListener(v -> {
            changeStatus(Constants.CompletedByVol, MapID);
            dialog.dismiss();
        });
        dialog.show();

    }

    public void changeStatus(int map_status, String mapID) {
        ChangeVolunteerStatusRequest changeVolunteerStatusRequest = new ChangeVolunteerStatusRequest();
        changeVolunteerStatusRequest.setUser_id(sharedPref.getUserId());
        changeVolunteerStatusRequest.setUser_type(sharedPref.getUserType());
        changeVolunteerStatusRequest.setUser_device(Utility.getDeviceId(context));
        changeVolunteerStatusRequest.setMap_id(mapID);
        changeVolunteerStatusRequest.setMap_status(String.valueOf(map_status));

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
                                break;
                        }
                        getBooking(false);
                    } else {
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

}
