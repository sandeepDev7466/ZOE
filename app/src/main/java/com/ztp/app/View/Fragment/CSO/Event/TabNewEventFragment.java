package com.ztp.app.View.Fragment.CSO.Event;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.AddEventViewModel;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.GetEventTypeViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.TimeZoneViewModel;
import com.ztp.app.Viewmodel.UpdateEventViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TabNewEventFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener, LocationListener,
        SensorEventListener {

    public TabNewEventFragment() {
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
    String country_id = "";
    String timezone_id = "";
    String event_type__id = "";
    String state_id = "";


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


    String str_event = "";
    String str_event_description = "";
    String str_city = "";
    String str_zip = "";
    String str_address = "";
    String str_contact_name = "";
    String str_phone_no = "";
    String str_email = "";
    String str_start_date = "";
    String str_end_date = "";

    Button update;
    Button clear;


    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_new_event, container, false);

        sharedPref = SharedPref.getInstance(context);

        sp_country = v.findViewById(R.id.sp_country);
        sp_state = v.findViewById(R.id.sp_state);
        sp_event_type = v.findViewById(R.id.sp_event_type);
        sp_time_zone = v.findViewById(R.id.sp_time_zone);

        et_event = v.findViewById(R.id.et_event);
        et_event_description = v.findViewById(R.id.et_event_description);
        et_city = v.findViewById(R.id.et_city);
        et_zip = v.findViewById(R.id.et_zip);
        et_address = v.findViewById(R.id.et_address);
        et_contact_name = v.findViewById(R.id.et_contact_name);
        et_phone_no = v.findViewById(R.id.et_phone_no);
        et_email = v.findViewById(R.id.et_email);
        et_start_date = v.findViewById(R.id.et_start_date);
        et_end_date = v.findViewById(R.id.et_end_date);

        update = v.findViewById(R.id.update);
        clear = v.findViewById(R.id.clear);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        timeZoneViewModel = ViewModelProviders.of(this).get(TimeZoneViewModel.class);
        eventTypeViewModel = ViewModelProviders.of(this).get(GetEventTypeViewModel.class);
        addEventViewModel = ViewModelProviders.of(this).get(AddEventViewModel.class);

        updateEventViewModel = ViewModelProviders.of(this).get(UpdateEventViewModel.class);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            et_phone_no.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        } else {
            et_phone_no.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        }

        getCountryList();
        getTimeZonelist();
        getEventType();

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    country_id = countryListData.get(position - 1).getCountryId();
                    if (stateListData.size() == 0)
                        getStateList(country_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    state_id = stateListData.get(position - 1).getStateId();

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
                    timezone_id = timezoneListData.get(position - 1).getTimezoneId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
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

                submit();

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.replaceFragment(context, new AddNewShiftFragment(), "AddNewShiftFragment");

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
                if (event.getAction() == MotionEvent.ACTION_UP) {

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
                if (event.getAction() == MotionEvent.ACTION_UP) {

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
        } catch (Exception e) {
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

    private void getCountryList() {

        countryList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Please wait...");
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                if (countryResponse != null) {
                    countryListData = countryResponse.getResData();
                    for (int i = 0; i < countryListData.size(); i++) {
                        countryList.add(countryListData.get(i).getCountryName());
                    }
                    countryList.add(0, "Select Country");
                    setCountrySpinner(countryList);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            countryList.add("Select Country");
            setCountrySpinner(countryList);
        }

        stateList.add("Select State");
        setStateSpinner(stateList);
    }

    private void setCountrySpinner(List<String> countryList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
    }


    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Fetching states...");
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    stateListData = stateResponse.getStateList();
                    for (int i = 0; i < stateListData.size(); i++) {
                        stateList.add(stateListData.get(i).getStateName());
                    }
                    stateList.add(0, "Select State");
                    setStateSpinner(stateList);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }


    private void setStateSpinner(List<String> stateList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);
    }

    private void setTimezoneSpinner(List<String> timezoneList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, timezoneList);
        sp_time_zone.setAdapter(adapter);

    }

    public void getTimeZonelist() {
        zoneList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Please wait...");

            timeZoneViewModel.getTimezoneResponse(context).observe((LifecycleOwner) context, new Observer<TimeZoneResponse>() {
                @Override
                public void onChanged(@Nullable TimeZoneResponse timeZoneResponse) {

                    if (timeZoneResponse != null) {
                        timezoneListData = timeZoneResponse.getResData();
                        for (int i = 0; i < timezoneListData.size(); i++) {
                            timezoneList.add(timezoneListData.get(i).getTimezoneName());
                        }


                        setTimezoneSpinner(timezoneList);
                    }
                    myProgressDialog.dismiss();
                }
            });


        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            timezoneList.add(0, "Select Timezone");
            setTimezoneSpinner(timezoneList);
        }

        timezoneList.add("Select Time Zone");
        setTimezoneSpinner(timezoneList);

    }

    private void getEventType() {

        eventTypeList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Please wait...");
            eventTypeViewModel.getEventTypeResponse(context).observe((LifecycleOwner) context, eventTypeResponse -> {

                if (eventTypeResponse != null) {
                    eventTypeListData = eventTypeResponse.getResData();
                    for (int i = 0; i < eventTypeListData.size(); i++) {
                        eventTypeList.add(eventTypeListData.get(i).getEventTypeName());
                    }
                    eventTypeList.add(0, "Select Event Type");
                    setEventTypeSpinner(eventTypeList);
                }
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            eventTypeList.add("Select Event Type");
            setEventTypeSpinner(eventTypeList);
        }
    }

    private void setEventTypeSpinner(List<String> eventTypeList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown_item, R.id.text, eventTypeList);
        sp_event_type.setAdapter(adapter);

        sp_event_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    event_type__id = eventTypeListData.get(position - 1).getEventTypeId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


    }

    private void submit() {
        myProgressDialog.show("Please wait......");
        str_event = et_event.getText().toString().trim();
        str_event_description = et_event_description.getText().toString().trim();
        str_city = et_city.getText().toString().trim();
        str_zip = et_zip.getText().toString().trim();
        str_address = et_address.getText().toString().trim();
        str_contact_name = et_contact_name.getText().toString().trim();
        str_phone_no = et_phone_no.getText().toString().trim();
        str_email = et_email.getText().toString().trim();
        str_start_date = et_start_date.getText().toString().trim();
        str_end_date = et_end_date.getText().toString().trim();

        EventAddRequest eventAddRequest = new EventAddRequest();

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
        eventAddRequest.setEvent_latitude("26.09");
        eventAddRequest.setEvent_longitude("32.08");
        eventAddRequest.setEvent_phone(str_phone_no);
        eventAddRequest.setEvent_email(str_email);
        eventAddRequest.setEvent_image("abc.jpg");
        eventAddRequest.setEvent_register_start_date(str_start_date);
        eventAddRequest.setEvent_register_end_date(str_end_date);

        addEventViewModel.getRegisterResponse(eventAddRequest).observe((LifecycleOwner) context, registerResponse -> {

            if (registerResponse != null && registerResponse.getResStatus().equalsIgnoreCase("200")) {

                Toast.makeText(getActivity(), "Event Added Successfully...", Toast.LENGTH_LONG).show();

                myToast.show("Event Added Successfully...", true);

            } else {

                Toast.makeText(getActivity(), "Failed...", Toast.LENGTH_LONG).show();
                myToast.show("Failed...", false);
            }
            myProgressDialog.dismiss();

        });

    }


    public void updateData() {
        myProgressDialog.show("Saving...");
        str_event = et_event.getText().toString().trim();
        str_event_description = et_event_description.getText().toString().trim();
        str_city = et_city.getText().toString().trim();
        str_zip = et_zip.getText().toString().trim();
        str_address = et_address.getText().toString().trim();
        str_contact_name = et_contact_name.getText().toString().trim();
        str_phone_no = et_phone_no.getText().toString().trim();
        str_email = et_email.getText().toString().trim();

        EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();

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
        eventUpdateRequest.setEvent_latitude("26.09");
        eventUpdateRequest.setEvent_longitude("32.08");
        eventUpdateRequest.setEvent_phone(str_phone_no);
        eventUpdateRequest.setEvent_email(str_email);
        eventUpdateRequest.setEvent_image("abc.jpg");
        eventUpdateRequest.setEvent_register_start_date("2019-04-08 12:00:00");
        eventUpdateRequest.setEvent_register_end_date("2019-04-18 12:00:00");

        updateEventViewModel.getUpdateEventResponse(eventUpdateRequest).observe((LifecycleOwner) context, updateResponse -> {

            if (updateResponse != null && updateResponse.getResStatus().equalsIgnoreCase("200")) {

                Toast.makeText(getActivity(), "Event Updated Successfully...",
                        Toast.LENGTH_LONG).show();

//                myToast.show(registerResponse.toString().trim(), true);
                myProgressDialog.dismiss();
            } else {

                Toast.makeText(getActivity(), "Failed...",
                        Toast.LENGTH_LONG).show();

//                myToast.show("Basic information registration failed", Toast.LENGTH_SHORT, false);
//                myProgressDialog.dismiss();
            }


        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //  et_start_date.setText(sdf.format(myCalendar.getTime()));
        et_start_date.setText(Utility.formatDateFull(myCalendar.getTime()));
    }


    private void updateLabelone() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //  et_end_date.setText(sdf.format(myCalendar.getTime()));

        et_end_date.setText(Utility.formatDateFull(myCalendar.getTime()));
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
//        if (mapFragment != null) {
//            getFragmentManager().beginTransaction().remove(mapFragment).commit();
//        }
//    }
}
