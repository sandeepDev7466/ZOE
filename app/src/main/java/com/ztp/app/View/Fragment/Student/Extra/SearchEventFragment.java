package com.ztp.app.View.Fragment.Student.Extra;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.ztp.app.Viewmodel.SearchEventViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchEventFragment extends Fragment implements AbsListView.OnScrollListener {

    SearchView searchView;
    ListView lv_events;
    Context context;
    SearchEventViewModel searchEventViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    ProgressBar progress, progressBottom;
    MyTextView search;
    InputMethodManager im;
    SearchedEventAdapter searchedEventAdapter;
    final static int DIFF = 5;
    int offset = 0, limit = DIFF;
    String queryString;
    boolean hit = true;
    List<SearchEventResponse.SearchedEvent> searchEventList = new ArrayList<>();
    SharedPref sharedPref;

    public SearchEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_event, container, false);
        searchView = view.findViewById(R.id.searchView);
        lv_events = view.findViewById(R.id.lv_events);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        progress = view.findViewById(R.id.progress);
        progressBottom = view.findViewById(R.id.progressBottom);
        search = view.findViewById(R.id.search);
        searchEventViewModel = ViewModelProviders.of(this).get(SearchEventViewModel.class);
        im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                im.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                queryString = query;
                if ((Utility.isNetworkAvailable(context))) {
                    searchEventList = new ArrayList<>();
                    lv_events.setAdapter(null);
                    search(queryString, offset, limit, false);
                } else
                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_LONG, false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    //lv_events.setAdapter(null);
                    //searchEventList = new ArrayList<>();
                    lv_events.setVisibility(View.GONE);
                    search.setVisibility(View.GONE);
                    offset = 0;
                    limit = DIFF;
                }
                return true;
            }
        });

        lv_events.setOnScrollListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && (lv_events.getLastVisiblePosition() - lv_events.getHeaderViewsCount() -
                lv_events.getFooterViewsCount()) >= (searchedEventAdapter.getCount() - 1)) {

            if (searchedEventAdapter.getCount() == limit) {
                offset = offset + limit;
                limit = offset + DIFF;
                if (Utility.isNetworkAvailable(context)) {
                    progressBottom.setVisibility(View.VISIBLE);
                    search(queryString, offset, limit, true);
                } else
                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_LONG, false);
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private void search(String query, int offset, int limit, boolean type) {

        if (progress.getVisibility() == View.GONE)
            progress.setVisibility(View.VISIBLE);
        search.setVisibility(View.GONE);

        if (hit) {
            searchEventViewModel.getSearchedEventsResponse(new SearchEventRequest(sharedPref.getUserId(), query, String.valueOf(offset), String.valueOf(limit))).observe(this, searchEventResponse -> {
                progress.setVisibility(View.GONE);
                if (type) {
                    progressBottom.setVisibility(View.GONE);
                }
                if (searchEventResponse != null) {
                    if (searchEventResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (searchEventResponse.getSearchedEvents() != null) {
                            if (type) {
                                searchEventList.addAll(searchEventResponse.getSearchedEvents());
                            } else {
                                searchEventList = searchEventResponse.getSearchedEvents();
                            }
                            lv_events.setVisibility(View.VISIBLE);
                            search.setVisibility(View.GONE);
                            searchedEventAdapter = new SearchedEventAdapter(context, searchEventList);
                            lv_events.setAdapter(searchedEventAdapter);
                            lv_events.setSelection(offset>0?offset-1:0);
                            hit = true;
                        } else {
                            hit = false;
                        }
                    } else {
                        if(!type) {
                            lv_events.setVisibility(View.GONE);
                            search.setVisibility(View.VISIBLE);
                            search.setText(R.string.no_events_found);
                        }
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_LONG, false);
                    lv_events.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                    hit = true;
                }
            });
        }
    }
}
