package com.ztp.app.View.Fragment.CSO.Message;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class MessagePager extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();

    public MessagePager(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
