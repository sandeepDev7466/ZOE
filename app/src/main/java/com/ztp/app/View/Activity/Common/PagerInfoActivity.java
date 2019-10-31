package com.ztp.app.View.Activity.Common;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.TabletTransformer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ztp.app.R;
import com.ztp.app.View.Pager.ViewInfoPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PagerInfoActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<HashMap<String, Object>> data = new ArrayList<>();
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_info);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        count = getData().size();
        ViewInfoPager viewInfoPager = new ViewInfoPager(getSupportFragmentManager(), count, getData());
        viewPager.setAdapter(viewInfoPager);
        viewPager.setPageTransformer(true, new TabletTransformer());
        tabLayout.setupWithViewPager(viewPager, true);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private List<HashMap<String, Object>> getData() {

        data.clear();

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("image", R.drawable.track_hours);
        map1.put("title", getString(R.string.track_your_hours));
        map1.put("color", "#59ED96");
        map1.put("description", getString(R.string.track_hours_message));
        data.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("image", R.drawable.documents);
        map2.put("title", getString(R.string.place_for_documents));
        map2.put("color", "#3FDDD0");
        map2.put("description", getString(R.string.place_for_documents_message));
        data.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("image", R.drawable.cso_events);
        map3.put("title", getString(R.string.book_your_cso_events));
        map3.put("color", "#0F96F9");
        map3.put("description", getString(R.string.book_your_cso_events_message));
        data.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("image", R.drawable.hangout);
        map4.put("title", getString(R.string.somewhere_to_hangout));
        map4.put("color", "#63F5B7");
        map4.put("description", getString(R.string.somewhere_to_hangout_message));
        data.add(map4);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("image", R.drawable.chat_connected);
        map5.put("title", getString(R.string.you_are_connected));
        map5.put("color", "#3FDDD0");
        map5.put("description", getString(R.string.you_are_connected_message));
        data.add(map5);

        return data;
    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() > 0) {

            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
    }

    private void requestPermission()
    {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

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
                        Toast.makeText(PagerInfoActivity.this, "Error occurred " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
