package com.ztp.app.View.Fragment.Volunteer.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ztp.app.R;

import java.util.HashMap;
import java.util.Map;

public class DashboardImageFragment extends Fragment {

    Context context;
    ImageView image;
    Map<String, Object> map;

    public DashboardImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_image, container, false);
        image = view.findViewById(R.id.image);

        Bundle bundle = getArguments();
        if (bundle != null) {
            map = (HashMap<String, Object>) bundle.getSerializable("data");
            image.setImageResource(Integer.parseInt(String.valueOf(map.get("image"))));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
