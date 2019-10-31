package com.ztp.app.View.Fragment.Volunteer.Hangout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

//class ConnectPager extends FragmentPagerAdapter {
//
//    private List<Map<String, Object>>  potentialFriendData;
//    List<MyCSOResponse_V.TargetData> myFriendData;
//    int count;
//
//    public ConnectPager(FragmentManager childFragmentManager, List<MyCSOResponse_V.TargetData> myFriendData, List<Map<String, Object>> potentialFriendData, int count) {
//        super(childFragmentManager);
//        this.count = count;
//        this.myFriendData = myFriendData;
//        this.potentialFriendData = potentialFriendData;
//    }
//
//    @Override
//    public Fragment getItem(int i) {
//        Fragment fragment = null;
//        if (i == 0) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("myFriendData", (Serializable) myFriendData);
//            fragment = new MyCSOFragment();
//            fragment.setArguments(bundle);
//        } else if (i == 1) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("potentialFriendData", (Serializable) potentialFriendData);
//            fragment = new PotentialCSOFragment();
//            fragment.setArguments(bundle);
//        }
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        return count;
//    }
//}
class ConnectPager extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();

    public ConnectPager(FragmentManager childFragmentManager) {
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

