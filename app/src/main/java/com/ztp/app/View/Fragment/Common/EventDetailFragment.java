package com.ztp.app.View.Fragment.Common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.EventRatingRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetEventDetailViewModel;
import com.ztp.app.Viewmodel.RateEventViewModel;
import com.ztp.app.Viewmodel.VolunteerEventRequestViewModel;

public class EventDetailFragment extends Fragment {

    MyTextView tv_detail, tv_email, tv_phone, tv_address, ratingAvg;
    MyTextView tv_heading, tv_view_shifts, statusText, time, date;
    ImageView imv_event_detail, statusImage;
    LinearLayout view_shift;
    Context context;
    SharedPref sharedPref;
    GetEventDetailViewModel getEventsViewModel;
    RateEventViewModel rateEventViewModel;
    VolunteerEventRequestViewModel volunteerEventRequestViewModel;
    String event_id = "", shift_id = "";
    MyProgressDialog myProgressDialog;
    GetEventDetailResponse.EventDetail resData;
    boolean show = false;
    RoomDB roomDB;
    ScaleRatingBar rating;
    TextView ratingLayout;
    String status;
    LinearLayout statusLayout;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        roomDB = RoomDB.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        tv_address = view.findViewById(R.id.tv_event_address);
        tv_heading = view.findViewById(R.id.tv_event_heading);
        tv_email = view.findViewById(R.id.tv_event_email);
        tv_phone = view.findViewById(R.id.tv_event_phone);
        tv_detail = view.findViewById(R.id.tv_event_desc);
        tv_view_shifts = view.findViewById(R.id.tv_view_shifts);
        imv_event_detail = view.findViewById(R.id.imv_event_detail);
        statusImage = view.findViewById(R.id.status);
        rating = view.findViewById(R.id.rating);
        ratingAvg = view.findViewById(R.id.ratingAvg);
        view_shift = view.findViewById(R.id.view_shift);
        ratingLayout = view.findViewById(R.id.ratingLayout);
        statusText = view.findViewById(R.id.statusText);
        statusLayout = view.findViewById(R.id.statusLayout);
        time = view.findViewById(R.id.time);
        date = view.findViewById(R.id.date);

        Bundle b = getArguments();
        if (b != null) {
            if (b.get("event_id") != null) {
                event_id = b.getString("event_id");
                status = b.getString("status");

                if (status != null && status.equalsIgnoreCase("u")) {
                    statusImage.setImageResource(R.drawable.close);
                    statusText.setText(getString(R.string.unpublished));
                } else if (status != null && status.equalsIgnoreCase("p")) {
                    statusImage.setImageResource(R.drawable.tick);
                    statusText.setText(getString(R.string.published));
                } else {
                    statusLayout.setVisibility(View.GONE);
                }
            }
            if (b.get("shift_id") != null)
                shift_id = b.getString("shift_id");
            if (b.get("booking") != null)
                show = b.getBoolean("booking");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            tv_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        } else {
            tv_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

        if (show)
            view_shift.setVisibility(View.VISIBLE);

        sharedPref = SharedPref.getInstance(context);
        getEventsViewModel = ViewModelProviders.of(this).get(GetEventDetailViewModel.class);
        volunteerEventRequestViewModel = ViewModelProviders.of(this).get(VolunteerEventRequestViewModel.class);
        rateEventViewModel = ViewModelProviders.of(this).get(RateEventViewModel.class);

        getData();

        view_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShiftDetailFragment shiftDetailFragment = new ShiftDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("shift_id", shift_id);
                shiftDetailFragment.setArguments(bundle);
                Utility.replaceFragment(context, shiftDetailFragment, "ShiftDetailFragment");
            }
        });

//        rating.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//               openRatingDialog(0);
//                return true;
//            }
//        });
//        rating.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
//            @Override
//            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
//                openRatingDialog(v);
//            }
//        });
        /* ratingLayout.setOnClickListener(new View.OnClickListener() {
         *//**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         *//*
            @Override
            public void onClick(View v) {
                openRatingDialog();
            }
        });*/
        return view;
    }

    public void openRatingDialog() {
        Dialog dialog1 = new Dialog(context);
        View vw = LayoutInflater.from(context).inflate(R.layout.rating_dialog, null);
        dialog1.setContentView(vw);
        dialog1.setCancelable(false);

        LinearLayout yes = vw.findViewById(R.id.yes);
        LinearLayout no = vw.findViewById(R.id.no);
        ScaleRatingBar rating_dialog = vw.findViewById(R.id.rating);
        MyTextView ratingAvg = vw.findViewById(R.id.ratingAvg);

        yes.setOnClickListener(v12 -> {
            if (rating_dialog.getRating() == 0)
                new MyToast(context).show(context.getString(R.string.err_rating), Toast.LENGTH_SHORT, false);
            else {
                dialog1.dismiss();
                setRating(rating_dialog.getRating());
            }
        });

        no.setOnClickListener(v12 -> {
            dialog1.dismiss();
        });

        dialog1.show();
    }

    @SuppressLint("StaticFieldLeak")
    public void getData() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            GetEventDetailRequest getEventDetailRequest = new GetEventDetailRequest(event_id);
            Log.i("", "" + new Gson().toJson(getEventDetailRequest));


            getEventsViewModel.getEventDetailResponseLiveData(context, getEventDetailRequest).observe(this, getEventDetailResponse -> {

                if (getEventDetailResponse != null) {
                    if (getEventDetailResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (getEventDetailResponse.getResData() != null) {
                            resData = getEventDetailResponse.getResData();
                            setData(resData);
                        }
                    } else if (getEventDetailResponse.getResStatus().equalsIgnoreCase("401")) {
                        new MyToast(context).show(context.getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

                myProgressDialog.dismiss();
            });
        } else {
            // new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

            new AsyncTask<Void, Void, GetEventDetailResponse.EventDetail>() {
                @Override
                protected void onPostExecute(GetEventDetailResponse.EventDetail eventDetail) {
                    if (eventDetail != null)
                        setData(eventDetail);
                }

                @Override
                protected GetEventDetailResponse.EventDetail doInBackground(Void... voids) {
                    return roomDB.getEventDetailResponseDao().getEventDetailFromId(event_id);
                }
            }.execute();
        }
    }

    public void setRating(float rating) {
        EventRatingRequest eventRatingRequest = new EventRatingRequest();
        eventRatingRequest.setEventId(event_id);
        eventRatingRequest.setEventRating(String.valueOf(rating));
        eventRatingRequest.setUserDevice(Utility.getDeviceId(context));
        eventRatingRequest.setUserId(sharedPref.getUserId());
        eventRatingRequest.setUserType(sharedPref.getUserType());
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            rateEventViewModel.getEventRatingResponse(eventRatingRequest).observe(this, eventRatingResponse -> {

                if (eventRatingResponse != null) {
                    if (eventRatingResponse.getResStatus().equalsIgnoreCase("200")) {
                        getData();
                        new MyToast(context).show(eventRatingResponse.getResMessage(), Toast.LENGTH_SHORT, true);
                    } else if (eventRatingResponse.getResStatus().equalsIgnoreCase("401")) {
                        new MyToast(context).show(eventRatingResponse.getResMessage(), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

                myProgressDialog.dismiss();
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setData(GetEventDetailResponse.EventDetail resData) {

        if (!resData.getEventImage().isEmpty())
            Picasso.with(context).load(resData.getEventImage()).error(R.drawable.no_image).placeholder(R.drawable.no_image).into(imv_event_detail);
        else
            Picasso.with(context).load(R.drawable.no_image).into(imv_event_detail);

        tv_heading.setText(resData.getEventHeading());
        date.setText(resData.getEventRegisterStartDate() + " - " + resData.getEventRegisterEndDate());
        if (!resData.getEventStartTime().isEmpty() && !resData.getEventEndTime().isEmpty())
            time.setText(Utility.getTimeAmPm(resData.getEventStartTime()) + " - " + Utility.getTimeAmPm(resData.getEventEndTime()));
        else
            time.setText("N/A");
        tv_email.setText(resData.getEventEmail());
        tv_phone.setText(Utility.getFormattedPhoneNumber(resData.getEventPhone()));

        tv_detail.setText(resData.getEventDetails());
        if (resData.getAverageRating().isEmpty())
            ratingAvg.setText(getString(R.string.message_no_rating));
        else {
            String formattedString = String.format("%.01f", Float.parseFloat(resData.getAverageRating()));
            rating.setRating(Float.parseFloat(resData.getAverageRating()));
            String text = String.format(getString(R.string.message_rating), formattedString, resData.getEventCountRating());
            ratingAvg.setText(text);
        }

        if (!resData.getEventCity().equalsIgnoreCase("")) {
            if (!resData.getEventState().equalsIgnoreCase("")) {
                if (!resData.getEventCountry().equalsIgnoreCase("")) {
                    if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                        tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
                    } else {
                        tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState() + ", " + resData.getEventCountry());
                    }
                } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState() + ", " + resData.getEventPostcode());
                } else {
                    tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState());
                }
            } else {
                if (!resData.getEventCountry().equalsIgnoreCase("")) {
                    if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                        tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
                    } else {
                        tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCountry());
                    }
                } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    tv_address.setText(resData.getEventAddress() + ", " + resData.getEventPostcode());
                } else {
                    tv_address.setText(resData.getEventAddress());
                }
            }
        } else if (!resData.getEventState().equalsIgnoreCase("")) {
            if (!resData.getEventCountry().equalsIgnoreCase("")) {
                if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    tv_address.setText(resData.getEventAddress() + ", " + resData.getEventState() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
                } else {
                    tv_address.setText(resData.getEventAddress() + "," + resData.getEventState() + ", " + resData.getEventCountry());
                }
            } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                tv_address.setText(resData.getEventAddress() + ", " + resData.getEventState() + ", " + resData.getEventPostcode());
            } else {
                tv_address.setText(resData.getEventAddress() + ", " + resData.getEventState());
            }
        } else if (!resData.getEventCountry().equalsIgnoreCase("")) {
            if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
            } else {
                tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCountry());
            }
        } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
            tv_address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventPostcode());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
