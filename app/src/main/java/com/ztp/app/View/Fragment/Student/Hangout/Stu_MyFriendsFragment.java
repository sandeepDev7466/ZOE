package com.ztp.app.View.Fragment.Student.Hangout;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.MyFriendsRequest;
import com.ztp.app.Data.Remote.Model.Response.MyFriendsResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.MyFriendsViewModel;

public class Stu_MyFriendsFragment extends Fragment {

    Context context;
    ListView listView;
    TextView noData;
    MyFriendsViewModel myFriendsViewModel;
    SharedPref sharedPref;
    MyToast myToast;
    MyProgressDialog myProgressDialog;

    public Stu_MyFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu__my_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        myFriendsViewModel = ViewModelProviders.of((FragmentActivity) context).get(MyFriendsViewModel.class);
        if (Utility.isNetworkAvailable(context))
            getMyFriends();
        else
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void getMyFriends() {
        myProgressDialog.show(getString(R.string.please_wait));
        myFriendsViewModel.getMyFriendsResponse(new MyFriendsRequest(sharedPref.getUserId(), sharedPref.getUserType(), Utility.getDeviceId(context))).observe((LifecycleOwner) context, (Observer<MyFriendsResponse>) myFriendsResponse -> {
            if (myFriendsResponse != null) {
                if (myFriendsResponse.getResStatus().equalsIgnoreCase("200")) {
                    if (myFriendsResponse.getResData() != null && myFriendsResponse.getResData().size() > 0) {

                        MyFriendAdapter myFriendAdapter = new MyFriendAdapter(context, myFriendsResponse.getResData());
                        listView.setAdapter(myFriendAdapter);

                        noData.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                    } else {
                        noData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            } else {
                myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                noData.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            myProgressDialog.dismiss();
        });
    }
}
