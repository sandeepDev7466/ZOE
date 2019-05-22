package com.ztp.app.View.Fragment.CSO.Students;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoMyVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoMyVolunteerResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CsoMyVolunteerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabStudentsFollowersFragment extends Fragment {

    Context context;
    ListView listView;
    MyTextView noData;
    CsoMyVolunteerViewModel csoMyVolunteerViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    SharedPref sharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student, container, false);
        listView = view.findViewById(R.id.lv_student);
        noData = view.findViewById(R.id.noData);
        csoMyVolunteerViewModel = ViewModelProviders.of((FragmentActivity) context).get(CsoMyVolunteerViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);

        if(Utility.isNetworkAvailable(context))
        {
            myProgressDialog.show(getString(R.string.please_wait));
            csoMyVolunteerViewModel.getCsoMyVolunteerResponse(new CsoMyVolunteerRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, new Observer<CsoMyVolunteerResponse>() {
                @Override
                public void onChanged(@Nullable CsoMyVolunteerResponse csoMyVolunteerResponse) {

                    if(csoMyVolunteerResponse!=null)
                    {
                        if(csoMyVolunteerResponse.getResStatus().equalsIgnoreCase("200"))
                        {
                            if(csoMyVolunteerResponse.getResData()!=null && csoMyVolunteerResponse.getResData().size()>0)
                            {
                                listView.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.INVISIBLE);

                                FollowerAdapter adapter = new FollowerAdapter(context,csoMyVolunteerResponse.getResData());
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
                            listView.setVisibility(View.INVISIBLE);
                            noData.setVisibility(View.VISIBLE);
                            myToast.show(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT,false);
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
            listView.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.VISIBLE);
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT,false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
