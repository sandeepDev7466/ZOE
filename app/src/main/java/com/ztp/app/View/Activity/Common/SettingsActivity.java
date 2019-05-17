package com.ztp.app.View.Activity.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

import com.github.angads25.toggle.widget.LabeledSwitch;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.Student.StudentDashboardActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LabeledSwitch sw_theme, sw_language,sw_location;
    SharedPref sharedPref;
    boolean theme, language,location;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;
        sharedPref = SharedPref.getInstance(context);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        Utility.makeStatusBarTransparent(context);
        sw_theme = findViewById(R.id.theme);
        sw_language = findViewById(R.id.language);
        sw_location = findViewById(R.id.location);
        back = findViewById(R.id.back);

        back.setOnClickListener(this);

        theme = sharedPref.getTheme();
        language = sharedPref.getLanguage();
        location = sharedPref.getLocation();

        sw_theme.setOn(theme);
        sw_language.setOn(language);
        sw_location.setOn(location);


        sw_theme.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setTheme(isOn);
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                sharedPref.setIsChanged(true);

            } else {
                sharedPref.setTheme(isOn);
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                sharedPref.setIsChanged(true);
            }
        });

        sw_language.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setLanguage(true);
                Utility.setLocale(context, "es");
                recreate();
                sharedPref.setIsChanged(true);

            } else {
                sharedPref.setLanguage(false);
                Utility.setLocale(context, "en");
                recreate();
                sharedPref.setIsChanged(true);
            }
        });

        sw_location.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setLocation(true);


            } else {
                sharedPref.setLocation(false);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void recreate() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
