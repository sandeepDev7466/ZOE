package com.ztp.app.View.Fragment.Volunteer.Target;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;

public class CountdownFagment extends Fragment {

    Context context;
    MyBoldTextView tv_gradDays, tv_semDays;
    LinearLayout countdownLayout;
    SharedPref sharedPref;
    public CountdownFagment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countdown, container, false);
        tv_gradDays = view.findViewById(R.id.tv_gradDays);
        tv_semDays = view.findViewById(R.id.tv_semDays);
        countdownLayout = view.findViewById(R.id.countdownLayout);
        sharedPref = SharedPref.getInstance(context);
        if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer))
            setVolunteer();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    private void setVolunteer() {
        countdownLayout.setAlpha(Constants.alpha);
    }
}
