package com.ztp.app.View.Fragment.CSO.Event;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ztp.app.Data.Remote.Model.Request.ShiftUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.SiftAddRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftListResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftAddResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.AddShiftViewModel;
import com.ztp.app.Viewmodel.UpdateShiftViewModel;

import java.text.SimpleDateFormat;
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
    MyTextInputEditText et_rank;
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
    String event_id="";
    GetShiftListResponse.ShiftData shiftData;
    String status="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_new_shift, container, false);

        if(getArguments()!=null && getArguments().getString("status").equalsIgnoreCase("add"))
        {
            event_id=getArguments().getString("event_id");
            status="add";
        }
        else
        {
            status="update";
        }

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
        et_rank = v.findViewById(R.id.et_rank);
        update = v.findViewById(R.id.update);
        clear = v.findViewById(R.id.clear);
        myToast = new MyToast(context);

        
        myProgressDialog = new MyProgressDialog(context);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            System.out.print(view.getId());
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

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), (timePicker, selectedHour, selectedMinute) -> et_start_time.setText(selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                return true;
            }
            return false;
        });


        et_end_time_volunteers.setOnTouchListener((v13, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), (timePicker, selectedHour, selectedMinute) -> et_end_time_volunteers.setText(selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                return true;
            }
            return false;
        });

        if(status.equalsIgnoreCase("add"))
        {
            update.setOnClickListener(v14 -> addShift());
        }
        else
        {
            Bundle b = getArguments();
            shiftData = (GetShiftListResponse.ShiftData) b.getSerializable("shiftData");
            populateData(shiftData);

            update.setOnClickListener(v14 -> updateShift());
        }

        clear.setOnClickListener(v15 -> {
//                Utility.replaceFragment(context, new AddNewShiftFragment(), "AddNewShiftFragment");
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

       // et_date.setText(sdf.format(myCalendar.getTime()));
        et_date.setText(Utility.formatDateFull(myCalendar.getTime()));
    }

    public void addShift() {

        str_date = et_date.getText().toString().trim();
        str_start_time = et_start_time.getText().toString().trim();
        str_end_time = et_end_time_volunteers.getText().toString().trim();
        str_vol = et_volunteer.getText().toString().trim();
        str_task = et_task.getText().toString().trim();
        str_rank = et_rank.getText().toString().trim();

        if (str_date != null && !str_date.isEmpty()) {
            if (str_vol != null && !str_vol.isEmpty()) {
                if (str_start_time != null && !str_start_time.isEmpty()) {
                    if (str_end_time != null && !str_end_time.isEmpty()) {
                        if (str_task != null && !str_task.isEmpty()) {
                            if (str_rank != null && !str_rank.isEmpty()) {

                                myProgressDialog.show("Please wait...");
                                SiftAddRequest siftAddRequest = new SiftAddRequest();
                                siftAddRequest.setEvent_id(event_id);
                                siftAddRequest.setShift_date(str_date);
                                siftAddRequest.setShift_start_time(str_start_time);
                                siftAddRequest.setShift_end_time(str_end_time);
                                siftAddRequest.setShift_vol_req(str_vol);
                                siftAddRequest.setShift_task(str_task);
                                siftAddRequest.setShift_rank(str_rank);


                                addShiftViewModel.getAddShiftResponse(siftAddRequest).observe((LifecycleOwner) context, shiftResponse -> {

                                    if (shiftResponse != null && shiftResponse.getResStatus().equalsIgnoreCase("200")) {
                                        myToast.show("Shift Added Successfully", Toast.LENGTH_SHORT, true);
                                        myProgressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Failedss",
                                                Toast.LENGTH_LONG).show();
                                        myProgressDialog.dismiss();
                                    }


                                });
                            } else {
                                myToast.show("Enter Rank", Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show("Enter Task", Toast.LENGTH_SHORT, false);
                        }

                    } else {
                        myToast.show("Select End Time", Toast.LENGTH_SHORT, false);

                    }
                } else {
                    myToast.show("Select Start Time", Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show("Enter No Of Volunteer", Toast.LENGTH_SHORT, false);

                //myToast.show("Select Start Time", Toast.LENGTH_SHORT, false);
            }
        } else {
            myToast.show("Select Start Date", Toast.LENGTH_SHORT, false);
        }

    }

    public void updateShift()
    {
        str_date = et_date.getText().toString().trim();
        str_start_time = et_start_time.getText().toString().trim();
        str_end_time = et_end_time_volunteers.getText().toString().trim();
        str_vol = et_volunteer.getText().toString().trim();
        str_task = et_task.getText().toString().trim();
        str_rank = et_rank.getText().toString().trim();

        if (str_date != null && !str_date.isEmpty()) {
            if (str_vol != null && !str_vol.isEmpty()) {
                if (str_start_time != null && !str_start_time.isEmpty()) {
                    if (str_end_time != null && !str_end_time.isEmpty()) {
                        if (str_task != null && !str_task.isEmpty()) {
                            if (str_rank != null && !str_rank.isEmpty()) {

                                myProgressDialog.show("Please wait...");
                                ShiftUpdateRequest shiftUpdateRequest = new ShiftUpdateRequest();
                                shiftUpdateRequest.setShift_id(shiftData.getShift_id());

                                Date d = Utility.convertStringToDateWithoutTime(str_date);
                                shiftUpdateRequest.setShift_date(Utility.formatDateFull(d));

//                              shiftUpdateRequest.setShift_date(str_date);
                                shiftUpdateRequest.setShift_start_time(str_start_time);
                                shiftUpdateRequest.setShift_end_time(str_end_time);
                                shiftUpdateRequest.setShift_vol_req(str_vol);
                                shiftUpdateRequest.setShift_task(str_task);
                                shiftUpdateRequest.setShift_rank(str_rank);

                                updateShiftViewModel.getUpdateShiftResponse(shiftUpdateRequest).observe((LifecycleOwner) context, shiftResponse -> {
                                    if (shiftResponse != null && shiftResponse.getResStatus().equalsIgnoreCase("200")) {
                                        myToast.show("Shift Updated Successfully", Toast.LENGTH_SHORT, true);
                                        myProgressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Failed",
                                                Toast.LENGTH_LONG).show();
                                        myProgressDialog.dismiss();
                                    }
                                });
                            } else {
                                myToast.show("Enter Rank", Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show("Enter Task", Toast.LENGTH_SHORT, false);
                        }

                    } else {
                        myToast.show("Select End Time", Toast.LENGTH_SHORT, false);

                    }
                } else {
                    myToast.show("Select Start Time", Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show("Enter No Of Volunteer", Toast.LENGTH_SHORT, false);

                //myToast.show("Select Start Time", Toast.LENGTH_SHORT, false);
            }
        } else {
            myToast.show("Select Start Date", Toast.LENGTH_SHORT, false);
        }

    }


    public void populateData(GetShiftListResponse.ShiftData shiftData)
    {
        et_date.setText(shiftData.getShift_date());
        et_start_time.setText(shiftData.getShift_start_time());
        et_end_time_volunteers.setText(shiftData.getShift_end_time());
        et_volunteer.setText(shiftData.getShift_vol_req());
        et_task.setText(shiftData.getShift_task());
        et_rank.setText(shiftData.getShift_rank());
    }


}
