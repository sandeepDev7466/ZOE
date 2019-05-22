package com.ztp.app.View.Fragment.Student.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Student.StudentDashboardActivity;
import com.ztp.app.View.Fragment.Student.Booking.TabFindCsoFragment;
import com.ztp.app.View.Fragment.Student.Extra.SearchEventFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    Context context;
    ViewPager viewPager;
    TabLayout tabLayout;
    int count;
    List<HashMap<String, Object>> data = new ArrayList<>();
    LinearLayout volunter, upload, target, message, cso;
    MyToast myToast;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        myToast = new MyToast(context);
        init(view);

        return view;

    }

    public void init(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        volunter = view.findViewById(R.id.volunter);
        upload = view.findViewById(R.id.upload);
        target = view.findViewById(R.id.target);
        message = view.findViewById(R.id.message);
        cso = view.findViewById(R.id.cso);

        volunter.setOnClickListener(this);
        upload.setOnClickListener(this);
        target.setOnClickListener(this);
        message.setOnClickListener(this);
        cso.setOnClickListener(this);


        count = getData().size();
        DashboardFragmentPager hangoutFragmentPager = new DashboardFragmentPager(getChildFragmentManager(), count, getData());
        viewPager.setAdapter(hangoutFragmentPager);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private List<HashMap<String, Object>> getData() {

        data = new ArrayList<>();

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("image", R.drawable.scene_2);
        data.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("image", R.drawable.scene_2);
        data.add(map2);

        return data;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.volunter:
                Utility.replaceFragment(context, new SearchEventFragment(), "SearchEventFragment");
                break;
            case R.id.upload:
                ((StudentDashboardActivity) context).setLockerFragment();
                break;
            case R.id.target:
                ((StudentDashboardActivity) context).setTargetFragment();
                break;
            case R.id.message:
                ((StudentDashboardActivity) context).setHangoutFragment();
                break;
            case R.id.cso:
                Utility.replaceFragment(context, new TabFindCsoFragment(), "TabFindCsoFragment");
                break;

        }
    }
}
