package com.ztp.app.View.Fragment.Volunteer.Hangout;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectFragment extends Fragment {

    Context context;
    MyHeadingTextView typeFriend;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Map<String, Object>> myFriendList = new ArrayList<>();
    List<Map<String, Object>> potentialFriendList = new ArrayList<>();

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connect, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        typeFriend = view.findViewById(R.id.typeFriend);
        typeFriend.setText(R.string.my_friends);

//        ConnectPager connectPager = new ConnectPager(getChildFragmentManager(), getMyFriendData(), getPotentialFriendData(), 2);
//        viewPager.setAdapter(connectPager);

        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if(i==0)
                {
                    typeFriend.setText(R.string.my_friends);
                }
                else if(i==1)
                {
                    typeFriend.setText(R.string.potential_friends);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }

    private List<Map<String, Object>> getMyFriendData() {
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "Andy Dwer");
//        map1.put("userImage", R.drawable.user);
//        myFriendList.add(map1);
//
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("name", "Sam Curan");
//        map2.put("userImage", R.drawable.user);
//        myFriendList.add(map2);
//
//        Map<String, Object> map3 = new HashMap<>();
//        map3.put("name", "Potter Dan");
//        map3.put("userImage", R.drawable.user);
//        myFriendList.add(map3);
//
//        Map<String, Object> map4 = new HashMap<>();
//        map4.put("name", "Ron Dakly");
//        map4.put("userImage", R.drawable.user);
//        myFriendList.add(map4);
//
//        Map<String, Object> map5 = new HashMap<>();
//        map5.put("name", "John Dwerly");
//        map5.put("userImage", R.drawable.user);
//        myFriendList.add(map5);

        return myFriendList;
    }

    private List<Map<String, Object>> getPotentialFriendData() {

//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("name", "Andy Dwer");
//        map1.put("userImage", R.drawable.user);
//        potentialFriendList.add(map1);
//
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("name", "Sam Curan");
//        map2.put("userImage", R.drawable.user);
//        potentialFriendList.add(map2);
//
//        Map<String, Object> map3 = new HashMap<>();
//        map3.put("name", "Potter Dan");
//        map3.put("userImage", R.drawable.user);
//        potentialFriendList.add(map3);
//
//        Map<String, Object> map4 = new HashMap<>();
//        map4.put("name", "Ron Dakly");
//        map4.put("userImage", R.drawable.user);
//        potentialFriendList.add(map4);
//
//        Map<String, Object> map5 = new HashMap<>();
//        map5.put("name", "John Dwerly");
//        map5.put("userImage", R.drawable.user);
//        potentialFriendList.add(map5);

        return potentialFriendList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
