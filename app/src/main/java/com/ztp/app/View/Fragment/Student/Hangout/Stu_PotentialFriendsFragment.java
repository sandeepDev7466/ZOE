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
import com.ztp.app.Data.Remote.Model.Request.PotentialFriendsRequest;
import com.ztp.app.Data.Remote.Model.Response.PotentialFriendsResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetPotentialFriendsViewModel;

public class Stu_PotentialFriendsFragment extends Fragment {

    Context context;
    ListView listView;
    TextView noData;
    GetPotentialFriendsViewModel getPotentialFriendsViewModel;
    SharedPref sharedPref;
    MyToast myToast;
    MyProgressDialog myProgressDialog;

    public Stu_PotentialFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu__potential_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);

        getPotentialFriendsViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetPotentialFriendsViewModel.class);
        if (Utility.isNetworkAvailable(context))
            getPotentialFriends();
        else
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void getPotentialFriends() {
        //myProgressDialog.show(getString(R.string.please_wait));
        getPotentialFriendsViewModel.getPotentialFriends(new PotentialFriendsRequest(sharedPref.getUserId(), sharedPref.getUserType(), Utility.getDeviceId(context))).observe((LifecycleOwner) context, new Observer<PotentialFriendsResponse>() {
            @Override
            public void onChanged(@Nullable PotentialFriendsResponse getPotentialFriendsResponse) {

                if (getPotentialFriendsResponse != null) {
                    if (getPotentialFriendsResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (getPotentialFriendsResponse.getResData() != null && getPotentialFriendsResponse.getResData().size() > 0) {

                            PotentialFriendAdapter myPotentialFriendAdapter = new PotentialFriendAdapter(context, getPotentialFriendsResponse.getResData());
                            listView.setAdapter(myPotentialFriendAdapter);

                            noData.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);

                        } else {
                            noData.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }

                    } else if (getPotentialFriendsResponse.getResStatus().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    noData.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                //myProgressDialog.dismiss();
            }
        });
    }
}
