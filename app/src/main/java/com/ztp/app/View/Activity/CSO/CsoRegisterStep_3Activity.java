package com.ztp.app.View.Activity.CSO;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetProfile;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_3;
import com.ztp.app.Data.Remote.Model.Response.CSOQuestionResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.ValidateOTPActivity;
import com.ztp.app.Viewmodel.CSOQuestionViewModel;
import com.ztp.app.Viewmodel.CsoRegisterStep_3ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CsoRegisterStep_3Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    MyButton register, clear;
    RadioButton yes1, yes2, yes3, yes4, no1, no2, no3, no4;
    //MyTextInputEditText etServices, etTarget, etPerson, etTimePeriod, etVolunteer;
    CsoRegisterStep_3ViewModel csoRegisterStep_3ViewModel;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    Context context;
    SharedPref sharedPref;
    ScrollView scrollView;
    String action;
    GetProfileResponse getProfileResponse;
    DBGetProfile dbGetProfile;
    Spinner sp_answer1, sp_answer2, sp_answer3;
    CSOQuestionViewModel csoQuestionViewModel;
    List<CSOQuestionResponse.Question1Data> question1Data = new ArrayList<>();
    List<CSOQuestionResponse.Question2Data> question2Data = new ArrayList<>();
    List<CSOQuestionResponse.Question3Data> question3Data = new ArrayList<>();
    List<String> answer1List = new ArrayList<>();
    List<String> answer2List = new ArrayList<>();
    List<String> answer3List = new ArrayList<>();
    String ans1, ans2, ans3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        sharedPref = SharedPref.getInstance(context);
        dbGetProfile = new DBGetProfile(context, sharedPref.getUserId());
        setContentView(R.layout.activity_cso_register_step_3);
        init();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        csoQuestionViewModel = ViewModelProviders.of((FragmentActivity) context).get(CSOQuestionViewModel.class);

        setDefaultAnswer1Dropdown();
        setDefaultAnswer2Dropdown();
        setDefaultAnswer3Dropdown();

        if (Utility.isNetworkAvailable(context))
            hitAnswerAPI();
        else
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);


        sp_answer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    ans1 = answer1List.get(position);
                } else {
                    ans1 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        sp_answer2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    ans2 = answer2List.get(position);
                } else {
                    ans2 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        sp_answer3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    ans3 = answer3List.get(position);
                } else {
                    ans3 = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    private void hitAnswerAPI() {

        myProgressDialog.show(getString(R.string.please_wait));
        csoQuestionViewModel.getCSOQuestionResponse(context).observe((LifecycleOwner) context, csoQuestionResponse -> {

            if (csoQuestionResponse != null) {
                if (csoQuestionResponse.getResStatus().equalsIgnoreCase("200")) {

                    question1Data = csoQuestionResponse.getResData().getQuestion1Data();
                    question2Data = csoQuestionResponse.getResData().getQuestion2Data();
                    question3Data = csoQuestionResponse.getResData().getQuestion3Data();

                    if (question1Data != null && question1Data.size() > 0) {
                        for (int i = 0; i < question1Data.size(); i++) {
                            answer1List.add(i + 1, question1Data.get(i).getAnswerDetails());
                        }
                        setSpinner(answer1List, sp_answer1);
                    }

                    if (question2Data != null && question2Data.size() > 0) {
                        for (int i = 0; i < question2Data.size(); i++) {
                            answer2List.add(i + 1, question2Data.get(i).getAnswerDetails());
                        }
                        setSpinner(answer2List, sp_answer2);
                    }

                    if (question3Data != null && question3Data.size() > 0) {
                        for (int i = 0; i < question3Data.size(); i++) {
                            answer3List.add(i + 1, question3Data.get(i).getAnswerDetails());
                        }
                        setSpinner(answer3List, sp_answer3);
                    }

                    if (getIntent() != null) {
                        action = getIntent().getStringExtra("action");
                        if (action != null && action.equalsIgnoreCase("update")) {
                            getProfileResponse = (GetProfileResponse) getIntent().getSerializableExtra("model");
                            fillData();
                        } else {
                            register.setText(getString(R.string.register));
                            yes1.setChecked(true);
                            yes2.setChecked(true);
                            yes3.setChecked(true);
                            yes4.setChecked(true);
                        }
                    }
                } else if (csoQuestionResponse.getResStatus().equalsIgnoreCase("401")) {
                    myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                } else {
                    myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }
            myProgressDialog.dismiss();
        });
    }

    private void setDefaultAnswer1Dropdown() {
        answer1List.add(0, "Select answer");
        setSpinner(answer1List, sp_answer1);
    }

    private void setDefaultAnswer2Dropdown() {
        answer2List.add(0, "Select answer");
        setSpinner(answer2List, sp_answer2);
    }

    private void setDefaultAnswer3Dropdown() {
        answer3List.add(0, "Select answer");
        setSpinner(answer3List, sp_answer3);
    }

    private void setSpinner(List<String> ansList, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, ansList);
        spinner.setAdapter(adapter);
    }

    private void fillData() {
        if (Utility.isNetworkAvailable(context)) {
            register.setText(getString(R.string.update));
            if (getProfileResponse.getResData() != null) {
                if (getProfileResponse.getResData().getOrgNonProfit().equalsIgnoreCase("1"))
                    yes1.setChecked(true);
                else
                    no1.setChecked(true);

                sp_answer1.setSelection(getDropdownPosition(answer1List, getProfileResponse.getResData().getOrgService()), true);
                sp_answer2.setSelection(getDropdownPosition(answer2List, getProfileResponse.getResData().getOrgTarget()), true);
                sp_answer3.setSelection(getDropdownPosition(answer3List, getProfileResponse.getResData().getOrgVolunteerReq()), true);

                ans1 = getProfileResponse.getResData().getOrgService();
                ans2 = getProfileResponse.getResData().getOrgTarget();
                ans3 = getProfileResponse.getResData().getOrgVolunteerReq();

                /*etServices.setText(getProfileResponse.getResData().getOrgService());
                etTarget.setText(getProfileResponse.getResData().getOrgTarget());
                etPerson.setText(getProfileResponse.getResData().getOrgProfile());
                etTimePeriod.setText(getProfileResponse.getResData().getOrgMinTime());
                etVolunteer.setText(getProfileResponse.getResData().getOrgVolunteerReq());*/

                if (getProfileResponse.getResData().getOrgVoluteerPolice().equalsIgnoreCase("1"))
                    yes2.setChecked(true);
                else
                    no2.setChecked(true);

                if (getProfileResponse.getResData().getOrgEasyAccess().equalsIgnoreCase("1"))
                    yes3.setChecked(true);
                else
                    no3.setChecked(true);

                if (getProfileResponse.getResData().getOrg501C3().equalsIgnoreCase("1"))
                    yes4.setChecked(true);
                else
                    no4.setChecked(true);
            }
        } else {
            register.setText(getString(R.string.done));
            if (dbGetProfile != null && dbGetProfile.getProfile().size() > 0) {
                if (dbGetProfile.getProfile().get(0).getOrgNonProfit().equalsIgnoreCase("1"))
                    yes1.setChecked(true);
                else
                    no1.setChecked(true);

                sp_answer1.setSelection(getDropdownPosition(answer1List, dbGetProfile.getProfile().get(0).getOrgService()), true);
                sp_answer2.setSelection(getDropdownPosition(answer2List, dbGetProfile.getProfile().get(0).getOrgTarget()), true);
                sp_answer3.setSelection(getDropdownPosition(answer3List, dbGetProfile.getProfile().get(0).getOrgVolunteerReq()), true);

                ans1 = dbGetProfile.getProfile().get(0).getOrgService();
                ans2 = dbGetProfile.getProfile().get(0).getOrgTarget();
                ans3 = dbGetProfile.getProfile().get(0).getOrgVolunteerReq();

               /* etServices.setText(dbGetProfile.getProfile().get(0).getOrgService());
                etTarget.setText(dbGetProfile.getProfile().get(0).getOrgTarget());
                etPerson.setText(dbGetProfile.getProfile().get(0).getOrgProfile());
                etTimePeriod.setText(dbGetProfile.getProfile().get(0).getOrgMinTime());
                etVolunteer.setText(dbGetProfile.getProfile().get(0).getOrgVolunteerReq());*/


                if (dbGetProfile.getProfile().get(0).getOrgVoluteerPolice().equalsIgnoreCase("1"))
                    yes2.setChecked(true);
                else
                    no2.setChecked(true);

                if (dbGetProfile.getProfile().get(0).getOrgEasyAccess().equalsIgnoreCase("1"))
                    yes3.setChecked(true);
                else
                    no3.setChecked(true);

                if (dbGetProfile.getProfile().get(0).getOrg501C3().equalsIgnoreCase("1"))
                    yes4.setChecked(true);
                else
                    no4.setChecked(true);
            }
        }
    }

    private void init() {
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        csoRegisterStep_3ViewModel = ViewModelProviders.of(this).get(CsoRegisterStep_3ViewModel.class);
        scrollView = findViewById(R.id.scrollView);
        back = findViewById(R.id.back);
        register = findViewById(R.id.register);
        clear = findViewById(R.id.clear);
        yes1 = findViewById(R.id.yes1);
        yes2 = findViewById(R.id.yes2);
        yes3 = findViewById(R.id.yes3);
        yes4 = findViewById(R.id.yes4);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);

        sp_answer1 = findViewById(R.id.sp_answer1);
        sp_answer2 = findViewById(R.id.sp_answer2);
        sp_answer3 = findViewById(R.id.sp_answer3);

        yes1.setOnClickListener(this);
        yes2.setOnClickListener(this);
        yes3.setOnClickListener(this);
        yes4.setOnClickListener(this);
        no1.setOnClickListener(this);
        no2.setOnClickListener(this);
        no3.setOnClickListener(this);
        no4.setOnClickListener(this);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.exit_registration_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        LinearLayout yes = view.findViewById(R.id.yes);
        LinearLayout no = view.findViewById(R.id.no);

        yes.setOnClickListener(v -> {
            dialog.dismiss();

            if (sharedPref.getIsLogin()) {
                startActivity(new Intent(context, CsoDashboardActivity.class));
                finish();

            } else {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.clear:

                if (action != null && action.equalsIgnoreCase("update")) {
                    yes1.setChecked(false);
                    yes2.setChecked(false);
                    yes3.setChecked(false);
                    yes4.setChecked(false);
                    no1.setChecked(false);
                    no2.setChecked(false);
                    no3.setChecked(false);
                    no4.setChecked(false);
                    fillData();
                } else {
                    yes1.setChecked(false);
                    yes2.setChecked(false);
                    yes3.setChecked(false);
                    yes4.setChecked(false);
                    no1.setChecked(true);
                    no2.setChecked(true);
                    no3.setChecked(true);
                    no4.setChecked(true);

                    sp_answer1.setSelection(0, true);
                    sp_answer2.setSelection(0, true);
                    sp_answer3.setSelection(0, true);

                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);

                break;
            case R.id.register:

                if (Utility.isNetworkAvailable(context)) {


                   /* if (yes1.isChecked() || no1.isChecked()) {

                        if (etServices.getText() != null && !etServices.getText().toString().isEmpty()) {

                            if (etTarget.getText() != null && !etTarget.getText().toString().isEmpty()) {

                                if (etPerson.getText() != null && !etPerson.getText().toString().isEmpty()) {

                                    if (etTimePeriod.getText() != null && !etTimePeriod.getText().toString().isEmpty()) {

                                        if (etVolunteer.getText() != null && !etVolunteer.getText().toString().isEmpty()) {

                                            if (yes2.isChecked() || no2.isChecked()) {

                                                if (yes3.isChecked() || no3.isChecked()) {*/


                   if(ans1.isEmpty() || ans2.isEmpty()  || ans3.isEmpty())
                   {
                       myToast.show("Answer(s) not selected", Toast.LENGTH_SHORT, false);
                       return;
                   }
                    myProgressDialog.show(getString(R.string.please_wait));
                    CsoRegisterRequestStep_3 csoRegisterRequest_3 = new CsoRegisterRequestStep_3();
                    csoRegisterRequest_3.setUserId(sharedPref.getUserId());
                    csoRegisterRequest_3.setUserType(sharedPref.getUserType());
                    csoRegisterRequest_3.setUserDevice(Utility.getDeviceId(context));
                    if (yes1.isChecked())
                        csoRegisterRequest_3.setOrgNonprofit("1");
                    else if (no1.isChecked())
                        csoRegisterRequest_3.setOrgNonprofit("0");
                    if (yes2.isChecked())
                        csoRegisterRequest_3.setOrgVolunteerPolice("1");
                    else if (no2.isChecked())
                        csoRegisterRequest_3.setOrgVolunteerPolice("0");
                    if (yes3.isChecked())
                        csoRegisterRequest_3.setOrgEasyAccess("1");
                    else if (no3.isChecked())
                        csoRegisterRequest_3.setOrgEasyAccess("0");
                    if (yes4.isChecked())
                        csoRegisterRequest_3.setOrg_501C3("1");
                    else if (no4.isChecked())
                        csoRegisterRequest_3.setOrg_501C3("0");

                    csoRegisterRequest_3.setOrgService(ans1);
                    csoRegisterRequest_3.setOrgTarget(ans2);
                    csoRegisterRequest_3.setOrgVolunteerReq(ans3);
                    csoRegisterRequest_3.setOrgMinTime("");
                    csoRegisterRequest_3.setOrgVolunteerNum("");

                    Log.i("", "" + new Gson().toJson(csoRegisterRequest_3));

                    /* csoRegisterRequest_3.setOrgService(etServices.getText().toString());
                       csoRegisterRequest_3.setOrgTarget(etTarget.getText().toString());
                       csoRegisterRequest_3.setOrgVolunteerReq(etVolunteer.getText().toString());
                       csoRegisterRequest_3.setOrgMinTime(etTimePeriod.getText().toString());
                       csoRegisterRequest_3.setOrgVolunteerNum(etVolunteer.getText().toString());*/

                        csoRegisterStep_3ViewModel.getRegisterResponse(csoRegisterRequest_3).observe((LifecycleOwner) context, registerResponse -> {

                            if (registerResponse != null) {
                                Log.i("", "" + new Gson().toJson(registerResponse));
                                if (registerResponse.getResStatus().equalsIgnoreCase("200")) {
                                    sharedPref.setOtp(registerResponse.getResData().getPhoneOtp());

                                    if (action != null && action.equalsIgnoreCase("update")) {
                                        myToast.show(getString(R.string.profile_updated), Toast.LENGTH_SHORT, true);
                                        Intent intent1 = new Intent(context, CsoDashboardActivity.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    } else {
                                        Intent intent1 = new Intent(context, ValidateOTPActivity.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }

                                } else if (registerResponse.getResStatus().equalsIgnoreCase("401")) {
                                    myToast.show(getString(R.string.err_org_inf_regisitration_failed), Toast.LENGTH_SHORT, false);
                                }
                            } else {
                                myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                            }

                            myProgressDialog.dismiss();

                        });


                                               /* } else {
                                                    myToast.show(getString(R.string.check_your_choice), Toast.LENGTH_SHORT, false);
                                                }
                                            } else {
                                                myToast.show(getString(R.string.check_your_choice), Toast.LENGTH_SHORT, false);
                                            }
                                        } else {
                                            myToast.show(getString(R.string.err_enter_volunterr_inf), Toast.LENGTH_SHORT, false);
                                        }

                                    } else {
                                        myToast.show(getString(R.string.err_enter_time_period), Toast.LENGTH_SHORT, false);
                                    }
                                } else {
                                    myToast.show(getString(R.string.err_describe_person), Toast.LENGTH_SHORT, false);
                                }
                            } else {
                                myToast.show(getString(R.string.err_enter_your_target), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.err_type_service), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.check_your_choice), Toast.LENGTH_SHORT, false);
                    }*/
                } else {
                    /*Intent intent1 = new Intent(context, CsoDashboardActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/

                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                }

                break;
            case R.id.yes1:
                yes1.setChecked(true);
                no1.setChecked(false);
                break;
            case R.id.no1:
                yes1.setChecked(false);
                no1.setChecked(true);
                break;
            case R.id.yes2:
                yes2.setChecked(true);
                no2.setChecked(false);
                break;
            case R.id.no2:
                yes2.setChecked(false);
                no2.setChecked(true);
                break;
            case R.id.yes3:
                yes3.setChecked(true);
                no3.setChecked(false);
                break;
            case R.id.no3:
                yes3.setChecked(false);
                no3.setChecked(true);
                break;
            case R.id.yes4:
                yes4.setChecked(true);
                no4.setChecked(false);
                break;
            case R.id.no4:
                yes4.setChecked(false);
                no4.setChecked(true);
                break;
        }
    }

    public int getDropdownPosition(List<String> listData, String compare) {
        int position = -1;
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).equalsIgnoreCase(compare)) {
                position = i;
                break;
            }
        }
        return position;
    }
}
