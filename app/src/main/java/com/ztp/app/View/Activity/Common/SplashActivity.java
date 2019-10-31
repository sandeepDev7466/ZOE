package com.ztp.app.View.Activity.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.View.Activity.CSO.CsoDashboardActivity;
import com.ztp.app.View.Activity.Student.StudentDashboardActivity;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;

public class SplashActivity extends AppCompatActivity {

    Context context;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        sharedPref = SharedPref.getInstance(context);
        init();
    }

    private void init() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);

                    if (sharedPref.getFirstRun()) {

                        if (!sharedPref.getIsLogin()) {
                            Intent i = new Intent(context, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else {
                            if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer)) {
                                Intent i = new Intent(context, VolunteerDashboardActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                            else if(sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_student))
                            {
                                Intent i = new Intent(context, StudentDashboardActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                            else if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso) || sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_csa)) {
                                Intent i = new Intent(context, CsoDashboardActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        }
                    } else {
                        Intent i = new Intent(context, PagerInfoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
