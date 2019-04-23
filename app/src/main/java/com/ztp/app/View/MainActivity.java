package com.ztp.app.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.R;
import com.ztp.app.Utils.RuntimePermission;

public class MainActivity extends AppCompatActivity {
    Context context;
    int permission;
    RoomDB roomDB;
    RuntimePermission runtimePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        roomDB = RoomDB.getInstance(context);
        runtimePermission = RuntimePermission.getInstance();

       /* new Thread() {
            @Override
            public void run() {
                super.run();

                    *//*roomDB.getStudentDao()
                            .insert(new Student("Sandeep Pathak", "sandeeppathak30791@gmail.com"));

                    roomDB.getCSODao()
                            .insert(new CSO("Sandeep", "sandeeppathak7466@gmail.com"));*//*

                stuList = roomDB.getStudentDao().getAllStudents();

                csoList = roomDB.getCSODao().getAllCSO();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, csoList.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(context, stuList.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        }.start();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            requestCameraPermission();
        }
        else
        {
            openCamera();
        }
    }

    private void requestCameraPermission() {

        permission = runtimePermission.setSinglePermission((Activity) context, Manifest.permission.CAMERA);
        if (permission == 0) {
            openCamera();
        } else if (permission == 1) {//denied
            openNavigationDialog();
        } else {
            Toast.makeText(this, "Error occured try again", Toast.LENGTH_SHORT).show();
        }
    }
    private void openCamera()
    {

    }
    private void navigateToSettings()
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
    private void openNavigationDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Navigate to app settings");
        builder.setMessage("Provide required permission to continue");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                navigateToSettings();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}

