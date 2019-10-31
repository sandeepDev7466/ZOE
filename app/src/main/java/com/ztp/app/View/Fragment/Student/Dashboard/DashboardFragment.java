package com.ztp.app.View.Fragment.Student.Dashboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ztp.app.Attendance.AttendanceService;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ApprovedCSORequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerDashboardCombineRequest;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
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
import java.util.HashMap;
import java.util.List;

public class DashboardFragment extends Fragment {

    Context context;
    ViewPager viewPager;
    TabLayout tabLayout;
    int count;
    List<HashMap<String, Object>> data = new ArrayList<>();
    LinearLayout volunter, upload, target, message, cso, upcoming_events;
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
    ApprovedCSOViewModel approvedCSOViewModel;
    List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList = new ArrayList<>();
    MyTextView noEvents, noUpcomingEvents;
    MyBoldTextView seeAll;
    Address fetchedAddress;
    StringBuilder strAddress;
    String postalCode;
    final static int DIFF = 5;
    int offset = 0, limit = DIFF;
    MyHeadingTextView name;
    ImageView rankImage;
    MyHeadingTextView volHours;
    InsertFirebaseIdViewModel insertFirebaseIdViewModel;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        View view = inflater.inflate(R.layout.fragment_dashboard_stu, container, false);
        myToast = new MyToast(context);
        gpsTracker = new GPSTracker(context);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        roomDB = RoomDB.getInstance(context);
        approvedCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(ApprovedCSOViewModel.class);
        volunteerDashboardCombineViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerDashboardCombineViewModel.class);
        insertFirebaseIdViewModel = ViewModelProviders.of((FragmentActivity) context).get(InsertFirebaseIdViewModel.class);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert(context);
        }

        init(view);

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
        name = view.findViewById(R.id.name);
        rankImage = view.findViewById(R.id.rankImage);
        volHours = view.findViewById(R.id.volHours);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        name.setText("HEY " + sharedPref.getFirstName().toUpperCase() + " " + sharedPref.getLastName().toUpperCase() + " !");

       /* recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int total = linearLayoutManager.getItemCount();
                int firstVisibleItemCount = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemCount = linearLayoutManager.findLastVisibleItemPosition();

                //to avoid multiple calls to loadMore() method
                //maintain a boolean value (isLoading). if loadMore() task started set to true and completes set to false
                if (!isLoading) {
                    if (total > 0)
                        if ((total - 1) == lastVisibleItemCount){
                            loadMore();//your HTTP stuff goes in this method
                            loadingProgress.setVisibility(View.VISIBLE);
                        }else
                            loadingProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

        });*/

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
                        volHours.setText(volunteerDashboardCombineResponse.getResData().getVolHours() + " " + getString(R.string.VOL_HOURS));
                        sharedPref.setVolHours(volunteerDashboardCombineResponse.getResData().getVolHours());
                    }

                    if (volunteerDashboardCombineResponse.getResData().getVolRank() != null) {
                        int rank = (int) Math.ceil(Double.parseDouble(volunteerDashboardCombineResponse.getResData().getVolRank()));
                        sharedPref.setVolRank(volunteerDashboardCombineResponse.getResData().getVolRank());
                        if (rank == 1)
                            Picasso.with(context).load(R.drawable.rank_one_stu).into(rankImage);
                        else if (rank == 2)
                            Picasso.with(context).load(R.drawable.rank_two_stu).into(rankImage);
                        else if (rank == 3)
                            Picasso.with(context).load(R.drawable.rank_three_stu).into(rankImage);
                        else if (rank == 4)
                            Picasso.with(context).load(R.drawable.rank_four_stu).into(rankImage);
                        else if (rank == 5)
                            Picasso.with(context).load(R.drawable.rank_five_stu).into(rankImage);
                    }

                    if (volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("200")) {
                        listView.setVisibility(View.VISIBLE);
                        noUpcomingEvents.setVisibility(View.GONE);
                        Log.i("REQUEST", "" + new Gson().toJson(volunteerDashboardCombineResponse));
                        if (volunteerDashboardCombineResponse.getResData().getEventData() != null) {

                            if (volunteerDashboardCombineResponse.getResData().getEventData().size() > 0) {

                                eventDataList = volunteerDashboardCombineResponse.getResData().getEventData();

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

                       /* if (!sharedPref.getProfileImage().isEmpty()) {

                            Picasso.with(context).load(sharedPref.getProfileImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.user_png)
                                    .error(R.drawable.user_png)
                                    .into(StudentDashboardActivity.user);
                            Picasso.with(context).load(sharedPref.getProfileImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.user_png)
                                    .error(R.drawable.user_png)
                                    .into(StudentDashboardActivity.userNav);


                        } else {
                            Picasso.with(context).load(R.drawable.user).into(StudentDashboardActivity.user);
                            Picasso.with(context).load(R.drawable.user).into(StudentDashboardActivity.userNav);
                        }*/

                        /*if (!sharedPref.getCoverImage().isEmpty()) {

                         *//* Picasso.with(context).load(sharedPref.getCoverImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.scene)
                                    .error(R.drawable.scene)
                                    .into(StudentDashboardActivity.cover);*//*

                            Picasso.with(context).load(sharedPref.getCoverImage())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE )
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .placeholder(R.drawable.back)
                                    .error(R.drawable.back)
                                    .into(StudentDashboardActivity.coverNav);

                        } else {
                           // Picasso.with(context).load(R.drawable.scene).into(StudentDashboardActivity.cover);
                            Picasso.with(context).load(R.drawable.scene).into(StudentDashboardActivity.coverNav);
                        }*/

                    } else if (volunteerDashboardCombineResponse.getResStatus().equalsIgnoreCase("401")) {
                        // myToast.show(getString(R.string.err_events_not_found), Toast.LENGTH_SHORT, false);

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

   /* public void getAddress(double LATITUDE, double LONGITUDE) {

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


                //myToast.show("Found "+address+" "+city+" "+state+" "+country+" "+postalCode, Toast.LENGTH_LONG, false);

                hitFindCsoByArea(postalCode);

            }
            else
            {
               // myToast.show("Else "+ sharedPref.getPostalCode(), Toast.LENGTH_LONG, false);
                hitFindCsoByArea(sharedPref.getPostalCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //myToast.show("Exception "+ sharedPref.getPostalCode(), Toast.LENGTH_LONG, false);
            hitFindCsoByArea(sharedPref.getPostalCode());
        }
    }
*/

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

        //hitInsertFirebaseId();
        hitUpcomingEventsApi();
    }

    /*public void hitInsertFirebaseId() {
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
    }*/

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
