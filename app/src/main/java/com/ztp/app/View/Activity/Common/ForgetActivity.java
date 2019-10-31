package com.ztp.app.View.Activity.Common;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.raywenderlich.android.validatetor.ValidateTor;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ForgotPasswordRequest;
import com.ztp.app.Data.Remote.Model.Request.GetProfileRequest;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ForgotPasswordViewModel;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    MyTextInputLayout etEmailLayout;
    MyTextInputEditText etEmail;
    ValidateTor validate;
    MyButton submit;
    MyToast myToast;
    Context context;
    boolean error = false;
    MyProgressDialog myProgressDialog;
    ForgotPasswordViewModel forgotPasswordViewModel;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        init();
    }

    private void init() {
        context = this;
        validate = new ValidateTor();
        myToast = new MyToast(context);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        etEmail = findViewById(R.id.etEmail);
        etEmailLayout = findViewById(R.id.etEmailLayout);
        submit = findViewById(R.id.submit);
        myProgressDialog = new MyProgressDialog(context);
        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        submit.setOnClickListener(v -> {

            if(Utility.isNetworkAvailable(context)) {
                if (etEmail.getText()!=null && !etEmail.getText().toString().isEmpty()) {
                    if(!error)
                    {
                       callAPI();
                    }
                    else
                    {
                        myToast.show(getString(R.string.err_enter_valid_email),Toast.LENGTH_SHORT,false);
                    }

                } else {
                    myToast.show(getString(R.string.err_enter_email),Toast.LENGTH_SHORT,false);
                }
            }else
            {
                myToast.show(getString(R.string.no_internet_connection),Toast.LENGTH_SHORT,false);
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

                if (s != null && s.toString().length() > 0) {
                    if (Utility.isValidEmail(s.toString())) {
                        etEmailLayout.setError(null);
                        error = false;
                    } else {
                        etEmailLayout.setError(getString(R.string.err_enter_valid_email));
                        error = true;
                    }
                } else {
                    etEmailLayout.setError(null);
                    error = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
    private void callAPI(){
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            forgotPasswordViewModel.getForgotPasswordResponse(new ForgotPasswordRequest(etEmail.getText().toString())).observe((LifecycleOwner) context, forgotPasswordResponse -> {

                if (forgotPasswordResponse != null && forgotPasswordResponse.getResStatus().equalsIgnoreCase("200")) {
                    myToast.show(forgotPasswordResponse.getRes_message(), Toast.LENGTH_SHORT, true);
                    etEmail.setText("");
                } else if (forgotPasswordResponse != null && forgotPasswordResponse.getResStatus().equalsIgnoreCase("401")) {
                    myToast.show(forgotPasswordResponse.getRes_message(), Toast.LENGTH_SHORT, false);
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
