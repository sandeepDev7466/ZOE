package com.ztp.app.View.Activity.Student;

import android.app.Activity;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.syncmanager.SendBirdSyncManager;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.MyApp.MyZTPApplication;
import com.ztp.app.R;
import com.ztp.app.SendBird.Manager.ConnectionManager;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.SendBird.utils.PreferenceUtils;
import com.ztp.app.SendBird.utils.PushUtils;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.ChangePasswordActivity;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.NotificationActivity;
import com.ztp.app.View.Activity.Common.SettingsActivity;
import com.ztp.app.View.Activity.Common.StuVolEditProfileActivity;
import com.ztp.app.View.Activity.Common.TimezoneActivity;
import com.ztp.app.View.Fragment.Student.Dashboard.DashboardFragment;
import com.ztp.app.View.Fragment.Volunteer.Event.BookingFragment;
import com.ztp.app.View.Fragment.Volunteer.Hangout.HangoutFragment;
import com.ztp.app.View.Fragment.Volunteer.Locker.LockerFragment;
import com.ztp.app.View.Fragment.Volunteer.Target.TargetFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawer;
    Context context;
    ImageView menu, tint, tint_nav;
    LinearLayout hangout, locker, target, booking, dashboard, body, bottom_nav;
    ViewPager viewPager;
    TabLayout tabLayout;
    MyToast myToast;
    MyHeadingTextView nav_title;
    SharedPref sharedPref;
    LinearLayout logout, settings, edit_profile, changePass, timezone;
    String name;
    boolean theme;
    public static ImageView coverNav;
    public static CircleImageView userNav, user;
    RelativeLayout notifLayout;
    MyTextView notifCount, timezoneCode, statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        Utility.addAutoStartupPermission(context);
        sharedPref = SharedPref.getInstance(context);
        Utility.makeStatusBarTransparent(context);
        myToast = new MyToast(context);
        theme = sharedPref.getTheme();
        init();
        setLoggedIn();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.get("groupChannelUrl") != null) {
                if (b.getString("groupChannelUrl") != null)
                    setHangoutFragment();

            }
        } else
            setDashboardFragment();

        if (sharedPref.getUserStatus().equalsIgnoreCase("20")) {
            statusText.setVisibility(View.VISIBLE);
            statusText.setText(getString(R.string.err_acc_not_approved_by_school));
        } else {
            statusText.setVisibility(View.GONE);
        }
    }

    private void setNotificationCount() {
        if (!sharedPref.getUnreadPushNotif().equalsIgnoreCase("0")) {
            notifCount.setVisibility(View.VISIBLE);
            if (Integer.parseInt(sharedPref.getUnreadPushNotif()) < 10)
                notifCount.setText(sharedPref.getUnreadPushNotif());
            else
                notifCount.setText("9+");
        } else {
            notifCount.setVisibility(View.GONE);
        }
    }

    private void setLoggedIn() {
        sharedPref.setIsLogin(true);
    }

    private void setDashTitle() {
        name = sharedPref.getFirstName().toUpperCase() + " " + sharedPref.getLastName().toUpperCase();
        // title.setText(getString(R.string.dashboard).toUpperCase());
        nav_title.setText(name);
    }

    public void init() {
        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        statusText = findViewById(R.id.statusText);
        user = findViewById(R.id.user);
        userNav = findViewById(R.id.userNav);
        //cover = findViewById(R.id.cover);
        coverNav = findViewById(R.id.coverNav);
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
        //title = findViewById(R.id.title);
        nav_title = findViewById(R.id.nav_title);

        changePass = findViewById(R.id.changePass);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        edit_profile = findViewById(R.id.edit_profile);
        notifLayout = findViewById(R.id.notifLayout);
        notifCount = findViewById(R.id.notifCount);
        timezone = findViewById(R.id.timezone);
        timezoneCode = findViewById(R.id.timezoneCode);

        hangout.setOnClickListener(this);
        locker.setOnClickListener(this);
        target.setOnClickListener(this);
        booking.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        menu.setOnClickListener(this);
        logout.setOnClickListener(this);
        settings.setOnClickListener(this);
        changePass.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        notifLayout.setOnClickListener(this);
        timezone.setOnClickListener(this);


       /* if (theme) {
            tint.setBackgroundColor(getResources().getColor(R.color.black));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            tint.setBackgroundColor(getResources().getColor(R.color.white));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.white));
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        timezoneCode.setText(sharedPref.getTimezone());
        setNotificationCount();
        setDashTitle();
        PreferenceUtils.init(this);

        connectToSendBird(sharedPref.getEmail(), sharedPref.getFirstName() + " " + sharedPref.getLastName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (sharedPref.getIsChanged()) {
            recreate();
            sharedPref.setIsChanged(false);
        }
    }

    public void recreate() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
//        SendBird.init(Constants.APP_ID, this.getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Fragment frag = getLastFragment();
            if (frag != null)
                if (frag.getTag() != null && frag.getTag().equals("HangoutFragment")) {
                    hangout.setAlpha(Constants.no_alpha);
                    locker.setAlpha(Constants.alpha);
                    target.setAlpha(Constants.alpha);
                    booking.setAlpha(Constants.alpha);
                    dashboard.setAlpha(Constants.alpha);
                    //title.setText(getString(R.string.message).toUpperCase());
                } else if (frag.getTag() != null && frag.getTag().equals("LockerFragment")) {
                    hangout.setAlpha(Constants.alpha);
                    locker.setAlpha(Constants.no_alpha);
                    target.setAlpha(Constants.alpha);
                    booking.setAlpha(Constants.alpha);
                    dashboard.setAlpha(Constants.alpha);
                    //title.setText(getString(R.string.locker).toUpperCase());
                } else if (frag.getTag() != null && frag.getTag().equals("TargetFragment")) {
                    hangout.setAlpha(Constants.alpha);
                    locker.setAlpha(Constants.alpha);
                    target.setAlpha(Constants.no_alpha);
                    booking.setAlpha(Constants.alpha);
                    dashboard.setAlpha(Constants.alpha);
                    //title.setText(getString(R.string.target).toUpperCase());
                } else if (frag.getTag() != null && frag.getTag().equals("BookingFragment")) {
                    hangout.setAlpha(Constants.alpha);
                    locker.setAlpha(Constants.alpha);
                    target.setAlpha(Constants.alpha);
                    booking.setAlpha(Constants.no_alpha);
                    dashboard.setAlpha(Constants.alpha);
                    //title.setText(getString(R.string.booking).toUpperCase());
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
                    //title.setText(getString(R.string.message).toUpperCase());
                    setHangoutFragment();
                }

                break;
            case R.id.locker:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof LockerFragment)) {
                    //title.setText(getString(R.string.locker).toUpperCase());
                    setLockerFragment();
                }
                break;
            case R.id.target:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof TargetFragment)) {
                    // title.setText(getString(R.string.target).toUpperCase());
                    setTargetFragment();
                }
                break;
            case R.id.booking:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof BookingFragment)) {
                    //title.setText(getString(R.string.booking).toUpperCase());
                    setBookingFragment();
                }
                break;
            case R.id.dashboard:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof DashboardFragment)) {
                    setDashTitle();
                    setDashboardFragment();
                }
                break;
            case R.id.changePass:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent_change = new Intent(context, ChangePasswordActivity.class);
                startActivity(intent_change);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                    SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
                        @Override
                        public void onUnregistered(SendBirdException e) {
                            if (e != null) {
                                // Error!
                                e.printStackTrace();

                                // Don't return because we still need to disconnect.
                            } else {
//                    Toast.makeText(MainActivity.this, "All push tokens unregistered.", Toast.LENGTH_SHORT).show();
                            }

                            ConnectionManager.logout(new SendBird.DisconnectHandler() {
                                @Override
                                public void onDisconnected() {
                                    PreferenceUtils.init(StudentDashboardActivity.this);
                                    String userId = PreferenceUtils.getUserId();
                                    // if you want to clear cache of specific user when logout, you can do like this.

                                    if (((MyZTPApplication) getApplication()).isSyncManagerSetup()) {
                                        SendBirdSyncManager.getInstance().clearCache(userId);
                                    }

                                    PreferenceUtils.setConnected(false);
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }
                            });
                        }
                    });

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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.edit_profile:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent1 = new Intent(context, StuVolEditProfileActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.notifLayout:
                Intent in = new Intent(context, NotificationActivity.class);
                startActivity(in);
                break;

            case R.id.timezone:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                startActivity(new Intent(context, TimezoneActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                recreate();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setHangoutProps() {
        hangout.setAlpha(Constants.no_alpha);
        locker.setAlpha(Constants.alpha);
        target.setAlpha(Constants.alpha);
        booking.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
        //title.setText(getString(R.string.message).toUpperCase());
    }

    public void setHangoutFragment() {
        hangout.setAlpha(Constants.no_alpha);
        locker.setAlpha(Constants.alpha);
        target.setAlpha(Constants.alpha);
        booking.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
        Utility.replaceFragment(context, new GroupChannelListFragment(), "HangoutFragment");
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

    /**
     * Attempts to connect a user to SendBird.
     *
     * @param userId       The unique ID of the user.
     * @param userNickname The user's nickname, which will be displayed in chats.
     */
    private void connectToSendBird(final String userId, final String userNickname) {
        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {

                if (e != null) {
                    // Error!
//                    myToast.show(e.getCode() + ":" + e.getMessage(),
//                            Toast.LENGTH_SHORT, false);

                    return;
                }
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(StudentDashboardActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Log.e("newToken", newToken);
                        updateCurrentUserPushToken(newToken);
                    }
                });
                PreferenceUtils.setConnected(true);
                //                // Update the user's nickname
                updateCurrentUserInfo(userNickname);

            }
        });
    }

    /**
     * Updates the user's nickname.
     *
     * @param userNickname The new nickname of the user.
     */
    private void updateCurrentUserInfo(String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, sharedPref.getProfileImage(), new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
//                    myToast.show(e.getCode() + ":" + e.getMessage(),
//                            Toast.LENGTH_SHORT, false);
                    return;
                }

            }
        });
    }

    /**
     * Update the user's push token.
     */
    private void updateCurrentUserPushToken(String newToken) {
        PushUtils.registerPushTokenForCurrentUser(StudentDashboardActivity.this, null, newToken);
    }
}
