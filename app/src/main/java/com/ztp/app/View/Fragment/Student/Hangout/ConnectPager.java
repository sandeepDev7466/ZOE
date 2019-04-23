package com.ztp.app.View.Fragment.Student.Hangout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

class ConnectPager extends FragmentPagerAdapter {

    private List<Map<String, Object>> myFriendData, potentialFriendData;
    int count;

    public ConnectPager(FragmentManager childFragmentManager, List<Map<String, Object>> myFriendData, List<Map<String, Object>> potentialFriendData, int count) {
        super(childFragmentManager);
        this.count = count;
        this.myFriendData = myFriendData;
        this.potentialFriendData = potentialFriendData;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (i == 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("myFriendData", (Serializable) myFriendData);
            fragment = new MyFriendAndMyCSOFragment();
            fragment.setArguments(bundle);
        } else if (i == 1) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("potentialFriendData", (Serializable) potentialFriendData);
            fragment = new PotentialFriendAndPotentialCSOFragment();
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
