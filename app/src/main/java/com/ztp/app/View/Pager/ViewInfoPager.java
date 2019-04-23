package com.ztp.app.View.Pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.ztp.app.View.Fragment.Common.InfoPagerFragment;

import java.util.HashMap;
import java.util.List;

public class ViewInfoPager extends FragmentPagerAdapter {
    int count;
    List<HashMap<String, Object>> data;

    public ViewInfoPager(FragmentManager supportFragmentManager, int count, List<HashMap<String, Object>> data) {
        super(supportFragmentManager);
        this.count = count;
        this.data = data;
    }

    @Override
    public Fragment getItem(int i) {
        InfoPagerFragment fragment = new InfoPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data.get(i));
        bundle.putString("position", String.valueOf(i));
        bundle.putString("total", String.valueOf(count));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
