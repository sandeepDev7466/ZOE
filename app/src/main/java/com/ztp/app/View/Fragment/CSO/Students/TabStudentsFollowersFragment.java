package com.ztp.app.View.Fragment.CSO.Students;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ztp.app.Data.Local.Room.Async.Get.DBGetAllFollower;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoMyVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;
import com.ztp.app.Helper.MyEditText;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CsoMyVolunteerViewModel;

import java.util.ArrayList;
import java.util.List;

public class TabStudentsFollowersFragment extends Fragment {

    Context context;
    ListView listView;
    MyTextView noData;
    CsoMyVolunteerViewModel csoMyVolunteerViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    SharedPref sharedPref;
    DBGetAllFollower dbGetAllFollower;
    MyEditText searchView;
    InputMethodManager im;
    List<CsoMyFollowerResponse.SeeFollower> seeFollowerList = new ArrayList<>();
    List<CsoMyFollowerResponse.SeeFollower> seeFollowerListSeached = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbGetAllFollower = new DBGetAllFollower(context);
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        listView = view.findViewById(R.id.lv_student);
        noData = view.findViewById(R.id.noData);
        csoMyVolunteerViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoMyVolunteerViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        searchView = view.findViewById(R.id.searchView);
        im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);


        if(Utility.isNetworkAvailable(context))
        {
            myProgressDialog.show(getString(R.string.please_wait));
            csoMyVolunteerViewModel.getCsoMyVolunteerResponse(context,new CsoMyVolunteerRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, new Observer<CsoMyFollowerResponse>() {
                @Override
                public void onChanged(@Nullable CsoMyFollowerResponse csoMyFollowerResponse) {

                    if(csoMyFollowerResponse !=null)
                    {
                        if(csoMyFollowerResponse.getResStatus().equalsIgnoreCase("200"))
                        {
                            if(csoMyFollowerResponse.getResData()!=null && csoMyFollowerResponse.getResData().size()>0)
                            {
                                listView.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.INVISIBLE);
                                seeFollowerList = csoMyFollowerResponse.getResData();
                                FollowerAdapter adapter = new FollowerAdapter(context, seeFollowerList);
                                listView.setAdapter(adapter);
                            }
                            else
                            {
                                listView.setVisibility(View.INVISIBLE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        }
                        else if(csoMyFollowerResponse.getResStatus().equalsIgnoreCase("401"))
                        {
                            listView.setVisibility(View.INVISIBLE);
                            noData.setVisibility(View.VISIBLE);
//                            myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT,false);
                        }
                    }
                    else
                    {
                        listView.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT,false);
                    }
                    myProgressDialog.dismiss();
                }
            });
        }
        else
        {

            if(dbGetAllFollower!=null && dbGetAllFollower.getFollowerList().size()>0)
            {
                listView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.INVISIBLE);

                FollowerAdapter adapter = new FollowerAdapter(context, dbGetAllFollower.getFollowerList());
                listView.setAdapter(adapter);
            }
            else
            {
                listView.setVisibility(View.INVISIBLE);
                noData.setVisibility(View.VISIBLE);
            }
        }




        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                seeFollowerListSeached = new ArrayList<>();

                if(s!=null && s.length()>0)
                {
                    for(int i=0;i<seeFollowerList.size();i++)
                    {
                        if(seeFollowerList.get(i).getUserFName().toLowerCase().contains(s.toString().toLowerCase()))
                        {
                            seeFollowerListSeached.add(seeFollowerList.get(i));
                        }
                    }
                    if(seeFollowerListSeached != null && seeFollowerListSeached.size()>0) {

                        listView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.INVISIBLE);
                        FollowerAdapter adapter = new FollowerAdapter(context, seeFollowerListSeached);
                        listView.setAdapter(adapter);

                    }
                    else
                    {
                        listView.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                    }
                }
                else
                {

                    listView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.INVISIBLE);
                    FollowerAdapter adapter = new FollowerAdapter(context, seeFollowerList);
                    listView.setAdapter(adapter);

                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
