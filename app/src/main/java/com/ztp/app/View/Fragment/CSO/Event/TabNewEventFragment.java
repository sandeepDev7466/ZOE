package com.ztp.app.View.Fragment.CSO.Event;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.raywenderlich.android.validatetor.ValidateTor;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadDocumentResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.SelectDateFragment;
import com.ztp.app.Viewmodel.AddEventViewModel;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.GetEventTypeViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.TimeZoneViewModel;
import com.ztp.app.Viewmodel.UpdateEventViewModel;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    static MyToast myToast;
    MapView mapView;
    private GoogleMap mMap;
    static Context context;
    boolean errorPostalCode = false, errorPhone = false, errorEmail = false, errorEventName = false, errorEventDescription = false;

    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
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


    MyTextInputEditText et_event, et_event_description, et_city, et_address, et_contact_name, et_phone_no, et_email, etPostalCode;

    static MyTextInputEditText et_start_date;
    static MyTextInputEditText et_end_date;
    MyTextInputLayout etEmailLayout, etPhoneLayout, etPostalCodeLayout, et_event_layout, et_event_description_layout;
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
    GPSTracker gpsTracker;
    double latitude, longitude;
    static String start_date = "", end_date = "";
    ValidateTor validate;
    String type;
    GetEventsResponse.EventData eventData;
    MyBoldTextView heading;
    //ImageView edit;
    ImageView image;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_new_event, container, false);
        validate = new ValidateTor();
        gpsTracker = new GPSTracker(context);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }

        sharedPref = SharedPref.getInstance(context);
        sp_country = v.findViewById(R.id.sp_country);
        sp_state = v.findViewById(R.id.sp_state);
        sp_event_type = v.findViewById(R.id.sp_event_type);
        sp_time_zone = v.findViewById(R.id.sp_time_zone);
        heading = v.findViewById(R.id.heading);
        et_event = v.findViewById(R.id.et_event);
        et_event_description = v.findViewById(R.id.et_event_description);
        et_city = v.findViewById(R.id.etCity);
        et_address = v.findViewById(R.id.et_address);
        et_contact_name = v.findViewById(R.id.et_contact_name);
        et_phone_no = v.findViewById(R.id.et_phone_no);
        et_email = v.findViewById(R.id.et_email);
        et_start_date = v.findViewById(R.id.et_start_date);
        et_end_date = v.findViewById(R.id.et_end_date);
        etEmailLayout = v.findViewById(R.id.et_email_layout);
        etPhoneLayout = v.findViewById(R.id.et_phone_no_layout);
        etPostalCode = v.findViewById(R.id.et_zip);
        etPostalCodeLayout = v.findViewById(R.id.et_zip_layout);
        et_event_layout = v.findViewById(R.id.et_event_layout);
        et_event_description_layout = v.findViewById(R.id.et_event_description_layout);
        //edit = v.findViewById(R.id.edit);
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
        image = v.findViewById(R.id.image);


        Bundle b = getArguments();
        if (b != null) {
            type = b.getString("action");
            if (type != null && type.equalsIgnoreCase("update")) {
                update.setText(R.string.update);
                heading.setText(R.string.update_event_detail);
                eventData = (GetEventsResponse.EventData) b.getSerializable("model");
                populateData(eventData);
            } else {
                getCountryList();
                getTimeZonelist();
                getEventType();
                update.setText(R.string.add);
                heading.setText(R.string.add_event_detail);
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            et_phone_no.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        } else {
            et_phone_no.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        }
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidEmail(s.toString())) {
                        etEmailLayout.setError(getString(R.string.err_enter_valid_email));
                        errorEmail = true;
                    } else {
                        etEmailLayout.setError(null);
                        errorEmail = false;
                    }
                } else {
                    etEmailLayout.setError(null);
                    errorEmail = false;
                }
            }
        });
        et_phone_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidPhoneNumber(s.toString())) {
                        etPhoneLayout.setError(getString(R.string.err_enter_valid_phone));
                        errorPhone = true;
                    } else {
                        etPhoneLayout.setError(null);
                        errorPhone = false;
                    }
                } else {
                    etPhoneLayout.setError(null);
                    errorPhone = false;
                }
            }
        });
        etPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidPostalCode(s.toString())) {
                        etPostalCodeLayout.setError(getString(R.string.err_enter_valid_postal));
                        errorPostalCode = true;
                    } else {
                        etPostalCodeLayout.setError(null);
                        errorPostalCode = false;
                    }
                } else {
                    etPostalCodeLayout.setError(null);
                    errorPostalCode = false;
                }
            }
        });

        et_event.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidName(s.toString())) {
                        et_event_layout.setError(getString(R.string.err_event_name_validation));
                        errorEventName = true;
                    } else {
                        et_event_layout.setError(null);
                        errorEventName = false;
                    }
                } else {
                    et_event_layout.setError(null);
                    errorEventName = false;
                }
            }
        });

        et_event_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidName(s.toString())) {
                        et_event_description_layout.setError(getString(R.string.err_event_description_validation));
                        errorEventDescription = true;
                    } else {
                        et_event_description_layout.setError(null);
                        errorEventDescription = false;
                    }
                } else {
                    et_event_description_layout.setError(null);
                    errorEventDescription = false;
                }
            }
        });

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

                state_id = stateListData.get(position).getStateId();


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

                } else if (position == 0) {
                    timezone_id = "";
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
                    event_type__id = eventTypeListData.get(position - 1).getEventTypeId();
                } else if (position == 0) {
                    event_type__id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        image.setOnClickListener(v15 -> {

            Intent intent = new Intent(context, UpdateImageActivity.class);
            intent.putExtra("action", type);
            if(type.equalsIgnoreCase("update"))
            intent.putExtra("image",eventData.getEventImage());
            startActivity(intent);
            ((AppCompatActivity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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


        update.setOnClickListener(v1 -> {

            if (!event_type__id.equalsIgnoreCase("")) {
                if (et_event.getText() != null && !et_event.getText().toString().isEmpty()) {
                    if (!Utility.isValidName(et_event.getText().toString())) {
                        myToast.show(getString(R.string.err_event_name_validation), Toast.LENGTH_SHORT, false);
                        return;
                    }
                    if (et_event_description.getText() != null && !et_event_description.getText().toString().isEmpty()) {
                        if (!Utility.isValidName(et_event_description.getText().toString())) {
                            myToast.show(getString(R.string.err_event_description_validation), Toast.LENGTH_SHORT, false);
                            return;
                        }
                        if (!country_id.equalsIgnoreCase("")) {
                            if (!state_id.equalsIgnoreCase("")) {
                                if (et_city.getText() != null && !et_city.getText().toString().isEmpty()) {
                                    if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {
                                        if (!Utility.isValidPostalCode(etPostalCode.getText().toString())) {
                                            myToast.show(getString(R.string.err_enter_valid_postal), Toast.LENGTH_SHORT, false);
                                            return;
                                        }
                                        if (!timezone_id.equalsIgnoreCase("")) {
                                            if (et_address.getText() != null && !et_address.getText().toString().isEmpty()) {
                                                if (et_phone_no.getText() != null && !et_phone_no.getText().toString().isEmpty()) {
                                                    if (!Utility.isValidPhoneNumber(et_phone_no.getText().toString())) {
                                                        myToast.show(getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT, false);
                                                        return;
                                                    }
                                                    if (et_email.getText() != null && !et_email.getText().toString().isEmpty()) {
                                                        if (!Utility.isValidEmail(et_email.getText().toString())) {
                                                            myToast.show(getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                                                            return;
                                                        }
                                                        if (et_start_date.getText() != null && !et_start_date.getText().toString().isEmpty()) {
                                                            if (et_end_date.getText() != null && !et_end_date.getText().toString().isEmpty()) {
                                                                if (!errorPostalCode && !errorPhone && !errorEmail && !errorEventName && !errorEventDescription) {


                                                                    if (UpdateImageActivity.uploadFile != null)
                                                                        uploadDocument(UpdateImageActivity.uploadFile);
                                                                    else
                                                                        submit();


                                                                } else {
                                                                    myToast.show(getString(R.string.err_invalid_data), Toast.LENGTH_SHORT, false);
                                                                }
                                                            } else {
                                                                myToast.show(getString(R.string.enter_end_date), Toast.LENGTH_SHORT, false);
                                                            }
                                                        } else {
                                                            myToast.show(getString(R.string.enter_start_date), Toast.LENGTH_SHORT, false);
                                                        }
                                                    } else {
                                                        myToast.show(getString(R.string.err_enter_email), Toast.LENGTH_SHORT, false);
                                                    }
                                                } else {
                                                    myToast.show(getString(R.string.err_enter_phone_number), Toast.LENGTH_SHORT, false);
                                                }
                                            } else {
                                                myToast.show(getString(R.string.err_enter_address), Toast.LENGTH_SHORT, false);
                                            }
                                        } else {
                                            myToast.show(getString(R.string.err_enter_time_zone), Toast.LENGTH_SHORT, false);
                                        }
                                    } else {
                                        myToast.show(getString(R.string.err_enter_postal_code), Toast.LENGTH_SHORT, false);
                                    }
                                } else {
                                    myToast.show(getString(R.string.err_enter_city), Toast.LENGTH_SHORT, false);
                                }

                            } else {
                                myToast.show(getString(R.string.select_state), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.select_country), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_enter_event_description), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_enter_event_name), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.err_enter_event_type), Toast.LENGTH_SHORT, false);
            }
        });

        clear.setOnClickListener(v14 -> {

            if (type.equalsIgnoreCase("add"))
                clear();
            else
                populateData(eventData);

        });

        et_start_date.setOnClickListener(v12 -> {
            Constants.cal = 1;
            DialogFragment df = new SelectDateFragment();
            df.show(getFragmentManager(), "datePicker");
        });
        et_end_date.setOnClickListener(v13 -> {
            Constants.cal = 2;
            DialogFragment df = new SelectDateFragment();
            df.show(getFragmentManager(), "datePicker");
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }

           /* if (sharedPref.getEventImageBase64() != null && !sharedPref.getEventImageBase64().equalsIgnoreCase("")) {
                image.setImageBitmap(Utility.decodeBase64(sharedPref.getEventImageBase64()));
            }*/
    }


    private void uploadDocument(File fileToUpload) {


        myProgressDialog.show(getString(R.string.please_wait));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("user_id_file", fileToUpload.getName(), RequestBody.create(MediaType.parse("*/*"), fileToUpload));
        ApiInterface apiInterface = Api.getClient();

        Call<UploadDocumentResponse> call = apiInterface.uploadDocumentNew(filePart, sharedPref.getUserId(), UpdateImageActivity.uploadFile.getName(), "1234", "cso_event_file_upload");

        call.enqueue(new Callback<UploadDocumentResponse>() {
            @Override
            public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {

                if (response.body() != null) {

                    if (response.body().getResStatus().equalsIgnoreCase("200")) {
                        myProgressDialog.dismiss();
                        myToast.show(getString(R.string.file_uploaded_successfully), Toast.LENGTH_SHORT, true);
                        submit();
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
                t.printStackTrace();
                myProgressDialog.dismiss();
            }
        });
    }

    public void populateData(GetEventsResponse.EventData eventData) {
        et_event.setText(eventData.getEventHeading());
        et_event_description.setText(eventData.getEventDetails());
        et_city.setText(eventData.getEventCity());
        etPostalCode.setText(eventData.getEventPostcode());
        et_address.setText(eventData.getEventAddress());
        et_contact_name.setText(eventData.getEventPhone());
        et_phone_no.setText(Utility.getFormattedPhoneNumber(eventData.getEventPhone()));
        et_email.setText(eventData.getEventEmail());
        et_start_date.setText(eventData.getEventRegisterStartDate());
        et_end_date.setText(eventData.getEventRegisterEndDate());
        if(!eventData.getEventImage().isEmpty())
        Picasso.with(context).load(eventData.getEventImage()).error(R.drawable.no_image).into(image);

      /*  Date d = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterStartDate());
        Date ed = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterEndDate());
        et_start_date.setText(Utility.formatDateFull(d));
        et_end_date.setText(Utility.formatDateFull(ed));
*/
      /*  sp_event_type.setSelection(getEventTypePosition());
        sp_country.setSelection(getCountryPosition());
        sp_state.setSelection(getStatePosition());
        sp_time_zone.setSelection(getTimezonePosition());*/

        getCountryList();
        getTimeZonelist();
        getEventType();
    }


    private void clear() {
        event_type__id = "";
        sp_event_type.setSelection(0, true);
        et_event.setText("");
        et_event_description.setText("");
        country_id = "";
        sp_country.setSelection(0, true);
        state_id = "";
        sp_state.setSelection(0, true);
        timezone_id = "";
        sp_time_zone.setSelection(0, true);
        et_city.setText("");
        etPostalCode.setText("");
        et_address.setText("");
        et_contact_name.setText("");
        et_phone_no.setText("");
        et_email.setText("");
        et_start_date.setText("");
        et_end_date.setText("");
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
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(50.0f);

        try {
            LatLng sydneys = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneys));
            mMap.addMarker(new MarkerOptions().position(sydneys).title("Here"));
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
            myProgressDialog.show(getString(R.string.please_wait));
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                if (countryResponse != null) {
                    countryListData = countryResponse.getResData();
                    if (countryListData.size() > 0) {
                        for (int i = 0; i < countryListData.size(); i++) {
                            countryList.add(countryListData.get(i).getCountryName());
                        }

                        setCountrySpinner(countryList);
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
    }

    private void setCountrySpinner(List<String> countryList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
        if (type.equalsIgnoreCase("update")) {
            if (getCountryPosition() != -1) {
                sp_country.setSelection(getCountryPosition());
            } else {
                sp_country.setSelection(0);
            }
        }
    }


    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null && stateResponse.getResStatus().equalsIgnoreCase("200")) {
                    stateListData = stateResponse.getStateList();
                    if (stateListData.size() > 0) {
                        for (int i = 0; i < stateListData.size(); i++) {
                            stateList.add(stateListData.get(i).getStateName());
                        }
                        setStateSpinner(stateList);
                        myProgressDialog.dismiss();
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(getString(R.string.err_no_state_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myProgressDialog.dismiss();
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }


    private void setStateSpinner(List<String> stateList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);
        if (type.equalsIgnoreCase("update")) {
            if (getStatePosition() != -1) {
                sp_state.setSelection(getStatePosition());
            } else {
                sp_state.setSelection(0);
            }
        }
    }

    private void setTimezoneSpinner(List<String> timezoneList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, timezoneList);
        sp_time_zone.setAdapter(adapter);
        if (type.equalsIgnoreCase("update")) {

            sp_time_zone.setSelection(getTimezonePosition());

            /*if (getTimezonePosition() != -1) {
                sp_time_zone.setSelection(getTimezonePosition());
            } else {
                sp_time_zone.setSelection(0);
            }*/
        }

    }

    public void getTimeZonelist() {
        timezoneList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            timeZoneViewModel.getTimezoneResponse(context).observe((LifecycleOwner) context, new Observer<TimeZoneResponse>() {
                @Override
                public void onChanged(@Nullable TimeZoneResponse timeZoneResponse) {

                    if (timeZoneResponse != null) {
                        if (timeZoneResponse.getResStatus().equalsIgnoreCase("200")) {
                            timezoneListData = timeZoneResponse.getResData();
                            if (timezoneListData.size() > 0) {
                                for (int i = 0; i < timezoneListData.size(); i++) {
                                    timezoneList.add(timezoneListData.get(i).getTimezoneName());
                                }

                                setTimezoneSpinner(timezoneList);

                                myProgressDialog.dismiss();
                            } else {
                                myProgressDialog.dismiss();
                                myToast.show(getString(R.string.err_no_timezone_found), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myProgressDialog.dismiss();
                            myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                }
            });


        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            /*timezoneList.add(0, "Select Timezone");
            setTimezoneSpinner(timezoneList);*/
        }

        timezoneList.add(getString(R.string.select_time_zone));
        setTimezoneSpinner(timezoneList);

    }

    private void getEventType() {

        eventTypeList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            eventTypeViewModel.getEventTypeResponse(context).observe((LifecycleOwner) context, eventTypeResponse -> {

                if (eventTypeResponse != null && eventTypeResponse.getResStatus().equalsIgnoreCase("200")) {
                    eventTypeListData = eventTypeResponse.getResData();
                    if (eventTypeListData.size() > 0) {
                        for (int i = 0; i < eventTypeListData.size(); i++) {
                            eventTypeList.add(eventTypeListData.get(i).getEventTypeName());
                        }
                        eventTypeList.add(0, getString(R.string.select_event_type));
                        setEventTypeSpinner(eventTypeList);
                        myProgressDialog.dismiss();
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(getString(R.string.err_no_event_type_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myProgressDialog.dismiss();
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            eventTypeList.add(getString(R.string.select_event_type));
            setEventTypeSpinner(eventTypeList);
        }
    }

    private void setEventTypeSpinner(List<String> eventTypeList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, eventTypeList);
        sp_event_type.setAdapter(adapter);

        if (type.equalsIgnoreCase("update")) {

            sp_event_type.setSelection(getEventTypePosition());

           /* if (getEventTypePosition() != -1) {
                sp_event_type.setSelection(getEventTypePosition());
            } else {
                sp_event_type.setSelection(0);
            }*/
        }
    }

    private void submit() {
        myProgressDialog.show(getString(R.string.please_wait));
        str_event = et_event.getText().toString().trim();
        str_event_description = et_event_description.getText().toString().trim();
        str_city = et_city.getText().toString().trim();
        str_zip = etPostalCode.getText().toString().trim();
        str_address = et_address.getText().toString().trim();
        //str_contact_name = et_contact_name.getText().toString().trim();
        str_phone_no = et_phone_no.getText().toString().trim();
        str_email = et_email.getText().toString().trim();
        str_start_date = et_start_date.getText().toString().trim();
        str_end_date = et_end_date.getText().toString().trim();


        if (type.equalsIgnoreCase("add")) {
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
            eventAddRequest.setEvent_latitude(String.valueOf(latitude));
            eventAddRequest.setEvent_longitude(String.valueOf(longitude));
            eventAddRequest.setEvent_phone(str_phone_no);
            eventAddRequest.setEvent_email(str_email);
            eventAddRequest.setEvent_image(UpdateImageActivity.uploadFile != null ? UpdateImageActivity.uploadFile.getName() : "");
            eventAddRequest.setEvent_register_start_date(str_start_date);
            eventAddRequest.setEvent_register_end_date(str_end_date);

            if (Utility.isNetworkAvailable(context)) {
                addEventViewModel.getRegisterResponse(eventAddRequest).observe((LifecycleOwner) context, registerResponse -> {

                    if (registerResponse != null) {
                        if (registerResponse.getResStatus().equalsIgnoreCase("200")) {

                            EventListFragment parentFrag = ((EventListFragment) TabNewEventFragment.this.getParentFragment());
                            parentFrag.viewPager.setCurrentItem(0, true);

                            int index = parentFrag.viewPager.getCurrentItem();
                            EventPager adapter = ((EventPager) parentFrag.viewPager.getAdapter());
                            TabMyEventFragment fragment = (TabMyEventFragment) adapter.getItem(index);
                            fragment.getData();
                            clear();

                            myToast.show(getString(R.string.toast_event_added_success), Toast.LENGTH_SHORT, true);

                        } else {

                            myToast.show(getString(R.string.toast_event_added_failed), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();

                });
            } else {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }
        } else if (type.equalsIgnoreCase("update")) {
            EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();
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
            eventUpdateRequest.setEvent_image(UpdateImageActivity.uploadFile != null ? UpdateImageActivity.uploadFile.getName() : "");
            eventUpdateRequest.setEvent_register_start_date(str_start_date);
            eventUpdateRequest.setEvent_register_end_date(str_end_date);
            if (Utility.isNetworkAvailable(context)) {
                updateEventViewModel.getUpdateEventResponse(eventUpdateRequest).observe((LifecycleOwner) context, updateResponse -> {

                    if (updateResponse != null) {
                        if (updateResponse.getResStatus().equalsIgnoreCase("200")) {

                            clear();
                            myToast.show(getString(R.string.event_updated_successfully), Toast.LENGTH_SHORT, true);
                            //getActivity().getFragmentManager().popBackStack();
                            ((AppCompatActivity) context).onBackPressed();

                        } else {

                            myToast.show(getString(R.string.err_event_not_updated), Toast.LENGTH_SHORT, false);

                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();
                });
            } else {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }

        }

    }

    public static void setDate(String date) {


        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        if (Constants.cal == 1) {
//            Date newDate = null;
//            try {
//                newDate = Constants.ff.parse(date);
//                start_date =  Constants.ff.format(newDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            String issue = null;
//
//            Date issue_date = null;
//            try {
//                issue_date = Constants.ff.parse(date);
//                today = Constants.ff.parse(Constants.ff.format(today));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            issue = Utility.formatDateFull(issue_date);
//
//            if (issue_date.before(today)) {
//                et_start_date.setText("");
//                et_start_date.setHint("Issue Date can not be a Past Date!");
//                et_start_date.setHintTextColor(Color.RED);
//            } else {
            et_start_date.setText(date);
            // et_start_date.setTextColor(Color.BLACK);

//            }
            if (!et_end_date.getText().toString().equalsIgnoreCase("")) {

                Date end_date = null;
                Date start_date = null;
                try {
                    end_date = Constants.ff.parse(et_end_date.getText().toString());
                    start_date = Constants.ff.parse(et_start_date.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (Utility.daysInBetween(start_date, end_date) <= 0)
                    et_end_date.setText("");
            }
        } else if (Constants.cal == 2) {
            Date newDate = null;
            try {
                newDate = Constants.ff.parse(date);
                end_date = Constants.ff.format(newDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String start_date = et_start_date.getText().toString();
//            String issue_d = issue_date;
            if (!start_date.equalsIgnoreCase("")) {

                Constants.ff.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                try {
                    Date issued = Constants.ff.parse(start_date);
                    today = Constants.ff.parse(Constants.ff.format(today));

                    Date submitD = null;
                    try {
                        submitD = Constants.ff.parse(end_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    if (submitD.before(today)) {
//                        et_end_date.setText("");
//                        et_end_date.setHint("Submit Date can not be a Past Date!");
//                        et_end_date.setHintTextColor(Color.RED);
//                    }else
                    if (submitD.before(issued)) {
                        et_end_date.setText("");
                        et_end_date.setHintTextColor(Color.RED);
                        myToast.show(context.getString(R.string.hint_end_date_before_start), Toast.LENGTH_LONG, false);
//                    } else if (Utils.daysInBetween(issued, submitD) >= 0) {
//                        String sub_date = Utils.formatDate(submitD);
//                        et_submit.setText(sub_date);
//                        et_submit.setTextColor(Color.BLACK);
                    } else {
                        et_end_date.setText(date);
                        //et_end_date.setTextColor(Color.BLACK);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                et_end_date.setHint(context.getString(R.string.hint_select_start_date));
                et_end_date.setHintTextColor(Color.RED);
            }
        }
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
        int position = -1;
        for (int i = 0; i < timezoneListData.size(); i++) {
            if (timezoneListData.get(i).getTimezoneName().equalsIgnoreCase(eventData.getEventTimezone()) || timezoneListData.get(i).getTimezoneId().equalsIgnoreCase(eventData.getEventTimezone())) {
                position = i + 1;
                break;
            }
        }
        return position;
    }

    public int getEventTypePosition() {
        int position = -1;
        for (int i = 0; i < eventTypeListData.size(); i++) {
            if (eventTypeListData.get(i).getEventTypeName().equalsIgnoreCase(eventData.getEventTypeId()) || eventTypeListData.get(i).getEventTypeId().equalsIgnoreCase(eventData.getEventTypeId())) {
                position = i + 1;
                break;
            }
        }
        return position;
    }
}
