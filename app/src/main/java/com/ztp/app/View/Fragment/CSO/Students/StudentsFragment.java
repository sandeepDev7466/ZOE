package com.ztp.app.View.Fragment.CSO.Students;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.R;

public class StudentsFragment extends Fragment {

    Context context;
    public static ViewPager viewPager;
    TabLayout tabLayout;
    SharedPref sharedPref;
    boolean theme;

    public StudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cso_students, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        sharedPref = SharedPref.getInstance(context);
        theme = sharedPref.getTheme();
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
                    if(theme)
                    {
                        text.setTextColor(getResources().getColor(R.color.white));
                        image.setColorFilter(getResources().getColor(R.color.white));
                    }
                    else
                    {
                        text.setTextColor(getResources().getColor(R.color.black));
                        image.setColorFilter(getResources().getColor(R.color.black));
                    }
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

                    text.setTextColor(getResources().getColor(R.color.background_3));
                    image.setColorFilter(getResources().getColor(R.color.background_3));

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

        StudentsPager bookingPager = new StudentsPager(getChildFragmentManager());
        bookingPager.addFragment(new TabStudentsFollowersFragment());
        bookingPager.addFragment(new TabStudentsVolunteersFragment());
        viewPager.setAdapter(bookingPager);
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
            text.setText(getString(R.string.tab_students_followers));
            image.setImageResource(R.drawable.ic_user);

        } else if (position == 1) {
            text.setText(getString(R.string.tab_volunteers));
            image.setImageResource(R.drawable.ic_user);

            text.setTextColor(getResources().getColor(R.color.background_3));
            image.setColorFilter(getResources().getColor(R.color.background_3));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
