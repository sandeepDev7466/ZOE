package com.ztp.app.View.Fragment.Student.Dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.List;

public class DashboardFragmentPager extends FragmentPagerAdapter {
    int count;
    List<HashMap<String, Object>> data;

    public DashboardFragmentPager(FragmentManager fm, int count, List<HashMap<String, Object>> data) {
        super(fm);
        this.count = count;
        this.data = data;
    }

    @Override
    public Fragment getItem(int i) {
        DashboardImageFragment fragment = new DashboardImageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data.get(i));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
