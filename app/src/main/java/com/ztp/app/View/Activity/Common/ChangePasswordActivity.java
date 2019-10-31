package com.ztp.app.View.Activity.Common;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.raywenderlich.android.validatetor.ValidateTor;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ChangePasswordRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_1;
import com.ztp.app.Data.Remote.Model.Request.ForgotPasswordRequest;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ChangePasswordViewModel;
import com.ztp.app.Viewmodel.ForgotPasswordViewModel;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    MyTextInputLayout etOldPassLayout, etNewPassLayout, etConfirmPassLayout;
    MyTextInputEditText etOldPass, etNewPass, etConfirmPass;
    ValidateTor validate;
    MyButton submit;
    MyToast myToast;
    Context context;
    boolean error = false;
    MyProgressDialog myProgressDialog;
    ChangePasswordViewModel changePasswordViewModel;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        init();
    }

    private void init() {
        context = this;
        validate = new ValidateTor();
        myToast = new MyToast(context);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        etOldPassLayout = findViewById(R.id.etOldPassLayout);
        etNewPassLayout = findViewById(R.id.etNewPassLayout);
        etConfirmPassLayout = findViewById(R.id.etConfirmPassLayout);
        submit = findViewById(R.id.submit);
        myProgressDialog = new MyProgressDialog(context);
        changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        submit.setOnClickListener(v -> {

            if (Utility.isNetworkAvailable(context)) {
                if (etOldPass.getText() != null && !etOldPass.getText().toString().isEmpty()) {
                    if (etNewPass.getText() != null && !etNewPass.getText().toString().isEmpty()) {
                        if (validate.isAtleastLength(etNewPass.getText().toString(), 8)
                                && validate.hasAtleastOneDigit(etNewPass.getText().toString())
                                && validate.hasAtleastOneUppercaseCharacter(etNewPass.getText().toString())
                                && validate.hasAtleastOneSpecialCharacter(etNewPass.getText().toString())) {
                            if (etConfirmPass.getText() != null && !etConfirmPass.getText().toString().isEmpty()) {
                                if (validate.isAtleastLength(etConfirmPass.getText().toString(), 8)
                                        && validate.hasAtleastOneDigit(etConfirmPass.getText().toString())
                                        && validate.hasAtleastOneUppercaseCharacter(etConfirmPass.getText().toString())
                                        && validate.hasAtleastOneSpecialCharacter(etConfirmPass.getText().toString())) {
                                    if (etNewPass.getText().toString().trim().equalsIgnoreCase(etConfirmPass.getText().toString().trim())) {
                                        callAPI();
                                    } else {
                                        myToast.show(getString(R.string.err_password_confirmpassword_not_match), Toast.LENGTH_SHORT, false);
                                    }
                                } else
                                    myToast.show(getString(R.string.err_password), Toast.LENGTH_SHORT, false);
                            } else
                                myToast.show(getString(R.string.err_enter_confirm_password), Toast.LENGTH_SHORT, false);
                        } else
                            myToast.show(getString(R.string.err_password), Toast.LENGTH_SHORT, false);
                    } else
                        myToast.show(getString(R.string.new_password), Toast.LENGTH_SHORT, false);
                } else {
                    myToast.show(getString(R.string.old_password), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }
        });
        etNewPass.addTextChangedListener(new TextWatcher() {
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

                        etNewPassLayout.setError(null);

                    } else {
                        etNewPassLayout.setError(getString(R.string.err_password));
                    }
                } else {
                    etNewPassLayout.setError(null);
                }
            }
        });

        etConfirmPass.addTextChangedListener(new TextWatcher() {
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

                        etConfirmPassLayout.setError(null);

                    } else {
                        etConfirmPassLayout.setError(getString(R.string.err_password));
                    }
                } else {
                    etConfirmPassLayout.setError(null);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void callAPI() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
            changePasswordRequest.setUser_id(sharedPref.getUserId());
            changePasswordRequest.setUser_device(Utility.getDeviceId(context));
            changePasswordRequest.setUser_type(sharedPref.getUserType());
            changePasswordRequest.setUser_pass_old(etOldPass.getText().toString().trim());
            changePasswordRequest.setUser_pass_new(etNewPass.getText().toString().trim());

            changePasswordViewModel.getChangePasswordResponse(changePasswordRequest).observe((LifecycleOwner) context, changePasswordResponse -> {

                if (changePasswordResponse != null && changePasswordResponse.getResStatus().equalsIgnoreCase("200")) {
                    myToast.show(changePasswordResponse.getRes_message(), Toast.LENGTH_SHORT, true);
                    etOldPass.setText("");
                    etNewPass.setText("");
                    etConfirmPass.setText("");
                    finish();
                } else if (changePasswordResponse != null && changePasswordResponse.getResStatus().equalsIgnoreCase("401")) {
                    myToast.show(changePasswordResponse.getRes_message(), Toast.LENGTH_SHORT, false);
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
