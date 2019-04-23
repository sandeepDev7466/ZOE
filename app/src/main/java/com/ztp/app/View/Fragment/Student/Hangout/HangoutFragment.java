package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

public class HangoutFragment extends Fragment {

    Context context;
    ViewPager viewPager;
    TabLayout tabLayout;
    MyToast myToast;
    String tag;
    boolean theme;
    SharedPref sharedPref;

    public HangoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hangout, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
        tag = "friend";

        setViewPager();
        createTabIcons();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                try {
                    View v = tab.getCustomView();
                    ImageView image = v.findViewById(R.id.image);
                    MyHeadingTextView text = v.findViewById(R.id.text);
                    ImageView threeDots = v.findViewById(R.id.threeDots);

                    if (theme) {

                        text.setTextColor(getResources().getColor(R.color.white));
                        image.setColorFilter(getResources().getColor(R.color.white));
                        threeDots.setColorFilter(getResources().getColor(R.color.white));

                    } else {

                        text.setTextColor(getResources().getColor(R.color.black));
                        image.setColorFilter(getResources().getColor(R.color.black));
                        threeDots.setColorFilter(getResources().getColor(R.color.black));

                    }

                    if (tab.getPosition() == 1)
                        threeDots.setVisibility(View.VISIBLE);

                    threeDots.setOnClickListener(v1 -> openPopUp(v1));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {

                    View v = tab.getCustomView();
                    ImageView image = v.findViewById(R.id.image);
                    MyHeadingTextView text = v.findViewById(R.id.text);
                    ImageView threeDots = v.findViewById(R.id.threeDots);
                    text.setTextColor(getResources().getColor(R.color.background_3));
                    image.setColorFilter(getResources().getColor(R.color.background_3));
                    threeDots.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }

    private void setViewPager() {

        HangoutPager hangoutPager = new HangoutPager(getChildFragmentManager());
        hangoutPager.addFragment(new TimelineFragment());
        hangoutPager.addFragment(new ConnectFragment());
        viewPager.setAdapter(hangoutPager);
    }

    private void createTabIcons() {

        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(0)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(1)));
    }

    public View getTabView(int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.my_tab, null);
        ImageView image = view.findViewById(R.id.image);
        MyHeadingTextView text = view.findViewById(R.id.text);
        ImageView threeDots = view.findViewById(R.id.threeDots);

        if (position == 0) {
            text.setText(getString(R.string.TIMELINE));
            image.setImageResource(R.drawable.ic_user);
        } else if (position == 1) {
            text.setText(getString(R.string.CONNECT));
            image.setImageResource(R.drawable.ic_add);
            text.setTextColor(getResources().getColor(R.color.background_3));
            image.setColorFilter(getResources().getColor(R.color.background_3));
            threeDots.setColorFilter(getResources().getColor(R.color.background_3));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void openPopUp(View view) {
        View v = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);

        MyBoldTextView friend = v.findViewById(R.id.friend);
        MyBoldTextView cso = v.findViewById(R.id.cso);

        PopupWindow pw = new PopupWindow(v, getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pw.showAsDropDown(view, 0, ((View) view.getParent()).getHeight(), Gravity.START);
        } else {
            pw.showAsDropDown(view, 0, ((View) view.getParent()).getHeight());
        }

        friend.setOnClickListener(v1 -> {
            pw.dismiss();
            myToast.show("FRIENDS", Toast.LENGTH_SHORT, true);
        });

        cso.setOnClickListener(v12 -> {
            pw.dismiss();
            myToast.show("CSO'S", Toast.LENGTH_SHORT, true);
        });

    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels / 2;
    }
}
