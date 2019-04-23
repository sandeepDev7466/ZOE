package com.ztp.app.Utils;

import android.app.Activity;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class RuntimePermission {
    Activity activity;
    String permission;
    String[] permissions;
    int per;

    private static RuntimePermission runtimePermission;

    private RuntimePermission() {
    }

    public static RuntimePermission getInstance() {
        if (runtimePermission == null)
            runtimePermission = new RuntimePermission();
        return runtimePermission;
    }

    public int setSinglePermission(final Activity activity, String permission) {
        this.activity = activity;
        this.permission = permission;

        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        per = 0;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            per = 1;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        per = 2;
                    }
                }).check();
        return per;

    }

    public void setMultiplePermissions(final Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
        Dexter.withActivity(activity)
                .withPermissions(getPermissionString(permissions))
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(activity, "Permission granted", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(activity, "Error occurred " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .check();
    }

    public String getPermissionString(String[] permission) {
        String per = "";
        for (int i = 0; i < permission.length; i++) {
            if (i != permission.length - 1)
                per += permission[i] + ",";
            else
                per += permission[i];
        }
        return per;
    }

}
