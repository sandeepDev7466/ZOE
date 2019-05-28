package com.ztp.app.View.Activity.CSO;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.raywenderlich.android.validatetor.ValidateTor;
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
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.GPSTracker;
import com.ztp.app.Utils.RuntimePermission;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.CsoRegisterStep_2ViewModel;
import com.ztp.app.Viewmodel.StateViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoRegisterStep_2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICKFILE_REQUEST_CODE = 100;
    MyButton clear, next;
    Context context;
    ImageView back, uploadDoc;
    CsoRegisterStep_2ViewModel csoRegisterStep_2ViewModel;
    MyTextInputLayout etOrgNameLayout, etOrgEmailLayout, etOrganizationPhoneLayout, etOrgWebsiteLayout, etPostalCodeLayout;
    MyTextInputEditText etOrgName, etOrgEmail, etOrganizationPhone, etOrgWebsite, etOrgMission, etOrgCause, etOrgProfile, etCity, etPostalCode, etAddress, etTaxId, etUploadDocument;
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
    ValidateTor validate;
    boolean error = false;

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

        etOrgName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidName(s.toString())) {
                        etOrgNameLayout.setError(getString(R.string.err_org_name));
                        error = true;
                    } else {
                        etOrgNameLayout.setError(null);
                        error = false;
                    }
                } else {
                    etOrgNameLayout.setError(null);
                    error = false;
                }
            }
        });

        etOrgEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidEmail(s.toString())) {
                        etOrgEmailLayout.setError(getString(R.string.err_enter_valid_email));
                        error = true;
                    } else {
                        etOrgEmailLayout.setError(null);
                        error = false;
                    }
                } else {
                    etOrgEmailLayout.setError(null);
                    error = false;
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            etOrganizationPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

        } else {
            etOrganizationPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

        etOrganizationPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidPhoneNumber(s.toString())) {
                        etOrganizationPhoneLayout.setError(getString(R.string.err_enter_valid_phone));
                        error = true;
                    } else {
                        etOrganizationPhoneLayout.setError(null);
                        error = false;
                    }
                } else {
                    etOrganizationPhoneLayout.setError(null);
                    error = false;
                }
            }
        });

        etOrgWebsite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidWebsite(s.toString())) {
                        etOrgWebsiteLayout.setError(getString(R.string.err_enter_valid_website_address));
                        error = true;
                    } else {
                        etOrgWebsiteLayout.setError(null);
                        error = false;
                    }
                } else {
                    etOrgWebsiteLayout.setError(null);
                    error = false;
                }
            }
        });
        etPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.length() > 0) {
                    if (!Utility.isValidPostalCode(s.toString())) {
                        etPostalCodeLayout.setError(getString(R.string.err_enter_valid_postal));
                        error = true;
                    } else {
                        etPostalCodeLayout.setError(null);
                        error = false;
                    }
                } else {
                    etPostalCodeLayout.setError(null);
                    error = false;
                }
            }
        });

    }


    private void fillData() {

        flag = true;
        etOrgName.setText(getProfileResponse.getResData().getOrgName());
        etOrgEmail.setText(getProfileResponse.getResData().getOrgEmail());
        etOrganizationPhone.setText(getProfileResponse.getResData().getOrgPhone());
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
        validate = new ValidateTor();
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
        etOrganizationPhone = findViewById(R.id.etOrganizationPhone);
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

        etOrgNameLayout = findViewById(R.id.etOrgNameLayout);
        etOrgEmailLayout = findViewById(R.id.etOrgEmailLayout);
        etOrganizationPhoneLayout = findViewById(R.id.etOrganizationPhoneLayout);
        etOrgWebsiteLayout = findViewById(R.id.etOrgWebsiteLayout);
        etPostalCodeLayout = findViewById(R.id.etPostalCodeLayout);

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
                else {
                    country_id = "";
                    state_id = "";
                    sp_country.setSelection(0, true);
                    sp_state.setSelection(0, true);
                    etOrgName.setText("");
                    etOrgEmail.setText("");
                    etOrganizationPhone.setText("");
                    etOrgWebsite.setText("");
                    etOrgMission.setText("");
                    etOrgCause.setText("");
                    etOrgProfile.setText("");
                    etCity.setText("");
                    etPostalCode.setText("");
                    etAddress.setText("");
                    etTaxId.setText("");
                    etUploadDocument.setText("");
                    path = "";
                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);

                break;
            case R.id.next:

                if (etOrgName.getText() != null && !etOrgName.getText().toString().isEmpty()) {
                    if (!Utility.isValidName(etOrgName.getText().toString())) {
                        myToast.show(getString(R.string.err_org_name), Toast.LENGTH_SHORT, false);
                        return;
                    }
                    if (etOrgEmail.getText() != null && !etOrgEmail.getText().toString().isEmpty()) {
                        if (!Utility.isValidEmail(etOrgEmail.getText().toString())) {
                            myToast.show(getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                            return;
                        }
                        if (etOrganizationPhone.getText() != null && !etOrganizationPhone.getText().toString().isEmpty()) {
                            if (!Utility.isValidPhoneNumber(etOrganizationPhone.getText().toString())) {
                                myToast.show(getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT, false);
                                return;
                            }
                            if (etOrgWebsite.getText() != null && !etOrgWebsite.getText().toString().isEmpty()) {
                                if (!Utility.isValidWebsite(etOrgWebsite.getText().toString())) {
                                    myToast.show(getString(R.string.err_enter_valid_website_address), Toast.LENGTH_SHORT, false);
                                    return;
                                }
                                if (etOrgMission.getText() != null && !etOrgMission.getText().toString().isEmpty()) {

                                    if (etOrgCause.getText() != null && !etOrgCause.getText().toString().isEmpty()) {

                                        if (etOrgProfile.getText() != null && !etOrgProfile.getText().toString().isEmpty()) {

                                            if (country_id != null && !country_id.isEmpty()) {

                                                if (state_id != null && !state_id.isEmpty()) {

                                                    if (etCity.getText() != null && !etCity.getText().toString().isEmpty()) {

                                                        if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty()) {
                                                            if (!Utility.isValidPostalCode(etPostalCode.getText().toString())) {
                                                                myToast.show(getString(R.string.err_enter_valid_postal), Toast.LENGTH_SHORT, false);
                                                                return;
                                                            }
                                                            if (etAddress.getText() != null && !etAddress.getText().toString().isEmpty()) {

                                                                if (etTaxId.getText() != null && !etTaxId.getText().toString().isEmpty()) {

                                                                    if (etUploadDocument.getText() != null && !etUploadDocument.getText().toString().isEmpty()) {

                                                                        if (path != null) {

                                                                            uploadDocument(new File(path));

                                                                        } else {
                                                                            myToast.show(getString(R.string.err_upload_document), Toast.LENGTH_SHORT, false);
                                                                        }

                                                                    } else {
                                                                        myToast.show(getString(R.string.err_upload_document_title), Toast.LENGTH_SHORT, false);
                                                                    }

                                                                } else {
                                                                    myToast.show(getString(R.string.err_enter_taxId), Toast.LENGTH_SHORT, false);
                                                                }

                                                            } else {
                                                                myToast.show(getString(R.string.err_enter_address), Toast.LENGTH_SHORT, false);
                                                            }
                                                        } else {
                                                            myToast.show(getString(R.string.err_enter_postal_code), Toast.LENGTH_SHORT, false);
                                                        }
                                                    } else {
                                                        myToast.show(getString(R.string.err_enter_city), Toast.LENGTH_SHORT, false);
                                                    }

                                                } else {
                                                    myToast.show(getString(R.string.err_select_state), Toast.LENGTH_SHORT, false);
                                                }
                                            } else {
                                                myToast.show(getString(R.string.err_select_country), Toast.LENGTH_SHORT, false);
                                            }
                                        } else {
                                            myToast.show(getString(R.string.err_org_profile), Toast.LENGTH_SHORT, false);
                                        }

                                    } else {
                                        myToast.show(getString(R.string.err_org_cause), Toast.LENGTH_SHORT, false);
                                    }

                                } else {
                                    myToast.show(getString(R.string.err_org_mission), Toast.LENGTH_SHORT, false);
                                }

                            } else {
                                myToast.show(getString(R.string.err_org_website), Toast.LENGTH_SHORT, false);
                            }
                        } else {
                            myToast.show(getString(R.string.err_org_phone), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_org_email), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_enter_org_name), Toast.LENGTH_SHORT, false);
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
                    myToast.show(getString(R.string.err_document_title), Toast.LENGTH_SHORT, false);
                }

                break;
        }
    }

    private void hitRegisterApi() {

        myProgressDialog.show(getString(R.string.please_wait));

        CsoRegisterRequestStep_2 csoRegisterRequest_2 = new CsoRegisterRequestStep_2();
        csoRegisterRequest_2.setUserId(sharedPref.getUserId());
        csoRegisterRequest_2.setUserType(sharedPref.getUserType());
        csoRegisterRequest_2.setUserDevice(Utility.getDeviceId(context));
        csoRegisterRequest_2.setOrgName(etOrgName.getText().toString());
        csoRegisterRequest_2.setOrgEmail(etOrgEmail.getText().toString());
        csoRegisterRequest_2.setOrgPhone(etOrganizationPhone.getText().toString());
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
        csoRegisterRequest_2.setOrgLatitude("0.00");
        csoRegisterRequest_2.setOrgLongitude("0.00");

        if (Utility.isNetworkAvailable(context)) {
            csoRegisterStep_2ViewModel.getRegisterResponse(csoRegisterRequest_2).observe((LifecycleOwner) context, registerResponse -> {

                if (registerResponse != null) {
                    if (registerResponse.getResStatus().equalsIgnoreCase("200")) {

                        Intent intent1 = new Intent(context, CsoRegisterStep_3Activity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        if (action.equalsIgnoreCase("update")) {
                            intent1.putExtra("action", "update");
                            intent1.putExtra("model", getProfileResponse);
                        } else {
                            intent1.putExtra("action", "register");
                        }
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } else if(registerResponse.getResStatus().equalsIgnoreCase("401")){
                        myToast.show(getString(R.string.err_org_inf_regisitration_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
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


        myProgressDialog.show(getString(R.string.please_wait));
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
                        myToast.show(getString(R.string.file_uploaded_successfully), Toast.LENGTH_SHORT, true);
                        hitRegisterApi();
                    } else {
                        myProgressDialog.dismiss();
                        myToast.show(getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, true);
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

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.exit_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        LinearLayout yes = view.findViewById(R.id.yes);
        LinearLayout no = view.findViewById(R.id.no);

        yes.setOnClickListener(v -> {
            dialog.dismiss();

            if (sharedPref.getIsLogin()) {
                startActivity(new Intent(context, CsoDashboardActivity.class));
                finish();

            } else {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }

            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
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
            myProgressDialog.show(getString(R.string.please_wait));
            countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                if (countryResponse != null) {
                    if (countryResponse.getResStatus().equalsIgnoreCase("200") && countryResponse.getResData() != null) {
                        countryListData = countryResponse.getResData();
                        for (int i = 0; i < countryListData.size(); i++) {
                            countryList.add(countryListData.get(i).getCountryName());
                        }

                        setCountrySpinner(countryListData);
                    }
                    else if(countryResponse.getResStatus().equalsIgnoreCase("401")){
                        myToast.show(getString(R.string.err_no_country_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }

                if (action.equalsIgnoreCase("update"))
                    fillData();
                myProgressDialog.dismiss();

            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

        }

    }

    private void getStateList(String country_id) {
        stateList = new ArrayList<>();
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                if (stateResponse != null) {
                    if (stateResponse.getResStatus().equalsIgnoreCase("200")) {
                        stateListData = stateResponse.getStateList();
                        for (int i = 0; i < stateListData.size(); i++) {
                            stateList.add(stateListData.get(i).getStateName());
                        }

                        setStateSpinner(stateListData);
                    } else if(stateResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        myToast.show(getString(R.string.err_no_state_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }
}
