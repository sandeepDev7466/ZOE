package com.ztp.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Utils.Utility;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formUri = "" , mailTo = "sandeep.pathak@hashtaglabs.biz")
public class MyZTPApplication extends Application {
    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    SharedPref sharedPref;
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPref = SharedPref.getInstance(this);
        context = this;
        ACRA.init(this);
        if(sharedPref.getTheme()) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (sharedPref.getLanguage()) {
            Utility.setLocale(this,"es");
        } else {
            Utility.setLocale(this,"en");
        }

    }
}
