package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.Pager;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

public class Stu_ConnectFragment extends Fragment {

    Context context;
    ImageView threeDots;
    TextView type;
    TabLayout tabLayout;
    ViewPager viewPagerFriend, viewPagerCso;

    public Stu_ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu__connect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        threeDots = view.findViewById(R.id.threeDots);
        type = view.findViewById(R.id.type);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPagerFriend = view.findViewById(R.id.viewPagerFriend);
        viewPagerCso = view.findViewById(R.id.viewPagerCso);
        type.setText(R.string.my_friends);
        setFriendsViewPager();
        tabLayout.setupWithViewPager(viewPagerFriend, true);

        viewPagerFriend.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    type.setText(R.string.my_friends);
                } else if (i == 1) {
                    type.setText(R.string.potential_friends);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPagerCso.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    type.setText(R.string.my_cso);
                } else if (i == 1) {
                    type.setText(getString(R.string.search_csos));
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        threeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUp(v);
            }
        });
    }

    private void setFriendsViewPager() {
        viewPagerFriend.setVisibility(View.VISIBLE);
        viewPagerCso.setVisibility(View.GONE);
        tabLayout.setupWithViewPager(viewPagerFriend, true);
        Pager connectPager = new Pager(getChildFragmentManager());
        connectPager.addFragment(new Stu_MyFriendsFragment());
        connectPager.addFragment(new Stu_PotentialFriendsFragment());
        viewPagerFriend.setAdapter(connectPager);
    }

    private void setCsoViewPager() {
        viewPagerFriend.setVisibility(View.GONE);
        viewPagerCso.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPagerCso, true);
        Pager connectPager = new Pager(getChildFragmentManager());
        connectPager.addFragment(new Stu_MyCsoFragment());
        connectPager.addFragment(new Stu_SearchCsoFragment());
        viewPagerCso.setAdapter(connectPager);
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

        PopupWindow pw = new PopupWindow(v, Utility.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pw.showAsDropDown(view, 0, 0, Gravity.START);
        } else {
            pw.showAsDropDown(view, 0, 0);
        }

        friend.setOnClickListener(v1 -> {
            pw.dismiss();
            type.setText(R.string.my_friends);
            setFriendsViewPager();
        });

        cso.setOnClickListener(v12 -> {
            pw.dismiss();
            type.setText(R.string.my_cso);
            setCsoViewPager();
        });
    }
}
