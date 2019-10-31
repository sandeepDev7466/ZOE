package com.ztp.app.View.Fragment.CSO.Event;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCountry;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetEventType;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetState;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetTimeZone;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.GetProfileRequest;
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
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.CSO.ViewLocationActivity;
import com.ztp.app.View.Activity.Common.WebviewActivity;
import com.ztp.app.View.Fragment.Common.SelectDateFragment;
import com.ztp.app.Viewmodel.AddEventViewModel;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.GetEventTypeViewModel;
import com.ztp.app.Viewmodel.GetProfileViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.TimeZoneViewModel;
import com.ztp.app.Viewmodel.UpdateEventViewModel;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabNewEventFragment extends Fragment implements View.OnClickListener {

    public TabNewEventFragment() {
        // Required empty public constructor
    }

    boolean formFilled = false;
    SharedPref sharedPref;
    Spinner sp_country, sp_state, sp_event_type, sp_time_zone;
    static MyToast myToast;
    MapView mapView;
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
    List<StateResponse.State> stateListData = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    List<String> timezoneList = new ArrayList<>();
    StateViewModel stateModel;
    String country_id = "", timezone_id = "", event_type__id = "", state_id = "";
    MyTextInputEditText et_event, et_event_description, et_city, et_address, et_contact_name, et_phone_no, et_email, etPostalCode;
    static MyTextInputEditText et_start_date, et_end_date;
    MyTextInputLayout etEmailLayout, etPhoneLayout, etPostalCodeLayout, et_event_layout, et_event_description_layout;
    String str_event = "";
    String str_event_description = "";
    String str_city = "";
    String str_zip = "";
    String str_address = "";
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
    GetEventsResponse.Event eventData;
    MyBoldTextView heading;
    ImageView image;
    boolean location, theme;
    int countImage = 0, updateAddress = 0;
    LinearLayout viewLocation;
    int countryPos, statePos;
    EventAddRequest eventAddRequest;
    EventUpdateRequest eventUpdateRequest;
    DBGetCountry dbGetCountry;
    DBGetState dbGetState;
    DBGetTimeZone dbGetTimeZone;
    DBGetEventType dbGetEventType;
    CheckBox check;
    CardView cardViewWaiver;
    ImageView uploadWaiver;
    MyButton cancelBtn;
    String waiverPath = null, waiverChecked = "0", waiverUploadPath;
    MyTextView waiverName;
    private static final int PICKFILE_REQUEST_CODE = 100;
    GetProfileViewModel getProfileViewModel;
    MyTextInputEditText etStartTime, etEndTime;
    String startTime, endTime;
    Bundle savedState;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab_new_event, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        dbGetCountry = new DBGetCountry(context);
        dbGetState = new DBGetState(context);
        dbGetEventType = new DBGetEventType(context);
        dbGetTimeZone = new DBGetTimeZone(context);
        validate = new ValidateTor();
        gpsTracker = new GPSTracker(context);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        } /*else {
            gpsTracker.showSettingsAlert();
        }*/

        eventAddRequest = new EventAddRequest();
        eventUpdateRequest = new EventUpdateRequest();

        sharedPref = SharedPref.getInstance(context);
        sharedPref.setEventLatitude("");
        sharedPref.setEventLongitude("");
        sharedPref.setEventPostalCode("");
        sharedPref.setEventAddress("");
        sharedPref.setEventCity("");

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
        check = v.findViewById(R.id.check);
        cardViewWaiver = v.findViewById(R.id.cardViewWaiver);
        uploadWaiver = v.findViewById(R.id.uploadWaiver);
        cancelBtn = v.findViewById(R.id.cancelBtn);
        waiverName = v.findViewById(R.id.waiverName);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        etStartTime = v.findViewById(R.id.et_start_time);
        etEndTime = v.findViewById(R.id.et_end_time);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        timeZoneViewModel = ViewModelProviders.of(this).get(TimeZoneViewModel.class);
        eventTypeViewModel = ViewModelProviders.of(this).get(GetEventTypeViewModel.class);
        addEventViewModel = ViewModelProviders.of(this).get(AddEventViewModel.class);
        updateEventViewModel = ViewModelProviders.of(this).get(UpdateEventViewModel.class);
        getProfileViewModel = ViewModelProviders.of(this).get(GetProfileViewModel.class);
        image = v.findViewById(R.id.image);
        viewLocation = v.findViewById(R.id.viewLocation);
        viewLocation.setOnClickListener(this);
        uploadWaiver.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        waiverName.setOnClickListener(this);
        theme = sharedPref.getTheme();

        if (theme) {
            cardViewWaiver.setCardBackgroundColor(context.getResources().getColor(R.color.transparent));
        } else {
            cardViewWaiver.setCardBackgroundColor(context.getResources().getColor(R.color.background_2));
        }

        Bundle b = getArguments();
        if (b != null) {
            type = b.getString("action");
            if (type != null && type.equalsIgnoreCase("update")) {
                update.setText(R.string.update);
                heading.setText(R.string.update_event_detail);
                eventData = (GetEventsResponse.Event) b.getSerializable("model");
                populateData(eventData);
                sharedPref.setEventAddress(eventData.getEventAddress());
                sharedPref.setEventCity(eventData.getEventCity());
                sharedPref.setEventPostalCode(eventData.getEventPostcode());
            } else {
                if (!formFilled) {
                    getCountryList();
                    getTimeZonelist();
                    getEventType();
                }
                waiverPath = null;
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
                        etEmailLayout.setError(context.getString(R.string.err_enter_valid_email));
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
                        etPhoneLayout.setError(context.getString(R.string.err_enter_valid_phone));
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
                        etPostalCodeLayout.setError(context.getString(R.string.err_enter_valid_postal));
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
//                    if (!Utility.isValidName(s.toString())) {
//                        et_event_layout.setError(getString(R.string.err_event_name_validation));
//                        errorEventName = true;
//                    } else {
//                        et_event_layout.setError(null);
//                        errorEventName = false;
//                    }
//                } else {
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
//                    if (!Utility.isValidName(s.toString())) {
//                        et_event_description_layout.setError(getString(R.string.err_event_description_validation));
//                        errorEventDescription = true;
//                    } else {
//                        et_event_description_layout.setError(null);
//                        errorEventDescription = false;
//                    }
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
                countryPos = position;
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

                if (position > 0)
                    state_id = stateListData.get(position - 1).getStateId();
                else
                    state_id = "";
                statePos = position - 1;

               /* et_city.setText("");
                etPostalCode.setText("");
                et_address.setText("");*/

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


            sharedPref.setEventCity(et_city.getText().toString());
            sharedPref.setEventPostalCode(etPostalCode.getText().toString());
            sharedPref.setEventAddress(et_address.getText().toString());

            countImage++;
            Intent intent = new Intent(context, PickImageActivity.class);
            if (type.equalsIgnoreCase("update")) {
                intent.putExtra("event_image", eventData.getEventImage());
            }
            intent.putExtra("count", countImage);
            startActivity(intent);


        });


        etStartTime.setOnTouchListener((v1, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                TimePickerDialog mTimePicker;
                etEndTime.setText("");

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);


                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        startTime = String.format(Locale.ENGLISH, "%02d:%02d", selectedHour, selectedMinute);

                        etStartTime.setText(Utility.getTimeAmPm(startTime));
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

                return true;
            }
            return false;
        });


        etEndTime.setOnTouchListener((v13, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (startTime != null && !startTime.isEmpty()) {

                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, (timePicker, selectedHour, selectedMinute) -> {

                        endTime = String.format(Locale.ENGLISH, "%02d:%02d", selectedHour, selectedMinute);

                        int hour1 = Integer.parseInt(startTime.split(":")[0]);
                        int minute1 = Integer.parseInt(startTime.split(":")[1]);

                        int tempStart = (60 * minute1) + (3600 * hour1);


                        int hour2 = Integer.parseInt(endTime.split(":")[0]);
                        int minute2 = Integer.parseInt(endTime.split(":")[1]);

                        int tempEnd = (60 * minute2) + (3600 * hour2);

                        if (tempStart > tempEnd) {
                            myToast.show(context.getString(R.string.err_endtime_notbefore_startime), Toast.LENGTH_SHORT, false);
                            etStartTime.setText("");
                        } else {

                            //et_end_time_volunteers.setText(endTime);
                            etEndTime.setText(Utility.getTimeAmPm(endTime));
                        }

                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                } else {
                    myToast.show(context.getString(R.string.err_enter_start_time_first), Toast.LENGTH_SHORT, false);
                }

                return true;
            }
            return false;
        });

        update.setOnClickListener(v1 -> {

            if (!event_type__id.equalsIgnoreCase("")) {
                if (et_event.getText() != null && !et_event.getText().toString().trim().isEmpty()) {
//                    if (!Utility.isValidName(et_event.getText().toString())) {
//                        myToast.show(getString(R.string.err_event_name_validation), Toast.LENGTH_SHORT, false);
//                        return;
//                    }
                    if (et_event_description.getText() != null && !et_event_description.getText().toString().trim().isEmpty()) {
//                        if (!Utility.isValidName(et_event_description.getText().toString())) {
//                            myToast.show(getString(R.string.err_event_description_validation), Toast.LENGTH_SHORT, false);
//                            return;
//                        }
                        if (!country_id.equalsIgnoreCase("")) {
                            if (!state_id.equalsIgnoreCase("")) {
                                if (et_city.getText() != null && !et_city.getText().toString().isEmpty()) {
                                    if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {
                                        if (!Utility.isValidPostalCode(etPostalCode.getText().toString())) {
                                            myToast.show(context.getString(R.string.err_enter_valid_postal), Toast.LENGTH_SHORT, false);
                                            return;
                                        }
                                        if (!timezone_id.equalsIgnoreCase("")) {
                                            if (et_address.getText() != null && !et_address.getText().toString().isEmpty()) {
                                                if (et_phone_no.getText() != null && !et_phone_no.getText().toString().isEmpty()) {
                                                    if (!Utility.isValidPhoneNumber(et_phone_no.getText().toString())) {
                                                        myToast.show(context.getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT, false);
                                                        return;
                                                    }
                                                    if (et_email.getText() != null && !et_email.getText().toString().isEmpty()) {
                                                        if (!Utility.isValidEmail(et_email.getText().toString())) {
                                                            myToast.show(context.getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                                                            return;
                                                        }
                                                        if (et_start_date.getText() != null && !et_start_date.getText().toString().isEmpty()) {

                                                            if (et_end_date.getText() != null && !et_end_date.getText().toString().isEmpty()) {

                                                                if (startTime != null && !startTime.isEmpty()) {

                                                                    if (endTime != null && !endTime.isEmpty()) {

                                                                        if (!errorPostalCode && !errorPhone && !errorEmail && !errorEventName && !errorEventDescription) {
                                                                            if (check.isChecked()) {
                                                                                if (waiverPath != null && waiverPath.isEmpty()) {
                                                                                    myToast.show(context.getString(R.string.err_enter_waiver_doc), Toast.LENGTH_SHORT, false);
                                                                                    return;
                                                                                }
                                                                            }
                                                                            if (Utility.isNetworkAvailable(context)) {
                                                                                myProgressDialog.show(context.getString(R.string.please_wait));
                                                                                submit();
                                                                            } else
                                                                                myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);


                                                                        } else {
                                                                            myToast.show(context.getString(R.string.err_invalid_data), Toast.LENGTH_SHORT, false);
                                                                        }
                                                                    } else {
                                                                        myToast.show(context.getString(R.string.enter_end_time), Toast.LENGTH_SHORT, false);
                                                                    }

                                                                } else {
                                                                    myToast.show(context.getString(R.string.enter_start_time), Toast.LENGTH_SHORT, false);
                                                                }
                                                            } else {
                                                                myToast.show(context.getString(R.string.enter_end_date), Toast.LENGTH_SHORT, false);
                                                            }
                                                        } else {
                                                            myToast.show(context.getString(R.string.enter_start_date), Toast.LENGTH_SHORT, false);
                                                        }
                                                    } else {
                                                        myToast.show(context.getString(R.string.err_enter_email), Toast.LENGTH_SHORT, false);
                                                    }
                                                } else {
                                                    myToast.show(context.getString(R.string.err_enter_phone_number), Toast.LENGTH_SHORT, false);
                                                }
                                            } else {
                                                myToast.show(context.getString(R.string.err_enter_address), Toast.LENGTH_SHORT, false);
                                            }
                                        } else {
                                            myToast.show(context.getString(R.string.err_enter_time_zone), Toast.LENGTH_SHORT, false);
                                        }
                                    } else {
                                        myToast.show(context.getString(R.string.err_enter_postal_code), Toast.LENGTH_SHORT, false);
                                    }
                                } else {
                                    myToast.show(context.getString(R.string.err_enter_city), Toast.LENGTH_SHORT, false);
                                }

                            } else {
                                myToast.show(context.getString(R.string.select_state), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(context.getString(R.string.select_country), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(context.getString(R.string.err_enter_event_description), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(context.getString(R.string.err_enter_event_name), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_enter_event_type), Toast.LENGTH_SHORT, false);
            }
        });

        clear.setOnClickListener(v14 -> {

            if (type.equalsIgnoreCase("add"))
                clear();
            else {
                PickImageActivity.uploadFile = null;
                populateData(eventData);
            }

        });

        et_start_date.setOnClickListener(v12 -> {
            Constants.cal = 1;
            DialogFragment df = new SelectDateFragment("StartDate", "");
            df.show(getFragmentManager(), "datePicker");
        });
        et_end_date.setOnClickListener(v13 -> {
            Constants.cal = 2;
            DialogFragment df = new SelectDateFragment("EndDate", et_start_date.getText().toString());
            df.show(getFragmentManager(), "datePicker");
        });

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardViewWaiver.setVisibility(View.VISIBLE);
                    waiverChecked = "1";
                } else {
                    cardViewWaiver.setVisibility(View.GONE);
                    waiverChecked = "0";
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("address", et_address.getText().toString());
        outState.putString("city", et_city.getText().toString());
        outState.putString("zipcode", etPostalCode.getText().toString());
        savedState = outState;
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mapView != null) {
            mapView.onResume();
        }

        if (PickImageActivity.cameFrom) {
            if (PickImageActivity.uploadFile == null) {
                if (eventData != null)
                    if (!eventData.getEventImage().isEmpty())
                        Picasso.with(context)
                                .load(eventData.getEventImage())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .placeholder(R.drawable.no_image)
                                .error(R.drawable.no_image)
                                .into(image);

            } else {
                image.setImageBitmap(BitmapFactory.decodeFile(PickImageActivity.uploadFile.getAbsolutePath()));
            }
        }
        location = sharedPref.getLocation();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Dialog dialog = new Dialog(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.exit_registration_dialog, null);
                    dialog.setContentView(view);
                    dialog.setCancelable(false);

                    LinearLayout yes = view.findViewById(R.id.yes);
                    LinearLayout no = view.findViewById(R.id.no);

                    yes.setOnClickListener(v1 -> {
                        dialog.dismiss();
                        PickImageActivity.uploadFile = null;
                        if (getActivity() != null)
                            getActivity().getSupportFragmentManager().popBackStack();
                    });

                    no.setOnClickListener(v12 -> dialog.dismiss());

                    dialog.show();

                    return true;
                }
                return false;
            }
        });


        if (type.equalsIgnoreCase("update")) {
            if (!sharedPref.getEventAddress().isEmpty()) {
                et_address.setText(sharedPref.getEventAddress());
                sharedPref.setEventAddress("");
            } else {
                et_address.setText("");
            }
            if (!sharedPref.getEventPostalCode().isEmpty()) {
                etPostalCode.setText(sharedPref.getEventPostalCode());
                sharedPref.setEventPostalCode("");
            } else {
                etPostalCode.setText("");
            }
            if (!sharedPref.getEventCity().isEmpty()) {
                et_city.setText(sharedPref.getEventCity());
                sharedPref.setEventCity("");
            } else {
                et_city.setText("");
            }
        } else if (type.equalsIgnoreCase("add")) {

            if (!sharedPref.getEventAddress().isEmpty()) {
                et_address.setText(sharedPref.getEventAddress());
                sharedPref.setEventAddress("");
            } else {
                et_address.setText("");
            }
            if (!sharedPref.getEventPostalCode().isEmpty()) {
                etPostalCode.setText(sharedPref.getEventPostalCode());
                sharedPref.setEventPostalCode("");
            } else {
                etPostalCode.setText("");
            }
            if (!sharedPref.getEventCity().isEmpty()) {
                et_city.setText(sharedPref.getEventCity());
                sharedPref.setEventCity("");
            } else {
                et_city.setText("");
            }
        }
    }

    public void populateData(GetEventsResponse.Event eventData) {
        et_event.setText(eventData.getEventHeading());
        et_event_description.setText(eventData.getEventDetails());
        et_city.setText(eventData.getEventCity());
        etPostalCode.setText(eventData.getEventPostcode());
        et_address.setText(eventData.getEventAddress());
        et_contact_name.setText(eventData.getEventPhone());

        if (!eventData.getEventStartTime().isEmpty()) {
            etStartTime.setText(Utility.getTimeAmPm(eventData.getEventStartTime()));
            startTime = eventData.getEventStartTime();
        }
        if (!eventData.getEventEndTime().isEmpty()) {
            etEndTime.setText(Utility.getTimeAmPm(eventData.getEventEndTime()));
            endTime = eventData.getEventEndTime();
        }
        et_phone_no.setText(Utility.getFormattedPhoneNumber(eventData.getEventPhone()));
        et_email.setText(eventData.getEventEmail());
        et_start_date.setText(eventData.getEventRegisterStartDate());
        et_end_date.setText(eventData.getEventRegisterEndDate());

        if (eventData.getEventWaiverRequired() != null) {
            if (eventData.getEventWaiverRequired().equalsIgnoreCase("1")) {
                check.setChecked(true);
                String fileName = eventData.getEventWaiverDocument().substring(eventData.getEventWaiverDocument().lastIndexOf('/') + 1);
                SpannableString string = new SpannableString(fileName);
                string.setSpan(new UnderlineSpan(), 0, string.length(), 0);
                waiverName.setText(string);
                waiverUploadPath = eventData.getEventWaiverDocument();
                //waiverName.setText(eventData.getEventWaiverDocument());
                cardViewWaiver.setVisibility(View.VISIBLE);
            } else {
                check.setChecked(false);
                waiverName.setText("");
                cardViewWaiver.setVisibility(View.GONE);
                waiverPath = null;
            }
        }
        if (PickImageActivity.uploadFile == null) {
            if (!eventData.getEventImage().isEmpty())
                Picasso.with(context)
                        .load(eventData.getEventImage())
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(image);

        } else {
//            Bitmap bitmap = BitmapFactory.decodeFile(PickImageActivity.uploadFile.getName());
            image.setImageBitmap(BitmapFactory.decodeFile(PickImageActivity.uploadFile.getAbsolutePath()));
        }
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
        etStartTime.setText("");
        etEndTime.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isAdded() && context != null)
            TabNewEventFragment.context = context;
        else
            TabNewEventFragment.context = getActivity();
    }

    private void getCountryList() {

        countryList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {
                if (countryResponse != null) {
                    if (countryResponse.getResStatus().equalsIgnoreCase("200")) {
                        countryListData = countryResponse.getResData();
                        if (countryListData.size() > 0) {
                            for (int i = 0; i < countryListData.size(); i++) {
                                countryList.add(countryListData.get(i).getCountryName());
                            }

                            setCountrySpinner(countryList);
                        }
                    } else if (countryResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(context.getString(R.string.err_no_country_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            countryListData = dbGetCountry.getCountryList();
            if (countryListData.size() > 0) {
                for (int i = 0; i < countryListData.size(); i++) {
                    countryList.add(countryListData.get(i).getCountryName());
                }
                setCountrySpinner(countryList);
            }
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
            myProgressDialog.show(context.getString(R.string.please_wait));
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {

                if (stateResponse != null) {
                    if (stateResponse.getResStatus().equalsIgnoreCase("200")) {
                        stateListData = stateResponse.getStateList();
                        stateList.add(0, context.getString(R.string.select_state));
                        if (stateListData.size() > 0) {
                            for (int i = 0; i < stateListData.size(); i++) {
                                stateList.add(i + 1, stateListData.get(i).getStateName());
                            }
                            setStateSpinner(stateList);
                        }
                    } else if (stateResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(context.getString(R.string.err_no_state_found), Toast.LENGTH_SHORT, false);
                    }

                } else {
                    myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            stateListData = dbGetState.getStateList();
            stateList.add(0, context.getString(R.string.select_state));
            if (stateListData.size() > 0) {
                for (int i = 0; i < stateListData.size(); i++) {
                    stateList.add(i + 1, stateListData.get(i).getStateName());
                }
                setStateSpinner(stateList);
            }
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
        } else {
            getProfileData();
        }
    }

    private void setTimezoneSpinner(List<String> timezoneList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, timezoneList);
        sp_time_zone.setAdapter(adapter);
        if (type.equalsIgnoreCase("update")) {

            if (getTimezonePosition() != -1) {
                sp_time_zone.setSelection(getTimezonePosition());
            } else {
                sp_time_zone.setSelection(0);
            }
        } else {
            if (!sharedPref.getTimezone().isEmpty())
                sp_time_zone.setSelection(getTimezonePosition(sharedPref.getTimezone()), true);
        }
    }

    private void getProfileData() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            getProfileViewModel.getProfileResponse(new GetProfileRequest(sharedPref.getUserId()/*"S789123"*//*"C20190409Q9l4hzL3916"*//*sharedPref.getUserId()*/)).observe((LifecycleOwner) context, getProfileResponse ->
            {
                try {
                    if (getProfileResponse != null) {
                        if (getProfileResponse.getResStatus().equalsIgnoreCase("200") && getProfileResponse.getResData() != null) {
                            if (getProfileResponse.getResData() != null)
                                et_email.setText(getProfileResponse.getResData().getUserEmail());
                            et_phone_no.setText(Utility.getFormattedPhoneNumber(getProfileResponse.getResData().getUserPhone()));
                            //et_phone_no.setText(getProfileResponse.getResData().getUserPhone());
                            et_city.setText(getProfileResponse.getResData().getUserCity());
                            etPostalCode.setText(getProfileResponse.getResData().getUserZipcode());
                            et_address.setText(getProfileResponse.getResData().getUserAddress());
                            sp_country.setSelection(getCountryPositionAutofill(getProfileResponse.getResData().getUserCountry(), getProfileResponse.getResData().getUserCountryName()), true);
                            sp_state.setSelection(getStatePositionAutofill(getProfileResponse.getResData().getUserState(), getProfileResponse.getResData().getUserStateName()), true);

                        } else if (getProfileResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(context.getString(R.string.err_no_profile_found), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myProgressDialog.dismiss();
            });
        }
    }

    public void getTimeZonelist() {
        timezoneList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));

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
                                timezoneList.add(0, context.getString(R.string.select_time_zone));
                                setTimezoneSpinner(timezoneList);
                            }
                        } else if (timeZoneResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(context.getString(R.string.err_no_timezone_found), Toast.LENGTH_SHORT, false);
                        }
                    } else {

                        myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                    myProgressDialog.dismiss();
                }
            });
        } else {
            timezoneListData = dbGetTimeZone.getTimeZoneList();
            if (timezoneListData.size() > 0) {
                for (int i = 0; i < timezoneListData.size(); i++) {
                    timezoneList.add(timezoneListData.get(i).getTimezoneName());
                }
                timezoneList.add(0, context.getString(R.string.select_time_zone));
                setTimezoneSpinner(timezoneList);
            }
        }
    }

    private void getEventType() {

        eventTypeList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            eventTypeViewModel.getEventTypeResponse(context).observe((LifecycleOwner) context, eventTypeResponse -> {

                if (eventTypeResponse != null) {
                    if (eventTypeResponse.getResStatus().equalsIgnoreCase("200")) {
                        eventTypeListData = eventTypeResponse.getResData();
                        if (eventTypeListData.size() > 0) {
                            for (int i = 0; i < eventTypeListData.size(); i++) {
                                eventTypeList.add(eventTypeListData.get(i).getEventTypeName());
                            }
                            eventTypeList.add(0, context.getString(R.string.select_event_type));
                            setEventTypeSpinner(eventTypeList);
                        }
                    } else if (eventTypeResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(context.getString(R.string.err_no_event_type_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {

            eventTypeListData = dbGetEventType.getEventTypeList();
            if (eventTypeListData.size() > 0) {
                for (int i = 0; i < eventTypeListData.size(); i++) {
                    eventTypeList.add(eventTypeListData.get(i).getEventTypeName());
                }
                eventTypeList.add(0, context.getString(R.string.select_event_type));
                setEventTypeSpinner(eventTypeList);
            }
        }
    }

    private void setEventTypeSpinner(List<String> eventTypeList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, eventTypeList);
        sp_event_type.setAdapter(adapter);

        if (type.equalsIgnoreCase("update")) {
            if (getEventTypePosition() != -1) {
                sp_event_type.setSelection(getEventTypePosition());
            } else {
                sp_event_type.setSelection(0);
            }
        }
    }

    private void submit() {

        update.setEnabled(false);

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
            eventAddRequest.setUser_id(sharedPref.getUserId());
            eventAddRequest.setEvent_type_id(event_type__id);
            eventAddRequest.setEvent_heading(str_event);
            eventAddRequest.setEvent_details(str_event_description);
            eventAddRequest.setEvent_timezone(timezone_id);
            eventAddRequest.setEvent_country(country_id);
            eventAddRequest.setEvent_state(state_id);

            if (!sharedPref.getEventAddress().isEmpty())
                eventAddRequest.setEvent_address(sharedPref.getEventAddress());
            else
                eventAddRequest.setEvent_address(str_address);


            if (!sharedPref.getEventCity().isEmpty())
                eventAddRequest.setEvent_city(sharedPref.getEventCity());
            else
                eventAddRequest.setEvent_city(str_city);

            if (!sharedPref.getEventPostalCode().isEmpty())
                eventAddRequest.setEvent_postcode(sharedPref.getEventPostalCode());
            else
                eventAddRequest.setEvent_postcode(str_zip);

            if (!sharedPref.getEventLatitude().isEmpty() || !sharedPref.getEventLongitude().isEmpty()) {
                eventAddRequest.setEvent_latitude(sharedPref.getEventLatitude());
                eventAddRequest.setEvent_longitude(sharedPref.getEventLongitude());
            } else {
                LatLng latLng = Utility.getLocationFromAddress(context, getAddressString());
                if (latLng != null) {
                    eventAddRequest.setEvent_latitude(String.valueOf(latLng.latitude));
                    eventAddRequest.setEvent_longitude(String.valueOf(latLng.longitude));
                }
            }
            eventAddRequest.setEvent_phone(str_phone_no);
            eventAddRequest.setEvent_email(str_email);
            eventAddRequest.setEvent_image(PickImageActivity.uploadFile != null ? PickImageActivity.uploadFile.getName() : "");
            eventAddRequest.setEvent_register_start_date(str_start_date);
            eventAddRequest.setEvent_register_end_date(str_end_date);
            if (check.isChecked())
                waiverChecked = "1";
            else
                waiverChecked = "0";

            eventAddRequest.setEvent_waiver_req(waiverChecked);
            eventAddRequest.setEvent_waiver_doc(waiverName.getText().toString());
            eventAddRequest.setEvent_start_time(startTime);
            eventAddRequest.setEvent_end_time(endTime);
            Log.i("REQUEST", "" + new Gson().toJson(eventAddRequest));


            /*  if (PickImageActivity.uploadFile != null) {*/

            myProgressDialog.show(context.getString(R.string.please_wait));

            addEventViewModel.getAddEventResponse(eventAddRequest).observe((LifecycleOwner) context, registerResponse -> {

                if (registerResponse != null) {
                    if (registerResponse.getResStatus().equalsIgnoreCase("200")) {

                        sharedPref.setEventLatitude("");
                        sharedPref.setEventLongitude("");

                        //clear();

                        if (PickImageActivity.uploadFile != null)
                            uploadDocument(PickImageActivity.uploadFile, registerResponse.getResData().getEventId());

                        if (waiverPath != null)
                            uploadWaiverDocument(new File(waiverPath), registerResponse.getResData().getEventId());

                        if (PickImageActivity.uploadFile == null && waiverPath == null) {
                            ((CsoDashboardActivity) context).setEventFragment();
                            myProgressDialog.dismiss();
                        }
                        myToast.show(context.getString(R.string.toast_event_added_success), Toast.LENGTH_SHORT, true);
                    } else if (registerResponse.getResStatus().equalsIgnoreCase("401")) {
                        myProgressDialog.dismiss();
                        myToast.show(context.getString(R.string.toast_event_added_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myProgressDialog.dismiss();
                    myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

            });

        } else if (type.equalsIgnoreCase("update")) {
            eventUpdateRequest.setEvent_id(eventData.getEventId());
            eventUpdateRequest.setUser_id(sharedPref.getUserId());
            eventUpdateRequest.setEvent_type_id(event_type__id);
            eventUpdateRequest.setEvent_heading(str_event);
            eventUpdateRequest.setEvent_details(str_event_description);
            eventUpdateRequest.setEvent_country(country_id);
            eventUpdateRequest.setEvent_state(state_id);
            eventUpdateRequest.setEvent_timezone(timezone_id);
            if (check.isChecked())
                waiverChecked = "1";
            else
                waiverChecked = "0";
            eventUpdateRequest.setEvent_waiver_req(waiverChecked);
            eventUpdateRequest.setEvent_waiver_doc(waiverName.getText().toString());

            if (!sharedPref.getEventAddress().isEmpty())
                eventUpdateRequest.setEvent_address(sharedPref.getEventAddress());
            else
                eventUpdateRequest.setEvent_address(str_address);


            if (!sharedPref.getEventCity().isEmpty())
                eventUpdateRequest.setEvent_city(sharedPref.getEventCity());
            else
                eventUpdateRequest.setEvent_city(str_city);

            if (!sharedPref.getEventPostalCode().isEmpty())
                eventUpdateRequest.setEvent_postcode(sharedPref.getEventPostalCode());
            else
                eventUpdateRequest.setEvent_postcode(str_zip);


            if (!sharedPref.getEventLatitude().isEmpty() || !sharedPref.getEventLongitude().isEmpty()) {
                eventUpdateRequest.setEvent_latitude(sharedPref.getEventLatitude());
                eventUpdateRequest.setEvent_longitude(sharedPref.getEventLongitude());
            } else {
                LatLng latLng = Utility.getLocationFromAddress(context, getAddressString());
                if (latLng != null) {
                    eventUpdateRequest.setEvent_latitude(String.valueOf(latLng.latitude));
                    eventUpdateRequest.setEvent_longitude(String.valueOf(latLng.longitude));
                }
            }

            eventUpdateRequest.setEvent_phone(str_phone_no);
            eventUpdateRequest.setEvent_email(str_email);
            if (PickImageActivity.uploadFile != null)
                eventUpdateRequest.setEvent_image(PickImageActivity.uploadFile.getName());
            else
                eventUpdateRequest.setEvent_image(eventData.getEventImage().substring(eventData.getEventImage().lastIndexOf('/') + 1));
            eventUpdateRequest.setEvent_register_start_date(str_start_date);
            eventUpdateRequest.setEvent_register_end_date(str_end_date);
            eventUpdateRequest.setEvent_start_time(startTime);
            eventUpdateRequest.setEvent_end_time(endTime);
            Log.i("REQUEST", "" + new Gson().toJson(eventUpdateRequest));

            updateEventViewModel.getUpdateEventResponse(eventUpdateRequest).observe((LifecycleOwner) context, updateResponse -> {

                if (updateResponse != null) {
                    Log.i("RESPONSE", "" + new Gson().toJson(updateResponse));
                    if (updateResponse.getResStatus().equalsIgnoreCase("200")) {

                        sharedPref.setEventLatitude("");
                        sharedPref.setEventLongitude("");

                        //clear();

                            /*if (PickImageActivity.uploadFile != null)
                                uploadDocument(PickImageActivity.uploadFile, eventData.getEventId());

                            if (waiverPath != null) {
                                uploadWaiverDocument(new File(waiverPath), eventData.getEventId());
                            }*/

                        if (PickImageActivity.uploadFile != null)
                            uploadDocument(PickImageActivity.uploadFile, eventData.getEventId());

                        if (waiverPath != null)
                            uploadWaiverDocument(new File(waiverPath), eventData.getEventId());

                        if (PickImageActivity.uploadFile == null && waiverPath == null) {
                            ((CsoDashboardActivity) context).setEventFragment();
                            myProgressDialog.dismiss();
                        }

                        myToast.show(context.getString(R.string.event_updated_successfully), Toast.LENGTH_SHORT, true);
                    } else if (updateResponse.getResStatus().equalsIgnoreCase("401")) {
                        myProgressDialog.dismiss();
                        myToast.show(context.getString(R.string.err_event_not_updated), Toast.LENGTH_SHORT, false);

                    }
                } else {
                    myProgressDialog.dismiss();
                    myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
            });
        }
    }

    private void uploadDocument(File fileToUpload, String event_id) {

        //myProgressDialog.show(getString(R.string.please_wait));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("event_image", fileToUpload.getName(), RequestBody.create(MediaType.parse("*/*"), fileToUpload));
        ApiInterface apiInterface = Api.getClient();

        Call<UploadDocumentResponse> call = apiInterface.uploadEventImage(filePart, event_id, sharedPref.getUserId(), "1234", "cso_event_file_upload");

        call.enqueue(new Callback<UploadDocumentResponse>() {
            @Override
            public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {

                if (response.body() != null) {

                    if (response.body().getResStatus().equalsIgnoreCase("200")) {
                        myProgressDialog.dismiss();
                        //myToast.show(getString(R.string.file_uploaded_successfully), Toast.LENGTH_SHORT, true);
                        PickImageActivity.uploadFile = null;
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(context.getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, false);
                    }
                    if (waiverPath == null)
                        ((CsoDashboardActivity) context).setEventFragment();
                }
            }

            @Override
            public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
                t.printStackTrace();
                myProgressDialog.dismiss();
            }
        });
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
                        //et_end_date.setHintTextColor(Color.RED);
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

                myToast.show(context.getString(R.string.hint_select_start_date), Toast.LENGTH_LONG, false);
                //et_end_date.setHint(context.getString(R.string.hint_select_start_date));
                //et_end_date.setHintTextColor(Color.RED);
            }
        }
    }

    public int getCountryPositionAutofill(String country_id, String country_name) {
        int position = -1;
        for (int i = 0; i < countryListData.size(); i++) {
            if (countryListData.get(i).getCountryName().equalsIgnoreCase(country_name) || countryListData.get(i).getCountryId().equalsIgnoreCase(country_id)) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getStatePositionAutofill(String state_id, String state_name) {
        int position = -1;
        for (int i = 0; i < stateListData.size(); i++) {
            if (stateListData.get(i).getStateName().equalsIgnoreCase(state_name) || stateListData.get(i).getStateId().equalsIgnoreCase(state_id)) {
                position = i + 1;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getCountryPosition() {
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
        int position = -1;
        for (int i = 0; i < stateListData.size(); i++) {
            if (stateListData.get(i).getStateName().equalsIgnoreCase(eventData.getEventState()) || stateListData.get(i).getStateId().equalsIgnoreCase(eventData.getEventState())) {
                position = i + 1;
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

    @Override
    public void onPause() {
        super.onPause();
        gpsTracker.stopUsingGPS();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewLocation:
                formFilled = true;
                sharedPref.setEventCity(et_city.getText().toString());
                sharedPref.setEventPostalCode(etPostalCode.getText().toString());
                sharedPref.setEventAddress(et_address.getText().toString());

                if (Utility.isNetworkAvailable(context)) {
                    LatLng latLng = Utility.getLocationFromAddress(context, getAddressString());
                    if (latLng != null || statePos != -1) {
                        Intent intent = new Intent(context, ViewLocationActivity.class);
                        intent.putExtra("latitude", latLng != null ? latLng.latitude : 0.00);
                        intent.putExtra("longitude", latLng != null ? latLng.longitude : 0.00);
                        intent.putExtra("state_name", stateListData.get(statePos).getStateName());
                        startActivityForResult(intent, 10);
                        ((AppCompatActivity) context).overridePendingTransition(0, 0);
                    } else {
                        myToast.show(context.getString(R.string.select_state), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                }

                break;

            case R.id.uploadWaiver:
                formFilled = true;
                sharedPref.setEventCity(et_city.getText().toString());
                sharedPref.setEventPostalCode(etPostalCode.getText().toString());
                sharedPref.setEventAddress(et_address.getText().toString());

                Intent intent = null;
                if (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG")) {
                    intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                    intent.putExtra("CONTENT_TYPE", "*/*");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                } else {
                    String type = "*/*";

                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    intent.setType(type);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }
                startActivityForResult(Intent.createChooser(intent, "select file"), PICKFILE_REQUEST_CODE);

                break;

            case R.id.cancelBtn:
                waiverPath = null;
                waiverName.setText("");
                break;
            case R.id.waiverName:
                formFilled = true;
                sharedPref.setEventCity(et_city.getText().toString());
                sharedPref.setEventPostalCode(etPostalCode.getText().toString());
                sharedPref.setEventAddress(et_address.getText().toString());
                if (waiverUploadPath.contains("/")) {
                    Intent intent1 = new Intent(context, WebviewActivity.class);
                    intent1.putExtra("url", waiverUploadPath);
                    context.startActivity(intent1);
                }
                break;
        }
    }

    private void uploadWaiverDocument(File fileToUpload, String event_id) {

        //myProgressDialog.show(getString(R.string.please_wait));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("event_waiver_doc", fileToUpload.getName(), RequestBody.create(MediaType.parse("*/*"), fileToUpload));
        ApiInterface apiInterface = Api.getClient();

        Call<UploadDocumentResponse> call = apiInterface.uploadEventImage(filePart, event_id, sharedPref.getUserId(), "1234", "event_doc_upload");

        call.enqueue(new Callback<UploadDocumentResponse>() {
            @Override
            public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {

                if (response.body() != null) {

                    if (response.body().getResStatus().equalsIgnoreCase("200")) {
                        myProgressDialog.dismiss();
                        //myToast.show(getString(R.string.file_uploaded_successfully), Toast.LENGTH_SHORT, true);
                        waiverPath = null;

                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(context.getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, false);
                    }
                    if(PickImageActivity.uploadFile == null)
                    ((CsoDashboardActivity) context).setEventFragment();
                }
            }

            @Override
            public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
                t.printStackTrace();
                myProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE && data != null) {
            formFilled = true;
            Uri uri = data.getData();
            try {
                waiverPath = Utility.getPath(context, uri);
                if (waiverPath == null) {
                    myToast.show(context.getString(R.string.err_select_other_path), Toast.LENGTH_SHORT, false);
                    waiverName.setVisibility(View.GONE);
                } else {
                    String fileExt = MimeTypeMap.getFileExtensionFromUrl(waiverPath);
                    File file = new File(waiverPath);
                    int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));
                    waiverName.setVisibility(View.VISIBLE);
                    waiverName.setText(file.getName());

                    if (!(fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("png") || fileExt.equalsIgnoreCase("jpeg") || fileExt.equalsIgnoreCase("docx") || fileExt.equalsIgnoreCase("doc") || fileExt.equalsIgnoreCase("pptx") || fileExt.equalsIgnoreCase("xlsx") || fileExt.equalsIgnoreCase("pdf") || fileExt.equalsIgnoreCase("txt"))) {
                        waiverPath = null;
                        myToast.show(context.getString(R.string.wrong_file_format), Toast.LENGTH_SHORT, false);
                    } else {
                        if (file_size > 1024) {
                            myToast.show(context.getString(R.string.err_file_size), Toast.LENGTH_SHORT, false);
                            waiverPath = null;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getAddressString() {
        String address = "";

        if (stateListData.size() > 0 && countryListData.size() > 0) {
            if (countryPos != -1 && statePos != -1) {
                String stateName = stateListData.get(statePos).getStateName();
                String countryName = countryListData.get(countryPos).getCountryName();

                address = stateName + "," + countryName;

                if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {
                    address = stateName + " " + etPostalCode.getText().toString().trim() + "," + countryName;
                }

                if (et_city.getText() != null && !et_city.getText().toString().isEmpty()) {
                    address = et_city.getText().toString().trim() + "," + address;
                }

                if (et_address.getText() != null && !et_address.getText().toString().trim().isEmpty()) {
                    address = et_address.getText().toString().trim() + "," + address;
                }
            }
        }
        return address;
    }

    public int getTimezonePosition(String timezone_code) {
        int position = -1;
        for (int i = 0; i < timezoneListData.size(); i++) {
            if (timezoneListData.get(i).getTimezoneCode().equalsIgnoreCase(timezone_code)) {
                position = i + 1;
                break;
            }
        }
        return position;
    }

    @Override
    public void onStop() {
        super.onStop();
        sharedPref.setEventAddress(et_address.getText().toString());
        sharedPref.setEventCity(et_city.getText().toString());
        sharedPref.setEventPostalCode(etPostalCode.getText().toString());
    }
}
