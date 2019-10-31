package com.ztp.app.View.Fragment.Volunteer.Hangout;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.MyCSORequest_V;
import com.ztp.app.Data.Remote.Model.Response.MyCSOResponse_V;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.MyCso_VViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyCSOFragment extends Fragment {

    Context context;

    ListView listView;
    MyToast myToast;

    MyProgressDialog myProgressDialog;
    MyTextView noData;
    SharedPref sharedPref;
    MyCso_VViewModel myCso_vViewModel;
    List<MyCSOResponse_V.ResData> friendData = new ArrayList<>();

    public MyCSOFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_friend, container, false);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        myCso_vViewModel = ViewModelProviders.of(this).get(MyCso_VViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        getMyFriendData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void getMyFriendData() {

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            myCso_vViewModel.getMyCSO_V(new MyCSORequest_V(sharedPref.getUserId())).observe(this, myCSOResponse_v -> {
                if (myCSOResponse_v != null) {
                    if (myCSOResponse_v.getResStatus().equalsIgnoreCase("200")) {
                        if (myCSOResponse_v.getResData() != null && myCSOResponse_v.getResData().size() > 0) {

                            MyCSOAdapter myFriendAndMyCSOAdapter = new MyCSOAdapter(context, myCSOResponse_v.getResData());
                            listView.setAdapter(myFriendAndMyCSOAdapter);

                        } else {
                            noData.setVisibility(View.VISIBLE);
                        }
                    } else if (myCSOResponse_v.getResStatus().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                    }
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }

    }
}
