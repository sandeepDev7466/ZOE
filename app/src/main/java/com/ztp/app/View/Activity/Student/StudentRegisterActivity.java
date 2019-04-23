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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.ValidateOTPActivity;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.SchoolViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.StudentRegisterViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StudentRegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    Context context;
    MyButton register, clear;
    Spinner type, school, country, state, gender;
    MyTextInputLayout etPasswordLayout, etConfirmPasswordLayout;
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

        type = findViewById(R.id.type);
        school = findViewById(R.id.school);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        gender = findViewById(R.id.gender);

        etPasswordLayout = findViewById(R.id.etPasswordLayout);
        etConfirmPasswordLayout = findViewById(R.id.etConfirmPasswordLayout);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        }else {
            etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);

        register.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        etDob.setOnTouchListener(this);

        setTypeSpinner();
        setGenderSpinner();
        getCountryList();
        getSchoolList();

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    type_id = getResources().getStringArray(R.array.type)[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });

        school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    school_id = schoolListData.get(position - 1).getSchoolId();
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            myProgressDialog.show("Please wait...");
            schoolModel.getSchoolResponse().observe((LifecycleOwner) context, schoolResponse -> {

                if (schoolResponse != null) {
                    schoolListData = schoolResponse.getSchoolData();
                    for (int i = 0; i < schoolListData.size(); i++) {
                        schoolList.add(schoolListData.get(i).getSchoolName());
                    }
                    schoolList.add(0, "Select School");
                    setSchoolSpinner(schoolList);
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

    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Fetching states...");
            stateModel.getStateResponse(context,new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
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

    private void setGenderSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.gender));
        gender.setAdapter(adapter);
    }

    private void setTypeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.type));
        type.setAdapter(adapter);
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

                if (type_id != null && !type_id.isEmpty()) {
                    if (school_id != null && !school_id.isEmpty()) {
                        if (etFirstName.getText() != null && !etFirstName.getText().toString().isEmpty()) {
                            if (etLastName.getText() != null && !etLastName.getText().toString().isEmpty()) {
                                if (etEmail.getText() != null && !etEmail.getText().toString().isEmpty()) {
                                    if (etPhone.getText() != null && !etPhone.getText().toString().isEmpty()) {
                                        if (country_id != null && !country_id.isEmpty()) {
                                            if (state_id != null && !state_id.isEmpty()) {
                                                if (etCity.getText() != null && !etCity.getText().toString().isEmpty()) {
                                                    if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {
                                                        if (etAddress.getText() != null && !etAddress.getText().toString().isEmpty()) {
                                                            if (etDob.getText() != null && !etDob.getText().toString().isEmpty()) {
                                                                if (gender_id != null && !gender_id.isEmpty()) {

                                                                    if (etPassword.getText() != null && !etPassword.getText().toString().isEmpty()) {
                                                                        if (etConfirmPassword.getText() != null && !etConfirmPassword.getText().toString().isEmpty()) {

                                                                            if (etPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {

                                                                                myProgressDialog.show("Registering...");
                                                                                sharedPref.setUserType(String.valueOf(type_id.substring(0, 3).toUpperCase()));

                                                                                StudentRegisterRequest studentRegisterRequest = new StudentRegisterRequest();
                                                                                studentRegisterRequest.setUserType(String.valueOf(type_id.substring(0, 3).toUpperCase()));
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

                                                                                studentRegisterViewModel.getRegisterResponse(studentRegisterRequest).observe((LifecycleOwner) context, registerResponse -> {

                                                                                    if (registerResponse != null && registerResponse.getResStatus().equalsIgnoreCase("200")) {
                                                                                        sharedPref.setUserId(registerResponse.getResData().getUserId());
                                                                                        sharedPref.setOtp(registerResponse.getResData().getPhoneOtp());
                                                                                        sharedPref.setEmail(etEmail.getText().toString());

                                                                                        Intent intent1 = new Intent(context, ValidateOTPActivity.class);
                                                                                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                        startActivity(intent1);
                                                                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                                                                                    } else {
                                                                                        myToast.show("Registration failed", Toast.LENGTH_SHORT, false);
                                                                                    }

                                                                                    myProgressDialog.dismiss();

                                                                                });

                                                                            } else {
                                                                                myToast.show("Password & Confirm Password not match", Toast.LENGTH_SHORT, false);
                                                                                etConfirmPassword.setText("");
                                                                            }
                                                                        } else {
                                                                            myToast.show("Enter Confirm Password", Toast.LENGTH_SHORT, false);
                                                                        }
                                                                    } else {
                                                                        myToast.show("Enter Password", Toast.LENGTH_SHORT, false);
                                                                    }
                                                                } else {
                                                                    myToast.show("Select Gender", Toast.LENGTH_SHORT, false);
                                                                }
                                                            } else {
                                                                myToast.show("Enter Date of Birth", Toast.LENGTH_SHORT, false);
                                                            }
                                                        } else {
                                                            myToast.show("Enter Address", Toast.LENGTH_SHORT, false);
                                                        }
                                                    } else {
                                                        myToast.show("Enter Postal Code", Toast.LENGTH_SHORT, false);
                                                    }
                                                } else {
                                                    myToast.show("Enter City", Toast.LENGTH_SHORT, false);
                                                }
                                            } else {
                                                myToast.show("Select State", Toast.LENGTH_SHORT, false);
                                            }
                                        } else {
                                            myToast.show("Select Country", Toast.LENGTH_SHORT, false);
                                        }
                                    } else {
                                        myToast.show("Enter Phone", Toast.LENGTH_SHORT, false);
                                    }
                                } else {
                                    myToast.show("Enter Email", Toast.LENGTH_SHORT, false);
                                }
                            } else {
                                myToast.show("Enter Last Name", Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show("Enter First Name", Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show("Select School", Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show("Select Identity", Toast.LENGTH_SHORT, false);
                }
                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.clear:
                type.setSelection(0, true);
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

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        etDob.setText(sdf.format(myCalendar.getTime()));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            switch (v.getId()) {
                case R.id.etDob:
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
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
}

