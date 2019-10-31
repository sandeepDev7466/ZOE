package com.ztp.app.View.Fragment.Volunteer.Extra;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.SearchEventbyAreaRequest;
import com.ztp.app.Data.Remote.Model.Response.SearchEventbyAreaResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.SearchEventByAreaViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchEventByAreaFragment extends Fragment implements AbsListView.OnScrollListener {

    SearchView searchView;
    ListView lv_events;
    Context context;
    SearchEventByAreaViewModel searchEventViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    ProgressBar progress, progressBottom;
    MyTextView search, tv_search, tv_search_event;
    InputMethodManager im;
    SearchEventByAreaAdapter searchedEventAdapter;
    final static int DIFF = 5;
    int offset = 0, limit = DIFF;
    boolean hit = true;
    View view_bg;
    List<SearchEventbyAreaResponse.SearchedEventByArea> searchEventList = new ArrayList<>();
    SharedPref sharedPref;
    GPSTracker gpsTracker;
    double latitude, longitude;
    Address fetchedAddress;
    StringBuilder strAddress;
    String postalCode;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    public SearchEventByAreaFragment() {
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
        tv_search_event = view.findViewById(R.id.tv_search_event);
        tv_search = view.findViewById(R.id.tv_search);
        search = view.findViewById(R.id.search);
        view_bg = view.findViewById(R.id.view_bg);
        searchEventViewModel = ViewModelProviders.of(this).get(SearchEventByAreaViewModel.class);

        searchView.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        tv_search.setVisibility(View.GONE);
        tv_search_event.setVisibility(View.GONE);
        view_bg.setVisibility(View.GONE);
        lv_events.setOnScrollListener(this);

        requestPermission();
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
                    search(offset, limit, true);
                } else
                    myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_LONG, false);
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private void search(int offset, int limit, boolean type) {
        if (Utility.isNetworkAvailable(context)) {
            if (progress.getVisibility() == View.GONE)
                progress.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);

            if (hit) {
                SearchEventbyAreaRequest request = new SearchEventbyAreaRequest();
                request.setSeachRowNumber(String.valueOf(offset));
                request.setSearch_city("");
                request.setSearch_event_type("");
                request.setSearch_postcode(postalCode);
                request.setSearch_state("");
                request.setSearchKeyword("");
                request.setSearchPageSize(String.valueOf(limit));
                request.setSearch_org("");
                searchEventViewModel.getSearchedEventsResponse(context, request).observe(this, searchEventbyAreaResponse -> {
                    progress.setVisibility(View.GONE);
                    if (type) {
                        progressBottom.setVisibility(View.GONE);
                    }
                    if (searchEventbyAreaResponse != null) {
                        if (searchEventbyAreaResponse.getResStatus().equalsIgnoreCase("200")) {
                            if (searchEventbyAreaResponse.getSearchedEvents() != null) {
                                if (type) {
                                    searchEventList.addAll(searchEventbyAreaResponse.getSearchedEvents());
                                } else {
                                    searchEventList = searchEventbyAreaResponse.getSearchedEvents();
                                }
                                lv_events.setVisibility(View.VISIBLE);
                                search.setVisibility(View.GONE);
                            searchedEventAdapter = new SearchEventByAreaAdapter(context, searchEventList);
                                lv_events.setAdapter(searchedEventAdapter);
                                lv_events.setSelection(offset > 0 ? offset - 1 : 0);
                                hit = true;
                            } else {
                                hit = false;
                            }
                        } else if (searchEventbyAreaResponse.getResStatus().equalsIgnoreCase("401")) {
                            if (!type) {
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
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            gpsTracker = new GPSTracker(context);
                            if (gpsTracker.getIsGPSTrackingEnabled()) {
                                latitude = gpsTracker.getLatitude();
                                longitude = gpsTracker.getLongitude();
                                getAddressFromLocation(latitude, longitude);
                            } else {
                                gpsTracker.showSettingsAlert(context);
                            }
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        myToast.show(error.toString(), Toast.LENGTH_LONG, false);
                    }
                })
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.grant_permission);
        builder.setMessage(R.string.zoeblueprint_need_permission);
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                fetchedAddress = addresses.get(0);
                strAddress = new StringBuilder();

                if (fetchedAddress.getMaxAddressLineIndex() > 0) {
                    for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i));
                    }
                } else {
                    try {
                        strAddress.append(fetchedAddress.getAddressLine(0));
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }

                if (fetchedAddress.getPostalCode() != null) {
                    postalCode = fetchedAddress.getPostalCode();
                    search(offset, limit, false);
                }

            } else {
                postalCode = "";
                search(offset, limit, false);
                myToast.show(getString(R.string.err_unable_to_get_address), Toast.LENGTH_SHORT, false);
            }


        } catch (IOException e) {
            e.printStackTrace();
            myToast.show(getString(R.string.err_unable_to_get_address), Toast.LENGTH_SHORT, false);
        }
    }

   /* private void getLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d("", "getLocation: permissions granted");
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    getAddressFromLocation(location.getLatitude(), location.getLongitude());
                } else {
                    myToast.show(getString(R.string.err_unable_to_get_address), Toast.LENGTH_SHORT, false);
                }
            }
        });
    }*/


}
