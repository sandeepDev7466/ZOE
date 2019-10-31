package com.ztp.app.View.Fragment.Volunteer.Locker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;

public class MyRatingFragment extends Fragment {

    Context context;
    MyTextView noData;
    TextView hours;
    ImageView rankImage;
    SharedPref sharedPref;

    public MyRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_rating, container, false);
        noData = view.findViewById(R.id.noData);
        hours = view.findViewById(R.id.hours);
        rankImage = view.findViewById(R.id.rank);
        sharedPref = SharedPref.getInstance(context);

        if(sharedPref.getVolHours().isEmpty() && sharedPref.getVolRank().isEmpty())
        {
            noData.setVisibility(View.VISIBLE);
            hours.setVisibility(View.GONE);
            rankImage.setVisibility(View.GONE);
        }
        if(sharedPref.getVolHours().isEmpty())
        {
            hours.setVisibility(View.GONE);
            rankImage.setVisibility(View.VISIBLE);
        }
        if(sharedPref.getVolRank().isEmpty())
        {
            hours.setVisibility(View.VISIBLE);
            rankImage.setVisibility(View.GONE);
        }

        hours.setText(sharedPref.getVolHours() + " VOLUNTEER HOURS COMPLETED");

        if(!sharedPref.getVolRank().isEmpty()) {
            int rank = (int) Math.ceil(Double.parseDouble(sharedPref.getVolRank()));
            if (rank == 1)
                Picasso.with(context).load(R.drawable.rank_one_vol).into(rankImage);
            else if (rank == 2)
                Picasso.with(context).load(R.drawable.rank_two_vol).into(rankImage);
            else if (rank == 3)
                Picasso.with(context).load(R.drawable.rank_three_vol).into(rankImage);
            else if (rank == 4)
                Picasso.with(context).load(R.drawable.rank_four_vol).into(rankImage);
            else if (rank == 5)
                Picasso.with(context).load(R.drawable.rank_five_vol).into(rankImage);
            else
                Picasso.with(context).load(R.drawable.rank_five_vol).into(rankImage);
        }
        else
        {
            Picasso.with(context).load(R.drawable.rank_five_vol).into(rankImage);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
