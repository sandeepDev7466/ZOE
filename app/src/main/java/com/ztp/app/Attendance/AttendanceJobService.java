package com.ztp.app.Attendance;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
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

public class AttendanceJobService extends JobService {

    GPSTracker gpsTracker;
    Handler mHandler;
    MyToast myToast;
    ApiInterface apiInterface;
    RoomDB roomDB;
    Context context;
    private boolean jobCancelled;
    Handler mainHandler;
    int i;
    SharedPref sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        gpsTracker = new GPSTracker(this);
        mHandler = new Handler();
        myToast = new MyToast(this);
        apiInterface = Api.getClient();
        roomDB = RoomDB.getInstance(this);
        mainHandler = new Handler(getMainLooper());
        sharedPref = SharedPref.getInstance(context);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(JobParameters params) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void run() {
                mHandler.post(() -> {

                    new AsyncTask<Void, Void, List<VolunteerDashboardCombineResponse.EventData>>() {
                        @Override
                        protected void onPostExecute(List<VolunteerDashboardCombineResponse.EventData> eventData) {

                            if (jobCancelled) {
                                return;
                            }

                            try {

                                if (eventData != null) {
                                    VolunteerDashboardCombineResponse.EventData event = eventData.get(0);
                                    double event_latitude = Double.parseDouble(event.getEventLatitude().isEmpty() ? "0.00" : event.getEventLatitude());
                                    double event_longitude = Double.parseDouble(event.getEventLongitude().isEmpty() ? "0.00" : event.getEventLongitude());

                                    Date shiftDate = Utility.convertStringToDateWithoutTime(eventData.get(0).getShiftDate());
                                    //Date shiftDate = Utility.convertStringToDateWithoutTime(Utility.getCurrentDate());
                                    Date currentDate = Utility.convertStringToDateWithoutTime(Utility.getCurrentDate());

                                    String shiftStart = event.getShiftStartTime();
                                    String shiftEnd = event.getShiftEndTime();
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
                                            if (Utility.distanceBetweenLatLong(gpsTracker.getLatitude(), gpsTracker.getLongitude(),event_latitude, event_longitude) < 1) {

                                                //Toast.makeText(AttendanceService.this, "DISTANCE : " + Utility.distanceBetweenLatLong(gpsTracker.getLatitude(), gpsTracker.getLongitude(), event_latitude, event_longitude), Toast.LENGTH_SHORT).show();

                                                hitAttendanceApi(event);

                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                jobFinished(params, false);
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
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }

    public void hitAttendanceApi(VolunteerDashboardCombineResponse.EventData eventData) {

        if (Utility.isNetworkAvailable(context)) {

            MarkAttendanceRequest markAttendanceRequest = new MarkAttendanceRequest();
            markAttendanceRequest.setEventId(eventData.getEventId());
            markAttendanceRequest.setShiftId(eventData.getShiftId());
            markAttendanceRequest.setUserId(sharedPref.getUserId());
            markAttendanceRequest.setLogLatitude(eventData.getEventLatitude());
            markAttendanceRequest.setLogLongitude(eventData.getEventLongitude());
            markAttendanceRequest.setLogTrackTime(Utility.getDashboardCurrentDateTime());

            Log.i("REQUEST","" + new Gson().toJson(markAttendanceRequest));

            Call<MarkAttendanceResponse> call = apiInterface.markAttendance(markAttendanceRequest);

            call.enqueue(new Callback<MarkAttendanceResponse>() {
                @Override
                public void onResponse(Call<MarkAttendanceResponse> call, Response<MarkAttendanceResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i("RESPONSE","" + new Gson().toJson(response.body()));
                            myToast.show("Attendance marked successfully", Toast.LENGTH_SHORT, true);

                        }
                    } else {
                        myToast.show("Attendance failed", Toast.LENGTH_SHORT, false);
                    }
                }

                @Override
                public void onFailure(Call<MarkAttendanceResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });


            /*markAttendanceViewModel.markAttendanceResponse(markAttendanceRequest).observe((LifecycleOwner) context, markAttendanceResponse -> {

                if (markAttendanceResponse != null) {

                    if(markAttendanceResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        myToast.show("Attendance marked", Toast.LENGTH_SHORT,false);
                    }
                    else if(markAttendanceResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show("Attendance failed",Toast.LENGTH_SHORT,false);
                    }
                    else
                    {
                        myToast.show(getString(R.string.err_server),Toast.LENGTH_SHORT,false);
                    }

                } else {
                    myToast.show(getString(R.string.err_server),Toast.LENGTH_SHORT,false);
                }
            });*/

        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
