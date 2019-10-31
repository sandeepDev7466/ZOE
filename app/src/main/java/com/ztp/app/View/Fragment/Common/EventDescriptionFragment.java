package com.ztp.app.View.Fragment.Common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.ztp.app.Data.Remote.Model.Request.GetUserStatusRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Volunteer.Event.ShiftListFragment;
import com.ztp.app.Viewmodel.GetEventDetailViewModel;
import com.ztp.app.Viewmodel.GetUserStatusViewModel;
import com.ztp.app.Viewmodel.RateEventViewModel;
import com.ztp.app.Viewmodel.VolunteerEventRequestViewModel;

public class EventDescriptionFragment extends Fragment implements View.OnClickListener {

    Context context;
    Button close, action;
    TextView date, address, title, description, ratingAvg, ratingLayout, signedEventText, time;
    ImageView image;
    String event_id;
    MyProgressDialog myProgressDialog;
    GetEventDetailResponse.EventDetail resData;
    GetEventDetailViewModel getEventsViewModel;
    VolunteerEventRequestViewModel volunteerEventRequestViewModel;
    GetUserStatusViewModel getUserStatusViewModel;
    RateEventViewModel rateEventViewModel;
    RoomDB roomDB;
    ScaleRatingBar rating;
    SharedPref sharedPref;
    MyToast myToast;

    public EventDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        roomDB = RoomDB.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        image = view.findViewById(R.id.image);
        close = view.findViewById(R.id.close);
        action = view.findViewById(R.id.action);
        signedEventText = view.findViewById(R.id.signedEventText);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        address = view.findViewById(R.id.address);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        ratingLayout = view.findViewById(R.id.ratingLayout);
        rating = view.findViewById(R.id.rating);
        ratingAvg = view.findViewById(R.id.ratingAvg);
        getEventsViewModel = ViewModelProviders.of(this).get(GetEventDetailViewModel.class);
        volunteerEventRequestViewModel = ViewModelProviders.of(this).get(VolunteerEventRequestViewModel.class);
        getUserStatusViewModel = ViewModelProviders.of(this).get(GetUserStatusViewModel.class);
        rateEventViewModel = ViewModelProviders.of(this).get(RateEventViewModel.class);

        signedEventText.setVisibility(View.GONE);  // Here

        Bundle b = getArguments();
        if (b != null) {
            if (b.get("event_id") != null) {
                event_id = b.getString("event_id");
            }
        }

        close.setOnClickListener(this);
        action.setOnClickListener(this);
        ratingLayout.setOnClickListener(this);

        if (Utility.isNetworkAvailable(context))
            getStatus(event_id);
        else
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRatingDialog();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                if (getActivity() != null)
                    getActivity().onBackPressed();
                break;
            case R.id.action:

                ShiftListFragment shiftListFragment = new ShiftListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id", event_id);
                shiftListFragment.setArguments(bundle);
                Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");
                /*if (v.getTag().toString().equalsIgnoreCase("1")) {

                    ShiftListFragment shiftListFragment = new ShiftListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", event_id);
                    shiftListFragment.setArguments(bundle);
                    Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");

                } else {
                    ShiftListFragment shiftListFragment = new ShiftListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", event_id);
                    shiftListFragment.setArguments(bundle);
                    Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");

                }*/
                break;
            case R.id.ratingLayout:
                openRatingDialog();
                break;
        }
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

    public void getStatus(String event_id) {
        getData();
        myProgressDialog.show(getString(R.string.please_wait));
        GetUserStatusRequest getUserStatusRequest = new GetUserStatusRequest();
        getUserStatusRequest.setUserId(sharedPref.getUserId());
        getUserStatusRequest.setEventId(event_id);
        getUserStatusViewModel.getUserStatusResponse(getUserStatusRequest).observe(this, getUserStatusResponse -> {

            if (getUserStatusResponse != null) {
                if (getUserStatusResponse.getResStatus().equalsIgnoreCase("200")) {
                    if (getUserStatusResponse.getResData().getUserStatus().equalsIgnoreCase("1")) {
                        signedEventText.setVisibility(View.VISIBLE);
                        action.setVisibility(View.VISIBLE);
                        action.setText(getString(R.string.view_status));
                        //action.setTag("1");
                    } else {
                        signedEventText.setVisibility(View.GONE);
                        action.setVisibility(View.VISIBLE);
                        action.setText(getString(R.string.sign_up));
                        //action.setTag("0");
                    }
                } else {
                    myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }

        });
    }

    @SuppressLint("StaticFieldLeak")
    public void getData() {
        if (Utility.isNetworkAvailable(context)) {

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

    private void setData(GetEventDetailResponse.EventDetail resData) {

        if (!resData.getEventImage().isEmpty())
            Picasso.with(context).load(resData.getEventImage()).error(R.drawable.no_image).placeholder(R.drawable.no_image).into(image);
        else
            Picasso.with(context).load(R.drawable.no_image).into(image);

        title.setText(resData.getEventHeading());
        description.setText(resData.getEventDetails());

        date.setText(resData.getEventRegisterStartDate() + " - " + resData.getEventRegisterEndDate());
        if (!resData.getEventStartTime().isEmpty() && !resData.getEventEndTime().isEmpty())
            time.setText(Utility.getTimeAmPm(resData.getEventStartTime()) + " - " + Utility.getTimeAmPm(resData.getEventEndTime()));
        else
            time.setText("N/A");

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
                        address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
                    } else {
                        address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState() + ", " + resData.getEventCountry());
                    }
                } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState() + ", " + resData.getEventPostcode());
                } else {
                    address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventState());
                }
            } else {
                if (!resData.getEventCountry().equalsIgnoreCase("")) {
                    if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                        address.setText(resData.getEventAddress() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
                    } else {
                        address.setText(resData.getEventAddress() + ", " + resData.getEventCountry());
                    }
                } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    address.setText(resData.getEventAddress() + ", " + resData.getEventPostcode());
                } else {
                    address.setText(resData.getEventAddress());
                }
            }
        } else if (!resData.getEventState().equalsIgnoreCase("")) {
            if (!resData.getEventCountry().equalsIgnoreCase("")) {
                if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                    address.setText(resData.getEventAddress() + ", " + resData.getEventState() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
                } else {
                    address.setText(resData.getEventAddress() + "," + resData.getEventState() + ", " + resData.getEventCountry());
                }
            } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                address.setText(resData.getEventAddress() + ", " + resData.getEventState() + ", " + resData.getEventPostcode());
            } else {
                address.setText(resData.getEventAddress() + ", " + resData.getEventState());
            }
        } else if (!resData.getEventCountry().equalsIgnoreCase("")) {
            if (!resData.getEventPostcode().equalsIgnoreCase("")) {
                address.setText(resData.getEventAddress() + ", " + resData.getEventCountry() + ", " + resData.getEventPostcode());
            } else {
                address.setText(resData.getEventAddress() + ", " + resData.getEventCountry());
            }
        } else if (!resData.getEventPostcode().equalsIgnoreCase("")) {
            address.setText(resData.getEventAddress() + ", " + resData.getEventCity() + ", " + resData.getEventPostcode());
        }
    }
}
