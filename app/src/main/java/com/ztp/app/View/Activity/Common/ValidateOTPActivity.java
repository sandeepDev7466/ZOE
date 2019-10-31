package com.ztp.app.View.Activity.Common;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ValidateOtpRequest;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ValidateOtpViewModel;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ValidateOTPActivity extends AppCompatActivity implements View.OnClickListener {

    MyTextView counter, resendOtp;
    LinearLayout countdownLayout;
    MyButton submit, change_phone;
    ImageView back;
    MyTextInputEditText etOtp;
    SharedPref sharedPref;
    Context context;
    MyToast myToast;
    ValidateOtpViewModel validateOtpViewModel;
    MyProgressDialog myProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_otp);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        init();
    }

    private void init() {
        context = this;
        myProgressDialog = new MyProgressDialog(context);
        validateOtpViewModel = ViewModelProviders.of(this).get(ValidateOtpViewModel.class);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        counter = findViewById(R.id.counter);
        resendOtp = findViewById(R.id.resendOtp);
        countdownLayout = findViewById(R.id.countdownLayout);
        submit = findViewById(R.id.submit);
        //change_phone = findViewById(R.id.change_phone);
        back = findViewById(R.id.back);
        etOtp = findViewById(R.id.etOtp);
        etOtp.requestFocus();
        startTimer(5 * 60 * 1000);


        if (getIntent().getStringExtra("otp") != null) {
            etOtp.setText(getIntent().getStringExtra("otp"));
        } else {
            etOtp.setText(sharedPref.getOtp());
        }

        resendOtp.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void startTimer(int time) {
        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                String hms = String.format(Locale.ENGLISH, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                counter.setText(hms);//set text
            }

            public void onFinish() {
                countdownLayout.setVisibility(View.GONE);
                resendOtp.setVisibility(View.VISIBLE);
                startTimer(5 * 60 * 1000);

            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resendOtp:

                countdownLayout.setVisibility(View.VISIBLE);
                resendOtp.setVisibility(View.GONE);

                break;

            case R.id.submit:

                if (etOtp.getText() != null && !etOtp.getText().toString().isEmpty()) {
                    if (Utility.isNetworkAvailable(context)) {
                        myProgressDialog.show(getString(R.string.validating_otp));
                        hitAPI(etOtp.getText().toString());
                    } else {
                        myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.enter_otp), Toast.LENGTH_SHORT, false);
                }

                break;
        }
    }

    private void hitAPI(String Otp) {

        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setPhoneOtp(Otp);
        validateOtpRequest.setUserDevice(Utility.getDeviceId(context));
        validateOtpRequest.setUserType(sharedPref.getUserType());
        validateOtpRequest.setUserId(sharedPref.getUserId());

        Log.i("","" + new Gson().toJson(validateOtpRequest));

        if (Utility.isNetworkAvailable(context)) {
            validateOtpViewModel.getValidateOtpResponse(validateOtpRequest).observe((LifecycleOwner) context, validateOtpResponse -> {
                if (validateOtpResponse != null) {
                    if (validateOtpResponse.getResStatus().equalsIgnoreCase("200")) {
                        Intent intent1 = new Intent(context, LoginActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        //myToast.show(getString(R.string.OTP_verified_successfully_please_login_to_continue), Toast.LENGTH_SHORT, true);
                        myToast.show(validateOtpResponse.getResMessage(), Toast.LENGTH_SHORT, true);
                    } else if (validateOtpResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.otp_validation_failed), Toast.LENGTH_SHORT, false);
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
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();

    }
}
