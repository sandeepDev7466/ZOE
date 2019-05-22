package com.ztp.app.View.Fragment.Common;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetEventDetailViewModel;
import com.ztp.app.Viewmodel.VolunteerEventRequestViewModel;

public class EventDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    MyTextView tv_detail, tv_email, tv_phone, tv_address, tv_startdate, tv_enddate;
    MyTextView tv_heading, tv_view_shifts;
    ImageView imv_event_detail;
    LinearLayout view_shift;
    Context context;
    SharedPref sharedPref;
    GetEventDetailViewModel getEventsViewModel;
    VolunteerEventRequestViewModel volunteerEventRequestViewModel;
    String event_id = "";
    String shift_id = "";
    MyProgressDialog myProgressDialog;
    //    VolunteerAllResponse.ResData data;
    GetEventDetailResponse.ResData resData;
    boolean show = false;

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
            if (b.get("shift_id") != null)
                shift_id = b.getString("shift_id");
            if (b.get("booking") != null)
                show = b.getBoolean("booking");
        }
        myProgressDialog = new MyProgressDialog(context);
        tv_address = view.findViewById(R.id.tv_event_address);
        tv_heading = view.findViewById(R.id.tv_event_heading);
        tv_email = view.findViewById(R.id.tv_event_email);
        tv_phone = view.findViewById(R.id.tv_event_phone);
        tv_detail = view.findViewById(R.id.tv_event_desc);
        tv_startdate = view.findViewById(R.id.tv_event_start_date);
        tv_enddate = view.findViewById(R.id.tv_event_end_date);
        tv_view_shifts = view.findViewById(R.id.tv_view_shifts);
        imv_event_detail = view.findViewById(R.id.imv_event_detail);
        view_shift = view.findViewById(R.id.view_shift);
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
        if (!resData.getEventImage().isEmpty())
            Picasso.with(context).load(resData.getEventImage()).fit().error(R.drawable.no_image).placeholder(R.drawable.no_image).into(imv_event_detail);
        else
            Picasso.with(context).load(R.drawable.no_image).fit().into(imv_event_detail);

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
}
