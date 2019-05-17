package com.ztp.app.View.Fragment.Student.Booking;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Request.DeleteShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.CSO.Event.AddNewShiftFragment;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;
import com.ztp.app.View.Fragment.Common.ShiftDetailFragment;
import com.ztp.app.Viewmodel.ChangeVolunteerStatusViewModel;
import com.ztp.app.Viewmodel.VolunteerAllRequestViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TabMyBookingFragment extends Fragment {

    Context context;
    //    ListView listView;
//    MyTextView noData;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    VolunteerAllRequestViewModel volunteerAllRequestViewModel;
    LinearLayout parentLayout;
    boolean theme;
    private CalendarView mCalendarView;
    List<EventDay> events;
    HashMap<EventDay, String> map;
    HashMap<EventDay, VolunteerAllResponse.ResData> shift_map;
    View childLayout;
    VolunteerAllResponse.ResData resData;
    ChangeVolunteerStatusViewModel changeVolunteerStatusViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_my_booking, container, false);
//        listView = view.findViewById(R.id.listView);
//        noData = view.findViewById(R.id.noData);
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

        mCalendarView = (CalendarView) childLayout.findViewById(R.id.calendarViews);
        try {
            mCalendarView.setDate(new Date());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }


        getBooking(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    private void getBooking(boolean show) {
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
                            } else {
//                                listView.setVisibility(View.INVISIBLE);
//                                noData.setVisibility(View.VISIBLE);
                            }
                        } else {
//                            listView.setVisibility(View.INVISIBLE);
//                            noData.setVisibility(View.VISIBLE);
                            // myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT,false);
                        }
                        setCalendar(volunteerAllResponse.getResData());
                    } else {
//                        listView.setVisibility(View.INVISIBLE);
//                        noData.setVisibility(View.VISIBLE);
                        setCalendar(null);
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }


                }
            });
        } else {
//            listView.setVisibility(View.INVISIBLE);
//            noData.setVisibility(View.VISIBLE);
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setCalendar(List<VolunteerAllResponse.ResData> resData) {
        map = new HashMap<>();
        shift_map = new HashMap<>();
        events = new ArrayList<EventDay>();

        mCalendarView = (CalendarView) childLayout.findViewById(R.id.calendarViews);

        if (resData != null) {
            for (int i = 0; i < resData.size(); i++) {
                VolunteerAllResponse.ResData data = resData.get(i);
                Calendar calendar = Calendar.getInstance();
                Date date = Utility.convertStringToDateWithoutTime(data.getShiftDate());
                String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
                String day = (String) DateFormat.format("dd", date); // 20
                String monthString = (String) DateFormat.format("MMM", date); // Jun
                String monthNumber = (String) DateFormat.format("MM", date); // 06
                String year = (String) DateFormat.format("yyyy", date); // 2013
                calendar.set(Integer.parseInt(year), Integer.parseInt(monthNumber) - 1, Integer.parseInt(day));
                EventDay eventDay = new EventDay(calendar, R.drawable.ic_event);
                map.put(eventDay, data.getEventId());
                shift_map.put(eventDay, data);
                events.add(eventDay);
                mCalendarView.setEvents(events);
            }

        } else {
            mCalendarView.setEvents(events);
        }
        myProgressDialog.dismiss();
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                if (map.get(eventDay) != null) {
                    openDialog(eventDay);
                }
            }
        });

    }


    private void openDialog(EventDay eventDay) {

        Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.booking_action_layout, null);
        dialog.setContentView(view1);
        dialog.show();

        LinearLayout view_event = view1.findViewById(R.id.view_event);
        LinearLayout change_status = view1.findViewById(R.id.change_status);
        VolunteerAllResponse.ResData data = shift_map.get(eventDay);
        view_event.setOnClickListener(vs -> {
            dialog.dismiss();
            String id = map.get(eventDay);
            EventDetailFragment eventDetailFragment = new EventDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id", id);
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
