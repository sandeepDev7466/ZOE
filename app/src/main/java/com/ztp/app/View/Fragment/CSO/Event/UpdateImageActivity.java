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
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yalantis.ucrop.UCrop;
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

import static android.support.v4.content.FileProvider.getUriForFile;

public class UpdateImageActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    MyToast myToast;
    String path;
    public static File uploadFile = null;
    public static boolean cameFrom = false;
    String type;
    MyTextView camera,gallery,fileName,file,title;
    ImageView image;
    SharedPref sharedPref;
    String imgPath;
    Button upload,cancel;
    MyProgressDialog myProgressDialog;
    LinearLayout mainLayout;
    public String fileNameCam;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;
    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private int IMAGE_COMPRESSION = 80;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);
        context = this;
        myToast = new MyToast(context);
        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        cancel = findViewById(R.id.cancel);
        upload = findViewById(R.id.upload);
        image = findViewById(R.id.image);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        fileName = findViewById(R.id.fileName);
        file = findViewById(R.id.file);
        title = findViewById(R.id.title);
        mainLayout = findViewById(R.id.mainLayout);

        cancel.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
            requestCameraPermission();
        }
        else
        {
            camera.setOnClickListener(this);
            upload.setOnClickListener(this);
        }

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

    private void requestCameraPermission() {
        Dexter.withActivity((Activity) context)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        camera.setOnClickListener(UpdateImageActivity.this);
                        upload.setOnClickListener(UpdateImageActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            myToast.show(getString(R.string.err_camera_permission), Toast.LENGTH_SHORT, false);
                            showSettingsDialog();
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

   /* @SuppressLint("SetTextI18n")
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
    }*/


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    cropImage(getCacheImagePath(fileNameCam));
                } else {
                    setResultCancelled();
                }
                break;
            case REQUEST_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    cropImage(imageUri);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    handleUCropResult(data);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.RESULT_ERROR:
                final Throwable cropError = UCrop.getError(data);
                Log.e("ERROR", "Crop error: " + cropError);
                setResultCancelled();
                break;

            default:
                setResultCancelled();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                /*CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .start(this);*/

                takeCameraImage();

                break;
            case R.id.gallery:

                chooseImageFromGallery();
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

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void takeCameraImage() {
        fileNameCam = System.currentTimeMillis() + ".jpg";
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileNameCam));
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void chooseImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE);
    }
    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(context, getPackageName() + ".provider", image);
    }
    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(IMAGE_COMPRESSION);

        // applying UI theme
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));

        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);

        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    private void handleUCropResult(Intent data) {
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        setResultOk(resultUri);
    }

    private void setResultOk(Uri imagePath) {
       /* Intent intent = new Intent();
        intent.putExtra("path", imagePath);
        setResult(Activity.RESULT_OK, intent);
        finish();*/

        try {
        image.setImageURI(imagePath);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagePath);
        sharedPref.setEventImageBase64(Utility.encodeTobase64(bitmap));
        path = imagePath.getPath();
        uploadFile = new File(path);
        fileName.setVisibility(View.VISIBLE);
        file.setText(uploadFile.getName());
        cameFrom = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }
}
