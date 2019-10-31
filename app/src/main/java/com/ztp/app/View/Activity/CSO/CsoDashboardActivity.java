package com.ztp.app.View.Activity.CSO;

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
import com.ztp.app.View.Activity.Common.CsoEditProfileActivity;
import com.ztp.app.View.Activity.Common.EditProfileActivity;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.NotificationActivity;
import com.ztp.app.View.Activity.Common.SettingsActivity;
import com.ztp.app.View.Activity.Common.TimezoneActivity;
import com.ztp.app.View.Fragment.CSO.Dashboard.DashboardFragment;
import com.ztp.app.View.Fragment.CSO.Event.EventListFragment;
import com.ztp.app.View.Fragment.CSO.Event.TabNewEventFragment;
import com.ztp.app.View.Fragment.CSO.Message.MessageFragment;
import com.ztp.app.View.Fragment.CSO.Students.StudentsFragment;
import com.ztp.app.View.Fragment.CSO.Students.TabStudentsVolunteersFragment;
import com.ztp.app.View.Fragment.Volunteer.Locker.DocumentsFragment;
import com.ztp.app.View.Fragment.Volunteer.Locker.LockerFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class CsoDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyToast myToast;
    SharedPref sharedPref;
    ImageView menu, tint, tint_nav;
    LinearLayout hangout, student, event, dashboard, body, locker;
    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawer;
    MyHeadingTextView  nav_title;
    LinearLayout logout, settings, edit_profile, changePass,timezone;
    boolean theme;
    public static final int ADD_NOTE = 44;
    String name;
    public static ImageView cover, coverNav;
    public static CircleImageView user, userNav;
    RelativeLayout notifLayout;
    MyTextView notifCount,timezoneCode;
    TabNewEventFragment tabNewEventFragment;

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

      /*  if (savedInstanceState != null) {
            //Restore the fragment's instance
            tabNewEventFragment = (TabNewEventFragment) getSupportFragmentManager().getFragment(savedInstanceState, "TabNewEventFragment");
        }*/

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.get("groupChannelUrl") != null) {
                if (b.getString("groupChannelUrl") != null)
                    setHangoutFragment();

            }
        } else
            setDashboardFragment();
    }



    private void setLoggedIn() {
        sharedPref.setIsLogin(true);
    }

    private void setDashTitle() {
        name = sharedPref.getFirstName().toUpperCase() + " " + sharedPref.getLastName().toUpperCase();
        //title.setText(getString(R.string.dashboard).toUpperCase());
        nav_title.setText(name);
    }

    private void setNotificationCount()
    {
        if(!sharedPref.getUnreadPushNotif().equalsIgnoreCase("0"))
        {
            notifCount.setVisibility(View.VISIBLE);
            notifCount.setText(sharedPref.getUnreadPushNotif());
        }
        else
        {
            notifCount.setVisibility(View.GONE);
        }
    }

    public void init() {
        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);

        user = findViewById(R.id.user);
        userNav = findViewById(R.id.userNav);
        cover = findViewById(R.id.cover);
        coverNav = findViewById(R.id.coverNav);
        menu = findViewById(R.id.menu);
        hangout = findViewById(R.id.hangout);
        locker = findViewById(R.id.locker);
        student = findViewById(R.id.student);
        event = findViewById(R.id.event);
        dashboard = findViewById(R.id.dashboard);
        body = findViewById(R.id.body);
       //title = findViewById(R.id.title);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        changePass = findViewById(R.id.changePass);
        timezone = findViewById(R.id.timezone);
        edit_profile = findViewById(R.id.edit_profile);
        tint_nav = findViewById(R.id.tint_nav);
        //tint = findViewById(R.id.tint);
        nav_title = findViewById(R.id.nav_title);
        notifLayout  = findViewById(R.id.notifLayout);
        notifCount = findViewById(R.id.notifCount);
        timezoneCode = findViewById(R.id.timezoneCode);

        hangout.setOnClickListener(this);
        locker.setOnClickListener(this);
        student.setOnClickListener(this);
        event.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        menu.setOnClickListener(this);
        logout.setOnClickListener(this);
        settings.setOnClickListener(this);
        changePass.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        notifLayout.setOnClickListener(this);
        timezone.setOnClickListener(this);

        if (theme) {
            //tint.setBackgroundColor(getResources().getColor(R.color.black));
            tint_nav.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            //tint.setBackgroundColor(getResources().getColor(R.color.white));
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

        if (sharedPref.getIsChanged()) {
            recreate();
            sharedPref.setIsChanged(false);
        }
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "TabNewEventFragment", tabNewEventFragment);
    }*/

    public void recreate() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Fragment frag = getLastFragment();
            if (frag != null)
                if (frag.getTag() != null && frag.getTag().equals("HangoutFragment")) {
                    setHangoutAlpha();
                    //title.setText(getString(R.string.message).toUpperCase());
                } else if (frag.getTag() != null && frag.getTag().equals("DocumentsFragment")) {
                    setLockerAlpha();
                    //title.setText(getString(R.string.locker).toUpperCase());
                } else if (frag.getTag() != null && frag.getTag().equals("StudentFragment")) {
                    setStudentAlpha();
                    //title.setText(getString(R.string.students).toUpperCase());
                } else if (frag.getTag() != null && frag.getTag().equals("EventListFragment")) {
                    setEventAlpha();
                    //title.setText(getString(R.string.events).toUpperCase());
                }
                else if (frag.getTag() != null && frag.getTag().equals("DashboardFragment")) {
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
                    SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
                        @Override
                        public void onUnregistered(SendBirdException e) {
                            if (e != null) {
                                // Error!
                                e.printStackTrace();
                            } else {
                            }

                            ConnectionManager.logout(new SendBird.DisconnectHandler() {
                                @Override
                                public void onDisconnected() {
                                    PreferenceUtils.init(CsoDashboardActivity.this);
                                    // if you want to clear cache of specific user when logout, you can do like this.
                                    String userId = PreferenceUtils.getUserId();
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

            case R.id.menu:
                showHideSideNavigation();
                break;


            case R.id.dashboard:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof DashboardFragment)) {
                    //title.setText(getString(R.string.dashboard).toUpperCase());
                    setDashboardFragment();
                }
                break;

            case R.id.locker:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof LockerFragment)) {
                    //title.setText(getString(R.string.locker).toUpperCase());
                    setLockerFragment();
                }
                break;

            case R.id.event:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof EventListFragment)) {
                    //title.setText(getString(R.string.events).toUpperCase());
                    setEventFragment();
                }
                break;

            case R.id.student:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof StudentsFragment)) {
                   // title.setText(getString(R.string.students).toUpperCase());
                    setStudentFragment();
                }
                break;

//            case R.id.message:
//                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof MessageFragment)) {
////                    title.setText(getString(R.string.message).toUpperCase());
//                    setMessageFragment();
//                }
//                break;
            case R.id.hangout:
                if (!(getSupportFragmentManager().findFragmentById(R.id.body) instanceof MessageFragment)) {

                    setHangoutFragment();
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
            case R.id.changePass:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent_change = new Intent(context, ChangePasswordActivity.class);
                startActivity(intent_change);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.edit_profile:
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                Intent intent1 = new Intent(context, CsoEditProfileActivity.class);
                intent1.putExtra("type", "cso");
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


    public void setLockerFragment() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.no_alpha);
        dashboard.setAlpha(Constants.alpha);
        Utility.replaceFragment(context, new DocumentsFragment(), "DocumentsFragment");
    }

    public void setHangoutFragment() {
        //title.setText(getString(R.string.message).toUpperCase());
        setHangoutAlpha();
        Utility.replaceFragment(context, new GroupChannelListFragment(), "HangoutFragment");
    }

    public void setStudentFragmentFrom() {
        setStudentAlpha();
        Utility.replaceFragment(context, new TabStudentsVolunteersFragment(), "TabStudentsVolunteersFragment");

    }

    public void setHangoutProps() {
        hangout.setAlpha(Constants.no_alpha);
        locker.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
       // title.setText(getString(R.string.message).toUpperCase());
    }

    private void setDashboardAlpha() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.no_alpha);
    }

    private void setEventAlpha() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.no_alpha);
        dashboard.setAlpha(Constants.alpha);
    }

    private void setStudentAlpha() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.alpha);
        student.setAlpha(Constants.no_alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
    }

    private void setHangoutAlpha() {
        hangout.setAlpha(Constants.no_alpha);
        locker.setAlpha(Constants.alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
    }

    private void setLockerAlpha() {
        hangout.setAlpha(Constants.alpha);
        locker.setAlpha(Constants.no_alpha);
        student.setAlpha(Constants.alpha);
        event.setAlpha(Constants.alpha);
        dashboard.setAlpha(Constants.alpha);
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
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(CsoDashboardActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        // Log.e("newToken", newToken);
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
                  /*  myToast.show(e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT, false);
*/
                    return;
                }

            }
        });
    }

    /**
     * Update the user's push token.
     */
    private void updateCurrentUserPushToken(String newToken) {
        PushUtils.registerPushTokenForCurrentUser(CsoDashboardActivity.this, null, newToken);
    }
}
