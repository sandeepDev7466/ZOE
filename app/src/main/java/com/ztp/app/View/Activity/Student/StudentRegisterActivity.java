package com.ztp.app.View.Activity.Student;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.google.gson.Gson;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Request.StudentRegisterRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.ValidateOTPActivity;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.SchoolViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.StudentRegisterViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class StudentRegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    Context context;
    MyButton register, clear;
    Spinner school, country, state, gender;
    MyTextInputLayout etPasswordLayout, etConfirmPasswordLayout, etEmailLayout, etPhoneLayout, etPostalCodeLayout, etFirstNameLayout, etLastNameLayout;
    MyTextInputEditText etFirstName, etLastName, etEmail, etPhone, etCity, etPostalCode, etDob, etPassword, etConfirmPassword, etAddress;
    MyToast myToast;
    SharedPref sharedPref;
    boolean theme;
    CountryViewModel countryModel;
    StateViewModel stateModel;
    SchoolViewModel schoolModel;
    StudentRegisterViewModel studentRegisterViewModel;
    List<CountryResponse.Country> countryListData = new ArrayList<>();
    List<StateResponse.State> stateListData = new ArrayList<>();
    List<SchoolResponse.School> schoolListData = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    List<String> schoolList = new ArrayList<>();
    String country_id, state_id, type_id, school_id, gender_id;
    Calendar myCalendar;
    ImageView back;
    ScrollView scrollView;
    ToggleSwitch typeSwitch;
    String type;
    LinearLayout schoolLayout;
    ValidateTor validate;
    boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        init();
        setThemePassword();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        validate = new ValidateTor();
        myCalendar = Calendar.getInstance();
        myProgressDialog = new MyProgressDialog(context);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        schoolModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        studentRegisterViewModel = ViewModelProviders.of(this).get(StudentRegisterViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        myToast = new MyToast(context);
        scrollView = findViewById(R.id.scrollView);
        register = findViewById(R.id.register);
        clear = findViewById(R.id.clear);
        back = findViewById(R.id.back);

        typeSwitch = findViewById(R.id.type);
        typeSwitch.setToggleWidth(350f);
        typeSwitch.setActiveBgColor(getResources().getColor(R.color.blue_light));


        school = findViewById(R.id.school);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        gender = findViewById(R.id.gender);

        etPasswordLayout = findViewById(R.id.etPasswordLayout);
        etConfirmPasswordLayout = findViewById(R.id.etConfirmPasswordLayout);
        etEmailLayout = findViewById(R.id.etEmailLayout);
        etPhoneLayout = findViewById(R.id.etPhoneLayout);
        etPostalCodeLayout = findViewById(R.id.etPostalCodeLayout);
        etFirstNameLayout = findViewById(R.id.etFirstNameLayout);
        etLastNameLayout = findViewById(R.id.etLastNameLayout);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
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
        schoolLayout = findViewById(R.id.schoolLayout);

        register.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        etDob.setOnTouchListener(this);

        setGenderSpinner();
        getCountryList();
        getSchoolList();


        ArrayList<String> labels = new ArrayList<>();
        labels.add(getString(R.string.STUDENT));
        labels.add(getString(R.string.VOLUNTEER));
        typeSwitch.setLabels(labels);
        type = "stu";

        typeSwitch.setOnToggleSwitchChangeListener((position, isChecked) -> {

            if (position == 0) {
                type = "stu";
                schoolLayout.setVisibility(View.VISIBLE);
                //clearData();
            } else if (position == 1) {
                type = "vol";
                schoolLayout.setVisibility(View.GONE);
                //clearData();
            }

        });

        school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    school_id = schoolListData.get(position - 1).getSchoolId();
                }
                else if(position==0)
                {
                    school_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    gender_id = getResources().getStringArray(R.array.gender)[position];
                }
                else if(position==0)
                {
                    gender_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    if (!validate.isAlpha(s.toString())) {
                        etFirstNameLayout.setError(getString(R.string.err_first_name));
                        error = true;
                    } else {
                        etFirstNameLayout.setError(null);
                        error = false;
                    }
                } else {
                    etFirstNameLayout.setError(null);
                    error = false;
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
                    if (!validate.isAlpha(s.toString())) {
                        etLastNameLayout.setError(getString(R.string.err_last_name));
                        error = true;
                    } else {
                        etLastNameLayout.setError(null);
                        error = false;
                    }
                } else {
                    etLastNameLayout.setError(null);
                    error = false;
                }
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
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
                        error = true;
                    } else {
                        etEmailLayout.setError(null);
                        error = false;
                    }
                } else {
                    etEmailLayout.setError(null);
                    error = false;
                }
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
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
                        error = true;
                    } else {
                        etPhoneLayout.setError(null);
                        error = false;
                    }
                } else {
                    etPhoneLayout.setError(null);
                    error = false;
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
                        error = true;
                    } else {
                        etPostalCodeLayout.setError(null);
                        error = false;
                    }
                } else {
                    etPostalCodeLayout.setError(null);
                    error = false;
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {

                    if (validate.isAtleastLength(s.toString(), 8)
                            && validate.hasAtleastOneDigit(s.toString())
                            && validate.hasAtleastOneUppercaseCharacter(s.toString())
                            && validate.hasAtleastOneSpecialCharacter(s.toString())) {

                        etPasswordLayout.setError(null);
                        error = false;

                    } else {
                        etPasswordLayout.setError(getString(R.string.err_password));
                        error = true;
                    }
                } else {
                    etPasswordLayout.setError(null);
                    error = false;
                }
            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {

                    if (validate.isAtleastLength(s.toString(), 8)
                            && validate.hasAtleastOneDigit(s.toString())
                            && validate.hasAtleastOneUppercaseCharacter(s.toString())
                            && validate.hasAtleastOneSpecialCharacter(s.toString())) {

                        etConfirmPasswordLayout.setError(null);
                        error = false;

                    } else {
                        etConfirmPasswordLayout.setError(getString(R.string.err_password));
                        error = true;
                    }
                } else {
                    etConfirmPasswordLayout.setError(null);
                    error = false;
                }
            }
        });
    }

    private void setThemePassword() {

        int theme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (theme == Configuration.UI_MODE_NIGHT_NO) {
            etPasswordLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            etConfirmPasswordLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        } else if (theme == Configuration.UI_MODE_NIGHT_YES) {
            etPasswordLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            etConfirmPasswordLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }
    }

    private void getSchoolList() {

        schoolList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            schoolModel.getSchoolResponse().observe((LifecycleOwner) context, schoolResponse -> {

                if (schoolResponse != null && schoolResponse.getResStatus().equalsIgnoreCase("200")) {
                    schoolListData = schoolResponse.getSchoolData();
                    if (schoolListData.size() > 0) {
                        for (int i = 0; i < schoolListData.size(); i++) {
                            schoolList.add(schoolListData.get(i).getSchoolName());
                        }
                        schoolList.add(0, getString(R.string.select_school));
                        setSchoolSpinner(schoolList);

                    } else {
                        myToast.show(getString(R.string.err_no_school), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            schoolList.add("Select School");
            setCountrySpinner(countryList);
        }
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

    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    if(stateResponse.getResStatus().equalsIgnoreCase("200")) {
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
                    }else
                    {
                        myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT, false);
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

    private void setGenderSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.gender));
        gender.setAdapter(adapter);
    }

    private void setSchoolSpinner(List<String> schoolList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, schoolList);
        school.setAdapter(adapter);
    }

    private void setCountrySpinner(List<String> countryList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        country.setAdapter(adapter);
    }

    private void setStateSpinner(List<String> stateList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
        state.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:

                if (type.equalsIgnoreCase("stu")) {
                    if (school_id != null && !school_id.isEmpty()) {
                        sharedPref.setUserType("stu");
                        checkStudentVal();

                    } else {
                        myToast.show(getString(R.string.select_school), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    sharedPref.setUserType("vol");
                    checkStudentVal();
                }


                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.clear:
                clearData();
                break;
        }
    }

    public void clearData() {
        getCountryList();
        getSchoolList();
        school.setSelection(0, true);
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etPhone.setText("");
        country.setSelection(0, true);
        state.setSelection(0, true);
        etCity.setText("");
        etPostalCode.setText("");
        etAddress.setText("");
        etDob.setText("");
        gender.setSelection(0, true);
        etPassword.setText("");
        etConfirmPassword.setText("");
        type_id = "";
        school_id = "";
        country_id = "";
        state_id = "";
        gender_id = "";
        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    public void checkStudentVal() {
        if (etFirstName.getText() == null || etFirstName.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_first_name), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etFirstName.getText().toString().isEmpty()) {
            if (!validate.isAlpha(etFirstName.getText().toString())) {
                myToast.show(getString(R.string.err_enter_first_name), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etLastName.getText() == null || etLastName.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_last_name), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etLastName.getText().toString().isEmpty()) {
            if (!validate.isAlpha(etLastName.getText().toString())) {
                myToast.show(getString(R.string.err_enter_last_name), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etEmail.getText() == null || etEmail.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_email), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etEmail.getText().toString().isEmpty()) {
            if (!Utility.isValidEmail(etEmail.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                return;
            }
        }

        if (etPhone.getText() == null || etPhone.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_phone_number), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etPhone.getText().toString().isEmpty()) {
            if (!Utility.isValidPhoneNumber(etPhone.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (country_id == null || country_id.isEmpty()) {
            myToast.show(getString(R.string.err_select_country), Toast.LENGTH_SHORT, false);
            return;
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
        }
        if (etDob.getText() == null || etDob.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_dob), Toast.LENGTH_SHORT, false);
            return;
        }
        if (gender_id == null || gender_id.isEmpty()) {
            myToast.show(getString(R.string.err_select_gender), Toast.LENGTH_SHORT, false);
            return;
        }
        if (etPassword.getText() == null || etPassword.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_password), Toast.LENGTH_SHORT, false);
            return;
        }else if(!etPassword.getText().toString().isEmpty()) {
            if (!validate.isAtleastLength(etPassword.getText().toString().toString(), 8)
                    && !validate.hasAtleastOneDigit(etPassword.getText().toString().toString())
                    && !validate.hasAtleastOneUppercaseCharacter(etPassword.getText().toString().toString())
                    && !validate.hasAtleastOneSpecialCharacter(etPassword.getText().toString().toString())) {
                myToast.show(getString(R.string.err_password), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etConfirmPassword.getText() == null || etConfirmPassword.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_confirm_password), Toast.LENGTH_SHORT, false);
            return;
        }else if(!etConfirmPassword.getText().toString().isEmpty()){
            if (!validate.isAtleastLength(etPassword.getText().toString().toString(), 8)
                    && !validate.hasAtleastOneDigit(etPassword.getText().toString().toString())
                    && !validate.hasAtleastOneUppercaseCharacter(etPassword.getText().toString().toString())
                    && !validate.hasAtleastOneSpecialCharacter(etPassword.getText().toString().toString())) {
                myToast.show(getString(R.string.err_password), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
            if (!error) {

                myProgressDialog.show(getString(R.string.please_wait));
                sharedPref.setUserType(sharedPref.getUserType().toUpperCase());

                StudentRegisterRequest studentRegisterRequest = new StudentRegisterRequest();
                studentRegisterRequest.setUserType(sharedPref.getUserType());
                studentRegisterRequest.setUserDevice(Utility.getDeviceId(context));
                studentRegisterRequest.setSchoolId(school_id);
                studentRegisterRequest.setUserFName(etFirstName.getText().toString());
                studentRegisterRequest.setUserLName(etLastName.getText().toString());
                studentRegisterRequest.setUserEmail(etEmail.getText().toString());
                studentRegisterRequest.setUserPhone(etPhone.getText().toString());
                studentRegisterRequest.setUserCountry(country_id);
                studentRegisterRequest.setUserState(state_id);
                studentRegisterRequest.setUserCity(etCity.getText().toString());
                studentRegisterRequest.setUserZipcode(etPostalCode.getText().toString());
                studentRegisterRequest.setUserAddress(etAddress.getText().toString());
                studentRegisterRequest.setUserDob(etDob.getText().toString());
                studentRegisterRequest.setUserGender(String.valueOf(gender_id.charAt(0)));
                studentRegisterRequest.setUserPass(etPassword.getText().toString());

                Log.i("REQUEST", "" + new Gson().toJson(studentRegisterRequest));

                if(Utility.isNetworkAvailable(context)) {
                    studentRegisterViewModel.getStudentRegisterResponse(studentRegisterRequest).observe((LifecycleOwner) context, registerResponse -> {

                        if(registerResponse != null) {
                            if (registerResponse.getResStatus().equalsIgnoreCase("200")) {

                                Log.i("RESPONSE", "" + new Gson().toJson(registerResponse));

                                sharedPref.setUserId(registerResponse.getResData().getUserId());
                                sharedPref.setOtp(registerResponse.getResData().getPhoneOtp());
                                sharedPref.setEmail(etEmail.getText().toString());

                                Intent intent1 = new Intent(context, ValidateOTPActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            } else {
                                myToast.show(registerResponse.getResMessage(), Toast.LENGTH_SHORT, false);
                            }
                        }else
                        {
                            myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        }

                        myProgressDialog.dismiss();

                    });
                }
                else
                {
                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.err_invalid_data), Toast.LENGTH_SHORT, false);
            }
        } else {
            myToast.show(getString(R.string.err_password_confirmpassword_not_match), Toast.LENGTH_SHORT, false);
            etConfirmPassword.setText("");
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

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        etDob.setText(sdf.format(myCalendar.getTime()));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            switch (v.getId()) {
                case R.id.etDob:
                    myCalendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    int year = myCalendar.get(Calendar.YEAR) - 18;
                    String string_date = myCalendar.get(Calendar.MONTH) + "-" + myCalendar.get(Calendar.DAY_OF_MONTH) + "-" + year;
                    try {
                        Date d = Constants.ff.parse(string_date);
                        long milliseconds = d.getTime();
                        datePickerDialog.getDatePicker().setMaxDate(milliseconds);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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
}

