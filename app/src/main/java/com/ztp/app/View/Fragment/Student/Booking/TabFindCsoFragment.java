package com.ztp.app.View.Fragment.Student.Booking;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.SearchEventRequest;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Student.Extra.SearchedEventAdapter;
import com.ztp.app.Viewmodel.SearchEventViewModel;

import java.util.ArrayList;
import java.util.List;

public class TabFindCsoFragment extends Fragment implements AbsListView.OnScrollListener {
    MyProgressDialog myProgressDialog;
    ProgressBar progress;
    MyToast myToast;
    Context context;
    SharedPref sharedPref;
    ListView listView;
    final static int DIFF = 5;
    int offset = 0, limit = DIFF;
    List<SearchEventResponse.SearchedEvent> searchEventList = new ArrayList<>();
    SearchEventViewModel searchEventViewModel;
    SearchedEventAdapter searchedEventAdapter;
    boolean hit = true;
    MyTextView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_find_cso, container, false);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        search = view.findViewById(R.id.search);
        listView = view.findViewById(R.id.listView);
        searchEventViewModel = ViewModelProviders.of(this).get(SearchEventViewModel.class);
        progress = view.findViewById(R.id.progress);
        listView.setOnScrollListener(this);
        if ((Utility.isNetworkAvailable(context))) {
            myProgressDialog.show(getString(R.string.please_wait));
            listView.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);
            search("", offset, limit, false);
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_LONG, false);
            listView.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void search(String query, int offset, int limit, boolean type) {

        if (hit) {
            searchEventViewModel.getSearchedEventsResponse(new SearchEventRequest(sharedPref.getUserId(), query, String.valueOf(offset), String.valueOf(limit))).observe(this, searchEventResponse -> {
                if (type) {
                    progress.setVisibility(View.GONE);
                }
                if (searchEventResponse != null) {
                    if (searchEventResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (searchEventResponse.getSearchedEvents() != null) {
                            if (type) {
                                searchEventList.addAll(searchEventResponse.getSearchedEvents());
                            } else {
                                searchEventList = searchEventResponse.getSearchedEvents();
                            }
                            listView.setVisibility(View.VISIBLE);
                            search.setVisibility(View.GONE);
                            searchedEventAdapter = new SearchedEventAdapter(context, searchEventList);
                            listView.setAdapter(searchedEventAdapter);
                            listView.setSelection(offset > 0 ? offset - 1 : 0);
                            hit = true;
                        } else {
                            hit = false;
                        }
                    } else {
                        if (!type) {
                            listView.setVisibility(View.GONE);
                            search.setVisibility(View.VISIBLE);
                            search.setText(R.string.no_events_found);
                        }
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                    listView.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                    hit = true;
                }
                if (myProgressDialog.isShowing())
                    myProgressDialog.dismiss();
            });
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                listView.getFooterViewsCount()) >= (searchedEventAdapter.getCount() - 1)) {

            if (searchedEventAdapter.getCount() == limit) {
                offset = offset + limit;
                limit = offset + DIFF;
                if (Utility.isNetworkAvailable(context)) {
                    progress.setVisibility(View.VISIBLE);
                    search("", offset, limit, true);
                } else
                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_LONG, false);
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
