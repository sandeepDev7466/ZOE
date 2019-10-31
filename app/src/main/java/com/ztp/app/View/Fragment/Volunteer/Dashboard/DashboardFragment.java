package com.ztp.app.View.Fragment.Volunteer.Dashboard;

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
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ztp.app.Attendance.AttendanceService;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.InsertFirebaseIdRequest;
import com.ztp.app.Data.Remote.Model.Request.SearchEventbyAreaRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerDashboardCombineRequest;
import com.ztp.app.Data.Remote.Model.Response.SearchEventbyAreaResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;
import com.ztp.app.View.Fragment.Volunteer.Extra.UpcomingEventsFragment;
import com.ztp.app.View.Fragment.Volunteer.Extra.VolunteerUpcomingEventAdapter;
import com.ztp.app.Viewmodel.InsertFirebaseIdViewModel;
import com.ztp.app.Viewmodel.SearchEventByAreaViewModel;
import com.ztp.app.Viewmodel.VolunteerDashboardCombineViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    Context context;
    MyToast myToast;
    GPSTracker gpsTracker;
    double latitude, longitude;
    VolunteerDashboardCombineViewModel volunteerDashboardCombineViewModel;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;
    List<VolunteerDashboardCombineResponse.EventData> eventDataList = new ArrayList<>();
    RoomDB roomDB;
    MyTextView tv_unread_count;
    ListView listView;
    RecyclerView recyclerView;
    SearchEventByAreaViewModel searchEventViewModel;
    List<SearchEventbyAreaResponse.SearchedEventByArea> eventsByAreaList = new ArrayList<>();
    MyTextView noEvents, noUpcomingEvents;
    MyBoldTextView seeAll;
    InsertFirebaseIdViewModel insertFirebaseIdViewModel;
    LinearLayout timerLayout, con_layout;
    MyBoldTextView tv_days, tv_hours, tv_minutes, tv_seconds;
    private Handler handler;
    private Runnable runnable;
    int countDown;
    MyHeadingTextView con_txt;
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
        View view = inflater.inflate(R.layout.fragment_dashboard_new, container, false);
        myToast = new MyToast(context);
        gpsTracker = new GPSTracker(context);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        myProgressDialog = new MyProgressDialog(context);
        roomDB = RoomDB.getInstance(context);
        countDown = 0;
        handler = new Handler();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        searchEventViewModel = ViewModelProviders.of((FragmentActivity) context).get(SearchEventByAreaViewModel.class);
        volunteerDashboardCombineViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerDashboardCombineViewModel.class);
        insertFirebaseIdViewModel = ViewModelProviders.of((FragmentActivity) context).get(InsertFirebaseIdViewModel.class);
        /*if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert(context);
        }*/
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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.isNetworkAvailable(context))
                    hitUpcomingEventsApi();
                else
                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("event_list", (Serializable) eventDataList);
                UpcomingEventsFragment upcomingEventsFragment = new UpcomingEventsFragment();
                upcomingEventsFragment.setArguments(bundle);
                Utility.replaceFragment(context, upcomingEventsFragment, "UpcomingEventsFragment");
            }
        });

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        gpsTracker.stopUsingGPS();
        //LocalBroadcastManager.getInstance(context).unregisterReceiver(mLocationReceiver);

    }

    public void init(View view) {
        seeAll = view.findViewById(R.id.seeAll);
        listView = view.findViewById(R.id.listView);
        recyclerView = view.findViewById(R.id.recyclerView);
        noEvents = view.findViewById(R.id.noEvents);
        noUpcomingEvents = view.findViewById(R.id.noUpcomingEvents);
        // name = view.findViewById(R.id.name);
        // rankImage = view.findViewById(R.id.rankImage);
        // volHours = view.findViewById(R.id.volHours);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        //layoutManager.setStackFromEnd(true);
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
                ((VolunteerDashboardActivity) context).setLockerFragment();
                break;
            case R.id.target:
                ((VolunteerDashboardActivity) context).setTargetFragment();
                break;
            case R.id.message:
                ((VolunteerDashboardActivity) context).setHangoutFragment();
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
        //getAddress(gpsTracker.getLatitude(), gpsTracker.getLongitude());

        progress.setVisibility(View.VISIBLE);

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
                        if (volunteerDashboardCombineResponse.getResData().getUserZipCode() != null)
                            hitFindCsoByArea(volunteerDashboardCombineResponse.getResData().getUserZipCode());
                        else
                            hitFindCsoByArea("");
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
                                        .into(VolunteerDashboardActivity.coverNav);
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
                                        .into(VolunteerDashboardActivity.user);
                                Picasso.with(context).load(sharedPref.getProfileImage())
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(R.drawable.user_png)
                                        .error(R.drawable.user_png)
                                        .into(VolunteerDashboardActivity.userNav);
                            }
                        }

                       /* if (!sharedPref.getProfileImage().isEmpty()) {

                            Picasso.with(context).load(sharedPref.getProfileImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.user_png)
                                    .error(R.drawable.user_png)
                                    .into(VolunteerDashboardActivity.user);
                            Picasso.with(context).load(sharedPref.getProfileImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.user_png)
                                    .error(R.drawable.user_png)
                                    .into(VolunteerDashboardActivity.userNav);


                        } else {
                            Picasso.with(context).load(R.drawable.user).into(VolunteerDashboardActivity.user);
                            Picasso.with(context).load(R.drawable.user).into(VolunteerDashboardActivity.userNav);
                        }*/

                        /*if (!sharedPref.getCoverImage().isEmpty()) {

                         *//* Picasso.with(context).load(sharedPref.getCoverImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.scene)
                                    .error(R.drawable.scene)
                                    .into(VolunteerDashboardActivity.cover);*//*

                            Picasso.with(context).load(sharedPref.getCoverImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.back)
                                    .error(R.drawable.back)
                                    .into(VolunteerDashboardActivity.coverNav);

                        } else {
                           // Picasso.with(context).load(R.drawable.scene).into(VolunteerDashboardActivity.cover);
                            Picasso.with(context).load(R.drawable.scene).into(VolunteerDashboardActivity.coverNav);
                        }*/

                    } else if (volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("401")) {
                        // myToast.show(getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);
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

    /*private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                fetchedAddress = addresses.get(0);
                strAddress = new StringBuilder();

                if (fetchedAddress.getMaxAddressLineIndex() > 0) {
                    for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i));
                    }
                } else {
                    try {
                        strAddress.append(fetchedAddress.getAddressLine(0));
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }

                if (fetchedAddress.getPostalCode() != null) {
                    postalCode = fetchedAddress.getPostalCode();
                    hitFindCsoByArea(postalCode);
                    //search(offset, limit, false);
                }

            } else {
                postalCode = "";
                //search(offset, limit, false);
                hitFindCsoByArea(postalCode);
                //myToast.show(getString(R.string.err_unable_to_get_address), Toast.LENGTH_SHORT, false);

            }


        } catch (IOException e) {
            e.printStackTrace();
            // myToast.show(getString(R.string.err_unable_to_get_address), Toast.LENGTH_SHORT, false);
        }
    }
*/

    /*public void getAddress(double LATITUDE, double LONGITUDE) {

        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                hitFindCsoByArea(postalCode);
                myToast.show(LATITUDE + " , " + LONGITUDE + " \n\t\t\t\t " + postalCode, Toast.LENGTH_SHORT, true);

            } else {
                noEvents.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                myToast.show(LATITUDE + " , " + LONGITUDE, Toast.LENGTH_SHORT, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            noEvents.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            myToast.show(LATITUDE + " , " + LONGITUDE + " \n\t\t\t\t " + e.getMessage(), Toast.LENGTH_SHORT, true);
        }
    }*/


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

   /* private void getUnreadCount() {
        if (!myProgressDialog.isShowing())
            myProgressDialog.show(getString(R.string.please_wait));

        GroupChannelListQuery channelListQuery = GroupChannel.createMyGroupChannelListQuery();
        channelListQuery.setIncludeEmpty(true);
        channelListQuery.next(new GroupChannelListQuery.GroupChannelListQueryResultHandler() {
            @Override
            public void onResult(List<GroupChannel> list, SendBirdException e) {
                int unreadMessageCount = 0;
                if (e != null) {    // Error.
                    return;
                }
                for (GroupChannel groupChannel : list) {
                    unreadMessageCount = unreadMessageCount + groupChannel.getUnreadMessageCount();
                }
                if (unreadMessageCount == 0)
                    tv_unread_count.setVisibility(View.GONE);
                else {
                    tv_unread_count.setVisibility(View.VISIBLE);
                    tv_unread_count.setText(String.valueOf(unreadMessageCount));
                }

            }
        });
        myProgressDialog.dismiss();

    }*/

    @Override
    public void onResume() {
        super.onResume();

        /*eventsByAreaList.clear();
        eventDataList.clear();*/

        if (!sharedPref.getCoverImage().isEmpty()) {
            Picasso.with(context).load(sharedPref.getCoverImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.back)
                    .error(R.drawable.back)
                    .into(VolunteerDashboardActivity.coverNav);
        } else {
            Picasso.with(context).load(R.drawable.back).into(VolunteerDashboardActivity.coverNav);
        }

        if (!sharedPref.getProfileImage().isEmpty()) {
            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(VolunteerDashboardActivity.user);
            Picasso.with(context).load(sharedPref.getProfileImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.user_png)
                    .error(R.drawable.user_png)
                    .into(VolunteerDashboardActivity.userNav);
        } else {
            Picasso.with(context).load(R.drawable.user).into(VolunteerDashboardActivity.user);
            Picasso.with(context).load(R.drawable.user).into(VolunteerDashboardActivity.userNav);
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

    public void hitFindCsoByArea(String postalCode) {
        if (Utility.isNetworkAvailable(context)) {
            //myProgressDialog.show(getString(R.string.please_wait));
            SearchEventbyAreaRequest request = new SearchEventbyAreaRequest();
            request.setSeachRowNumber("0");
            request.setSearch_city("");
            request.setSearch_event_type("");
            request.setSearch_postcode(postalCode);
            request.setSearch_state("");
            request.setSearchKeyword("");
            request.setSearchPageSize("10");
            request.setSearch_org("");

            Log.i("", "" + new Gson().toJson(request));

            searchEventViewModel.getSearchedEventsResponse(context, request).observe(this, searchEventbyAreaResponse -> {

                if (searchEventbyAreaResponse != null) {
                    Log.i("", "" + new Gson().toJson(searchEventbyAreaResponse));
                    if (searchEventbyAreaResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (searchEventbyAreaResponse.getSearchedEvents() != null) {
                            eventsByAreaList = searchEventbyAreaResponse.getSearchedEvents();
                            noEvents.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setItemViewCacheSize(20);
                            recyclerView.setDrawingCacheEnabled(true);
                            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                            LinearSnapHelper snapHelper = new LinearSnapHelper();
                            EventsInMyAreaAdapter eventsInMyAreaAdapter = new EventsInMyAreaAdapter(context, eventsByAreaList);
                            recyclerView.setAdapter(eventsInMyAreaAdapter);
                            recyclerView.setOnFlingListener(null);
                            snapHelper.attachToRecyclerView(recyclerView);
                        }
                    } else if (searchEventbyAreaResponse.getResStatus().equalsIgnoreCase("401")) {
                        //myToast.show(getString(R.string.no_events_found), Toast.LENGTH_LONG, false);
                        noEvents.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                }
                //myProgressDialog.dismiss();
                progress.setVisibility(View.GONE);
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

        }
    }
}
