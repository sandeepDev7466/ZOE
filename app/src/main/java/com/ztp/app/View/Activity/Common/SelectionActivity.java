package com.ztp.app.View.Activity.Common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.View.Activity.CSO.CsoRegisterStep_1Activity;
import com.ztp.app.View.Activity.Student.StudentRegisterActivity;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout studentLayout, csoLayout;
    Context context;
    MyToast myToast;
    SharedPref sharedPref;

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
        studentLayout.setOnClickListener(this);
        csoLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.studentLayout:
                //sharedPref.setUserType("stu");
                startActivity(new Intent(context, StudentRegisterActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.csoLayout:
                //sharedPref.setUserType("cso");
                startActivity(new Intent(context, CsoRegisterStep_1Activity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
