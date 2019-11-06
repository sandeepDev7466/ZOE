package com.ztp.app.View.Activity.Common;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.UploadCoverPicResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadProfilePicResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.ImagePickActivity;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.SendBird.utils.PreferenceUtils;
import com.ztp.app.SendBird.utils.PushUtils;
import com.ztp.app.Utils.Utility;
import java.io.File;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    LabeledSwitch sw_theme, sw_language, sw_location, sw_push;
    SharedPref sharedPref;
    boolean theme, language, location, push;
    ImageView back, cover;
    CircleImageView user;
    MyBoldTextView choose_profile, choose_cover;
    public static final int REQUEST_IMAGE = 100;
    File uploadFile = null;
    String path, typeImage;
    MyToast myToast;
    MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        Utility.makeStatusBarTransparent(context);
        sw_theme = findViewById(R.id.theme);
        sw_language = findViewById(R.id.language);
        sw_location = findViewById(R.id.location);
        sw_push = findViewById(R.id.push);
        back = findViewById(R.id.back);
        choose_profile = findViewById(R.id.choose_profile);
        choose_cover = findViewById(R.id.choose_cover);
        cover = findViewById(R.id.cover);
        user = findViewById(R.id.user);
        back.setOnClickListener(this);
        choose_profile.setOnClickListener(this);
        choose_cover.setOnClickListener(this);


        theme = sharedPref.getTheme();
        language = sharedPref.getLanguage();
        location = sharedPref.getLocation();
        push = sharedPref.getPushNotification();

        sw_theme.setOn(theme);
        sw_language.setOn(language);
        sw_location.setOn(location);
        sw_push.setOn(push);

        sw_theme.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setTheme(isOn);
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                sharedPref.setIsChanged(true);

            } else {
                sharedPref.setTheme(isOn);
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                sharedPref.setIsChanged(true);
            }
        });

        sw_language.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setLanguage(true);
                Utility.setLocale(context, "es");
                recreate();
                sharedPref.setIsChanged(true);

            } else {
                sharedPref.setLanguage(false);
                Utility.setLocale(context, "en");
                recreate();
                sharedPref.setIsChanged(true);
            }
        });

        sw_location.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setLocation(true);


            } else {
                sharedPref.setLocation(false);

            }
        });

        sw_push.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                sharedPref.setPushNotification(true);
            } else {
                sharedPref.setPushNotification(false);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void recreate() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.choose_cover:
                openPickImageDialog(getString(R.string.cover));
                break;
            case R.id.choose_profile:
                openPickImageDialog(getString(R.string.profile));
                break;
        }
    }

    private void openPickImageDialog(String type) {
        typeImage = type;
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.pick_image_dialog, null);
        dialog.setContentView(view);


        MyTextView title = view.findViewById(R.id.title);
        title.setText(getString(R.string.uploadImg) + " " + type + " " + getString(R.string.img));

        MyTextView camera = view.findViewById(R.id.camera);
        MyTextView gallery = view.findViewById(R.id.gallery);
        MyTextView cancel = view.findViewById(R.id.cancel);

        camera.setOnClickListener(v -> {
            dialog.dismiss();
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

        });

        gallery.setOnClickListener(v -> {
            dialog.dismiss();
            launchGalleryIntent();

        });

        cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!sharedPref.getProfileImage().isEmpty()) {
            Picasso.with(context).load(sharedPref.getProfileImage()).placeholder(R.drawable.user).error(R.drawable.user).into(user);

            //Glide.with(context).load(sharedPref.getProfileImage()).into(user);
        } else {
            Picasso.with(context).load(R.drawable.user).into(user);

            // Glide.with(context).load(R.drawable.user).into(user);

        }

        if (!sharedPref.getCoverImage().isEmpty()) {
            Picasso.with(context).load(sharedPref.getCoverImage()).placeholder(R.drawable.back).error(R.drawable.back).into(cover);

            //Glide.with(context).load(sharedPref.getCoverImage()).into(cover);
        } else {
            Picasso.with(context).load(R.drawable.back).into(cover);

            //Glide.with(context).load(R.drawable.scene).into(cover);
        }

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
                }
                else {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        //path = uri.getPath();
                        uploadFile = new File(path);

                        if (typeImage.equalsIgnoreCase("cover")) {
                            //sharedPref.setCoverImageBase64(Utility.encodeTobase64(bitmap));
                            //cover.setImageBitmap(bitmap);
                            if (Utility.isNetworkAvailable(context))
                                uploadDocument(bitmap, uploadFile, "user_cover_pic", "user_cover_pic_upload");
                            else
                                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                        } else if (typeImage.equalsIgnoreCase("profile")) {
                            //sharedPref.setProfileImageBase64(Utility.encodeTobase64(bitmap));
                            //user.setImageBitmap(bitmap);
                            if (Utility.isNetworkAvailable(context))
                                uploadDocument(bitmap, uploadFile, "user_profile_pic", "user_profile_pic_upload");
                            else
                                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void uploadDocument(Bitmap bitmap, File fileToUpload, String typeText, String action) {

        myProgressDialog.show(getString(R.string.please_wait));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(typeText, fileToUpload.getName(), RequestBody.create(MediaType.parse("*/*"), fileToUpload));
        ApiInterface apiInterface = Api.getClient();

        if (typeImage.equalsIgnoreCase("cover")) {
            Call<UploadCoverPicResponse> call = apiInterface.uploadCoverImage(filePart, sharedPref.getUserId(), "1234", action);

            call.enqueue(new Callback<UploadCoverPicResponse>() {
                @Override
                public void onResponse(Call<UploadCoverPicResponse> call, Response<UploadCoverPicResponse> response) {

                    if (response.body() != null) {

                        Log.i("RESPONSE", "" + new Gson().toJson(response));
                        if (response.body().getResStatus().equalsIgnoreCase("200")) {
                            //myToast.show("Cover image uploaded" , Toast.LENGTH_SHORT, true);
                            sharedPref.setCoverImage(response.body().getResData().getUserCoverPic());
                            cover.setImageBitmap(bitmap);
                        } else {
                            myToast.show("Cover image upload failed", Toast.LENGTH_SHORT, false);
                        }
                    }
                    myProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UploadCoverPicResponse> call, Throwable t) {
                    t.printStackTrace();
                    myToast.show("Cover image upload failed", Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
            });
        } else if (typeImage.equalsIgnoreCase("profile")) {
            Call<UploadProfilePicResponse> call = apiInterface.uploadProfileImage(filePart, sharedPref.getUserId(), "1234", action);

            call.enqueue(new Callback<UploadProfilePicResponse>() {
                @Override
                public void onResponse(Call<UploadProfilePicResponse> call, Response<UploadProfilePicResponse> response) {

                    if (response.body() != null) {

                        Log.i("RESPONSE", "" + new Gson().toJson(response));

                        if (response.body().getResStatus().equalsIgnoreCase("200")) {
                            //myToast.show("Profile image uploaded" , Toast.LENGTH_SHORT, true);
                            sharedPref.setProfileImage(response.body().getResData().getUserProfilePic());
                            user.setImageBitmap(bitmap);
                            updateCurrentUserInfo(sharedPref.getFirstName()+" "+sharedPref.getLastName(),fileToUpload);

                        } else {
                            myToast.show("Profile image upload failed", Toast.LENGTH_SHORT, false);
                        }
                    }
                    myProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UploadProfilePicResponse> call, Throwable t) {
                    t.printStackTrace();
                    myToast.show("Profile image upload failed", Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
            });
        }
    }

    private void updateCurrentUserInfo(String userNickname,File profileFile) {

        SendBird.updateCurrentUserInfoWithProfileImage(userNickname, profileFile, e -> {
            if (e != null) {
                // Error!
                Toast.makeText(
                        SettingsActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                        Toast.LENGTH_SHORT)
                        .show();

                return;
            }

            PreferenceUtils.setNickname(userNickname);
        });
    }
}
