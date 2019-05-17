package com.ztp.app.View.Fragment.CSO.Event;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import com.ztp.app.Data.Remote.Model.Request.ShiftUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.SiftAddRequest;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.AddShiftViewModel;
import com.ztp.app.Viewmodel.UpdateShiftViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewShiftFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();

    public AddNewShiftFragment() {
        // Required empty public constructor
    }

    MyTextInputEditText et_date;
    MyTextInputEditText et_volunteer;
    MyTextInputEditText et_start_time;
    MyTextInputEditText et_end_time_volunteers;
    MyTextInputEditText et_task;
    String str_date = "";
    String str_vol = "";
    String str_start_time = "";
    String str_end_time = "";
    String str_task = "";
    String str_rank = "";
    Context context;
    AddShiftViewModel addShiftViewModel;
    UpdateShiftViewModel updateShiftViewModel;
    MyProgressDialog myProgressDialog;
    Button update;
    Button clear;
    MyToast myToast;
    String event_id = "";
    GetCSOShiftResponse.ResData shiftData;
    String status = "";
    ScaleRatingBar rank;
    float ratingRank;
    String startTime,endTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_new_shift, container, false);

        init(v);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init(View v) {

        addShiftViewModel = ViewModelProviders.of(this).get(AddShiftViewModel.class);
        updateShiftViewModel = ViewModelProviders.of(this).get(UpdateShiftViewModel.class);
        et_date = v.findViewById(R.id.et_date);
        et_start_time = v.findViewById(R.id.et_start_time);
        et_end_time_volunteers = v.findViewById(R.id.et_end_time_volunteers);
        et_volunteer = v.findViewById(R.id.et_volunteers);
        et_task = v.findViewById(R.id.et_task);
        update = v.findViewById(R.id.update);
        clear = v.findViewById(R.id.clear);
        rank = v.findViewById(R.id.rank);
        myToast = new MyToast(context);

        if (getArguments() != null && getArguments().getString("status").equalsIgnoreCase("add")) {
            event_id = getArguments().getString("event_id");
            status = "add";
            update.setText(R.string.add);
        } else {
            status = "update";
            update.setText(R.string.update);
        }

        rank.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                ratingRank = v;
            }
        });


        myProgressDialog = new MyProgressDialog(context);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        };

        et_date.setOnTouchListener((v12, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return true;
            }
            return false;
        });


        et_start_time.setOnTouchListener((v1, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                et_end_time_volunteers.setText("");

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime = String.format(Locale.ENGLISH,"%02d:%02d",selectedHour,selectedMinute);
                        et_start_time.setText(startTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                return true;
            }
            return false;
        });


        et_end_time_volunteers.setOnTouchListener((v13, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                if(et_start_time.getText()!=null && !et_start_time.getText().toString().isEmpty())
                {

                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {

                        endTime = String.format(Locale.ENGLISH,"%02d:%02d",selectedHour,selectedMinute);

                        int hour1=Integer.parseInt(startTime.split(":")[0]);
                        int minute1=Integer.parseInt(startTime.split(":")[1]);

                        int tempStart = (60 * minute1) + (3600 * hour1);


                        int hour2=Integer.parseInt(endTime.split(":")[0]);
                        int minute2=Integer.parseInt(endTime.split(":")[1]);

                        int tempEnd = (60 * minute2) + (3600 * hour2);

                        if(tempStart>tempEnd)
                        {
                            myToast.show(getString(R.string.err_endtime_notbefore_startime),Toast.LENGTH_SHORT,false);
                            et_end_time_volunteers.setText("");
                        }
                        else
                        {
                            et_end_time_volunteers.setText(endTime);
                        }

                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
                else
                {
                    myToast.show(getString(R.string.err_enter_start_time_first),Toast.LENGTH_SHORT,false);
                }

                return true;
            }
            return false;
        });

        if (status.equalsIgnoreCase("add")) {
            update.setOnClickListener(v14 -> addShift());
        } else {
            Bundle b = getArguments();
            shiftData = (GetCSOShiftResponse.ResData) b.getSerializable("shiftData");
            populateData(shiftData);
            update.setOnClickListener(v14 -> updateShift());
        }

        clear.setOnClickListener(v15 -> {

          /*  if(status.equalsIgnoreCase("update"))
            {

            }
            else
            {

            }*/

        });
    }

    private void updateLabel() {
        et_date.setText(Utility.formatDateFull(myCalendar.getTime()));
    }

    public void addShift() {

        str_date = et_date.getText().toString().trim();
        str_start_time = et_start_time.getText().toString().trim();
        str_end_time = et_end_time_volunteers.getText().toString().trim();
        str_vol = et_volunteer.getText().toString().trim();
        str_task = et_task.getText().toString().trim();
        str_rank = String.valueOf(ratingRank);

        if (str_date != null && !str_date.isEmpty()) {
            if (str_vol != null && !str_vol.isEmpty()) {
                if (str_start_time != null && !str_start_time.isEmpty()) {
                    if (str_end_time != null && !str_end_time.isEmpty()) {
                        if (str_task != null && !str_task.isEmpty()) {
                            if (str_rank != null && !str_rank.isEmpty()) {

                                myProgressDialog.show(getString(R.string.please_wait));
                                SiftAddRequest siftAddRequest = new SiftAddRequest();
                                siftAddRequest.setEvent_id(event_id);
                                siftAddRequest.setShift_date(str_date);
                                siftAddRequest.setShift_start_time(str_start_time);
                                siftAddRequest.setShift_end_time(str_end_time);
                                siftAddRequest.setShift_vol_req(str_vol);
                                siftAddRequest.setShift_task(str_task);
                                siftAddRequest.setShift_rank(str_rank);

                                if (Utility.isNetworkAvailable(context)) {
                                    addShiftViewModel.getAddShiftResponse(siftAddRequest).observe((LifecycleOwner) context, shiftResponse -> {

                                        if (shiftResponse != null) {
                                            if (shiftResponse.getResStatus().equalsIgnoreCase("200")) {
                                                if (status.equalsIgnoreCase("add"))
                                                    myToast.show(getString(R.string.shift_added_successfully), Toast.LENGTH_SHORT, true);

                                                ((AppCompatActivity)context).getSupportFragmentManager().popBackStack();
                                            } else {
                                                myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                                            }
                                        } else {
                                            myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                                        }
                                        myProgressDialog.dismiss();
                                    });
                                } else {
                                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                                }
                            } else {
                                myToast.show(getString(R.string.err_enter_rank), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.err_enter_task), Toast.LENGTH_SHORT, false);
                        }

                    } else {
                        myToast.show(getString(R.string.err_select_end_time), Toast.LENGTH_SHORT, false);

                    }
                } else {
                    myToast.show(getString(R.string.err_select_start_time), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.err_enter_number_volunteer), Toast.LENGTH_SHORT, false);
            }
        } else {
            myToast.show(getString(R.string.err_select_start_date), Toast.LENGTH_SHORT, false);
        }

    }

    public void updateShift() {
        str_date = et_date.getText().toString().trim();
        str_start_time = et_start_time.getText().toString().trim();
        str_end_time = et_end_time_volunteers.getText().toString().trim();
        str_vol = et_volunteer.getText().toString().trim();
        str_task = et_task.getText().toString().trim();
        str_rank = String.valueOf(ratingRank);

        if (str_date != null && !str_date.isEmpty()) {
            if (str_vol != null && !str_vol.isEmpty()) {
                if (str_start_time != null && !str_start_time.isEmpty()) {
                    if (str_end_time != null && !str_end_time.isEmpty()) {
                        if (str_task != null && !str_task.isEmpty()) {
                            if (str_rank != null && !str_rank.isEmpty()) {

                                myProgressDialog.show(getString(R.string.please_wait));
                                ShiftUpdateRequest shiftUpdateRequest = new ShiftUpdateRequest();
                                shiftUpdateRequest.setShift_id(shiftData.getShiftId());

                                Date d = Utility.convertStringToDateWithoutTime(str_date);
                                shiftUpdateRequest.setShift_date(Utility.formatDateFull(d));

//                              shiftUpdateRequest.setShift_date(str_date);
                                shiftUpdateRequest.setShift_start_time(str_start_time);
                                shiftUpdateRequest.setShift_end_time(str_end_time);
                                shiftUpdateRequest.setShift_vol_req(str_vol);
                                shiftUpdateRequest.setShift_task(str_task);
                                shiftUpdateRequest.setShift_rank(str_rank);

                                if (Utility.isNetworkAvailable(context)) {
                                    updateShiftViewModel.getUpdateShiftResponse(shiftUpdateRequest).observe((LifecycleOwner) context, shiftResponse -> {
                                        if (shiftResponse != null) {
                                            if (shiftResponse.getResStatus().equalsIgnoreCase("200")) {
                                                myToast.show(getString(R.string.shift_updated_success), Toast.LENGTH_SHORT, true);
                                                ((AppCompatActivity)context).getSupportFragmentManager().popBackStack();

                                            } else {
                                                myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, true);
                                            }
                                        } else {
                                            myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, true);
                                        }
                                        myProgressDialog.dismiss();
                                    });
                                } else {
                                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, true);
                                }
                            } else {
                                myToast.show(getString(R.string.err_enter_rank), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.task), Toast.LENGTH_SHORT, false);
                        }

                    } else {
                        myToast.show(getString(R.string.err_select_end_time), Toast.LENGTH_SHORT, false);

                    }
                } else {
                    myToast.show(getString(R.string.err_select_start_time), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.err_enter_number_volunteer), Toast.LENGTH_SHORT, false);
            }
        } else {
            myToast.show(getString(R.string.err_select_start_date), Toast.LENGTH_SHORT, false);
        }

    }


    public void populateData(GetCSOShiftResponse.ResData shiftData) {
        et_date.setText(shiftData.getShiftDate());
        et_start_time.setText(shiftData.getShift_start_time_s());
        et_end_time_volunteers.setText(shiftData.getShift_end_time_s());
        et_volunteer.setText(shiftData.getShiftVolReq());
        et_task.setText(shiftData.getShiftTask());
        rank.setRating(Float.parseFloat(shiftData.getShiftRank()));
    }
}
