package com.ztp.app.MyApp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import com.sendbird.android.SendBird;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(mailTo = "praveen.sharma@hensongroup.com", formUri = "")
@SuppressLint("StaticFieldLeak")
public class MyZTPApplication extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    SharedPref sharedPref;
    private boolean mIsSyncManagerSetup = false;

    @Override
    public void onCreate() {
        super.onCreate();

        ACRA.init(this);
        sharedPref = SharedPref.getInstance(this);

        if (sharedPref.getTheme()) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (sharedPref.getLanguage()) {
            Utility.setLocale(this, "es");
        } else {
            Utility.setLocale(this, "en");
        }
        SendBird.init(Constants.APP_ID, getApplicationContext());
    }

    public boolean isSyncManagerSetup() {
        return mIsSyncManagerSetup;
    }

    public void setSyncManagerSetup(boolean syncManagerSetup) {
        mIsSyncManagerSetup = syncManagerSetup;
    }
}
