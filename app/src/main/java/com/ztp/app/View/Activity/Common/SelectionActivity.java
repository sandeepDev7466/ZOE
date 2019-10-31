package com.ztp.app.View.Activity.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ztp.app.BuildConfig;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_1Activity;
import com.ztp.app.View.Activity.Volunteer.VolunteerRegisterActivity;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout studentLayout, csoLayout;
    Context context;
    MyToast myToast;
    SharedPref sharedPref;
    MyBoldTextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        studentLayout = findViewById(R.id.studentLayout);
        csoLayout = findViewById(R.id.csoLayout);
        text = findViewById(R.id.text);
        studentLayout.setOnClickListener(this);
        csoLayout.setOnClickListener(this);

        if(BuildConfig.FLAVOR.equalsIgnoreCase(Constants.FLAVOUR_BLUEPRINT) || BuildConfig.FLAVOR.equalsIgnoreCase(Constants.FLAVOUR_PRODUCTION))
        {
            text.setText(R.string.i_m_a_student);
        }
        else if(BuildConfig.FLAVOR.equalsIgnoreCase(Constants.FLAVOUR_THUMBPRINT))
        {
            text.setText(R.string.i_m_a_student_volunteer);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.studentLayout:
                startActivity(new Intent(context, VolunteerRegisterActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.csoLayout:
                sharedPref.setUserType(Constants.user_type_cso);
                startActivity(new Intent(context, CsoRegisterStep_1Activity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
