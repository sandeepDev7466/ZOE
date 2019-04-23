package com.ztp.app.View.Activity.CSO;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_2;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadDocumentResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.RuntimePermission;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.CsoRegisterStep_2ViewModel;
import com.ztp.app.Viewmodel.StateViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoRegisterStep_2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICKFILE_REQUEST_CODE = 100;
    MyButton clear, next;
    Context context;
    ImageView back, uploadDoc;
    CsoRegisterStep_2ViewModel csoRegisterStep_2ViewModel;
    MyTextInputEditText etOrgName, etOrgEmail, etOrgPhone, etOrgWebsite, etOrgMission, etOrgCause, etOrgProfile, etCity, etPostalCode, etAddress, etTaxId, etUploadDocument;
    Spinner sp_country, sp_state;
    String country_id, state_id;
    ScrollView scrollView;
    CountryViewModel countryModel;
    StateViewModel stateModel;
    List<CountryResponse.Country> countryListData = new ArrayList<>();
    List<StateResponse.State> stateListData = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    MyToast myToast;
    SharedPref sharedPref;
    GetProfileResponse getProfileResponse;
    String action;
    boolean flag = false;
    RuntimePermission runtimePermission;
    String path;
    GPSTracker gpsTracker;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso_register_step_2);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        init();

        getCountryList();

        if (getIntent() != null) {
            action = getIntent().getStringExtra("action");
            if (action.equalsIgnoreCase("update")) {
                next.setText(getString(R.string.update));
                getProfileResponse = (GetProfileResponse) getIntent().getSerializableExtra("model");
            } else {
                next.setText(getString(R.string.next));
            }
        }

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    country_id = countryListData.get(position).getCountryId();
                    if (stateListData.size() == 0)
                        getStateList(country_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    state_id = stateListData.get(position).getStateId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            giveMultiplePermission();
        } else {
            gpsTracker = new GPSTracker(context);
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
    }

    private void giveMultiplePermission() {

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            gpsTracker = new GPSTracker(context);
                            if (gpsTracker.getIsGPSTrackingEnabled()) {
                                latitude = gpsTracker.getLatitude();
                                longitude = gpsTracker.getLongitude();
                            } else {
                                gpsTracker.showSettingsAlert();
                            }

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            myToast.show("Permission denied", Toast.LENGTH_SHORT, false);
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
                        myToast.show("Error occurred " + error.toString(), Toast.LENGTH_SHORT, false);
                    }
                })
                .check();

    }

    private void fillData() {

        flag = true;
        etOrgName.setText(getProfileResponse.getResData().getOrgName());
        etOrgEmail.setText(getProfileResponse.getResData().getOrgEmail());
        etOrgPhone.setText(getProfileResponse.getResData().getOrgPhone());
        etOrgWebsite.setText(getProfileResponse.getResData().getOrgWebsite());
        etOrgMission.setText(getProfileResponse.getResData().getOrgMission());
        etOrgCause.setText(getProfileResponse.getResData().getOrgCause());
        etOrgProfile.setText(getProfileResponse.getResData().getOrgProfile());
        sp_country.setSelection(getCountryPosition(), true);
        etCity.setText(getProfileResponse.getResData().getOrgCity());
        etPostalCode.setText(getProfileResponse.getResData().getOrgZipcode());
        etAddress.setText(getProfileResponse.getResData().getOrgAddress());
        etTaxId.setText(getProfileResponse.getResData().getOrgTaxid());
        etUploadDocument.setText("");

    }


    private void init() {
        context = this;
        runtimePermission = RuntimePermission.getInstance();
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        csoRegisterStep_2ViewModel = ViewModelProviders.of(this).get(CsoRegisterStep_2ViewModel.class);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        scrollView = findViewById(R.id.scrollView);
        clear = findViewById(R.id.clear);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        uploadDoc = findViewById(R.id.uploadDoc);
        etOrgName = findViewById(R.id.etOrgName);
        etOrgEmail = findViewById(R.id.etOrgEmail);
        etOrgPhone = findViewById(R.id.etOrgPhone);
        etOrgWebsite = findViewById(R.id.etOrgWebsite);
        etOrgMission = findViewById(R.id.etOrgMission);
        etOrgCause = findViewById(R.id.etOrgCause);
        etOrgProfile = findViewById(R.id.etOrgProfile);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etAddress = findViewById(R.id.etAddress);
        etTaxId = findViewById(R.id.etTaxId);
        etUploadDocument = findViewById(R.id.etUploadDocument);
        sp_country = findViewById(R.id.country);
        sp_state = findViewById(R.id.state);

        clear.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        uploadDoc.setOnClickListener(this);
    }

    private void setCountrySpinner(List<CountryResponse.Country> countryList) {
        ArrayAdapter<CountryResponse.Country> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
    }

    private void setStateSpinner(List<StateResponse.State> stateList) {
        ArrayAdapter<StateResponse.State> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
        sp_state.setAdapter(adapter);
        if (flag) {
            flag = false;
            sp_state.setSelection(getStatePosition(), true);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear:

                if (action.equalsIgnoreCase("update"))
                fillData();
                else
                {
                    country_id = "";
                    state_id = "";
                    sp_country.setSelection(0, true);
                    sp_state.setSelection(0, true);
                    etOrgName.setText("");
                    etOrgEmail.setText("");
                    etOrgPhone.setText("");
                    etOrgWebsite.setText("");
                    etOrgMission.setText("");
                    etOrgCause.setText("");
                    etOrgProfile.setText("");
                    etCity.setText("");
                    etPostalCode.setText("");
                    etAddress.setText("");
                    etTaxId.setText("");
                    etUploadDocument.setText("");
                    path= "";
                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);

                break;
            case R.id.next:
                /*startActivity(new Intent(context, CsoRegisterStep_3Activity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/

                if (etOrgName.getText() != null && !etOrgName.getText().toString().isEmpty()) {

                    if (etOrgEmail.getText() != null && !etOrgEmail.getText().toString().isEmpty()) {

                        if (etOrgPhone.getText() != null && !etOrgPhone.getText().toString().isEmpty()) {

                            if (etOrgWebsite.getText() != null && !etOrgWebsite.getText().toString().isEmpty()) {

                                if (etOrgWebsite.getText() != null && !etOrgWebsite.getText().toString().isEmpty()) {

                                    if (etOrgMission.getText() != null && !etOrgMission.getText().toString().isEmpty()) {

                                        if (etOrgCause.getText() != null && !etOrgCause.getText().toString().isEmpty()) {

                                            if (etOrgProfile.getText() != null && !etOrgProfile.getText().toString().isEmpty()) {

                                                if (country_id != null && !country_id.isEmpty()) {

                                                    if (state_id != null && !state_id.isEmpty()) {

                                                        if (etCity.getText() != null && !etCity.getText().toString().isEmpty()) {

                                                            if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {

                                                                if (etAddress.getText() != null && !etAddress.getText().toString().isEmpty()) {

                                                                    if (etTaxId.getText() != null && !etTaxId.getText().toString().isEmpty()) {

                                                                        if (etUploadDocument.getText() != null && !etUploadDocument.getText().toString().isEmpty()) {

                                                                            if(path!=null)
                                                                            {
                                                                                uploadDocument(new File(path));
                                                                            }
                                                                            else
                                                                            {
                                                                                myToast.show("Upload Document", Toast.LENGTH_SHORT, false);
                                                                            }

                                                                        } else {
                                                                            myToast.show("Upload Document Title", Toast.LENGTH_SHORT, false);
                                                                        }

                                                                    } else {
                                                                        myToast.show("Enter EIN/Tax Id", Toast.LENGTH_SHORT, false);
                                                                    }

                                                                } else {
                                                                    myToast.show("Enter Address", Toast.LENGTH_SHORT, false);
                                                                }
                                                            } else {
                                                                myToast.show("Enter Postal Code", Toast.LENGTH_SHORT, false);
                                                            }
                                                        } else {
                                                            myToast.show("Enter City", Toast.LENGTH_SHORT, false);
                                                        }

                                                    } else {
                                                        myToast.show("Select State", Toast.LENGTH_SHORT, false);
                                                    }
                                                } else {
                                                    myToast.show("Select Country", Toast.LENGTH_SHORT, false);
                                                }
                                            } else {
                                                myToast.show("Enter Organization Profile", Toast.LENGTH_SHORT, false);
                                            }

                                        } else {
                                            myToast.show("Enter Organization Cause", Toast.LENGTH_SHORT, false);
                                        }

                                    } else {
                                        myToast.show("Enter Organization Mission", Toast.LENGTH_SHORT, false);
                                    }

                                } else {
                                    myToast.show("Enter Organization Website", Toast.LENGTH_SHORT, false);
                                }

                            } else {
                                myToast.show("Enter Organization Website", Toast.LENGTH_SHORT, false);
                            }

                        } else {
                            myToast.show("Enter Organization Phone", Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show("Enter Organization Email", Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show("Enter Organization Name", Toast.LENGTH_SHORT, false);
                }

                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.uploadDoc:
                if (etUploadDocument.getText() != null && !etUploadDocument.getText().toString().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, PICKFILE_REQUEST_CODE);
                } else {
                    myToast.show("Enter Document Title", Toast.LENGTH_SHORT, false);
                }

                break;
        }
    }

    private void hitRegisterApi() {

        myProgressDialog.show("Registering...");

        CsoRegisterRequestStep_2 csoRegisterRequest_2 = new CsoRegisterRequestStep_2();
        csoRegisterRequest_2.setUserId(sharedPref.getUserId());
        csoRegisterRequest_2.setUserType(sharedPref.getUserType());
        csoRegisterRequest_2.setUserDevice(Utility.getDeviceId(context));
        csoRegisterRequest_2.setOrgName(etOrgName.getText().toString());
        csoRegisterRequest_2.setOrgEmail(etOrgEmail.getText().toString());
        csoRegisterRequest_2.setOrgPhone(etOrgPhone.getText().toString());
        csoRegisterRequest_2.setOrgWebsite(etOrgWebsite.getText().toString());
        csoRegisterRequest_2.setOrgMission(etOrgMission.getText().toString());
        csoRegisterRequest_2.setOrgCause(etOrgCause.getText().toString());
        csoRegisterRequest_2.setOrgProfile(etOrgProfile.getText().toString());
        csoRegisterRequest_2.setOrgCountry(country_id);
        csoRegisterRequest_2.setOrgState(state_id);
        csoRegisterRequest_2.setOrgCity(etCity.getText().toString());
        csoRegisterRequest_2.setOrgZipcode(etPostalCode.getText().toString());
        csoRegisterRequest_2.setOrgAddress(etAddress.getText().toString());
        csoRegisterRequest_2.setOrgTaxid(etTaxId.getText().toString());
        csoRegisterRequest_2.setUserIdFile(etUploadDocument.getText().toString());
        csoRegisterRequest_2.setUserFileTitle(etUploadDocument.getText().toString());
        csoRegisterRequest_2.setOrgLatitude(String.valueOf(latitude));
        csoRegisterRequest_2.setOrgLongitude(String.valueOf(longitude));

        csoRegisterStep_2ViewModel.getRegisterResponse(csoRegisterRequest_2).observe((LifecycleOwner) context, registerResponse -> {

            if (registerResponse != null && registerResponse.getResStatus().equalsIgnoreCase("200")) {

                Intent intent1 = new Intent(context, CsoRegisterStep_3Activity.class);
                //intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                if (action.equalsIgnoreCase("update")) {
                    intent1.putExtra("action", "update");
                    intent1.putExtra("model", getProfileResponse);
                } else {
                    intent1.putExtra("action", "register");
                }
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            } else {
                myToast.show("Organisation information registration failed", Toast.LENGTH_SHORT, false);
            }

            myProgressDialog.dismiss();

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE && data != null) {

            Uri uri = data.getData();
           /* String scheme = uri.getScheme();
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                doc_path = Utility.getRealPathFromURI_API19(context, uri);
            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                doc_path = uri.getPath();
            }*/


            path = Utility.getPath(context, uri);
        }
    }


    private void uploadDocument(File fileToUpload) {


        myProgressDialog.show("Uploading...");
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("user_id_file", fileToUpload.getName(), RequestBody.create(MediaType.parse("*/*"), fileToUpload));
        //String extension = MimeTypeMap.getFileExtensionFromUrl(fileToUpload.toString());
        ApiInterface apiInterface = Api.getClient();
        //Call<ResponseBody> call = apiInterface.uploadDocument(filePart, sharedPref.getUserId(), etUploadDocument.getText().toString(), extension);

        Call<UploadDocumentResponse> call = apiInterface.uploadDocumentNew(filePart, sharedPref.getUserId(), etUploadDocument.getText().toString(), "1234", "cso_reg_file_upload");

        call.enqueue(new Callback<UploadDocumentResponse>() {
            @Override
            public void onResponse(Call<UploadDocumentResponse> call, Response<UploadDocumentResponse> response) {

                if (response.body() != null) {

                    if (response.body().getResStatus().equalsIgnoreCase("200")) {
                        myProgressDialog.dismiss();
                        myToast.show("File uploaded successfully", Toast.LENGTH_SHORT, true);
                        hitRegisterApi();
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show("File upload failed", Toast.LENGTH_SHORT, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadDocumentResponse> call, Throwable t) {
                t.printStackTrace();
                myProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public int getCountryPosition() {
        int id = 3;
        int position = -1;
        for (int i = 0; i < countryListData.size(); i++) {
            if (countryListData.get(i).getCountryName().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    public int getStatePosition() {
        int id = 3;
        int position = -1;
        for (int i = 0; i < stateListData.size(); i++) {
            if (stateListData.get(i).getStateName().equalsIgnoreCase(getProfileResponse.getResData().getUserState()) || stateListData.get(i).getStateId().equalsIgnoreCase(getProfileResponse.getResData().getUserState())) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }

    private void getCountryList() {

        countryList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Please wait...");
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                if (countryResponse != null) {
                    countryListData = countryResponse.getResData();
                    for (int i = 0; i < countryListData.size(); i++) {
                        countryList.add(countryListData.get(i).getCountryName());
                    }
                    countryList.add(0, "Select Country");
                    setCountrySpinner(countryListData);
                }
                myProgressDialog.dismiss();

                if (action.equalsIgnoreCase("update"))
                fillData();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

        }

    }

    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show("Please wait...");
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    stateListData = stateResponse.getStateList();
                    for (int i = 0; i < stateListData.size(); i++) {
                        stateList.add(stateListData.get(i).getStateName());
                    }
                    stateList.add(0, "Select State");
                    setStateSpinner(stateListData);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
