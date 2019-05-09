package com.ztp.app.View.Activity.CSO;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.EditProfileActivity;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.SettingsActivity;
import com.ztp.app.View.Fragment.CSO.Dashboard.DashboardFragment;
import com.ztp.app.View.Fragment.CSO.Event.EventListFragment;
import com.ztp.app.View.Fragment.CSO.Message.MessageFragment;
import com.ztp.app.View.Fragment.CSO.Students.StudentsFragment;

public class CsoDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyToast myToast;
    SharedPref sharedPref;
    ImageView menu, tint, tint_nav;
    LinearLayout hangout, message, student, event, dashboard, body;
    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawer;
    MyHeadingTextView title, nav_title;
    LinearLayout logout, settings, edit_profile;
    boolean theme;
    public static final String RESULT = "result";
    public static final int ADD_NOTE = 44;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso_dashboard);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        context = this;
        Utility.makeStatusBarTransparent(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        init();
        setLoggedIn();
        setDashboardFragment();
    }

    private void setLoggedIn() {
        sharedPref.setIsLogin(true);
    }

    private void setDashTitle() {
        name = sharedPref.getFirstName().toUpperCase() + " " + sharedPref.getLastName().toUpperCase();
        title.setText(getString(R.string.dashboard).toUpperCase());
        nav_title.setText(name);
    }

    public void init() {
        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);

        menu = findViewById(R.id.menu);
        hangout = findViewById(R.id.hangout);
        message = findViewById(R.id.message);
        student = findViewById(R.id.student);
        event = findViewById(R.id.event);
        dashboard = findViewById(R.id.dashboard);
        body = findViewById(R.id.body);
        title = findViewById(R.id.title);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        edit_profile = findViewById(R.id.edit_profile);
        tint_nav = findViewById(R.id.tint_nav);
        tint = findViewById(R.id.tint);
        title = findViewById(R.id.title);
        nav_title = findViewById(R.id.nav_title);

        hangout.setOnClickListener(this);
        message.setOnClickListener(this);
        student.setOnClickListener(this);
        event.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        menu.setOnClickListener(this);
        logout.setOnClickListener(this);
        settings.setOnClickListener(this);
        edit_profile.setOnClickListener(this);

        if (theme) {
            tint.setBackgroundColor(getResources().getColor(R.color.black));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            tint.setBackgroundColor(getResources().getColor(R.color.white));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.white));
        }

    }

    public Fragment getLastFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName();
        return getSupportFragmentManager().findFragmentByTag(fragmentTag);
    }

    public void showHideSideNavigation() {

        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            drawer.openDrawer(Gravity.END);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void recreate() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDashTitle();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Fragment frag = getLastFragment();

            if (frag.getTag() != null && frag.getTag().equals("HangoutFragment")) {
                setHangoutAlpha();
                title.setText(getString(R.string.hangout).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("MessageFragment")) {
                setMessageAlpha();
                title.setText(getString(R.string.message).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("StudentFragment")) {
                setStudentAlpha();
                title.setText(getString(R.string.events).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("EventListFragment")) {
                setEventAlpha();
                title.setText(getString(R.string.events).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("DashboardFragment")) {
                setDashboardAlpha();
                setDashTitle();
            }

        } else {

            Dialog dialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.exit_dialog, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);

            LinearLayout yes = view.findViewById(R.id.yes);
            LinearLayout no = view.findViewById(R.id.no);

            yes.setOnClickListener(v -> {
                dialog.dismiss();
                finish();
            });

            no.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }

                Dialog dialog = new Dialog(context);
                View view = LayoutInflater.from(context).inflate(R.layout.logout_dialog, null);
                dialog.setContentView(view);
                dialog.setCancelable(false);

                LinearLayout yes = view.findViewById(R.id.yes);
                LinearLayout no = view.findViewById(R.id.no);

                yes.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    sharedPref.setIsLogin(false);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                });

                no.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });

                dialog.show();
                break;

            case R.id.menu:
                showHideSideNavigation();
                break;


            case R.id.dashboard:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof DashboardFragment)) {
                    title.setText(getString(R.string.dashboard).toUpperCase());
                    setDashboardFragment();
                }
                break;
            case R.id.event:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof EventListFragment)) {
                    title.setText(getString(R.string.events).toUpperCase());
                    setEventFragment();
                }
                break;

            case R.id.student:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof StudentsFragment)) {
                    title.setText(getString(R.string.students).toUpperCase());
                    setStudentFragment();
                }
                break;

            case R.id.message:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof MessageFragment)) {
                    title.setText(getString(R.string.message).toUpperCase());
                    setMessageFragment();
                }
                break;

            case R.id.settings:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.edit_profile:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent1 = new Intent(context, EditProfileActivity.class);
                intent1.putExtra("type", "cso");
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                recreate();
            }
        }
    }

    public void setDashboardFragment() {

        setDashboardAlpha();

        Utility.replaceFragment(context, new DashboardFragment(), "DashboardFragment");
    }

    public void setEventFragment() {
        setEventAlpha();

        Utility.replaceFragment(CsoDashboardActivity.this, new EventListFragment(), "EventListFragment");
    }

    public void setStudentFragment() {
        setStudentAlpha();
        Utility.replaceFragment(context, new StudentsFragment(), "StudentFragment");
    }


    public void setMessageFragment() {
        setMessageAlpha();
        Utility.replaceFragment(context, new MessageFragment(), "MessageFragment");
    }


    private void setDashboardAlpha() {
        hangout.setAlpha(Constants.alpha);
        message.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.no_alpha);
    }

    private void setEventAlpha() {
        hangout.setAlpha(Constants.alpha);
        message.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.no_alpha);
        dashboard.setAlpha(Constants.alpha);
    }

    private void setStudentAlpha() {
        hangout.setAlpha(Constants.alpha);
        message.setAlpha(Constants.alpha);
        student.setAlpha(Constants.no_alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
    }

    private void setHangoutAlpha() {
        hangout.setAlpha(Constants.no_alpha);
        message.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
    }

    private void setMessageAlpha() {
        hangout.setAlpha(Constants.alpha);
        message.setAlpha(Constants.no_alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
    }


}
