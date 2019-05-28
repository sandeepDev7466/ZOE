package com.ztp.app.View.Activity.CSO;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_3;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.ValidateOTPActivity;
import com.ztp.app.Viewmodel.CsoRegisterStep_3ViewModel;

public class CsoRegisterStep_3Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    MyButton register, clear;
    RadioButton yes1, yes2, yes3, yes4, no1, no2, no3, no4;
    MyTextInputEditText etServices, etTarget, etPerson, etTimePeriod, etVolunteer;
    CsoRegisterStep_3ViewModel csoRegisterStep_3ViewModel;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    Context context;
    SharedPref sharedPref;
    ScrollView scrollView;
    String action;
    GetProfileResponse getProfileResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso_register_step_3);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        init();

        if (getIntent() != null) {
            action = getIntent().getStringExtra("action");
            if (action.equalsIgnoreCase("update")) {
                register.setText(getString(R.string.update));
                getProfileResponse = (GetProfileResponse) getIntent().getSerializableExtra("model");
                fillData();
            } else {
                register.setText(getString(R.string.register));
            }
        }

    }

    private void fillData() {
        if(getProfileResponse.getResData()!=null) {
            if (getProfileResponse.getResData().getOrgNonProfit().equalsIgnoreCase("1"))
                yes1.setChecked(true);
            else
                no1.setChecked(true);

            etServices.setText(getProfileResponse.getResData().getOrgService());
            etTarget.setText(getProfileResponse.getResData().getOrgTarget());
            etPerson.setText(getProfileResponse.getResData().getOrgProfile());
            etTimePeriod.setText(getProfileResponse.getResData().getOrgMinTime());
            etVolunteer.setText(getProfileResponse.getResData().getOrgVolunteerReq());
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
    }

    private void init() {
        context = this;
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
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

        etServices = findViewById(R.id.etServices);
        etTarget = findViewById(R.id.etTarget);
        etPerson = findViewById(R.id.etPerson);
        etTimePeriod = findViewById(R.id.etTimePeriod);
        etVolunteer = findViewById(R.id.etVolunteer);

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
        View view = LayoutInflater.from(context).inflate(R.layout.exit_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        LinearLayout yes = view.findViewById(R.id.yes);
        LinearLayout no = view.findViewById(R.id.no);

        yes.setOnClickListener(v -> {
            dialog.dismiss();

            if(sharedPref.getIsLogin())
            {
                startActivity(new Intent(context,CsoDashboardActivity.class));
                finish();

            }
            else
            {
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

                if (action.equalsIgnoreCase("update")) {
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
                    no1.setChecked(false);
                    no2.setChecked(false);
                    no3.setChecked(false);
                    no4.setChecked(false);
                    etServices.setText("");
                    etTarget.setText("");
                    etPerson.setText("");
                    etTimePeriod.setText("");
                    etVolunteer.setText("");
                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);

                break;
            case R.id.register:

                if (yes1.isChecked() || no1.isChecked()) {
                    if (etServices.getText() != null && !etServices.getText().toString().isEmpty()) {

                        if (etTarget.getText() != null && !etTarget.getText().toString().isEmpty()) {

                            if (etPerson.getText() != null && !etPerson.getText().toString().isEmpty()) {

                                if (etTimePeriod.getText() != null && !etTimePeriod.getText().toString().isEmpty()) {

                                    if (etVolunteer.getText() != null && !etVolunteer.getText().toString().isEmpty()) {

                                        if (yes2.isChecked() || no2.isChecked()) {
                                            if (yes3.isChecked() || no3.isChecked()) {

                                                myProgressDialog.show(getString(R.string.please_wait));

                                                CsoRegisterRequestStep_3 csoRegisterRequest_3 = new CsoRegisterRequestStep_3();

                                                csoRegisterRequest_3.setUserId(sharedPref.getUserId());
                                                csoRegisterRequest_3.setUserType(sharedPref.getUserType());
                                                csoRegisterRequest_3.setUserDevice(Utility.getDeviceId(context));
                                                if (yes1.isChecked())
                                                    csoRegisterRequest_3.setOrgNonprofit("1");
                                                else if (no1.isChecked())
                                                    csoRegisterRequest_3.setOrgNonprofit("0");
                                                csoRegisterRequest_3.setOrgService(etServices.getText().toString());
                                                csoRegisterRequest_3.setOrgTarget(etTarget.getText().toString());
                                                csoRegisterRequest_3.setOrgVolunteerReq(etVolunteer.getText().toString());
                                                csoRegisterRequest_3.setOrgMinTime(etTimePeriod.getText().toString());
                                                csoRegisterRequest_3.setOrgVolunteerNum(etVolunteer.getText().toString());
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

                                                if(Utility.isNetworkAvailable(context)) {
                                                    csoRegisterStep_3ViewModel.getRegisterResponse(csoRegisterRequest_3).observe((LifecycleOwner) context, registerResponse -> {

                                                        if(registerResponse != null) {
                                                            if (registerResponse.getResStatus().equalsIgnoreCase("200")) {
                                                                sharedPref.setOtp(registerResponse.getResData().getPhoneOtp());

                                                                if (action.equalsIgnoreCase("update")) {
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

                                                            } else if(registerResponse.getResStatus().equalsIgnoreCase("401")) {
                                                                myToast.show(getString(R.string.err_org_inf_regisitration_failed), Toast.LENGTH_SHORT, false);
                                                            }
                                                        }
                                                        else
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
}
