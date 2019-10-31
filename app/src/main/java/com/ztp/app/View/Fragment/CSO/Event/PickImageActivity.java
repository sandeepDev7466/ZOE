package com.ztp.app.View.Fragment.CSO.Event;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Helper.ImagePickActivity;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PickImageActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout camera, gallery;
    ImageView image;
    Context context;
    public static final int REQUEST_IMAGE = 100;
    SharedPref sharedPref;
    public static File uploadFile = null;
    public static boolean cameFrom = false;
    String path;
    MyButton cancelBtn, doneBtn;
    MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        int screenHeight = (int) (metrics.heightPixels * 0.75);
        setContentView(R.layout.pick_media);
        getWindow().setLayout(screenWidth, screenHeight);

        context = this;
        myToast = new MyToast(context);
        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        image = findViewById(R.id.image);
        cancelBtn = findViewById(R.id.cancelBtn);
        doneBtn = findViewById(R.id.doneBtn);
        sharedPref = SharedPref.getInstance(context);

        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);

        if (getIntent() != null) {
            int count = getIntent().getIntExtra("count", 0);
            if (getIntent().getStringExtra("event_image") != null)
                Picasso.with(context).load(getIntent().getStringExtra("event_image")).error(R.drawable.no_image).into(image);
            if (count > 1)
                image.setImageBitmap(Utility.decodeBase64(sharedPref.getEventImageBase64()));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                Dexter.withActivity((Activity) context)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                launchCameraIntent();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                showSettingsDialog();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        }).check();
                break;
            case R.id.gallery:
                launchGalleryIntent();
                break;
            case R.id.cancelBtn:
                cameFrom = false;
                sharedPref.setEventImageBase64("");
                finish();
                break;
            case R.id.doneBtn:
                finish();
                break;

        }
    }

    private void launchCameraIntent() {
        try {

            Intent intent = new Intent(context, ImagePickActivity.class);
            intent.putExtra(ImagePickActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickActivity.REQUEST_IMAGE_CAPTURE);

            // setting aspect ratio
            intent.putExtra(ImagePickActivity.INTENT_LOCK_ASPECT_RATIO, true);
            intent.putExtra(ImagePickActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
            intent.putExtra(ImagePickActivity.INTENT_ASPECT_RATIO_Y, 1);

            // setting maximum bitmap width and height
            intent.putExtra(ImagePickActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
            intent.putExtra(ImagePickActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
            intent.putExtra(ImagePickActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

            startActivityForResult(intent, REQUEST_IMAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchGalleryIntent() {
        try {

            Intent intent = new Intent(context, ImagePickActivity.class);
            intent.putExtra(ImagePickActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickActivity.REQUEST_GALLERY_IMAGE);

            // setting aspect ratio
            intent.putExtra(ImagePickActivity.INTENT_LOCK_ASPECT_RATIO, true);
            intent.putExtra(ImagePickActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
            intent.putExtra(ImagePickActivity.INTENT_ASPECT_RATIO_Y, 1);
            startActivityForResult(intent, REQUEST_IMAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.grant_permission));
        builder.setMessage(getString(R.string.zoeblueprint_need_permission));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                path = Utility.getPath(context, uri);
                if (path == null) {
                    path = "";
                    myToast.show(getString(R.string.err_select_other_path), Toast.LENGTH_SHORT, false);
                } else {
                    try {
                        // You can update this bitmap to your server
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        if (!bitmap.equals("")) {
                            image.setImageBitmap(bitmap);
                            sharedPref.setEventImageBase64(Utility.encodeTobase64(bitmap));
                            //path = uri.getPath();
                            uploadFile = new File(path);
                            cameFrom = true;
                        } else {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
