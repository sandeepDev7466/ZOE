package com.ztp.app.View.Activity.CSO;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.raywenderlich.android.validatetor.ValidateTor;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCountry;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetState;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_1;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.CsoRegisterStep_1ViewModel;
import com.ztp.app.Viewmodel.SchoolViewModel;
import com.ztp.app.Viewmodel.StateViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CsoRegisterStep_1Activity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    Context context;
    MyButton next, clear;
    Spinner country, state, gender;
    MyTextInputLayout etPasswordLayout, etConfirmPasswordLayout, etEmailLayout, etPhoneLayout, etPostalCodeLayout, etFirstNameLayout, etLastNameLayout;
    MyTextInputEditText etFirstName, etLastName, etEmail, etPhone, etCity, etPostalCode, etDob, etPassword, etConfirmPassword, etAddress;
    MyToast myToast;
    SharedPref sharedPref;
    boolean theme;
    CountryViewModel countryModel;
    StateViewModel stateModel;
    SchoolViewModel schoolModel;
    CsoRegisterStep_1ViewModel csoRegisterStep_1ViewModel;
    List<CountryResponse.Country> countryListData = new ArrayList<>();
    List<StateResponse.State> stateListData = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    String country_id, state_id, gender_id;
    Calendar myCalendar;
    ImageView back;
    ScrollView scrollView;
    ValidateTor validate;
    DBGetCountry dbGetCountry;
    DBGetState dbGetState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso_register_step_1);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        init();
        setThemePassword();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        context = this;
        validate = new ValidateTor();
        myCalendar = Calendar.getInstance();
        dbGetCountry = new DBGetCountry(context);
        dbGetState = new DBGetState(context);
        myProgressDialog = new MyProgressDialog(context);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        schoolModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        csoRegisterStep_1ViewModel = ViewModelProviders.of(this).get(CsoRegisterStep_1ViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        myToast = new MyToast(context);
        scrollView = findViewById(R.id.scrollView);
        next = findViewById(R.id.next);
        clear = findViewById(R.id.clear);
        back = findViewById(R.id.back);

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

        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);

        next.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        etDob.setOnTouchListener(this);

        setGenderSpinner();
        getCountryList();

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    gender_id = getResources().getStringArray(R.array.gender)[position];
                } else if (position == 0) {
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
                if (position > 0)
                    state_id = stateListData.get(position-1).getStateId();
                else
                    state_id = "";
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
                        etFirstNameLayout.setError(getString(R.string.err_first_name));
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
                        etLastNameLayout.setError(getString(R.string.err_last_name));
                    } else {
                        etLastNameLayout.setError(null);
                    }
                } else {
                    etLastNameLayout.setError(null);
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
                    } else {
                        etEmailLayout.setError(null);
                    }
                } else {
                    etEmailLayout.setError(null);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        } else {
            etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

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
                    } else {
                        etPhoneLayout.setError(null);
                    }
                } else {
                    etPhoneLayout.setError(null);
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
                    } else {
                        etPostalCodeLayout.setError(null);
                    }
                } else {
                    etPostalCodeLayout.setError(null);
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

                    } else {
                        etPasswordLayout.setError(getString(R.string.err_password));
                    }
                } else {
                    etPasswordLayout.setError(null);
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

                    } else {
                        etConfirmPasswordLayout.setError(getString(R.string.err_password));
                    }
                } else {
                    etConfirmPasswordLayout.setError(null);
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

    private void getCountryList() {

        countryList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                if (countryResponse != null) {


                    if (countryResponse.getResStatus().equalsIgnoreCase("200") && countryResponse.getResData() != null) {
                        countryListData = countryResponse.getResData();
                        for (int i = 0; i < countryListData.size(); i++) {
                            countryList.add(countryListData.get(i).getCountryName());
                        }

                        setCountrySpinner(countryList);
                    } else if (countryResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_country_found), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }

                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
                // myProgressDialog.dismiss();

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

    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            // myProgressDialog.show(getString(R.string.please_wait));
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {

                    if (stateResponse.getResStatus().equalsIgnoreCase("200")) {
                        stateListData = stateResponse.getStateList();
                        stateList.add(0, getString(R.string.select_state));
                        for (int i = 0; i < stateListData.size(); i++) {
                            stateList.add(i + 1, stateListData.get(i).getStateName());
                        }

                        setStateSpinner(stateList);
                    } else if (stateResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_state_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            stateListData = dbGetState.getStateList();
            stateList.add(0, getString(R.string.select_state));
            if (stateListData.size() > 0) {
                for (int i = 0; i < stateListData.size(); i++) {
                    stateList.add(i+1,stateListData.get(i).getStateName());
                }
                setStateSpinner(stateList);
            }
        }
    }

    private void setGenderSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.gender));
        gender.setAdapter(adapter);
    }

    private void setCountrySpinner(List<String> countryList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        country.setAdapter(adapter);
    }

    private void setStateSpinner(List<String> stateList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
        state.setAdapter(adapter);
    }

    public void checkValidation() {
        if (etEmail.getText() == null || etEmail.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_email), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etEmail.getText().toString().isEmpty()) {
            if (!Utility.isValidEmail(etEmail.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etFirstName.getText() == null || etFirstName.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_first_name), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etFirstName.getText().toString().isEmpty()) {
            if (!Utility.isValidName(etFirstName.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_first_name), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etLastName.getText() == null || etLastName.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_last_name), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etLastName.getText().toString().isEmpty()) {
            if (!Utility.isValidName(etLastName.getText().toString())) {
                myToast.show(getString(R.string.err_enter_valid_last_name), Toast.LENGTH_SHORT, false);
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
       /* if (country_id == null || country_id.isEmpty()) {
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
       /* if (etDob.getText() == null || etDob.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_dob), Toast.LENGTH_SHORT, false);
            return;
        }*/
        /*if (gender_id == null || gender_id.isEmpty()) {
            myToast.show(getString(R.string.err_select_gender), Toast.LENGTH_SHORT, false);
            return;
        }*/
        if (etPassword.getText() == null || etPassword.getText().toString().isEmpty()) {
            myToast.show(getString(R.string.err_enter_password), Toast.LENGTH_SHORT, false);
            return;
        } else if (!etPassword.getText().toString().isEmpty()) {
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
        } else if (!etConfirmPassword.getText().toString().isEmpty()) {
            if (!validate.isAtleastLength(etPassword.getText().toString().toString(), 8)
                    && !validate.hasAtleastOneDigit(etPassword.getText().toString().toString())
                    && !validate.hasAtleastOneUppercaseCharacter(etPassword.getText().toString().toString())
                    && !validate.hasAtleastOneSpecialCharacter(etPassword.getText().toString().toString())) {
                myToast.show(getString(R.string.err_password), Toast.LENGTH_SHORT, false);
                return;
            }
        }
        if (etPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {

            sharedPref.setUserType(sharedPref.getUserType().toUpperCase());

            CsoRegisterRequestStep_1 csoRegisterRequest_1 = new CsoRegisterRequestStep_1();
            csoRegisterRequest_1.setUserType(sharedPref.getUserType());
            csoRegisterRequest_1.setUserDevice(Utility.getDeviceId(context));
            csoRegisterRequest_1.setSchoolId("");
            csoRegisterRequest_1.setUserFName(etFirstName.getText().toString());
            csoRegisterRequest_1.setUserLName(etLastName.getText().toString());
            csoRegisterRequest_1.setUserEmail(etEmail.getText().toString());
            csoRegisterRequest_1.setUserPhone(etPhone.getText().toString());
            csoRegisterRequest_1.setUserCountry(country_id);
            csoRegisterRequest_1.setUserState(state_id);
            csoRegisterRequest_1.setUserCity(etCity.getText().toString());
            csoRegisterRequest_1.setUserZipcode(etPostalCode.getText().toString());
            csoRegisterRequest_1.setUserAddress(etAddress.getText().toString());
            csoRegisterRequest_1.setUserDob(etDob.getText().toString());
            if (gender_id.isEmpty())
                csoRegisterRequest_1.setUserGender("");
            else
                csoRegisterRequest_1.setUserGender(String.valueOf(gender_id.charAt(0)));
            csoRegisterRequest_1.setUserPass(etPassword.getText().toString());

            if (Utility.isNetworkAvailable(context)) {
                myProgressDialog.show(getString(R.string.please_wait));
                csoRegisterStep_1ViewModel.getCSORegisterResponse(csoRegisterRequest_1).observe((LifecycleOwner) context, registerResponse -> {

                    if (registerResponse != null) {
                        if (registerResponse.getResStatus().equalsIgnoreCase("200") && registerResponse.getResData() != null) {
                            sharedPref.setUserId(registerResponse.getResData().getUserId());
                            sharedPref.setEmail(etEmail.getText().toString());

                            Intent intent1 = new Intent(context, CsoRegisterStep_2Activity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent1.putExtra("action", "register");
                            startActivity(intent1);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        } else if (registerResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(registerResponse.getResMessage(), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }

                    myProgressDialog.dismiss();

                });
            } else {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }

        } else {
            myToast.show(getString(R.string.err_password_confirmpassword_not_match), Toast.LENGTH_SHORT, false);
            etConfirmPassword.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:

                checkValidation();
                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.clear:
                etFirstName.setText("");
                etLastName.setText("");
                etEmail.setText("");
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

        etDob.setText(Utility.formatDateFull(myCalendar.getTime()));

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
}

