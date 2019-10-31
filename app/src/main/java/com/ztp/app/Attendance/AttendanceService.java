package com.ztp.app.Attendance;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.MarkAttendanceRequest;
import com.ztp.app.Data.Remote.Model.Response.MarkAttendanceResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.Utility;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceService extends Service {

    GPSTracker gpsTracker;
    Handler mHandler;
    MyToast myToast;
    ApiInterface apiInterface;
    RoomDB roomDB;
    Context context;
    SharedPref sharedPref;

    public AttendanceService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sharedPref = SharedPref.getInstance(context);
        gpsTracker = new GPSTracker(this);
        mHandler = new Handler();
        myToast = new MyToast(this);
        apiInterface = Api.getClient();
        roomDB = RoomDB.getInstance(this);

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(() -> {

                    new AsyncTask<Void, Void, List<VolunteerDashboardCombineResponse.EventData>>() {
                        @Override
                        protected void onPostExecute(List<VolunteerDashboardCombineResponse.EventData> eventData) {

                            try {
                                if (eventData != null) {
                                    double event_latitude = Double.parseDouble(eventData.get(0).getEventLatitude().isEmpty() ? "0.00" : eventData.get(0).getEventLatitude());
                                    double event_longitude = Double.parseDouble(eventData.get(0).getEventLongitude().isEmpty() ? "0.00" : eventData.get(0).getEventLongitude());

                                    Date shiftDate = Utility.convertStringToDateWithoutTime(eventData.get(0).getShiftDate());
                                    Date currentDate = Utility.convertStringToDateWithoutTime(Utility.getCurrentDate());

                                    String shiftStart = eventData.get(0).getShiftStartTime();
                                    String shiftEnd = eventData.get(0).getShiftEndTime();
                                    String current = Utility.getCurrentTimeOnly();

                                    if (shiftDate.equals(currentDate)) {

                                        int hourStart = Integer.parseInt(shiftStart.split(":")[0]);
                                        int minuteStart = Integer.parseInt(shiftStart.split(":")[1]);
                                        int secondsStart = Integer.parseInt(shiftStart.split(":")[2]);

                                        int tempStart = (60 * minuteStart) + (3600 * hourStart) + secondsStart;

                                        int hourCurrent = Integer.parseInt(current.split(":")[0]);
                                        int minuteCurrent = Integer.parseInt(current.split(":")[1]);
                                        int secondsCurrent = Integer.parseInt(current.split(":")[2]);

                                        int tempCurrent = (60 * minuteCurrent) + (3600 * hourCurrent) + secondsCurrent;


                                        int hourEnd = Integer.parseInt(shiftEnd.split(":")[0]);
                                        int minuteEnd = Integer.parseInt(shiftEnd.split(":")[1]);
                                        int secondsEnd = Integer.parseInt(shiftEnd.split(":")[2]);

                                        int tempEnd = (60 * minuteEnd) + (3600 * hourEnd) + secondsEnd;


                                        if (tempCurrent >= tempStart && tempEnd >= tempCurrent) {
                                            if (Utility.distanceBetweenLatLong(gpsTracker.getLatitude(), gpsTracker.getLongitude(), event_latitude, event_longitude) < 1) {

                                                //Toast.makeText(AttendanceService.this, "DISTANCE : " + Utility.distanceBetweenLatLong(gpsTracker.getLatitude(), gpsTracker.getLongitude(), event_latitude, event_longitude), Toast.LENGTH_SHORT).show();

                                                hitAttendanceApi(eventData.get(0), gpsTracker.getLatitude(), gpsTracker.getLongitude());
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        protected List<VolunteerDashboardCombineResponse.EventData> doInBackground(Void... voids) {
                            return roomDB.getUpcomingVolunteerEventsDao().getAll();
                        }
                    }.execute();

                });

            }

        }, 0, 15 * 60 * 1000);

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void hitAttendanceApi(VolunteerDashboardCombineResponse.EventData eventData, double latitiude, double longitude) {
        if (Utility.isNetworkAvailable(context)) {

            MarkAttendanceRequest markAttendanceRequest = new MarkAttendanceRequest();
            markAttendanceRequest.setUserId(sharedPref.getUserId());
            markAttendanceRequest.setEventId(eventData.getEventId());
            markAttendanceRequest.setShiftId(eventData.getShiftId());
            markAttendanceRequest.setLogLatitude(String.valueOf(latitiude));
            markAttendanceRequest.setLogLongitude(String.valueOf(longitude));
            markAttendanceRequest.setLogTrackTime(Utility.getDashboardCurrentDateTime());

            Call<MarkAttendanceResponse> call = Api.getClient().markAttendance(markAttendanceRequest);

            call.enqueue(new Callback<MarkAttendanceResponse>() {
                @Override
                public void onResponse(Call<MarkAttendanceResponse> call, Response<MarkAttendanceResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResStatus().equalsIgnoreCase("200")) {
                                myToast.show("Attendance marked", Toast.LENGTH_SHORT, false);
                            } else if (response.body().getResStatus().equalsIgnoreCase("401")) {
                                myToast.show("Attendance failed", Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                }

                @Override
                public void onFailure(Call<MarkAttendanceResponse> call, Throwable t) {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
