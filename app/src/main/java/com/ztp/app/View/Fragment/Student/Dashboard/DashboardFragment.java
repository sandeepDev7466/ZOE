package com.ztp.app.View.Fragment.Student.Dashboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ztp.app.Attendance.AttendanceService;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ApprovedCSORequest;
import com.ztp.app.Data.Remote.Model.Request.InsertFirebaseIdRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerDashboardCombineRequest;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Student.StudentDashboardActivity;
import com.ztp.app.View.Fragment.Volunteer.Extra.UpcomingEventsFragment;
import com.ztp.app.View.Fragment.Volunteer.Extra.VolunteerUpcomingEventAdapter;
import com.ztp.app.Viewmodel.ApprovedCSOViewModel;
import com.ztp.app.Viewmodel.InsertFirebaseIdViewModel;
import com.ztp.app.Viewmodel.VolunteerDashboardCombineViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    Context context;
    MyToast myToast;
    VolunteerDashboardCombineViewModel volunteerDashboardCombineViewModel;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    List<VolunteerDashboardCombineResponse.EventData> eventDataList = new ArrayList<>();
    RoomDB roomDB;
    ListView listView;
    RecyclerView recyclerView;
    ApprovedCSOViewModel approvedCSOViewModel;
    List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList = new ArrayList<>();
    TextView noEvents, noUpcomingEvents, seeAll, name, volHours, con_txt, tv_days, tv_hours, tv_minutes, tv_seconds;
    ImageView rankImage;
    InsertFirebaseIdViewModel insertFirebaseIdViewModel;
    LinearLayout timerLayout, con_layout;
    private Handler handler;
    private Runnable runnable;
    int countDown;
    boolean theme;
    ImageView con_img;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progress;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        View view = inflater.inflate(R.layout.fragment_dashboard_stu, container, false);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        roomDB = RoomDB.getInstance(context);

        theme = sharedPref.getTheme();
        countDown = 0;
        handler = new Handler();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);

        approvedCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(ApprovedCSOViewModel.class);
        volunteerDashboardCombineViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerDashboardCombineViewModel.class);
        insertFirebaseIdViewModel = ViewModelProviders.of((FragmentActivity) context).get(InsertFirebaseIdViewModel.class);
        init(view);

        if (theme) {
            con_txt.setTextColor(getResources().getColor(R.color.black));
            con_img.setColorFilter(getResources().getColor(R.color.black));
            con_layout.setBackgroundColor(getResources().getColor(R.color.white));
            timerLayout.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            con_txt.setTextColor(getResources().getColor(R.color.white));
            con_img.setColorFilter(getResources().getColor(R.color.white));
            con_layout.setBackgroundColor(getResources().getColor(R.color.black));
            timerLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }


        seeAll.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("event_list", (Serializable) eventDataList);
            UpcomingEventsFragment upcomingEventsFragment = new UpcomingEventsFragment();
            upcomingEventsFragment.setArguments(bundle);
            Utility.replaceFragment(context, upcomingEventsFragment, "UpcomingEventsFragment");
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            if (Utility.isNetworkAvailable(context))
                hitUpcomingEventsApi();
            else
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            mSwipeRefreshLayout.setRefreshing(false);
        });

        return view;

    }

    public void init(View view) {
        seeAll = view.findViewById(R.id.seeAll);
        listView = view.findViewById(R.id.listView);
        recyclerView = view.findViewById(R.id.recyclerView);
        noEvents = view.findViewById(R.id.noEvents);
        noUpcomingEvents = view.findViewById(R.id.noUpcomingEvents);
      /*  name = view.findViewById(R.id.name);
        rankImage = view.findViewById(R.id.rankImage);
        volHours = view.findViewById(R.id.volHours);*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        timerLayout = view.findViewById(R.id.timerLayout);
        con_layout = view.findViewById(R.id.con_layout);
        tv_days = view.findViewById(R.id.days);
        tv_hours = view.findViewById(R.id.hours);
        tv_minutes = view.findViewById(R.id.minutes);
        tv_seconds = view.findViewById(R.id.seconds);
        con_txt = view.findViewById(R.id.con_txt);
        con_img = view.findViewById(R.id.con_img);
        progress = view.findViewById(R.id.progress);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.volunter:
                Utility.replaceFragment(context, new SearchEventFragment(), "SearchEventFragment");
                break;
            case R.id.upload:
                ((StudentDashboardActivity) context).setLockerFragment();
                break;
            case R.id.target:
                ((StudentDashboardActivity) context).setTargetFragment();
                break;
            case R.id.message:
                ((StudentDashboardActivity) context).setHangoutFragment();
                break;
            case R.id.cso:
                Utility.replaceFragment(context, new SearchEventByAreaFragment(), "SearchEventByAreaFragment");
                break;
            case R.id.upcoming_event:
                Bundle bundle = new Bundle();
                bundle.putSerializable("event_list", (Serializable) eventDataList);
                UpcomingEventsFragment upcomingEventsFragment = new UpcomingEventsFragment();
                upcomingEventsFragment.setArguments(bundle);
                Utility.replaceFragment(context, upcomingEventsFragment, "UpcomingEventsFragment");
                break;

        }
    }*/

    private void hitUpcomingEventsApi() {

        getRegisteredCSO();

        if (Utility.isNetworkAvailable(context)) {

            myProgressDialog.show(getString(R.string.please_wait));
            VolunteerDashboardCombineRequest volunteerDashboardCombineRequest = new VolunteerDashboardCombineRequest();
            volunteerDashboardCombineRequest.setUserId(sharedPref.getUserId());
            volunteerDashboardCombineRequest.setListDate(Utility.getCurrentDate());

            Log.i("REQUEST", "" + new Gson().toJson(volunteerDashboardCombineRequest));

            volunteerDashboardCombineViewModel.getVolunteerDashboardCombinedResponse(volunteerDashboardCombineRequest).observe((LifecycleOwner) context, volunteerDashboardCombineResponse -> {

                if (volunteerDashboardCombineResponse != null) {

                    if (volunteerDashboardCombineResponse.getResData().getVolHours() != null) {
                        sharedPref.setVolHours(volunteerDashboardCombineResponse.getResData().getVolHours());
                        sharedPref.setVolRank(volunteerDashboardCombineResponse.getResData().getVolRank());
                    }

                    if (volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("200")) {
                        listView.setVisibility(View.VISIBLE);
                        noUpcomingEvents.setVisibility(View.GONE);
                        Log.i("REQUEST", "" + new Gson().toJson(volunteerDashboardCombineResponse));
                        if (volunteerDashboardCombineResponse.getResData().getEventData() != null) {

                            if (volunteerDashboardCombineResponse.getResData().getEventData().size() > 0) {

                                eventDataList = volunteerDashboardCombineResponse.getResData().getEventData();
                                timerLayout.setVisibility(View.VISIBLE);
                                con_txt.setVisibility(View.VISIBLE);
                                con_layout.setVisibility(View.VISIBLE);
                                handler.removeCallbacks(runnable);
                                if (countDown < eventDataList.size())
                                    countDownStart(eventDataList.get(countDown).getShiftDate() + " " + eventDataList.get(countDown).getShiftStartTimeTimer());

                                VolunteerUpcomingEventAdapter adapter = new VolunteerUpcomingEventAdapter(context, eventDataList);
                                listView.setAdapter(adapter);

                                new DBSaveUpcomingEventsForVolunteer(eventDataList).execute();

                                Date currentDate = Utility.convertStringToDateTimer(Utility.getCurrentTime());
                                Date futureShiftDate = Utility.convertStringToDateTimer(eventDataList.get(0).getShiftDate() + " " + eventDataList.get(0).getShiftStartTime());
                                if (currentDate != null && futureShiftDate != null) {
                                    long diffInMs = futureShiftDate.getTime() - currentDate.getTime();
                                    long time = System.currentTimeMillis();

                                    Intent intent = new Intent(context, AttendanceService.class);
                                    intent.putExtra("event_latitude", eventDataList.get(0).getEventLatitude());
                                    intent.putExtra("event_longitude", eventDataList.get(0).getEventLongitude());
                                    PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
                                    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                    alarm.setExact(AlarmManager.RTC_WAKEUP, diffInMs - 60 * 60 * 1000, pintent);
                                    //alarm.setExact(AlarmManager.RTC_WAKEUP,time + 5 * 1000, pintent);
                                }
                            }
                        } else {
                            listView.setVisibility(View.GONE);
                            noUpcomingEvents.setVisibility(View.VISIBLE);
                            timerLayout.setVisibility(View.GONE);
                            con_txt.setVisibility(View.GONE);
                            con_layout.setVisibility(View.GONE);
                        }

                        if (volunteerDashboardCombineResponse.getResData().getUserCoverPic() != null) {

                            if (!sharedPref.getCoverImage().equalsIgnoreCase(volunteerDashboardCombineResponse.getResData().getUserCoverPic())) {
                                sharedPref.setCoverImage(volunteerDashboardCombineResponse.getResData().getUserCoverPic());

                                Picasso.with(context).load(sharedPref.getCoverImage())
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(R.drawable.back)
                                        .error(R.drawable.back)
                                        .into(StudentDashboardActivity.coverNav);
                            }
                        }

                        if (volunteerDashboardCombineResponse.getResData().getUserProfilePic() != null) {
                            if (!sharedPref.getProfileImage().equalsIgnoreCase(volunteerDashboardCombineResponse.getResData().getUserProfilePic())) {
                                sharedPref.setProfileImage(volunteerDashboardCombineResponse.getResData().getUserProfilePic());

                                Picasso.with(context).load(sharedPref.getProfileImage())
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(R.drawable.user_png)
                                        .error(R.drawable.user_png)
                                        .into(StudentDashboardActivity.user);
                                Picasso.with(context).load(sharedPref.getProfileImage())
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(R.drawable.user_png)
                                        .error(R.drawable.user_png)
                                        .into(StudentDashboardActivity.userNav);
                            }
                        }

                    } else if (volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("401")) {
                        eventDataList.clear();
                        listView.setVisibility(View.GONE);
                        noUpcomingEvents.setVisibility(View.VISIBLE);
                    }

                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    listView.setVisibility(View.GONE);
                    noUpcomingEvents.setVisibility(View.VISIBLE);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
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

    private class DBSaveUpcomingEventsForVolunteer extends AsyncTask<Void, Void, Void> {
        List<VolunteerDashboardCombineResponse.EventData> eventDataList;

        public DBSaveUpcomingEventsForVolunteer(List<VolunteerDashboardCombineResponse.EventData> eventDataList) {
            this.eventDataList = eventDataList;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (roomDB.getUpcomingVolunteerEventsDao().getAll().size() == 0) {
                roomDB.getUpcomingVolunteerEventsDao().insertAll(eventDataList);
            } else {
                roomDB.getUpcomingVolunteerEventsDao().deleteAll();
                roomDB.getUpcomingVolunteerEventsDao().insertAll(eventDataList);
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!sharedPref.getCoverImage().isEmpty()) {
            Picasso.with(context).load(sharedPref.getCoverImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.back)
                    .error(R.drawable.back)
                    .into(StudentDashboardActivity.coverNav);
        } else {
            Picasso.with(context).load(R.drawable.back).into(StudentDashboardActivity.coverNav);
        }

        if (!sharedPref.getProfileImage().isEmpty()) {
            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(StudentDashboardActivity.user);
            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(StudentDashboardActivity.userNav);
        } else {
            Picasso.with(context).load(R.drawable.user).into(StudentDashboardActivity.user);
            Picasso.with(context).load(R.drawable.user).into(StudentDashboardActivity.userNav);
        }

        hitInsertFirebaseId();
        if (Utility.isNetworkAvailable(context))
            hitUpcomingEventsApi();
        else
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
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

    public void getRegisteredCSO() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            ApprovedCSORequest request = new ApprovedCSORequest();
            request.setUserId(sharedPref.getUserId());
            request.setSeachRowNumber("0");
            request.setSearchCity("");
            request.setSearchEventType("");
            request.setSearchPostcode("");
            request.setSearchState("");
            request.setSearchKeyword("");
            request.setSearchPageSize("10");
            request.setSearchOrg("");

            Log.i("", "" + new Gson().toJson(request));

            approvedCSOViewModel.getApprovedCSOResponse(request).observe((LifecycleOwner) context, approvedCSOResponse -> {

                if (approvedCSOResponse != null) {
                    if (approvedCSOResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (approvedCSOResponse.getResData() != null) {

                            approvedCSOList = approvedCSOResponse.getResData();
                            noEvents.setVisibility(View.GONE);

                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setItemViewCacheSize(20);
                            recyclerView.setDrawingCacheEnabled(true);
                            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            LinearSnapHelper snapHelper = new LinearSnapHelper();
                            ApprovedCSOAdapter approvedCSOAdapter = new ApprovedCSOAdapter(context, approvedCSOList);
                            recyclerView.setAdapter(approvedCSOAdapter);
                            recyclerView.setOnFlingListener(null);
                            snapHelper.attachToRecyclerView(recyclerView);
                        }
                    } else if (approvedCSOResponse.getResStatus().equalsIgnoreCase("401")) {
                        noEvents.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
