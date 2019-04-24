package com.ztp.app.View.Activity.Common;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_2Activity;
import com.ztp.app.View.Activity.Student.StudentDashboardActivity;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.GetProfileViewModel;
import com.ztp.app.Viewmodel.SchoolViewModel;
import com.ztp.app.Viewmodel.StateViewModel;
import com.ztp.app.Viewmodel.UpdateProfileViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    MyButton update, clear;
    String type;
    Context context;
    Spinner sp_type, sp_school, sp_country, sp_state, sp_gender;
    MyTextInputLayout etPasswordLayout, etConfirmPasswordLayout;
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
    String country_id, state_id, type_id, school_id, gender_id;
    ImageView back;
    ScrollView scrollView;
    Calendar myCalendar;
    GetProfileViewModel getProfileViewModel;
    UpdateProfileViewModel updateProfileViewModel;
    GetProfileResponse getProfileResponse;
    LinearLayout nav_layout, schoolLayout;
    LiveData<List<CountryResponse.Country>> countryLiveDataList;
    RoomDB roomDB;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        context = this;
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        if (getIntent() != null)
            type = getIntent().getStringExtra("type");
        roomDB = RoomDB.getInstance(context);
        myCalendar = Calendar.getInstance();
        myProgressDialog = new MyProgressDialog(context);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        schoolModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        getProfileViewModel = ViewModelProviders.of(this).get(GetProfileViewModel.class);
        updateProfileViewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        myToast = new MyToast(context);
        scrollView = findViewById(R.id.scrollView);
        update = findViewById(R.id.update);
        clear = findViewById(R.id.clear);
        back = findViewById(R.id.back);

        sp_type = findViewById(R.id.type);
        sp_school = findViewById(R.id.school);
        sp_country = findViewById(R.id.country);
        sp_state = findViewById(R.id.state);
        sp_gender = findViewById(R.id.gender);

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
        nav_layout = findViewById(R.id.nav_layout);
        schoolLayout = findViewById(R.id.schoolLayout);

        update.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
        etDob.setOnTouchListener(this);

        setTypeSpinner();
        setGenderSpinner();
        getSchoolList();
        getCountryList();
        getProfileData();

        etPhone.setEnabled(false);
        etEmail.setEnabled(false);
        sp_type.setEnabled(false);
        sp_school.setEnabled(false);

        if (type.equalsIgnoreCase("cso")) {
            nav_layout.setVisibility(View.VISIBLE);
            schoolLayout.setVisibility(View.GONE);
        }

        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    type_id = getResources().getStringArray(R.array.type_all)[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {


            }

        });

        sp_school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        }
    }

 /*   private void getCountryList() {

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

*//*
             countryLiveDataList =roomDB.getCountryDao().getAll();

             countryLiveDataList.observe((LifecycleOwner) context, countryList -> {


             });

             if(countryLiveDataList!=null)
             {
                 for (int i = 0; i < countryListData.size(); i++) {
                     countryList.add(countryListData.get(i).getCountryName());
                 }
                 countryList.add(0, "Select Country");
                 setCountrySpinner(countryList);
             }
             else
             {
                 myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                 countryList.add("Select Country");
                 setCountrySpinner(countryList);
             }*//*
        }

        stateList.add("Select State");
        setStateSpinner(stateList);

    }

    private void getStateList(String country_id) {

        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Please wait...");
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
*/
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
                 setCountrySpinner(countryListData);
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
            myProgressDialog.show("Please wait...");
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    stateListData = stateResponse.getStateList();
                    for (int i = 0; i < stateListData.size(); i++) {
                        stateList.add(stateListData.get(i).getStateName());
                    }
                    stateList.add(0, "Select State");
                    setStateSpinner(stateListData);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setGenderSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.gender));
        sp_gender.setAdapter(adapter);
    }

    private void setTypeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, getResources().getStringArray(R.array.type_all));
        sp_type.setAdapter(adapter);
    }

    private void setSchoolSpinner(List<String> schoolList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, schoolList);
        sp_school.setAdapter(adapter);
    }

    /*private void setCountrySpinner(List<String> countryList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
    }

    private void setStateSpinner(List<String> stateList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);
        if (flag) {
            flag = false;
            sp_state.setSelection(Integer.parseInt(getProfileResponse.getResData().getUserState()), true);
        }
    }
*/

    private void setCountrySpinner(List<CountryResponse.Country> countryList) {
        ArrayAdapter<CountryResponse.Country> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
    }

    private void setStateSpinner(List<StateResponse.State> stateList) {
        ArrayAdapter<StateResponse.State> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);
        if (flag) {
            flag = false;
            sp_state.setSelection(getStatePosition(), true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:

                if (etFirstName.getText() != null && !etFirstName.getText().toString().isEmpty()) {
                    if (etLastName.getText() != null && !etLastName.getText().toString().isEmpty()) {
                        if (country_id != null && !country_id.isEmpty()) {
                            if (state_id != null && !state_id.isEmpty()) {
                                if (etCity.getText() != null && !etCity.getText().toString().isEmpty()) {
                                    if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {
                                        if (etAddress.getText() != null && !etAddress.getText().toString().isEmpty()) {
                                            if (etDob.getText() != null && !etDob.getText().toString().isEmpty()) {
                                                if (gender_id != null && !gender_id.isEmpty()) {

                                                    myProgressDialog.show("Updating...");
                                                    sharedPref.setUserType(String.valueOf(type_id.substring(0, 3).toUpperCase()));

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

                                                    updateProfileRequest.setUserGender(String.valueOf(gender_id.charAt(0)));

                                                    updateProfileViewModel.updateProfileResponse(updateProfileRequest).observe((LifecycleOwner) context, updateProfileResponse ->
                                                    {
                                                        if (updateProfileResponse != null && updateProfileResponse.getResStatus().equalsIgnoreCase("200")) {

                                                            sharedPref.setFirstName(etFirstName.getText().toString());
                                                            sharedPref.setLastName(etLastName.getText().toString());
                                                            if (type.equalsIgnoreCase("stu")) {
                                                                myToast.show("Profile updated", Toast.LENGTH_SHORT, true);

                                                                Intent intent1 = new Intent(context, StudentDashboardActivity.class);
                                                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent1);
                                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                                            } else {
                                                                try {

                                                                    Intent intent1 = new Intent(context, CsoRegisterStep_2Activity.class);
                                                                    //intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    intent1.putExtra("model", getProfileResponse);
                                                                    intent1.putExtra("action", "update");
                                                                    startActivity(intent1);
                                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }

                                                        } else {
                                                            myToast.show("Registration failed", Toast.LENGTH_SHORT, false);
                                                        }

                                                        myProgressDialog.dismiss();
                                                    });

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
                        myToast.show("Enter Last Name", Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show("Enter First Name", Toast.LENGTH_SHORT, false);
                }
                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.clear:
                /*etFirstName.setText("");
                etLastName.setText("");
                sp_country.setSelection(0, true);
                sp_state.setSelection(0, true);
                etCity.setText("");
                etPostalCode.setText("");
                etAddress.setText("");
                etDob.setText("");
                sp_gender.setSelection(0, true);
                country_id = "";
                state_id = "";
                gender_id = "";*/

                if (getProfileResponse.getResData().getUserType().equalsIgnoreCase("stu"))
                    sp_type.setSelection(1);
                else if (getProfileResponse.getResData().getUserType().equalsIgnoreCase("vol"))
                    sp_type.setSelection(2);
                else
                    sp_type.setSelection(3);
                sharedPref.setUserType(getProfileResponse.getResData().getUserType().toLowerCase());

                setSchoolSpinnerValue(getProfileResponse.getResData().getSchoolId());

                etFirstName.setText(getProfileResponse.getResData().getUserFName());
                etLastName.setText(getProfileResponse.getResData().getUserLName());
                etEmail.setText(getProfileResponse.getResData().getUserEmail());
                etPhone.setText(getProfileResponse.getResData().getUserPhone());
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
//        etDob.setText(sdf.format(myCalendar.getTime()));

        etDob.setText(Utility.formatDateFull(myCalendar.getTime()));
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

    private void getProfileData() {
        myProgressDialog.show("Fetching user data...");
        getProfileViewModel.getProfileResponse(new GetProfileRequest(sharedPref.getUserId()/*"S789123"*//*"C20190409Q9l4hzL3916"*//*sharedPref.getUserId()*/)).observe((LifecycleOwner) context, getProfileResponse ->
        {
            flag = true;
            try {

                if (getProfileResponse != null && getProfileResponse.getResStatus().equalsIgnoreCase("200")) {

                    this.getProfileResponse = getProfileResponse;

                    if (getProfileResponse.getResData().getUserType().equalsIgnoreCase("stu"))
                        sp_type.setSelection(1);
                    else if (getProfileResponse.getResData().getUserType().equalsIgnoreCase("vol"))
                        sp_type.setSelection(2);
                    else
                        sp_type.setSelection(3);
                    sharedPref.setUserType(getProfileResponse.getResData().getUserType().toLowerCase());

                    setSchoolSpinnerValue(getProfileResponse.getResData().getSchoolId());

                    etFirstName.setText(getProfileResponse.getResData().getUserFName());
                    etLastName.setText(getProfileResponse.getResData().getUserLName());
                    etEmail.setText(getProfileResponse.getResData().getUserEmail());
                    etPhone.setText(getProfileResponse.getResData().getUserPhone());
                    sp_country.setSelection(getCountryPosition(), true);
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

                } else {
                    myToast.show("No profile data found", Toast.LENGTH_SHORT, false);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            myProgressDialog.dismiss();
        });

    }

    private void setSchoolSpinnerValue(String school_id) {
        int i;
        for (i = 0; i < schoolListData.size(); i++) {
            if (school_id.equalsIgnoreCase(schoolListData.get(i).getSchoolId())) {
                break;
            }
        }

        sp_school.setSelection(i);
    }
    public int getCountryPosition() {
        int position = -1;
        for (int i = 0; i < countryListData.size(); i++) {
            if (countryListData.get(i).getCountryName().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getStatePosition() {
        int position = -1;
        for (int i = 0; i < stateListData.size(); i++) {
            if (stateListData.get(i).getStateName().equalsIgnoreCase(getProfileResponse.getResData().getUserState()) || stateListData.get(i).getStateId().equalsIgnoreCase(getProfileResponse.getResData().getUserState())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }
}
