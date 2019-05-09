package com.ztp.app.View.Activity.Student;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.angads25.toggle.widget.LabeledSwitch;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.EditProfileActivity;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.SettingsActivity;
import com.ztp.app.View.Fragment.Student.Booking.BookingFragment;
import com.ztp.app.View.Fragment.Student.Dashboard.DashboardFragment;
import com.ztp.app.View.Fragment.Student.Hangout.HangoutFragment;
import com.ztp.app.View.Fragment.Student.Locker.LockerFragment;
import com.ztp.app.View.Fragment.Student.Target.TargetFragment;

public class StudentDashboardActivity extends AppCompatActivity
        implements  View.OnClickListener {

    DrawerLayout drawer;
    Context context;
    ImageView menu,tint,tint_nav;
    LinearLayout hangout, locker, target, booking, dashboard, body, bottom_nav;
    ViewPager viewPager;
    TabLayout tabLayout;
    MyToast myToast;
    MyHeadingTextView title,nav_title;
    SharedPref sharedPref;
    LinearLayout logout,settings,edit_profile;
    String name;
    boolean theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        context = this;
        sharedPref = SharedPref.getInstance(context);
        Utility.makeStatusBarTransparent(context);
        myToast = new MyToast(context);
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
        locker = findViewById(R.id.locker);
        target = findViewById(R.id.target);
        booking = findViewById(R.id.booking);
        dashboard = findViewById(R.id.dashboard);
        body = findViewById(R.id.body);
        bottom_nav = findViewById(R.id.bottom_nav);
        tint = findViewById(R.id.tint);
        tint_nav = findViewById(R.id.tint_nav);
        title = findViewById(R.id.title);
        nav_title = findViewById(R.id.nav_title);

        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        edit_profile = findViewById(R.id.edit_profile);

        hangout.setOnClickListener(this);
        locker.setOnClickListener(this);
        target.setOnClickListener(this);
        booking.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        menu.setOnClickListener(this);
        logout.setOnClickListener(this);
        settings.setOnClickListener(this);
        edit_profile.setOnClickListener(this);

        if(theme)
        {
            tint.setBackgroundColor(getResources().getColor(R.color.black));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else
        {
            tint.setBackgroundColor(getResources().getColor(R.color.white));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDashTitle();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void recreate()
    {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Fragment frag = getLastFragment();

            if (frag.getTag() != null && frag.getTag().equals("HangoutFragment")) {
                hangout.setAlpha(Constants.no_alpha);
                locker.setAlpha(Constants.alpha);
                target.setAlpha(Constants.alpha);
                booking.setAlpha(Constants.alpha);
                dashboard.setAlpha(Constants.alpha);
                title.setText(getString(R.string.hangout).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("LockerFragment")) {
                hangout.setAlpha(Constants.alpha);
                locker.setAlpha(Constants.no_alpha);
                target.setAlpha(Constants.alpha);
                booking.setAlpha(Constants.alpha);
                dashboard.setAlpha(Constants.alpha);
                title.setText(getString(R.string.locker).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("TargetFragment")) {
                hangout.setAlpha(Constants.alpha);
                locker.setAlpha(Constants.alpha);
                target.setAlpha(Constants.no_alpha);
                booking.setAlpha(Constants.alpha);
                dashboard.setAlpha(Constants.alpha);
                title.setText(getString(R.string.target).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("BookingFragment")) {
                hangout.setAlpha(Constants.alpha);
                locker.setAlpha(Constants.alpha);
                target.setAlpha(Constants.alpha);
                booking.setAlpha(Constants.no_alpha);
                dashboard.setAlpha(Constants.alpha);
                title.setText(getString(R.string.booking).toUpperCase());
            } else if (frag.getTag() != null && frag.getTag().equals("DashboardFragment")) {
                hangout.setAlpha(Constants.alpha);
                locker.setAlpha(Constants.alpha);
                target.setAlpha(Constants.alpha);
                booking.setAlpha(Constants.alpha);
                dashboard.setAlpha(Constants.no_alpha);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                showHideSideNavigation();
                break;
            case R.id.hangout:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof HangoutFragment)) {
                    title.setText(getString(R.string.hangout).toUpperCase());
                    setHangoutFragment();
                }
                break;
            case R.id.locker:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof LockerFragment)) {
                    title.setText(getString(R.string.locker).toUpperCase());
                    setLockerFragment();
                }
                break;
            case R.id.target:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof TargetFragment)) {
                    title.setText(getString(R.string.target).toUpperCase());
                    setTargetFragment();
                }
                break;
            case R.id.booking:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof BookingFragment)) {
                    title.setText(getString(R.string.booking).toUpperCase());
                    setBookingFragment();
                }
                break;
            case R.id.dashboard:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof DashboardFragment)) {
                    setDashTitle();
                    setDashboardFragment();
                }
                break;

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

            case R.id.settings:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.edit_profile:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent1 = new Intent(context, EditProfileActivity.class);
                intent1.putExtra("type","stu");
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
               recreate();
            }
        }
    }

    public void setHangoutFragment() {
        hangout.setAlpha(Constants.no_alpha);
        locker.setAlpha(Constants.alpha);
        target.setAlpha(Constants.alpha);
        booking.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
        Utility.replaceFragment(context, new HangoutFragment(), "HangoutFragment");
    }

    public void setLockerFragment() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.no_alpha);
        target.setAlpha(Constants.alpha);
        booking.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
        Utility.replaceFragment(context, new LockerFragment(), "LockerFragment");
    }

    public void setTargetFragment() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.alpha);
        target.setAlpha(Constants.no_alpha);
        booking.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
        Utility.replaceFragment(context, new TargetFragment(), "TargetFragment");
    }

    public void setBookingFragment() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.alpha);
        target.setAlpha(Constants.alpha);
        booking.setAlpha(Constants.no_alpha);
        dashboard.setAlpha(Constants.alpha);
        Utility.replaceFragment(context, new BookingFragment(), "BookingFragment");
    }

    public void setDashboardFragment() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.alpha);
        target.setAlpha(Constants.alpha);
        booking.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.no_alpha);
        Utility.replaceFragment(context, new DashboardFragment(), "DashboardFragment");
    }
}
