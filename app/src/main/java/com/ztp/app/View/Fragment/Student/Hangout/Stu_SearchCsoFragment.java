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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.ApprovedCSORequest;
import com.ztp.app.Data.Remote.Model.Request.SearchCSORequest;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchCSOResponse;
import com.ztp.app.Helper.MyEditText;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.ApprovedCSOViewModel;
import com.ztp.app.Viewmodel.SearchCSOViewModel;

import java.util.ArrayList;
import java.util.List;

public class Stu_SearchCsoFragment extends Fragment {

    Context context;
    ListView listView;
    TextView noData;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    SharedPref sharedPref;
    SearchCSOViewModel searchCSOViewModel;
    List<SearchCSOResponse.ResData> searchCSOList = new ArrayList<>();
    List<SearchCSOResponse.ResData> searchCSOListSearched = new ArrayList<>();
    MyEditText searchView;
    InputMethodManager im;

    public Stu_SearchCsoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stu__search_cso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        noData = view.findViewById(R.id.noData);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        searchView = view.findViewById(R.id.searchView);
        im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        searchCSOViewModel = ViewModelProviders.of((FragmentActivity) context).get(SearchCSOViewModel.class);
        getSearchedCSO();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchCSOListSearched = new ArrayList<>();

                if (s != null && s.length() > 0) {
                    for (int i = 0; i < searchCSOList.size(); i++) {
                        if (searchCSOList.get(i).getCsoName().toLowerCase().contains(s.toString().toLowerCase())) {
                            searchCSOListSearched.add(searchCSOList.get(i));
                        }
                    }
                    if (searchCSOListSearched != null && searchCSOListSearched.size() > 0) {

                        listView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.INVISIBLE);
                        StudentSearchCSOAdapter studentSearchCSOAdapter = new StudentSearchCSOAdapter(context, searchCSOListSearched);
                        listView.setAdapter(studentSearchCSOAdapter);

                    } else {
                        listView.setVisibility(View.INVISIBLE);
                        noData.setVisibility(View.VISIBLE);
                    }
                } else {

                    listView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.INVISIBLE);
                    StudentSearchCSOAdapter studentSearchCSOAdapter = new StudentSearchCSOAdapter(context, searchCSOList);
                    listView.setAdapter(studentSearchCSOAdapter);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void getSearchedCSO() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            SearchCSORequest request = new SearchCSORequest();
            request.setUserId(sharedPref.getUserId());
            request.setSeachRowNumber("0");
            request.setSearchCity("");
            request.setSearchEventType("");
            request.setSearchPostcode("");
            request.setSearchState("");
            request.setSearchKeyword("");
            request.setSearchPageSize("10");
            request.setSearchOrg("");

            Log.i("", "" + new Gson().toJson(request));

            searchCSOViewModel.getSearchCSOResponse(request).observe((LifecycleOwner) context, searchCSOResponse -> {
                if (searchCSOResponse != null) {
                    if (searchCSOResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (searchCSOResponse.getResData() != null) {

                            searchCSOList = searchCSOResponse.getResData();
                            noData.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            StudentSearchCSOAdapter studentSearchCSOAdapter = new StudentSearchCSOAdapter(context, searchCSOList);
                            listView.setAdapter(studentSearchCSOAdapter);
                        }
                    } else if (searchCSOResponse.getResStatus().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
