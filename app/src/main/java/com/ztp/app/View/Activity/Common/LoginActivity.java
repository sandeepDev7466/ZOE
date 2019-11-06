package com.ztp.app.View.Activity.Common;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.LoginRequest;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.Helper.TypefaceSpan;
import com.ztp.app.R;
import com.ztp.app.SendBird.Manager.ConnectionManager;
import com.ztp.app.SendBird.utils.PreferenceUtils;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.RuntimePermission;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_2Activity;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_3Activity;
import com.ztp.app.View.Activity.Student.StudentDashboardActivity;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;
import com.ztp.app.Viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyTextInputLayout etEmailLayout, etPasswordLayout;
    MyTextInputEditText etEmail, etPassword;
    MyTextView register, forget;
    MyButton login;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    LoginViewModel model;
    SharedPref sharedPref;
    RuntimePermission runtimePermission;
    LinearLayout cardLayout;
    ValidateTor validate;
    private boolean stu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        validate = new ValidateTor();
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        runtimePermission = RuntimePermission.getInstance();
        Utility.makeStatusBarTransparent(context);
        model = ViewModelProviders.of(this).get(LoginViewModel.class);
        init();
        setThemeOfComponents();

    }

    private void setThemeOfComponents() {

        int theme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (theme == Configuration.UI_MODE_NIGHT_NO) {
            //login.setBackgroundResource(R.drawable.black_border_rectangle);
            cardLayout.setBackgroundResource(R.drawable.white_plain_rectangle);
            etPasswordLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        } else if (theme == Configuration.UI_MODE_NIGHT_YES) {
            //login.setBackgroundResource(R.drawable.white_border_rectangle);
            cardLayout.setBackgroundResource(R.drawable.black_plain_rectangle);
            etPasswordLayout.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }
    }

    public void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etEmailLayout = findViewById(R.id.etEmailLayout);
        etPasswordLayout = findViewById(R.id.etPasswordLayout);
        register = findViewById(R.id.register);
        forget = findViewById(R.id.forget);
        login = findViewById(R.id.login);
        cardLayout = findViewById(R.id.cardLayout);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);


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
                    } else {
                        etEmailLayout.setError(getString(R.string.err_enter_valid_email));
                    }
                } else {
                    etEmailLayout.setError(null);
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
                if (s != null && s.toString().length() > 0) {
                    etPasswordLayout.setError(null);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                if (etEmail.getText() != null && !etEmail.getText().toString().isEmpty()) {

                    if (etPassword.getText() != null && !etPassword.getText().toString().isEmpty()) {

                        if (Utility.isNetworkAvailable(context)) {

                            myProgressDialog.show(getString(R.string.logging_you_in));

                            sharedPref.setEmail(etEmail.getText().toString().trim());

                            LoginRequest loginRequest = new LoginRequest(etEmail.getText().toString(), etPassword.getText().toString(), Utility.getDeviceId(context), Utility.getCurrentTime());

                            Log.i("","" + new Gson().toJson(loginRequest));

                            model.getLoginResponse(loginRequest).observe(this, loginResponse -> {

                                if (loginResponse != null) {

                                    Log.i("","" + new Gson().toJson(loginResponse));
                                    if (loginResponse.getResStatus().equalsIgnoreCase("200") && loginResponse.getResData() != null) {
                                        sharedPref.setFirstName(loginResponse.getResData().getUserFName());
                                        sharedPref.setLastName(loginResponse.getResData().getUserLName());
                                        sharedPref.setUserType(loginResponse.getResData().getUserType());
                                        sharedPref.setUserId(loginResponse.getResData().getUserId());
                                        sharedPref.setCoverImage(loginResponse.getResData().getUserCoverPic());
                                        sharedPref.setProfileImage(loginResponse.getResData().getUserProfilePic());
                                        sharedPref.setTimezone(loginResponse.getResData().getUserTimezone());
                                        sharedPref.setDaylight(loginResponse.getResData().getUserDayLight());
                                        sharedPref.setUserStatus(loginResponse.getResData().getUserStatus());
                                        sharedPref.setUnreadPushNotif("0");

                                        PreferenceUtils.init(this);
//                                      PreferenceUtils.setUserId(sharedPref.getEmail());
                                        PreferenceUtils.setNickname(sharedPref.getFirstName());
                                        if (loginResponse.getResData().getUserStatus().equalsIgnoreCase("10"))
                                            connectToSendBird(sharedPref.getEmail(), sharedPref.getFirstName() + " " + sharedPref.getLastName(),sharedPref.getProfileImage());
                                        else {
                                            if (loginResponse.getResData().getUserStatus().equalsIgnoreCase("1")) {
                                                if (loginResponse.getResData().getUserType().equalsIgnoreCase(Constants.user_type_cso)) {
                                                    Intent intent = new Intent(context, CsoRegisterStep_2Activity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                } else {
                                                    Intent intent = new Intent(context, ValidateOTPActivity.class);
                                                    intent.putExtra("otp", loginResponse.getResData().getPhoneValid());
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                }
                                            } else if (loginResponse.getResData().getUserStatus().equalsIgnoreCase("2")) {
                                                Intent intent = new Intent(context, CsoRegisterStep_3Activity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            } else if (loginResponse.getResData().getUserStatus().equalsIgnoreCase("3")) {
                                                Intent intent = new Intent(context, ValidateOTPActivity.class);
                                                intent.putExtra("otp", loginResponse.getResData().getPhoneValid());
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            } else if (loginResponse.getResData().getUserStatus().equalsIgnoreCase("30")) {
                                                myToast.show(getString(R.string.acccount_not_verified), Toast.LENGTH_SHORT, false);
                                            }
                                            else if (loginResponse.getResData().getUserStatus().equalsIgnoreCase("20")) {
                                                connectToSendBird(sharedPref.getEmail(), sharedPref.getFirstName() + " " + sharedPref.getLastName(), sharedPref.getProfileImage());
                                            }
                                            myProgressDialog.dismiss();
                                        }

                                        if (loginResponse.getResData().getUserType().equalsIgnoreCase(Constants.user_type_student) || loginResponse.getResData().getUserType().equalsIgnoreCase(Constants.user_type_volunteer)) {
                                            stu = true;

                                        } else {
                                            stu = false;
                                        }


                                    } else if (loginResponse.getResStatus().equalsIgnoreCase("401")) {
                                        etPassword.setText("");
                                        //myToast.show(getString(R.string.wrong_email_or_password), Toast.LENGTH_SHORT, false);
                                        myToast.show(loginResponse.getResMessage(), Toast.LENGTH_SHORT, false);
                                        myProgressDialog.dismiss();
                                    }
                                } else {
                                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                                    myProgressDialog.dismiss();
                                }
                            });

                        } else {
                            etPassword.setText("");
                            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                        }
                    } else {

                        SpannableString s = new SpannableString(getString(R.string.please_enter_password));
                        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
                        s.setSpan(new TypefaceSpan(face), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        etPasswordLayout.setError(s);

                    }
                } else {

                    SpannableString s = new SpannableString(getString(R.string.please_enter_email));
                    Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
                    s.setSpan(new TypefaceSpan(face), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    etEmailLayout.setError(s);
                }

                break;
            case R.id.register:

                startActivity(new Intent(context, SelectionActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.forget:

                startActivity(new Intent(context, ForgetActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }
    }

    private void connectToSendBird(final String userId, final String userNickname,String profileImage) {


        ConnectionManager.login(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                // Callback received; hide the progress bar.


                if (e != null) {
                    // Error!
//                    Toast.makeText(
//                            LoginActivity.this, "" + e.getCode() + ": " + e.getMessage(),
//                            Toast.LENGTH_SHORT)
//                            .show();

                    myProgressDialog.dismiss();
                    PreferenceUtils.setConnected(false);
                    return;
                }

                PreferenceUtils.setConnected(true);

                // Update the user's nickname
                updateCurrentUserInfo(userNickname,profileImage);
                updateCurrentUserPushToken();

                if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer)) {
                    Intent intent = new Intent(context, VolunteerDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_student)) {
                    Intent intent = new Intent(context, StudentDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    Intent intent = new Intent(context, CsoDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                myProgressDialog.dismiss();
            }
        });
    }

    private void updateCurrentUserInfo(String userNickname,String profileImage) {

        SendBird.updateCurrentUserInfo(userNickname, profileImage, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                PreferenceUtils.setNickname(userNickname);
            }
        });
    }

    /**
     * Update the user's push token.
     */
    private void updateCurrentUserPushToken() {
        //PushUtils.registerPushTokenForCurrentUser(LoginActivity.this, null);
    }
}
