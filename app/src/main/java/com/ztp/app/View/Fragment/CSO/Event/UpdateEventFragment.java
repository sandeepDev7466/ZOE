package com.ztp.app.View.Fragment.CSO.Event;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateEventResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_2Activity;
import com.ztp.app.Viewmodel.AddEventViewModel;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.GetEventTypeViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.TimeZoneViewModel;
import com.ztp.app.Viewmodel.UpdateEventViewModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class UpdateEventFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener, LocationListener,
        SensorEventListener {

    public UpdateEventFragment() {
        // Required empty public constructor
    }

    SharedPref sharedPref;

    Spinner sp_country;
    Spinner sp_state;
    Spinner sp_event_type;

    Spinner sp_time_zone;

    MapView mapView;
    private GoogleMap gmap;

    private GoogleMap mMap;

    Context context;
    MyToast myToast;

    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
    List<String> zoneList = new ArrayList<>();
    List<String> eventTypeList = new ArrayList<>();
    CountryViewModel countryModel;
    GetEventTypeViewModel eventTypeViewModel;
    TimeZoneViewModel timeZoneViewModel;
    AddEventViewModel addEventViewModel;
    UpdateEventViewModel updateEventViewModel;

    List<CountryResponse.Country> countryListData = new ArrayList<>();
    List<TimeZoneResponse.Timezone> timezoneListData = new ArrayList<>();
    List<EventTypeResponse.EventType> eventTypeListData = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    List<String> timezoneList = new ArrayList<>();
    StateViewModel stateModel;
    List<StateResponse.State> stateListData = new ArrayList<>();
    String country_id="";
    String timezone_id="";
    String event_type__id="";
    String state_id="";


    MyTextInputEditText et_event;
    MyTextInputEditText et_event_description;
    MyTextInputEditText et_city;
    MyTextInputEditText et_zip;
    MyTextInputEditText et_address;
    MyTextInputEditText et_contact_name;
    MyTextInputEditText et_phone_no;
    MyTextInputEditText et_email;
    MyTextInputEditText et_start_date;
    MyTextInputEditText et_end_date;


    String str_event="";
    String str_event_description="";
    String str_city="";
    String str_zip="";
    String str_address="";
    String str_contact_name="";
    String str_phone_no="";
    String str_email="";
    String str_start_date="";
    String str_end_date="";

    Button update;
    Button clear;

    GetEventsResponse.EventData eventData;

    GPSTracker gpsTracker;
    double latitude, longitude;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_update_event, container, false);

        gpsTracker = new GPSTracker(context);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }

        sharedPref = SharedPref.getInstance(context);
        sp_country=v.findViewById(R.id.sp_country);
        sp_state=v.findViewById(R.id.sp_state);
        sp_event_type=v.findViewById(R.id.sp_event_type);
        sp_time_zone=v.findViewById(R.id.sp_time_zone);

        et_event=v.findViewById(R.id.et_event);
        et_event_description=v.findViewById(R.id.et_event_description);
        et_city=v.findViewById(R.id.et_city);
        et_zip=v.findViewById(R.id.et_zip);
        et_address=v.findViewById(R.id.et_address);
        et_contact_name=v.findViewById(R.id.et_contact_name);
        et_phone_no=v.findViewById(R.id.et_phone_no);
        et_email=v.findViewById(R.id.et_email);
        et_start_date=v.findViewById(R.id.et_start_date);
        et_end_date=v.findViewById(R.id.et_end_date);

        update=v.findViewById(R.id.update);
        clear=v.findViewById(R.id.clear);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        timeZoneViewModel = ViewModelProviders.of(this).get(TimeZoneViewModel.class);
        eventTypeViewModel = ViewModelProviders.of(this).get(GetEventTypeViewModel.class);
        addEventViewModel = ViewModelProviders.of(this).get(AddEventViewModel.class);

        updateEventViewModel=ViewModelProviders.of(this).get(UpdateEventViewModel.class);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            et_phone_no.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        }else {
            et_phone_no.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        }


        if(getArguments()!=null) {
            Bundle b = getArguments();
            eventData = (GetEventsResponse.EventData) b.getSerializable("eventData");
            populateData(eventData);

        }





        getCountryList(eventData.getEventCountry());
        getTimeZonelist();
        getEventType();


        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    country_id = countryListData.get(position).getCountryId();
                    if (stateListData.size() == 0)
                        getStateList(country_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });





        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    state_id = stateListData.get(position).getStateId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        sp_time_zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    timezone_id = timezoneListData.get(position).getTimezoneId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });



        sp_event_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    event_type__id = eventTypeListData.get(position).getEventTypeId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });



        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        final ScrollView mScrollView = (ScrollView) v.findViewById(R.id.sv_container);

        ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(false);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Utility.isNetworkAvailable(context))
                updateData();
                else
                    myToast.show(getString(R.string.no_internet_connection),Toast.LENGTH_SHORT,false);

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                populateData(eventData);
                countryListData.clear();
                stateListData.clear();
                timezoneListData.clear();
                getCountryList(eventData.getEventCountry());
                getTimeZonelist();
                getEventType();
            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                System.out.print(view.getId());
                updateLabel();
            }

        };


        DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                System.out.print(view.getId());
                updateLabelone();
            }

        };


        et_start_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    return true;
                }
                return false;
            }
        });



        et_end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    new DatePickerDialog(getActivity(), dates, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    return true;
                }
                return false;
            }
        });



        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }




    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        icon1 = BitmapDescriptorFactory.fromResource(R.drawable.ic_beautician);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(50.0f);

        try {
            LatLng sydneys = new LatLng(26.8467, 80.9462);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneys));
            mMap.addMarker(new MarkerOptions().position(sydneys).title("Lucknow"));
        }
        catch (Exception e)
        {
            System.out.print(e);
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {


            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    private void getCountryList(String postCountryId) {

        countryList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                if (countryResponse != null) {
                    if(countryResponse.getResStatus().equalsIgnoreCase("200")) {
                        countryListData = countryResponse.getResData();
                        for (int i = 0; i < countryListData.size(); i++) {
                            countryList.add(countryListData.get(i).getCountryName());
                        }

                        countryList.add(0, getString(R.string.select_country));
                        setCountrySpinner(countryListData);
                    }
                    else if(countryResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show(getString(R.string.err_no_country_found), Toast.LENGTH_SHORT, false);
                    }
                }
                else
                {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            countryList.add(getString(R.string.select_country));
            setCountrySpinner(countryListData);
        }
    }

    private void setCountrySpinner(List<CountryResponse.Country> countryList) {
        ArrayAdapter<CountryResponse.Country> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);

        if(getCountryPosition()!=-1)
        {
            sp_country.setSelection(getCountryPosition());
        }
        else
        {
            sp_country.setSelection(0);
        }
    }

    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            stateModel.getStateResponse(context,new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    if(stateResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        stateListData = stateResponse.getStateList();
                        for (int i = 0; i < stateListData.size(); i++) {
                            stateList.add(stateListData.get(i).getStateName());
                        }
                        stateList.add(0, getString(R.string.select_state));
                        setStateSpinner(stateListData);
                    }
                    else if(stateResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show(getString(R.string.err_no_state_found),Toast.LENGTH_SHORT,false);
                    }
                }
                else
                {
                    myToast.show(getString(R.string.err_server),Toast.LENGTH_SHORT,false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setStateSpinner(List<StateResponse.State> stateList) {
        ArrayAdapter<StateResponse.State> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);

        if(getStatePosition()!=-1)
        {
            sp_state.setSelection(getStatePosition());
        }
        else
        {
            sp_state.setSelection(0);
        }
    }

    private void setTimezoneSpinner(List<TimeZoneResponse.Timezone> timezoneList) {
        ArrayAdapter<TimeZoneResponse.Timezone> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, timezoneList);
        sp_time_zone.setAdapter(adapter);


        if(getTimezonePosition()!=-1)
        {
            sp_time_zone.setSelection(getTimezonePosition());
        }
        else
        {
            sp_time_zone.setSelection(0);
        }

    }

    public void getTimeZonelist()
    {
        zoneList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            timeZoneViewModel.getTimezoneResponse(context).observe((LifecycleOwner) context, new Observer<TimeZoneResponse>() {
                @Override
                public void onChanged(@Nullable TimeZoneResponse timeZoneResponse) {

                    if (timeZoneResponse != null) {
                        if(timeZoneResponse.getResStatus().equalsIgnoreCase("200"))
                        {
                            timezoneListData = timeZoneResponse.getResData();
                            if (timezoneListData.size() > 0) {
                                for (int i = 0; i < timezoneListData.size(); i++) {
                                    timezoneList.add(timezoneListData.get(i).getTimezoneName());
                                }

                                setTimezoneSpinner(timezoneListData);
                            }
                        }
                        else if(timeZoneResponse.getResStatus().equalsIgnoreCase("401"))
                        {
                            myToast.show(getString(R.string.err_no_timezone_found), Toast.LENGTH_SHORT, false);
                        }
                    }
                    else {
                        myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                    }
                    myProgressDialog.dismiss();
                }
            });


        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

        }

    }

    private void getEventType() {

        eventTypeList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            eventTypeViewModel.getEventTypeResponse(context).observe((LifecycleOwner) context, eventTypeResponse -> {

                if(eventTypeResponse != null) {
                    if (eventTypeResponse.getResStatus().equalsIgnoreCase("200")) {
                        eventTypeListData = eventTypeResponse.getResData();
                        for (int i = 0; i < eventTypeListData.size(); i++) {
                            eventTypeList.add(eventTypeListData.get(i).getEventTypeName());
                        }
                        eventTypeList.add(0, getString(R.string.select_event_type));
                        setEventTypeSpinner(eventTypeListData);
                    }
                    else if(eventTypeResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                    }
                }
                else
                {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

        }
    }

    private void setEventTypeSpinner(List<EventTypeResponse.EventType> eventTypeList) {
        ArrayAdapter<EventTypeResponse.EventType> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, eventTypeList);
        sp_event_type.setAdapter(adapter);

        if(getEventTypePosition()!=-1)
        {
            sp_event_type.setSelection(getEventTypePosition());
        }
        else
        {
            sp_event_type.setSelection(0);
        }
    }

    private void submit()
    {
        myProgressDialog.show(getString(R.string.please_wait));
        str_event=et_event.getText().toString().trim();
        str_event_description=et_event_description.getText().toString().trim();
        str_city=et_city.getText().toString().trim();
        str_zip=et_zip.getText().toString().trim();
        str_address=et_address.getText().toString().trim();
        str_contact_name=et_contact_name.getText().toString().trim();
        str_phone_no=et_phone_no.getText().toString().trim();
        str_email=et_email.getText().toString().trim();
        str_start_date=et_start_date.getText().toString().trim();
        str_end_date=et_end_date.getText().toString().trim();

        EventAddRequest eventAddRequest=new EventAddRequest();

        eventAddRequest.setUser_id(sharedPref.getUserId());
        eventAddRequest.setEvent_type_id(event_type__id);
        eventAddRequest.setEvent_heading(str_event);
        eventAddRequest.setEvent_details(str_event_description);
        eventAddRequest.setEvent_address(str_address);
        eventAddRequest.setEvent_country(country_id);
        eventAddRequest.setEvent_state(state_id);
        eventAddRequest.setEvent_city(str_city);
        eventAddRequest.setEvent_postcode(str_zip);
        eventAddRequest.setEvent_timezone(timezone_id);
        eventAddRequest.setEvent_latitude(String.valueOf(latitude));
        eventAddRequest.setEvent_longitude(String.valueOf(longitude));
        eventAddRequest.setEvent_phone(str_phone_no);
        eventAddRequest.setEvent_email(str_email);
        eventAddRequest.setEvent_image("abc.jpg");
        eventAddRequest.setEvent_register_start_date(str_start_date);
        eventAddRequest.setEvent_register_end_date(str_end_date);

        addEventViewModel.getRegisterResponse(eventAddRequest).observe((LifecycleOwner) context, registerResponse -> {

            if (registerResponse != null && registerResponse.getResStatus().equalsIgnoreCase("200")) {

                myToast.show(getString(R.string.toast_event_added_success), true);
                myProgressDialog.dismiss();
            } else {


                myToast.show(getString(R.string.failed), true);
                myProgressDialog.dismiss();

            }


        });

    }


    public void populateData(GetEventsResponse.EventData eventData)
    {
        et_event.setText(eventData.getEventHeading());
        et_event_description.setText(eventData.getEventDetails());
        et_city.setText(eventData.getEventCity());
        et_zip.setText(eventData.getEventPostcode());
        et_address.setText(eventData.getEventAddress());
        //et_contact_name.setText(eventData.getEventPhone());
        et_phone_no.setText(eventData.getEventPhone());
        et_email.setText(eventData.getEventEmail());
        et_start_date.setText(eventData.getEventRegisterStartDate());
        et_end_date.setText(eventData.getEventRegisterEndDate());

        Date d = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterStartDate());
        Date ed = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterEndDate());
        et_start_date.setText(Utility.formatDateFull(d));
        et_end_date.setText(Utility.formatDateFull(ed));

        setEventType(eventData);

    }

    public void updateData()
    {
        myProgressDialog.show(getString(R.string.please_wait));
        str_event=et_event.getText().toString().trim();
        str_event_description=et_event_description.getText().toString().trim();
        str_city=et_city.getText().toString().trim();
        str_zip=et_zip.getText().toString().trim();
        str_address=et_address.getText().toString().trim();
        str_contact_name=et_contact_name.getText().toString().trim();
        str_phone_no=et_phone_no.getText().toString().trim();
        str_email=et_email.getText().toString().trim();
        str_start_date=et_start_date.getText().toString().trim();
        str_end_date=et_end_date.getText().toString().trim();

        EventUpdateRequest eventUpdateRequest =new EventUpdateRequest();
        eventUpdateRequest.setEvent_id(eventData.getEventId());
        eventUpdateRequest.setUser_id(sharedPref.getUserId());
        eventUpdateRequest.setEvent_type_id(event_type__id);
        eventUpdateRequest.setEvent_heading(str_event);
        eventUpdateRequest.setEvent_details(str_event_description);
        eventUpdateRequest.setEvent_address(str_address);
        eventUpdateRequest.setEvent_country(country_id);
        eventUpdateRequest.setEvent_state(state_id);
        eventUpdateRequest.setEvent_city(str_city);
        eventUpdateRequest.setEvent_postcode(str_zip);
        eventUpdateRequest.setEvent_timezone(timezone_id);
        eventUpdateRequest.setEvent_latitude(String.valueOf(latitude));
        eventUpdateRequest.setEvent_longitude(String.valueOf(longitude));
        eventUpdateRequest.setEvent_phone(str_phone_no);
        eventUpdateRequest.setEvent_email(str_email);
        eventUpdateRequest.setEvent_image("abc.jpg");

        Date d = Utility.convertStringToDateWithoutTime(str_start_date);
        Date ed = Utility.convertStringToDateWithoutTime(str_end_date);
        eventUpdateRequest.setEvent_register_start_date(Utility.formatDateFull(d));
        eventUpdateRequest.setEvent_register_end_date(Utility.formatDateFull(ed));


        updateEventViewModel.getUpdateEventResponse(eventUpdateRequest).observe((LifecycleOwner) context, updateResponse -> {

            if (updateResponse != null) {

                if(updateResponse.getResStatus().equalsIgnoreCase("200"))
                {
                    myToast.show(getString(R.string.event_updated_successfully), true);

                }
                else if(updateResponse.getResStatus().equalsIgnoreCase("401"))
                {
                    myToast.show(getString(R.string.err_event_not_updated), Toast.LENGTH_SHORT,false);
                }

            } else {
                myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT,false);
            }
            myProgressDialog.dismiss();

        });

    }

    private void updateLabel() {
        et_start_date.setText(Utility.formatDateFull(myCalendar.getTime()));
    }

    private void updateLabelone() {

        et_end_date.setText(Utility.formatDateFull(myCalendar.getTime()));
    }

    public  void setEventType(GetEventsResponse.EventData eventData)
    {
//        sp_event_type.setSelection(Integer.parseInt(eventData.getEventTypeId()),true);
//        sp_state.setSelection(Integer.parseInt(eventData.getEventState()),true);


    }

    public int getCountryPosition() {
        int id = 3;
        int position = -1;
        for (int i = 0; i < countryListData.size(); i++) {
            if (countryListData.get(i).getCountryName().equalsIgnoreCase(eventData.getEventCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(eventData.getEventCountry())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getStatePosition() {
        int id = 3;
        int position = -1;
        for (int i = 0; i < stateListData.size(); i++) {
            if (stateListData.get(i).getStateName().equalsIgnoreCase(eventData.getEventState()) || stateListData.get(i).getStateId().equalsIgnoreCase(eventData.getEventState())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getTimezonePosition() {
        int id = 3;
        int position = -1;
        for (int i = 0; i < timezoneListData.size(); i++) {
            if (timezoneListData.get(i).getTimezoneName().equalsIgnoreCase(eventData.getEventTimezone()) || timezoneListData.get(i).getTimezoneId().equalsIgnoreCase(eventData.getEventTimezone())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getEventTypePosition() {
        int id = 3;
        int position = -1;
        for (int i = 0; i < eventTypeListData.size(); i++) {
            if (eventTypeListData.get(i).getEventTypeName().equalsIgnoreCase(eventData.getEventTypeId()) || eventTypeListData.get(i).getEventTypeId().equalsIgnoreCase(eventData.getEventTypeId())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

}
