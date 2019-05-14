package com.ztp.app.View.Fragment.Common;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Request.PostVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetEventDetailViewModel;
import com.ztp.app.Viewmodel.VolunteerEventRequestViewModel;

public class EventDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    MyTextView tv_detail, tv_email, tv_phone, tv_address, tv_startdate, tv_enddate;
    MyTextView tv_heading;
    ImageView imv_event_detail;
    MyButton btn_volunteer;
    Context context;
    SharedPref sharedPref;
    GetEventDetailViewModel getEventsViewModel;
    VolunteerEventRequestViewModel volunteerEventRequestViewModel;
    String event_id = "";
    MyProgressDialog myProgressDialog;
    GetEventDetailResponse.ResData resData;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        Bundle b = getArguments();
        if (b != null) {
            if (b.get("event_id") != null)
                event_id = b.getString("event_id");
        }
        myProgressDialog = new MyProgressDialog(context);
        tv_address = view.findViewById(R.id.tv_event_address);
        tv_heading = view.findViewById(R.id.tv_event_heading);
        tv_email = view.findViewById(R.id.tv_event_email);
        tv_phone = view.findViewById(R.id.tv_event_phone);
        tv_detail = view.findViewById(R.id.tv_event_desc);
        tv_startdate = view.findViewById(R.id.tv_event_start_date);
        tv_enddate = view.findViewById(R.id.tv_event_end_date);
        btn_volunteer = view.findViewById(R.id.btn_volunteer);
        imv_event_detail = view.findViewById(R.id.imv_event_detail);

        sharedPref = SharedPref.getInstance(context);
        getEventsViewModel = ViewModelProviders.of(this).get(GetEventDetailViewModel.class);
        volunteerEventRequestViewModel = ViewModelProviders.of(this).get(VolunteerEventRequestViewModel.class);

        getData();
        btn_volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVolunteerRequest();
            }
        });

        return view;
    }

    public void getData() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            getEventsViewModel.getEventDetailResponseLiveData(new GetEventDetailRequest(event_id)).observe(this, getEventDetailResponse -> {
                if (getEventDetailResponse != null && getEventDetailResponse.getResData() != null) {
                    resData = getEventDetailResponse.getResData();
                    setData();
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setData() {
        Picasso.with(context).load(resData.getEventImage()).error(R.drawable.cso_events).into(imv_event_detail);
//        Glide
//                .with(context)
//                .load(resData.getEventImage())
//
//                .into(imv_event_detail);
        tv_heading.setText(resData.getEventHeading());
        tv_enddate.setText(resData.getEventRegisterEndDate());
        tv_startdate.setText(resData.getEventRegisterStartDate());
        tv_email.setText(resData.getEventEmail());
        tv_phone.setText(resData.getEventPhone());
        tv_detail.setText(resData.getEventDetails());
        if (!resData.getEventCity().equalsIgnoreCase("")) {
            if (!resData.getEventState().equalsIgnoreCase("")) {
                if (!resData.getEventCountry().equalsIgnoreCase("")) {
                    if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                        tv_address.setText(resData.getEventAddress() + "," + resData.getEventCity() + "," + resData.getEventState() + "," + resData.getEventCountry() + "," + resData.getEventPostcode());
                    } else {
                        tv_address.setText(resData.getEventAddress() + "," + resData.getEventCity() + "," + resData.getEventState() + "," + resData.getEventCountry());
                    }
                } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    tv_address.setText(resData.getEventAddress() + "," + resData.getEventCity() + "," + resData.getEventState() + "," + resData.getEventPostcode());
                } else {
                    tv_address.setText(resData.getEventAddress() + "," + resData.getEventCity() + "," + resData.getEventState());
                }
            } else {
                if (!resData.getEventCountry().equalsIgnoreCase("")) {
                    if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                        tv_address.setText(resData.getEventAddress() + "," + resData.getEventCountry() + "," + resData.getEventPostcode());
                    } else {
                        tv_address.setText(resData.getEventAddress() + "," + resData.getEventCountry());
                    }
                } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    tv_address.setText(resData.getEventAddress() + "," + resData.getEventPostcode());
                } else {
                    tv_address.setText(resData.getEventAddress());
                }
            }
        } else if (!resData.getEventState().equalsIgnoreCase("")) {
            if (!resData.getEventCountry().equalsIgnoreCase("")) {
                if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    tv_address.setText(resData.getEventAddress() + "," + resData.getEventState() + "," + resData.getEventCountry() + "," + resData.getEventPostcode());
                } else {
                    tv_address.setText(resData.getEventAddress() + "," + resData.getEventState() + "," + resData.getEventCountry());
                }
            } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                tv_address.setText(resData.getEventAddress() + "," + resData.getEventState() + "," + resData.getEventPostcode());
            } else {
                tv_address.setText(resData.getEventAddress() + "," + resData.getEventState());
            }
        } else if (!resData.getEventCountry().equalsIgnoreCase("")) {
            if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                tv_address.setText(resData.getEventAddress() + "," + resData.getEventCountry() + "," + resData.getEventPostcode());
            } else {
                tv_address.setText(resData.getEventAddress() + "," + resData.getEventCountry());
            }
        } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
            tv_address.setText(resData.getEventAddress() + "," + resData.getEventCity() + "," + resData.getEventPostcode());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_volunteer:
                postVolunteerRequest();
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void postVolunteerRequest() {
        PostVolunteerRequest postVolunteerRequest = new PostVolunteerRequest();
        postVolunteerRequest.setUser_id(sharedPref.getUserId());
        postVolunteerRequest.setUser_type(sharedPref.getUserType());
        postVolunteerRequest.setUser_device(Utility.getDeviceId(context));
        postVolunteerRequest.setEvent_id(resData.getEventId());
        postVolunteerRequest.setShift_id("");
        postVolunteerRequest.setCso_id("");

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            volunteerEventRequestViewModel.getPostVolunteerRequestResponseLiveData(postVolunteerRequest).observe(this, postVolunteerRequestResponse -> {
                if (postVolunteerRequestResponse != null && postVolunteerRequestResponse.getResData() != null) {
                    btn_volunteer.setEnabled(false);

                    btn_volunteer.setText(getString(R.string.volunteer_request));
                    new MyToast(context).show(context.getString(R.string.toast_volunteer_request_success), Toast.LENGTH_SHORT, false);
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }


}
