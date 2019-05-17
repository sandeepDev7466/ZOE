package com.ztp.app.View.Fragment.CSO.Event;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.ztp.app.BuildConfig;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.UploadDocumentResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateImageActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyToast myToast;
    String path;
    public static File uploadFile = null;
    public static boolean cameFrom = false;
    String type;
    MyTextView browse,fileName,file,title;
    ImageView image;
    SharedPref sharedPref;
    String imgPath;
    Button upload,cancel;
    MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);
        context = this;
        myToast = new MyToast(context);
        browse = findViewById(R.id.browse);
        cancel = findViewById(R.id.cancel);
        upload = findViewById(R.id.upload);
        image = findViewById(R.id.image);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        fileName = findViewById(R.id.fileName);
        file = findViewById(R.id.file);
        title = findViewById(R.id.title);

        browse.setOnClickListener(this);
        cancel.setOnClickListener(this);
        upload.setOnClickListener(this);

        if (getIntent() != null) {
            type = getIntent().getStringExtra("action");
            if(type.equalsIgnoreCase("update")) {
                imgPath = getIntent().getStringExtra("image");
                title.setText(getString(R.string.update_event_image));
                if(imgPath!=null && !imgPath.isEmpty())
                {
                    Picasso.with(context).load(imgPath).fit().error(R.drawable.no_image).into(image);
                }
            }
            else
            {
                title.setText(getString(R.string.add_event_image));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        Dexter.withActivity((Activity) context)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            myToast.show("CAMERA permission denied", Toast.LENGTH_SHORT, false);
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
                        myToast.show("Permission Error : " + error.toString(), Toast.LENGTH_SHORT, false);
                    }
                }).check();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    image.setImageURI(resultUri);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    sharedPref.setEventImageBase64(Utility.encodeTobase64(bitmap));
                    path = resultUri.getPath();
                    uploadFile = new File(path);
                    fileName.setVisibility(View.VISIBLE);
                    file.setText(uploadFile.getName());
                    cameFrom = true;

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    myToast.show(error.getMessage(),Toast.LENGTH_SHORT,false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.browse:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(this);
                break;
            case R.id.cancel:
                uploadFile = null;
                sharedPref.setEventImageBase64("");
                cameFrom = false;
                finish();
                break;
            case R.id.upload:
               finish();
               if(uploadFile!=null)
                   cameFrom = true;
               else
                   cameFrom = false;
                break;
        }
    }
}
