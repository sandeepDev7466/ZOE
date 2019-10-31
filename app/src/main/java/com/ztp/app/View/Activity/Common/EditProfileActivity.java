package com.ztp.app.View.Activity.Common;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.raywenderlich.android.validatetor.ValidateTor;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCountry;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetProfile;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetSchool;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetState;
import com.ztp.app.Data.Local.Room.Async.Save.DBSaveProfile;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetProfileRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateProfileRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_2Activity;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.GetProfileViewModel;
import com.ztp.app.Viewmodel.SchoolViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.UpdateProfileViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    MyButton update, clear;
    String type;
    Context context;
    Spinner sp_school, sp_country, sp_state, sp_gender;
    MyTextInputLayout etFirstNameLayout, etLastNameLayout, etPostalCodeLayout, etEmailLayout, etPhoneLayout;
    MyTextInputEditText etFirstName, etLastName, etEmail, etPhone, etCity, etPostalCode, etDob, etPassword, etConfirmPassword, etAddress;
    MyToast myToast;
    SharedPref sharedPref;
    boolean theme;
    CountryViewModel countryModel;
    StateViewModel stateModel;
    SchoolViewModel schoolModel;
    List<CountryResponse.Country> countryListData = new ArrayList<>();
    List<StateResponse.State> stateListData = new ArrayList<>();
    List<SchoolResponse.School> schoolListData = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    List<String> schoolList = new ArrayList<>();
    String country_id, state_id, school_id, gender_id;
    ImageView back;
    ScrollView scrollView;
    Calendar myCalendar;
    GetProfileViewModel getProfileViewModel;
    UpdateProfileViewModel updateProfileViewModel;
    GetProfileResponse getProfileResponse;
    LinearLayout nav_layout, schoolLayout;
    RoomDB roomDB;
    boolean flag = false;
    ValidateTor validate;
    boolean error = false;
    DBGetCountry dbGetCountry;
    DBGetState dbGetState;
    DBGetSchool dbGetSchool;
    DBGetProfile dbGetProfile;
    MyTextView school_spinner_heading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        context = this;
        sharedPref = SharedPref.getInstance(context);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        dbGetCountry = new DBGetCountry(context);
        dbGetState = new DBGetState(context);
        dbGetSchool = new DBGetSchool(context);
        dbGetProfile = new DBGetProfile(context, sharedPref.getUserId());
        validate = new ValidateTor();
        roomDB = RoomDB.getInstance(context);
        myCalendar = Calendar.getInstance();
        myProgressDialog = new MyProgressDialog(context);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        schoolModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        getProfileViewModel = ViewModelProviders.of(this).get(GetProfileViewModel.class);
        updateProfileViewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel.class);
        theme = sharedPref.getTheme();
        myToast = new MyToast(context);
        scrollView = findViewById(R.id.scrollView);
        update = findViewById(R.id.update);
        clear = findViewById(R.id.clear);
        back = findViewById(R.id.back);
        school_spinner_heading = findViewById(R.id.school_spinner_heading);

        sp_school = findViewById(R.id.school);
        sp_country = findViewById(R.id.country);
        sp_state = findViewById(R.id.state);
        sp_gender = findViewById(R.id.gender);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);

        etFirstNameLayout = findViewById(R.id.etFirstNameLayout);
        etLastNameLayout = findViewById(R.id.etLastNameLayout);
        etPostalCodeLayout = findViewById(R.id.etPostalCodeLayout);
        etEmailLayout = findViewById(R.id.etEmailLayout);
        etPhoneLayout = findViewById(R.id.etPhoneLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        } else {
            etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);
        nav_layout = findViewById(R.id.nav_layout);
        schoolLayout = findViewById(R.id.schoolLayout);

        update.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        etDob.setOnTouchListener(this);

        etPhone.setEnabled(false);
        etEmail.setEnabled(false);
        sp_school.setEnabled(false);

        if (Utility.isNetworkAvailable(context)) {
            update.setText(getString(R.string.update));
        } else {
            if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso)) {
                update.setText(getString(R.string.next_cso));
                etDob.setHint(getString(R.string.date_of_birth_cso));
            } else {
                update.setText(getString(R.string.done));
                etDob.setHint(getString(R.string.date_of_birth));
            }
        }

       /* if (type.equalsIgnoreCase("cso")) {
            nav_layout.setVisibility(View.VISIBLE);
            schoolLayout.setVisibility(View.GONE);
            school_spinner_heading.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("stu")) {
//            nav_layout.setVisibility(View.GONE);
//            schoolLayout.setVisibility(View.VISIBLE);
            nav_layout.setVisibility(View.VISIBLE);
            schoolLayout.setVisibility(View.GONE);
            school_spinner_heading.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase("vol")) {
            nav_layout.setVisibility(View.VISIBLE);
            schoolLayout.setVisibility(View.GONE);
            school_spinner_heading.setVisibility(View.GONE);
        }*/

        if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso)) {
            nav_layout.setVisibility(View.VISIBLE);
            schoolLayout.setVisibility(View.GONE);
            school_spinner_heading.setVisibility(View.GONE);
        } else if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_student)) {
            nav_layout.setVisibility(View.GONE);
            schoolLayout.setVisibility(View.VISIBLE);
            school_spinner_heading.setVisibility(View.VISIBLE);

        } else if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer) || sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_csa)) {
            nav_layout.setVisibility(View.GONE);
            schoolLayout.setVisibility(View.GONE);
            school_spinner_heading.setVisibility(View.GONE);
        }


        sp_school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                school_id = schoolListData.get(position).getSchoolId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }
        });

        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0)
                    gender_id = getResources().getStringArray(R.array.gender)[position];
                else
                    gender_id = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }
        });

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                country_id = countryListData.get(position).getCountryId();
                if (stateListData.size() == 0)
                    getStateList(country_id);
                else
                {
                    if(myProgressDialog.isShowing())
                        myProgressDialog.dismiss();
                }
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

        etFirstName.addTextChangedListener(new TextWatcher() {
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
                        etFirstNameLayout.setError("First name should only contain alphabets");
                    } else {
                        etFirstNameLayout.setError(null);
                    }
                } else {
                    etFirstNameLayout.setError(null);
                }
            }
        });

        etLastName.addTextChangedListener(new TextWatcher() {
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
                        etLastNameLayout.setError("Last name should only contain alphabets");
                    } else {
                        etLastNameLayout.setError(null);
                    }
                } else {
                    etLastNameLayout.setError(null);
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
                        etPostalCodeLayout.setError("Enter valid postal code");
                    } else {
                        etPostalCodeLayout.setError(null);
                    }
                } else {
                    etPostalCodeLayout.setError(null);
                }
            }
        });

       /* etEmail.addTextChangedListener(new TextWatcher() {
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
                    } else {
                        etEmailLayout.setError(null);
                    }
                } else {
                    etEmailLayout.setError(null);
                }
            }
        });
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        setGenderSpinner();
        myProgressDialog.show(getString(R.string.please_wait));
        if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_student))
            getSchoolList();
        else
            getCountryList();
    }

    private void getSchoolList() {

        schoolList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            // myProgressDialog.show(getString(R.string.please_wait));

            schoolModel.getSchoolResponse(context).observe((LifecycleOwner) context, schoolResponse -> {

                if (schoolResponse != null && schoolResponse.getResStatus().equalsIgnoreCase("200")) {
                    schoolListData = schoolResponse.getSchoolData();
                    if (schoolListData.size() > 0) {
                        for (int i = 0; i < schoolListData.size(); i++) {
                            schoolList.add(schoolListData.get(i).getSchoolName());
                        }
                        setSchoolSpinner(schoolList);
                    }
                } else if (schoolResponse != null && schoolResponse.getResStatus().equalsIgnoreCase("401")) {
                    myToast.show(getString(R.string.err_no_school), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
                //myProgressDialog.dismiss();
            });

        } else {
            schoolListData = dbGetSchool.getSchoolList();
            if (schoolListData.size() > 0) {
                for (int i = 0; i < schoolListData.size(); i++) {
                    schoolList.add(schoolListData.get(i).getSchoolName());
                }
                schoolList.add("Select School");
                setSchoolSpinner(schoolList);
            }
        }

        //getProfileData();

        getCountryList();
    }

    private void getCountryList() {

        countryList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            //myProgressDialog.show(getString(R.string.please_wait));
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {
                if (countryResponse != null) {

                    if (countryResponse.getResStatus().equalsIgnoreCase("200") && countryResponse.getResData() != null) {
                        countryListData = countryResponse.getResData();
                        if (countryListData.size() > 0) {
                            for (int i = 0; i < countryListData.size(); i++) {
                                countryList.add(countryListData.get(i).getCountryName());
                            }
                            setCountrySpinner(countryListData);
                        }
                    } else if (countryResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_country_found), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
                //myProgressDialog.dismiss();
            });
        } else {
            countryListData = dbGetCountry.getCountryList();
            if (countryListData.size() > 0) {
                for (int i = 0; i < countryListData.size(); i++) {
                    countryList.add(countryListData.get(i).getCountryName());
                }
                setCountrySpinner(countryListData);
            }
        }
    }

    private void getStateList(String country_id) {

        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            /*if (!myProgressDialog.isShowing())
                myProgressDialog.show(getString(R.string.please_wait));*/
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    if (stateResponse.getResStatus().equalsIgnoreCase("200")) {
                        stateListData = stateResponse.getStateList();
                        if (stateListData.size() > 0) {
                            for (int i = 0; i < stateListData.size(); i++) {
                                stateList.add(stateListData.get(i).getStateName());
                            }
                            setStateSpinner(stateListData);
                        }
                    } else if (stateResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_state_found), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }

                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
                //myProgressDialog.dismiss();
            });
        } else {
            stateListData = dbGetState.getStateList();
            if (stateListData.size() > 0) {
                for (int i = 0; i < stateListData.size(); i++) {
                    stateList.add(stateListData.get(i).getStateName());
                }
                setStateSpinner(stateListData);
            }
        }
    }

    private void setGenderSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.gender));
        sp_gender.setAdapter(adapter);
    }

    private void setSchoolSpinner(List<String> schoolList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, schoolList);
        sp_school.setAdapter(adapter);
    }

    private void setCountrySpinner(List<CountryResponse.Country> countryList) {
        ArrayAdapter<CountryResponse.Country> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
    }

    private void setStateSpinner(List<StateResponse.State> stateList) {
        ArrayAdapter<StateResponse.State> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);
        getProfileData();
        if (flag) {
            flag = false;
            sp_state.setSelection(getStatePosition(), true);
        }
    }

    public void checkValidation() {
        if (etFirstName.getText() == null || etFirstName.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_first_name), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etFirstName.getText().toString().isEmpty()) {
            if (!Utility.isValidName(etFirstName.getText().toString())) {
                myToast.show(getString(R.string.err_enter_first_name), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etLastName.getText() == null || etLastName.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_last_name), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etLastName.getText().toString().isEmpty()) {
            if (!Utility.isValidName(etLastName.getText().toString())) {
                myToast.show(getString(R.string.err_enter_last_name), Toast.LENGTH_SHORT, false);
                return;
            }
        }
       /* if (etEmail.getText() == null || etEmail.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_email), Toast.LENGTH_SHORT, false);
            return;
        } *//*else if (!etEmail.getText().toString().isEmpty()) {
            if (!Utility.isValidEmail(etEmail.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                return;
            }
        }
*//*
        if (etPhone.getText() == null || etPhone.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_phone_number), Toast.LENGTH_SHORT, false);
            return;
        } *//*else if (!etPhone.getText().toString().isEmpty()) {
            if (!Utility.isValidPhoneNumber(etPhone.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT, false);
                return;
            }
        }*//*
        if (country_id == null || country_id.isEmpty()) {
            myToast.show(getString(R.string.err_select_country), Toast.LENGTH_SHORT, false);
        }
        if (state_id == null || state_id.isEmpty()) {
            myToast.show(getString(R.string.err_select_state), Toast.LENGTH_SHORT, false);
            return;
        }
        if (etCity.getText() == null || etCity.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_city), Toast.LENGTH_SHORT, false);
            return;
        }
        if (etPostalCode.getText() == null || etPostalCode.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_postal_code), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etPostalCode.getText().toString().isEmpty()) {
            if (!Utility.isValidPostalCode(etPostalCode.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_postal), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etAddress.getText() == null || etAddress.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_address), Toast.LENGTH_SHORT, false);
            return;
        }*/
        if (!sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso))
            if (etDob.getText() == null || etDob.getText().toString().isEmpty()) {
                myToast.show(getString(R.string.err_enter_dob), Toast.LENGTH_SHORT, false);
                return;
            }
//        if (gender_id == null || gender_id.isEmpty()) {
//            myToast.show(getString(R.string.err_select_gender), Toast.LENGTH_SHORT, false);
//            return;
//        }

        if (!error) {

            myProgressDialog.show(getString(R.string.please_wait));

            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
            updateProfileRequest.setUserType(sharedPref.getUserType());
            updateProfileRequest.setUserDevice(Utility.getDeviceId(context));
            updateProfileRequest.setUserId(sharedPref.getUserId());
            updateProfileRequest.setUserFName(etFirstName.getText().toString());
            updateProfileRequest.setUserLName(etLastName.getText().toString());
            updateProfileRequest.setUserCountry(country_id);
            updateProfileRequest.setUserState(state_id);
            updateProfileRequest.setUserCity(etCity.getText().toString());
            updateProfileRequest.setUserZipcode(etPostalCode.getText().toString());
            updateProfileRequest.setUserAddress(etAddress.getText().toString());
            updateProfileRequest.setUserDob(etDob.getText().toString().trim());

            if (gender_id.isEmpty())
                updateProfileRequest.setUserGender("");
            else
                updateProfileRequest.setUserGender(String.valueOf(gender_id.charAt(0)));


            if (Utility.isNetworkAvailable(context)) {
                updateProfileViewModel.updateProfileResponse(updateProfileRequest).observe((LifecycleOwner) context, updateProfileResponse ->
                {
                    if (updateProfileResponse != null) {
                        if (updateProfileResponse.getResStatus().equalsIgnoreCase("200")) {

                            /* GetProfileResponse.TargetData profile = new GetProfileResponse.TargetData();  *//*new GetProfileResponse.TargetData();*//**//*dbGetProfile.getProfile().get(0)*//*;  // Save Offline
                            profile.setUserType(sharedPref.getUserType());
                            profile.setUserDevice(Utility.getDeviceId(context));
                            profile.setUserId(sharedPref.getUserId());
                            profile.setUserFName(etFirstName.getText().toString());
                            profile.setUserLName(etLastName.getText().toString());
                            profile.setUserCountry(country_id);
                            profile.setUserCountryName(countryListData.get(getCountryPosition()).getCountryName());
                            profile.setUserState(state_id);
                            profile.setUserStateName(stateListData.get(getStatePosition()).getStateName());
                            profile.setUserCity(etCity.getText().toString());
                            profile.setUserZipcode(etPostalCode.getText().toString());
                            profile.setUserAddress(etAddress.getText().toString());
                            profile.setUserDob(etDob.getText().toString().trim());
                            profile.setUserGender(String.valueOf(gender_id.charAt(0)));
                            profile.setUserEmail(etEmail.getText().toString());
                            profile.setUserPhone(etPhone.getText().toString());
                            profile.setSynced(true);
                            new DBSaveProfile(context, profile).execute();*/

                            sharedPref.setFirstName(etFirstName.getText().toString());
                            sharedPref.setLastName(etLastName.getText().toString());

                            if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_student) || sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer)) {
                                myToast.show(getString(R.string.profile_updated), Toast.LENGTH_SHORT, true);

                                Intent intent1 = new Intent(context, VolunteerDashboardActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            } else if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso)) {

                                Intent intent1 = new Intent(context, CsoRegisterStep_2Activity.class);
                                //intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent1.putExtra("model", getProfileResponse);
                                intent1.putExtra("action", "update");
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            } else {
                                myToast.show(getString(R.string.profile_updated), Toast.LENGTH_SHORT, true);
                                Intent intent1 = new Intent(context, CsoDashboardActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }

                        } else if (updateProfileResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();
                });
            } else {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

                //myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

             /*   GetProfileResponse.TargetData profile = dbGetProfile.getProfile().get(0);
                profile.setUserType(sharedPref.getUserType());
                profile.setUserDevice(Utility.getDeviceId(context));
                profile.setUserId(sharedPref.getUserId());
                profile.setUserFName(etFirstName.getText().toString());
                profile.setUserLName(etLastName.getText().toString());
                profile.setUserCountry(country_id);
                profile.setUserCountryName(countryListData.get(getCountryPosition()).getCountryName());
                profile.setUserState(state_id);
                profile.setUserStateName(stateListData.get(getStatePosition()).getStateName());
                profile.setUserCity(etCity.getText().toString());
                profile.setUserZipcode(etPostalCode.getText().toString());
                profile.setUserAddress(etAddress.getText().toString());
                profile.setUserDob(etDob.getText().toString().trim());
                profile.setUserGender(String.valueOf(gender_id.charAt(0)));
                profile.setSynced(false);
                new DBSaveProfile(context,profile).execute();
                myToast.show(getString(R.string.profile_updated), Toast.LENGTH_SHORT, true);*/


              /*  Intent intent1 = new Intent(context, VolunteerDashboardActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/

            }
        } else {
            myToast.show(getString(R.string.err_invalid_data), Toast.LENGTH_SHORT, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:

                if (Utility.isNetworkAvailable(context))
                    checkValidation();
                else {

                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

                  /*  if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso)) {
                        Intent intent1 = new Intent(context, CsoRegisterStep_2Activity.class);
                        intent1.putExtra("model", getProfileResponse);
                        intent1.putExtra("action", "update");
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else {
                        Intent intent1 = new Intent(context, VolunteerDashboardActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }*/
                }

                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.clear:

                if (Utility.isNetworkAvailable(context)) {
                    if (getProfileResponse.getResData() != null) {

                        sharedPref.setUserType(getProfileResponse.getResData().getUserType().toUpperCase());

                        setSchoolSpinnerValue();

                        etFirstName.setText(getProfileResponse.getResData().getUserFName());
                        etLastName.setText(getProfileResponse.getResData().getUserLName());
                        //etEmail.setText(getProfileResponse.getShiftDetail().getUserEmail());
                        //etPhone.setText(getProfileResponse.getShiftDetail().getUserPhone());
                        sp_country.setSelection(getCountryPosition(), true);
                        sp_state.setSelection(getStatePosition(), true);
                        etCity.setText(getProfileResponse.getResData().getUserCity());
                        etPostalCode.setText(getProfileResponse.getResData().getUserZipcode());
                        etAddress.setText(getProfileResponse.getResData().getUserAddress());
                        etDob.setText(getProfileResponse.getResData().getUserDob());
                        if (getProfileResponse.getResData().getUserGender().equalsIgnoreCase("m"))
                            sp_gender.setSelection(1, true);
                        else if (getProfileResponse.getResData().getUserGender().equalsIgnoreCase("f"))
                            sp_gender.setSelection(2, true);
                        else
                            sp_gender.setSelection(3, true);

                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                } else {
                    if (dbGetProfile.getProfile().get(0) != null) {
                        sharedPref.setUserType(dbGetProfile.getProfile().get(0).getUserType().toUpperCase());

                        setSchoolSpinnerValue();

                        etFirstName.setText(dbGetProfile.getProfile().get(0).getUserFName());
                        etLastName.setText(dbGetProfile.getProfile().get(0).getUserLName());
                        //etEmail.setText(getProfileResponse.getShiftDetail().getUserEmail());
                        //etPhone.setText(getProfileResponse.getShiftDetail().getUserPhone());
                        sp_country.setSelection(getCountryPosition(), true);
                        sp_state.setSelection(getStatePosition(), true);
                        etCity.setText(dbGetProfile.getProfile().get(0).getUserCity());
                        etPostalCode.setText(dbGetProfile.getProfile().get(0).getUserZipcode());
                        etAddress.setText(dbGetProfile.getProfile().get(0).getUserAddress());
                        etDob.setText(dbGetProfile.getProfile().get(0).getUserDob());
                        if (dbGetProfile.getProfile().get(0).getUserGender().equalsIgnoreCase("m"))
                            sp_gender.setSelection(1, true);
                        else if (dbGetProfile.getProfile().get(0).getUserGender().equalsIgnoreCase("f"))
                            sp_gender.setSelection(2, true);
                        else
                            sp_gender.setSelection(3, true);

                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                }
                break;
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        etDob.setText(Utility.formatDateFull(myCalendar.getTime()));
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            switch (v.getId()) {
                case R.id.etDob:
                    myCalendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    int month = myCalendar.get(Calendar.DAY_OF_MONTH) + 1;
                    String string_date = myCalendar.get(Calendar.MONTH) + "-" + month + "-" + myCalendar.get(Calendar.YEAR);

//                    try {
//                        Date d = Constants.ff.parse(string_date);
//                        long milliseconds = d.getTime();
//                        datePickerDialog.getDatePicker().setMaxDate(milliseconds);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                    break;
            }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void getProfileData() {
        if (Utility.isNetworkAvailable(context)) {
            // myProgressDialog.show(getString(R.string.please_wait));
            getProfileViewModel.getProfileResponse(new GetProfileRequest(sharedPref.getUserId()/*"S789123"*//*"C20190409Q9l4hzL3916"*//*sharedPref.getUserId()*/)).observe((LifecycleOwner) context, getProfileResponse ->
            {
                flag = true;
                try {
                    if (getProfileResponse != null) {
                        if (getProfileResponse.getResStatus().equalsIgnoreCase("200") && getProfileResponse.getResData() != null) {

                            this.getProfileResponse = getProfileResponse;

                            if (getProfileResponse.getResData() != null)
                                new DBSaveProfile(context, getProfileResponse.getResData()).execute();

                            sharedPref.setUserType(getProfileResponse.getResData().getUserType().toUpperCase());

                            setSchoolSpinnerValue();

                            etFirstName.setText(getProfileResponse.getResData().getUserFName());
                            etLastName.setText(getProfileResponse.getResData().getUserLName());
                            etEmail.setText(getProfileResponse.getResData().getUserEmail());
                            //etPhone.setText(getProfileResponse.getResData().getUserPhone());
                            etPhone.setText(Utility.getFormattedPhoneNumber(getProfileResponse.getResData().getUserPhone()));
                            sp_country.setSelection(getCountryPosition(), true);
                            etCity.setText(getProfileResponse.getResData().getUserCity());
                            etPostalCode.setText(getProfileResponse.getResData().getUserZipcode());
                            etAddress.setText(getProfileResponse.getResData().getUserAddress());
                            etDob.setText(getProfileResponse.getResData().getUserDob());

                            sp_country.setSelection(getCountryPosition(), true);
                            sp_state.setSelection(getStatePosition(), true);

                            if (getProfileResponse.getResData().getUserGender().equalsIgnoreCase("m"))
                                sp_gender.setSelection(1, true);
                            else if (getProfileResponse.getResData().getUserGender().equalsIgnoreCase("f"))
                                sp_gender.setSelection(2, true);
                            else
                                sp_gender.setSelection(0, true);
                            //getCountryList();
                        } else if (getProfileResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(getString(R.string.err_no_profile_found), Toast.LENGTH_SHORT, false);

                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });


        } else {
            if (dbGetProfile.getProfile() != null && dbGetProfile.getProfile().size() > 0) {
                sharedPref.setUserType(dbGetProfile.getProfile().get(0).getUserType().toUpperCase());

                setSchoolSpinnerValue();

                etFirstName.setText(dbGetProfile.getProfile().get(0).getUserFName());
                etLastName.setText(dbGetProfile.getProfile().get(0).getUserLName());
                etEmail.setText(dbGetProfile.getProfile().get(0).getUserEmail());
                etPhone.setText(dbGetProfile.getProfile().get(0).getUserPhone());
                sp_country.setSelection(getCountryPosition(), true);
                sp_state.setSelection(getStatePosition(), true);
                etCity.setText(dbGetProfile.getProfile().get(0).getUserCity());
                etPostalCode.setText(dbGetProfile.getProfile().get(0).getUserZipcode());
                etAddress.setText(dbGetProfile.getProfile().get(0).getUserAddress());
                etDob.setText(dbGetProfile.getProfile().get(0).getUserDob());
                if (dbGetProfile.getProfile().get(0).getUserGender().equalsIgnoreCase("m"))
                    sp_gender.setSelection(1, true);
                else if (dbGetProfile.getProfile().get(0).getUserGender().equalsIgnoreCase("f"))
                    sp_gender.setSelection(2, true);
                else
                    sp_gender.setSelection(3, true);
            }
        }


        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    myProgressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void setSchoolSpinnerValue() {

        if (getSchoolPosition() != -1)
            sp_school.setSelection(getSchoolPosition());
    }

    public int getSchoolPosition() {

        int position = -1;
        if (Utility.isNetworkAvailable(context)) {
            for (int i = 0; i < schoolListData.size(); i++) {
                if (schoolListData.get(i).getSchoolId().equalsIgnoreCase(getProfileResponse.getResData().getSchoolId()) || schoolListData.get(i).getSchoolName().equalsIgnoreCase(getProfileResponse.getResData().getSchoolId())) {
                    position = i;

                }
            }
        } else {
            for (int i = 0; i < schoolListData.size(); i++) {
                if (schoolListData.get(i).getSchoolId().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getSchoolId()) || schoolListData.get(i).getSchoolName().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getSchoolId())) {
                    position = i;

                }
            }
        }
        return position;
    }

    public int getCountryPosition() {
        int position = -1;
        if (Utility.isNetworkAvailable(context)) {
            for (int i = 0; i < countryListData.size(); i++) {
                if (countryListData.get(i).getCountryName().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry())) {
                    position = i;
                }
            }
        } else {
            for (int i = 0; i < countryListData.size(); i++) {
                if (countryListData.get(i).getCountryName().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserCountry())) {
                    position = i;
                }
            }
        }
        return position;
    }

    public int getStatePosition() {
        int position = -1;
        if (Utility.isNetworkAvailable(context)) {
            for (int i = 0; i < stateListData.size(); i++) {
                if (stateListData.get(i).getStateName().equalsIgnoreCase(getProfileResponse.getResData().getUserState()) || stateListData.get(i).getStateId().equalsIgnoreCase(getProfileResponse.getResData().getUserState())) {
                    position = i;
                }
            }
        } else {
            for (int i = 0; i < stateListData.size(); i++) {
                if (stateListData.get(i).getStateName().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserState()) || stateListData.get(i).getStateId().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserState())) {
                    position = i;
                }
            }
        }
        return position;
    }
}
