package com.ztp.app.View.Activity.CSO;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lal.adhish.gifprogressbar.GifView;

public class ViewLocationActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyBoldTextView txtLocationAddress;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    MyToast myToast;
    ImageView centerMarker;
    double latitude, longitude;
    ImageView submit, close;
    SharedPref sharedPref;
    StringBuilder strAddress;
    String stateName;
    Address fetchedAddress;
    GifView pGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_location);
        context = this;
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        if (getIntent() != null) {
            latitude = getIntent().getDoubleExtra("latitude", 0.00);
            longitude = getIntent().getDoubleExtra("longitude", 0.00);
            stateName = getIntent().getStringExtra("state_name");
        }

        pGif = (GifView) findViewById(R.id.progressBar);
        pGif.setImageResource(R.drawable.ripple);
        centerMarker = findViewById(R.id.centerMarker);
        txtLocationAddress = findViewById(R.id.txtLocationAddress);
        submit = findViewById(R.id.submit);
        close = findViewById(R.id.close);
        txtLocationAddress.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txtLocationAddress.setSingleLine(true);
        txtLocationAddress.setMarqueeRepeatLimit(-1);
        txtLocationAddress.setSelected(true);

        submit.setOnClickListener(this);
        close.setOnClickListener(this);
        centerMarker.setOnClickListener(this);

        mapFragment = (SupportMapFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                map.getUiSettings().setIndoorLevelPickerEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                LatLng latLng = new LatLng(latitude, longitude);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                initCameraIdle();
            }
        });
    }

    private void initCameraIdle() {
        map.setOnCameraIdleListener(() -> {

            center = map.getCameraPosition().target;
            getAddressFromLocation(center.latitude, center.longitude);

            /*center = map.getCameraPosition().target;
            if(Utility.distanceBetweenLatLong(latitude,longitude,center.latitude,center.longitude)<10)
                getAddressFromLocation(center.latitude, center.longitude);
            else
            {
                LatLng latLng = new LatLng(latitude, longitude);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
                myToast.show("Can't mark location that far",Toast.LENGTH_LONG,false);
            }*/

        });
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

                if (fetchedAddress.getAdminArea() != null && fetchedAddress.getAdminArea().equalsIgnoreCase(stateName)) {
                    txtLocationAddress.setTextColor(getResources().getColor(R.color.green));
                    txtLocationAddress.setText(strAddress.toString());
                    submit.setClickable(true);
                    centerMarker.setColorFilter(getResources().getColor(R.color.black));
                    pGif.setImageResource(R.drawable.ripple);
                } else if (fetchedAddress.getAdminArea() == null || !fetchedAddress.getAdminArea().equalsIgnoreCase(stateName)) {
                    txtLocationAddress.setText(getString(R.string.location_out_of_state) + " " + stateName);
                    txtLocationAddress.setTextColor(getResources().getColor(R.color.red));
                    submit.setClickable(false);
                    centerMarker.setColorFilter(getResources().getColor(R.color.red));
                    pGif.setImageResource(R.drawable.ripple_red);
                }

            } else {
                txtLocationAddress.setText(getString(R.string.searching_address));
            }

        } catch (IOException e) {
            e.printStackTrace();
            myToast.show(getString(R.string.err_unable_to_get_address), Toast.LENGTH_SHORT, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                onBackPressed();
                break;
            case R.id.submit:
                String address = "";
                sharedPref.setEventLatitude(String.valueOf(center.latitude));
                sharedPref.setEventLongitude(String.valueOf(center.longitude));
                sharedPref.setEventCity(fetchedAddress.getLocality());
                sharedPref.setEventPostalCode(fetchedAddress.getPostalCode());

                if (fetchedAddress.getSubThoroughfare() != null) {
                    address = address + fetchedAddress.getSubThoroughfare();
                }
                if (fetchedAddress.getThoroughfare() != null) {
                    if (address.isEmpty())
                        address = address + fetchedAddress.getThoroughfare();
                    else
                        address = address + " " + fetchedAddress.getThoroughfare();
                }
                if (address.isEmpty())
                    if (fetchedAddress.getSubAdminArea() != null) {
                        address = fetchedAddress.getSubAdminArea();
                    }
                sharedPref.setEventAddress(address);

                finish();
                break;
            case R.id.centerMarker:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        sharedPref.setEventLatitude("");
        sharedPref.setEventLongitude("");
        super.onBackPressed();
    }
}
