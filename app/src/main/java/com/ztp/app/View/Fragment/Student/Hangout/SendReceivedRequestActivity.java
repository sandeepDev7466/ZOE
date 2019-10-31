package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.Pager;
import com.ztp.app.R;

public class SendReceivedRequestActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    SharedPref sharedPref;
    boolean theme;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_received_request);
        context = this;
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
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
                   // ImageView image = v.findViewById(R.id.image);
                    MyHeadingTextView text = v.findViewById(R.id.text);
                    if (theme) {
                        text.setTextColor(getResources().getColor(R.color.white));
                        //image.setColorFilter(getResources().getColor(R.color.white));
                    } else {
                        text.setTextColor(getResources().getColor(R.color.black));
                        //image.setColorFilter(getResources().getColor(R.color.black));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {

                    View v = tab.getCustomView();
                    //ImageView image = v.findViewById(R.id.image);
                    MyHeadingTextView text = v.findViewById(R.id.text);

                    text.setTextColor(getResources().getColor(R.color.background_3));
                   // image.setColorFilter(getResources().getColor(R.color.background_3));

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
    }

    private void setViewPager() {

        Pager pager = new Pager(getSupportFragmentManager());
        pager.addFragment(new SendRequestFragment());
        pager.addFragment(new ReceivedRequestFragment());
        viewPager.setAdapter(pager);
    }

    private void createTabIcons() {

        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(0)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(1)));

    }

    public View getTabView(int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.my_tab, null);
        //ImageView image = view.findViewById(R.id.image);
        MyHeadingTextView text = view.findViewById(R.id.text);

        if (position == 0) {
            text.setText("SEND");
            //image.setImageResource(R.drawable.ic_user);

        } else if (position == 1) {
            text.setText("RECEIVED");
            //image.setImageResource(R.drawable.ic_user);

            text.setTextColor(getResources().getColor(R.color.background_3));
            //image.setColorFilter(getResources().getColor(R.color.background_3));
        }

        return view;
    }
}
