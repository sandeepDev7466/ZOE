package com.ztp.app.View.Fragment.Common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.View.Activity.Common.PagerInfoActivity;

import java.util.HashMap;

public class InfoPagerFragment extends Fragment {

    Context context;
    HashMap<String, Object> map;
    LinearLayout mainLayout;
    MyTextView description;
    MyBoldTextView skip;
    MyHeadingTextView next, title;
    ImageView image;
    int position, total;
    MyProgressDialog progressDialog;
    MyToast myToast;
    SharedPref sharedPref;

    public InfoPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_pager, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        skip = view.findViewById(R.id.skip);
        description = view.findViewById(R.id.description);
        title = view.findViewById(R.id.title);
        next = view.findViewById(R.id.next);
        image = view.findViewById(R.id.image);
        progressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);

        Bundle bundle = getArguments();
        if (bundle != null) {
            map = (HashMap<String, Object>) bundle.getSerializable("data");
            position = Integer.parseInt(bundle.getString("position"));
            total = Integer.parseInt(bundle.getString("total"));
            mainLayout.setBackgroundColor(Color.parseColor(String.valueOf(map.get("color"))));
            if (position == total - 1)
                next.setText(R.string.imdone);
            else
                next.setText(R.string.cont);

            title.setText(String.valueOf(map.get("title")));
            description.setText(String.valueOf(map.get("description")));
            image.setImageResource(Integer.parseInt(String.valueOf(map.get("image"))));

        }


        next.setOnClickListener(v -> {

            if (position == total - 1) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                sharedPref.setFirstRun(true);
            } else {
                next.setText(R.string.cont);
                ((PagerInfoActivity) context).getViewPager().setCurrentItem(((PagerInfoActivity) context).getViewPager().getCurrentItem() + 1);
            }

        });

        skip.setOnClickListener(v -> {

            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            sharedPref.setFirstRun(true);

        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
