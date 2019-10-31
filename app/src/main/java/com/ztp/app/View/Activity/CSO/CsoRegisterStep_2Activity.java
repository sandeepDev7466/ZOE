package com.ztp.app.View.Activity.CSO;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetCountry;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetProfile;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetState;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_2;
import com.ztp.app.Data.Remote.Model.Request.DocumentListRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadLockerDocumentResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextInputEditText;
import com.ztp.app.Helper.MyTextInputLayout;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.RuntimePermission;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Activity.Common.LoginActivity;
import com.ztp.app.Viewmodel.CountryViewModel;
import com.ztp.app.Viewmodel.CsoRegisterStep_2ViewModel;
import com.ztp.app.Viewmodel.DocumentListViewModel;
import com.ztp.app.Viewmodel.DocumentTypeViewModel;
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
    MyTextInputEditText etOrgName, etOrgEmail, etOrganizationPhone, etOrgWebsite, etOrgMission, etOrgCause, etOrgProfile, etCity, etPostalCode, etAddress, etTaxId;
    Spinner sp_country, sp_state, sp_docType;
    String country_id, state_id;
    ScrollView scrollView;
    CountryViewModel countryModel;
    StateViewModel stateModel;
    List<CountryResponse.Country> countryListData = new ArrayList<>();
    List<StateResponse.State> stateListData = new ArrayList<>();
    List<DocumentTypeResponse.Document> documentList = new ArrayList<>();
    MyProgressDialog myProgressDialog;
    List<String> countryList = new ArrayList<>();
    List<String> stateList = new ArrayList<>();
    MyToast myToast;
    SharedPref sharedPref;
    GetProfileResponse getProfileResponse;
    String action;
    boolean flag = false;
    RuntimePermission runtimePermission;
    String path, uploadPath;
    ValidateTor validate;
    boolean error = false;
    DBGetCountry dbGetCountry;
    DBGetState dbGetState;
    DBGetProfile dbGetProfile;
    MyTextView uploadedFile;
    DocumentTypeViewModel documentTypeViewModel;
    DocumentListViewModel documentListViewModel;
    MyButton addBtn, upBtn, viewBtn;
    String doc_type_id, doc_type_name;
    //    CardView cardView;
    boolean theme, uploaded = false;
    public static boolean certified501c3 = false;
    LinearLayout uploadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso_register_step_2);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        sharedPref = SharedPref.getInstance(context);
        dbGetCountry = new DBGetCountry(context);
        dbGetState = new DBGetState(context);
        dbGetProfile = new DBGetProfile(context, sharedPref.getUserId());
        documentTypeViewModel = ViewModelProviders.of((FragmentActivity) context).get(DocumentTypeViewModel.class);
        documentListViewModel = ViewModelProviders.of((FragmentActivity) context).get(DocumentListViewModel.class);
        theme = sharedPref.getTheme();
        init();

        if (getIntent() != null) {
            action = getIntent().getStringExtra("action");
            if (action != null && action.equalsIgnoreCase("update")) {
                uploadLayout.setVisibility(View.GONE);
                getProfileResponse = (GetProfileResponse) getIntent().getSerializableExtra("model");

                if (Utility.isNetworkAvailable(context)) {
                    next.setText(getString(R.string.update));
                } else {
                    next.setText(getString(R.string.next_cso));
                }

            } else {
//                if (theme) {
//                    cardView.setCardBackgroundColor(getResources().getColor(R.color.transparent));
//                } else {
//                    cardView.setCardBackgroundColor(getResources().getColor(R.color.background_2));
//                }
                uploadLayout.setVisibility(View.VISIBLE);
                next.setText(getString(R.string.next_cso));
            }
        }



/*
        if (theme) {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.background_2));
        }
        next.setText(getString(R.string.next));*/

        getDocumentType();


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
                if (position > 0)
                    state_id = stateListData.get(position-1).getStateId();
                else
                    state_id = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        sp_docType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position > 0) {
                    doc_type_id = documentList.get(position - 1).getDocument_type_id();
                    doc_type_name = documentList.get(position - 1).getDocument_type_name();
                } else if (position == 0) {
                    doc_type_id = "";
                    doc_type_name = "";
                }

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

    private void getDocumentType() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            documentTypeViewModel.getDocumentResponse(context).observe((LifecycleOwner) context, documentListResponse -> {

                if (documentListResponse != null) {
                    if (documentListResponse.getResStatus().equalsIgnoreCase("200") && documentListResponse.getResData() != null) {
                        ArrayList<String> nameList = new ArrayList<String>();
                        for (DocumentTypeResponse.Document doc : documentListResponse.getResData()) {
                            //documentHashMap.put(doc.getDocument_type_name(), doc);
                            documentList.add(doc);
                            nameList.add(doc.getDocument_type_name());
                        }
                        nameList.add(0, "Select document type");
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, nameList);
                        sp_docType.setAdapter(spinnerArrayAdapter);

                    } else if (documentListResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }

                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();

                }
                //myProgressDialog.dismiss();

            });


        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }

        getCountryList();

    }

    /*private void showPopup(String[] data) {
        Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.document_type_dialog, null);
        Spinner sp_docType = view1.findViewById(R.id.sp_docType);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, data);
        sp_docType.setAdapter(spinnerArrayAdapter);

        MyEditText et_doc_name = view1.findViewById(R.id.et_doc_name);

        LinearLayout ok = view1.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!et_doc_name.getText().toString().trim().equalsIgnoreCase("")) {
                    String name = (String) sp_docType.getSelectedItem();
                    DocumentTypeResponse.Document doc = documentHashMap.get(name);
                    uploadDocument(new File(path), doc.getDocument_type_id(), et_doc_name.getText().toString().trim());
                    dialog.dismiss();
                } else {
                    myToast.show(getString(R.string.enter_doc_name), Toast.LENGTH_SHORT, false);
                }
            }
        });
        LinearLayout cancel = view1.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view1);
        dialog.show();

    }*/

    private void fillData() {

        try {

            if (Utility.isNetworkAvailable(context)) {
                flag = true;
                etOrgName.setText(getProfileResponse.getResData().getOrgName());
                etOrgEmail.setText(getProfileResponse.getResData().getOrgEmail());
                etOrganizationPhone.setText(Utility.getFormattedPhoneNumber(getProfileResponse.getResData().getOrgPhone()));
                etOrgWebsite.setText(getProfileResponse.getResData().getOrgWebsite());
                etOrgMission.setText(getProfileResponse.getResData().getOrgMission());
                etOrgCause.setText(getProfileResponse.getResData().getOrgCause());
                etOrgProfile.setText(getProfileResponse.getResData().getOrgProfile());
                sp_country.setSelection(getCountryPosition(), true);
                etCity.setText(getProfileResponse.getResData().getOrgCity());
                etPostalCode.setText(getProfileResponse.getResData().getOrgZipcode());
                etAddress.setText(getProfileResponse.getResData().getOrgAddress());
                etTaxId.setText(getProfileResponse.getResData().getOrgTaxid());

                if (!getProfileResponse.getResData().getUserFileTitle().isEmpty() || !getProfileResponse.getResData().getUserIdFile().isEmpty()) {
                    uploadDoc.setColorFilter(getResources().getColor(R.color.green));
                    uploadPath = getProfileResponse.getResData().getUserIdFile();
                    String fileWithExt = uploadPath.substring(uploadPath.lastIndexOf('/') + 1);
                    uploadedFile.setText("Uploaded document : " + fileWithExt);
                } else {
                    uploadedFile.setText("");
                    uploadDoc.setColorFilter(getResources().getColor(R.color.black));
                    uploadPath = getProfileResponse.getResData().getUserIdFile();
                }
            } else {
                if (dbGetProfile.getProfile().get(0) != null) {
                    flag = true;
                    etOrgName.setText(dbGetProfile.getProfile().get(0).getOrgName());
                    etOrgEmail.setText(dbGetProfile.getProfile().get(0).getOrgEmail());
                    etOrganizationPhone.setText(Utility.getFormattedPhoneNumber(dbGetProfile.getProfile().get(0).getOrgPhone()));
                    etOrgWebsite.setText(dbGetProfile.getProfile().get(0).getOrgWebsite());
                    etOrgMission.setText(dbGetProfile.getProfile().get(0).getOrgMission());
                    etOrgCause.setText(dbGetProfile.getProfile().get(0).getOrgCause());
                    etOrgProfile.setText(dbGetProfile.getProfile().get(0).getOrgProfile());
                    sp_country.setSelection(getCountryPosition(), true);
                    etCity.setText(dbGetProfile.getProfile().get(0).getOrgCity());
                    etPostalCode.setText(dbGetProfile.getProfile().get(0).getOrgZipcode());
                    etAddress.setText(dbGetProfile.getProfile().get(0).getOrgAddress());
                    etTaxId.setText(dbGetProfile.getProfile().get(0).getOrgTaxid());

                    if (!dbGetProfile.getProfile().get(0).getUserFileTitle().isEmpty() || !dbGetProfile.getProfile().get(0).getUserIdFile().isEmpty()) {
                        uploadDoc.setColorFilter(getResources().getColor(R.color.green));
                        uploadPath = dbGetProfile.getProfile().get(0).getUserIdFile();
                        String fileWithExt = uploadPath.substring(uploadPath.lastIndexOf('/') + 1);
                        uploadedFile.setText("Uploaded document : " + fileWithExt);
                    } else {
                        uploadedFile.setText("");
                        uploadDoc.setColorFilter(getResources().getColor(R.color.black));
                        uploadPath = dbGetProfile.getProfile().get(0).getUserIdFile();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        validate = new ValidateTor();
        runtimePermission = RuntimePermission.getInstance();
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        csoRegisterStep_2ViewModel = ViewModelProviders.of(this).get(CsoRegisterStep_2ViewModel.class);
        countryModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        stateModel = ViewModelProviders.of(this).get(StateViewModel.class);
        scrollView = findViewById(R.id.scrollView);
        clear = findViewById(R.id.clear);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        uploadedFile = findViewById(R.id.uploadedFile);
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
        sp_country = findViewById(R.id.country);
        sp_state = findViewById(R.id.state);
        sp_docType = findViewById(R.id.sp_docType);
        addBtn = findViewById(R.id.addBtn);
        upBtn = findViewById(R.id.upBtn);
        viewBtn = findViewById(R.id.viewBtn);
        uploadLayout = findViewById(R.id.uploadLayout);
        etOrgNameLayout = findViewById(R.id.etOrgNameLayout);
        etOrgEmailLayout = findViewById(R.id.etOrgEmailLayout);
        etOrganizationPhoneLayout = findViewById(R.id.etOrganizationPhoneLayout);
        etOrgWebsiteLayout = findViewById(R.id.etOrgWebsiteLayout);
        etPostalCodeLayout = findViewById(R.id.etPostalCodeLayout);

        clear.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        uploadDoc.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        upBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);

    }

    private void setCountrySpinner(List<CountryResponse.Country> countryList) {
        ArrayAdapter<CountryResponse.Country> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, countryList);
        sp_country.setAdapter(adapter);
    }

    private void setStateSpinner(List<String> stateList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, R.id.text, stateList);
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

                if (action != null && action.equalsIgnoreCase("update"))
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
                    path = "";
                    sp_docType.setSelection(0, true);
                }
                scrollView.fullScroll(ScrollView.FOCUS_UP);

                break;
            case R.id.next:

                if (Utility.isNetworkAvailable(context)) {
                    if (etOrgName.getText() != null && !etOrgName.getText().toString().isEmpty()) {
                        if (!Utility.isValidName(etOrgName.getText().toString())) {
                            myToast.show(getString(R.string.err_org_name), Toast.LENGTH_SHORT, false);
                            return;
                        }
                        if (etOrganizationPhone.getText() != null && !etOrganizationPhone.getText().toString().isEmpty())
                            if (!Utility.isValidPhoneNumber(etOrganizationPhone.getText().toString())) {
                                myToast.show(getString(R.string.err_enter_valid_phone), Toast.LENGTH_SHORT, false);
                                return;
                            }

                        if (etOrgEmail.getText() != null && !etOrgEmail.getText().toString().isEmpty())
                            if (!Utility.isValidEmail(etOrgEmail.getText().toString())) {
                                myToast.show(getString(R.string.err_enter_valid_email), Toast.LENGTH_SHORT, false);
                                return;
                            }

                        if (etOrgWebsite.getText() != null && !etOrgWebsite.getText().toString().isEmpty())
                            if (!Utility.isValidWebsite(etOrgWebsite.getText().toString())) {
                                myToast.show(getString(R.string.err_enter_valid_website_address), Toast.LENGTH_SHORT, false);
                                return;
                            }
                                   /* if (etOrgMission.getText() != null && !etOrgMission.getText().toString().isEmpty()) {

                                        if (etOrgCause.getText() != null && !etOrgCause.getText().toString().isEmpty()) {

                                            if (etOrgProfile.getText() != null && !etOrgProfile.getText().toString().isEmpty()) {

                                                if (country_id != null && !country_id.isEmpty()) {

                                                    if (state_id != null && !state_id.isEmpty()) {

                                                        if (etCity.getText() != null && !etCity.getText().toString().isEmpty()) {
*/
                        if (etPostalCode.getText() != null && !etPostalCode.getText().toString().isEmpty())
                            if (!Utility.isValidPostalCode(etPostalCode.getText().toString())) {
                                myToast.show(getString(R.string.err_enter_valid_postal), Toast.LENGTH_SHORT, false);
                                return;
                            }/*
                                                                if (etAddress.getText() != null && !etAddress.getText().toString().isEmpty()) {

                                                                    if (etTaxId.getText() != null && !etTaxId.getText().toString().isEmpty()) {

                                                                        if (etTaxId.getText().toString().length() == 11) {
*/
                        if (action != null && !action.equalsIgnoreCase("update")) {

                            if (!uploaded) {
                                myToast.show(getString(R.string.err_upload_atleast_one_doc), Toast.LENGTH_SHORT, false);
                                return;
                            }
                            if (!certified501c3) {
                                myToast.show(getString(R.string.err_doc_501C3_cer), Toast.LENGTH_SHORT, false);
                                return;
                            }
                            hitRegisterApi();

                        } else {
                            hitRegisterApi();
                        }

                                                                               /* if (action != null && action.equalsIgnoreCase("update")) {

                                                                                    if (Utility.isNetworkAvailable(context))
                                                                                        if (path == null || path.isEmpty())
                                                                                            hitRegisterApi();
                                                                                        else
                                                                                            uploadDocument(new File(path));


                                                                                } else {
                                                                                    if (path != null) {
                                                                                        uploadDocument(new File(path));
                                                                                    } else {
                                                                                        myToast.show(getString(R.string.err_upload_document), Toast.LENGTH_SHORT, false);
                                                                                    }
                                                                                }*/


                                                                       /* } else {
                                                                            myToast.show(getString(R.string.err_taxId_lenght), Toast.LENGTH_SHORT, false);
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
                        }*/
                    } else{
                            myToast.show(getString(R.string.err_enter_org_name), Toast.LENGTH_SHORT, false);
                        }
                    } else {

                        myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);

                    /*Intent intent1 = new Intent(context, CsoRegisterStep_3Activity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if (action.equalsIgnoreCase("update")) {
                        intent1.putExtra("action", "update");
                        intent1.putExtra("model", getProfileResponse);
                    }
                    startActivity(intent1);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
                    }

                    break;
                    case R.id.back:
                        onBackPressed();
                        break;
                    case R.id.uploadDoc:
                        path = null;
                        if (doc_type_id != null && !doc_type_id.isEmpty()) {
                            Intent intent = null;
                            if (Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG")) {
                                intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                                intent.putExtra("CONTENT_TYPE", "*/*");
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                            } else {
                                String type = "*/*";

                                intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                                intent.setType(type);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                            }


                            startActivityForResult(Intent.createChooser(intent, "select file"), PICKFILE_REQUEST_CODE);

                        } else {
                            myToast.show(getString(R.string.err_sel_doc_type), Toast.LENGTH_SHORT, false);
                        }

                        break;

                    case R.id.addBtn:
                        sp_docType.setSelection(0, true);
                        path = "";
                        break;

                    case R.id.upBtn:
                        if (path != null && !path.isEmpty() && doc_type_id != null && !doc_type_id.isEmpty() && doc_type_name != null && !doc_type_name.isEmpty())
                            uploadDocument(new File(path), doc_type_id, doc_type_name);
                        else
                            myToast.show(getString(R.string.err_neccesary_fields_upload), Toast.LENGTH_SHORT, false);
                        break;

                    case R.id.viewBtn:
                        getDocuments();
                        break;


                }
        }

        private void hitRegisterApi () {

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
            csoRegisterRequest_2.setUserIdFile("");
            csoRegisterRequest_2.setUserFileTitle("");
            csoRegisterRequest_2.setOrgLatitude("0.00");
            csoRegisterRequest_2.setOrgLongitude("0.00");

            Log.i("REQUEST", "" + new Gson().toJson(csoRegisterRequest_2));

            if (Utility.isNetworkAvailable(context)) {
                myProgressDialog.show(getString(R.string.please_wait));
                csoRegisterStep_2ViewModel.getRegisterResponse(csoRegisterRequest_2).observe((LifecycleOwner) context, registerResponse -> {

                    if (registerResponse != null) {
                        if (registerResponse.getResStatus().equalsIgnoreCase("200")) {
                            Log.i("RESPONSE", "" + new Gson().toJson(registerResponse));
                            Intent intent1 = new Intent(context, CsoRegisterStep_3Activity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            if (action != null && action.equalsIgnoreCase("update")) {
                                intent1.putExtra("action", "update");
                                intent1.putExtra("model", getProfileResponse);
                            } else {
                                intent1.putExtra("action", "register");
                            }
                            startActivity(intent1);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        } else if (registerResponse.getResStatus().equalsIgnoreCase("401")) {
                            // myToast.show(getString(R.string.err_org_inf_regisitration_failed), Toast.LENGTH_SHORT, false);
                            myToast.show(registerResponse.getResMessage(), Toast.LENGTH_SHORT, false);
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
        protected void onActivityResult ( int requestCode, int resultCode, Intent
        data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICKFILE_REQUEST_CODE && data != null) {

                Uri uri = data.getData();
           /* String scheme = uri.getScheme();
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                doc_path = Utility.getRealPathFromURI_API19(context, uri);
            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                doc_path = uri.getPath();
            }*/

                try {

                    path = Utility.getPath(context, uri);
                    if (path == null) {
                        path = "";
                        myToast.show(getString(R.string.err_select_other_path), Toast.LENGTH_SHORT, false);
                    } else {
                        String fileExt = MimeTypeMap.getFileExtensionFromUrl(path);
                        File file = new File(path);
                        int file_size = Integer.parseInt(String.valueOf(file.length() / 1024));

                        //JPG, PNG, JPEG, DOCX, PPTX, XLSX, &amp; PDF only

                        if (!(fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("png") || fileExt.equalsIgnoreCase("jpeg") || fileExt.equalsIgnoreCase("docx") || fileExt.equalsIgnoreCase("doc") || fileExt.equalsIgnoreCase("pptx") || fileExt.equalsIgnoreCase("xlsx") || fileExt.equalsIgnoreCase("pdf") || fileExt.equalsIgnoreCase("txt"))) {
                            path = "";
                            myToast.show(getString(R.string.wrong_file_format), Toast.LENGTH_SHORT, false);
                        } else {
                            if (file_size > 1024) {
                                myToast.show(getString(R.string.err_file_size), Toast.LENGTH_SHORT, false);
                                path = "";
                            }
                        }
                    }


                    //path = Extra.getUriRealPath(context, uri);
                    // path = Commons.getPath(uri, context);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        private void uploadDocument (File fileToUpload, String
        document_type_id, String document_type_name){

            if (document_type_name.equalsIgnoreCase(Constants.Certificate501C3_Name)) {
                certified501c3 = true;
            }

            myProgressDialog.show(getString(R.string.please_wait));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("document_file_name", fileToUpload.getName(), RequestBody.create(MediaType.parse("*/*"), fileToUpload));
            ApiInterface apiInterface = Api.getClient();

            Call<UploadLockerDocumentResponse> call = apiInterface.uploadLockerDocument(filePart, sharedPref.getUserId(), "1234", "doc_locker_file_upload", document_type_id, document_type_name, Utility.getDeviceId(context), sharedPref.getUserType());

            call.enqueue(new Callback<UploadLockerDocumentResponse>() {
                @Override
                public void onResponse(Call<UploadLockerDocumentResponse> call, Response<UploadLockerDocumentResponse> response) {

                    if (response.body() != null) {

                        if (response.body().getResStatus().equalsIgnoreCase("200")) {
                            myProgressDialog.dismiss();
                            myToast.show(getString(R.string.file_uploaded_successfully), Toast.LENGTH_SHORT, true);
                            sp_docType.setSelection(0, true);
                            path = "";
                            uploaded = true;
                        } else {
                            myProgressDialog.dismiss();
                            if (!response.body().getResMessage().equalsIgnoreCase(""))
                                myToast.show(response.body().getResMessage(), Toast.LENGTH_SHORT, false);
                            else
                                myToast.show(getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, false);
                            sp_docType.setSelection(0, true);
                            path = "";
                            uploaded = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<UploadLockerDocumentResponse> call, Throwable t) {
                    t.printStackTrace();
                    myToast.show(getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
            });
        }

        @Override
        public void onBackPressed () {

            Dialog dialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.exit_registration_dialog, null);
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

        public int getCountryPosition () {
            int position = -1;
            if (Utility.isNetworkAvailable(context)) {
                for (int i = 0; i < countryListData.size(); i++) {
                    if (countryListData.get(i).getCountryName().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(getProfileResponse.getResData().getUserCountry())) {
                        position = i;
                    }
                }
            } else {
                for (int i = 0; i < countryListData.size(); i++) {
                    if (countryListData.get(i).getCountryName().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserCountry()) || countryListData.get(i).getCountryId().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserCountry())) {
                        position = i;
                    }
                }
            }
            return position;
        }

        public int getStatePosition () {
            int position = -1;
            if (Utility.isNetworkAvailable(context)) {
                for (int i = 0; i < stateListData.size(); i++) {
                    if (stateListData.get(i).getStateName().equalsIgnoreCase(getProfileResponse.getResData().getUserState()) || stateListData.get(i).getStateId().equalsIgnoreCase(getProfileResponse.getResData().getUserState())) {
                        position = i+1;
                    }
                }
            } else {
                for (int i = 0; i < stateListData.size(); i++) {
                    if (stateListData.get(i).getStateName().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserState()) || stateListData.get(i).getStateId().equalsIgnoreCase(dbGetProfile.getProfile().get(0).getUserState())) {
                        position = i+1;
                    }
                }
            }
            return position;
        }

        private void getCountryList () {

            countryList = new ArrayList<>();
            if (Utility.isNetworkAvailable(context)) {
                // myProgressDialog.show(getString(R.string.please_wait));
                countryModel.getCountryResponse(context).observe((LifecycleOwner) context, countryResponse -> {

                    if (countryResponse != null) {
                        if (countryResponse.getResStatus().equalsIgnoreCase("200") && countryResponse.getResData() != null) {
                            countryListData = countryResponse.getResData();
                            for (int i = 0; i < countryListData.size(); i++) {
                                countryList.add(countryListData.get(i).getCountryName());
                            }

                            setCountrySpinner(countryListData);
                        } else if (countryResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(getString(R.string.err_no_country_found), Toast.LENGTH_SHORT, false);
                            myProgressDialog.dismiss();
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }

                    //myProgressDialog.dismiss();

                });
            } else {
                countryListData = dbGetCountry.getCountryList();
                if (countryListData.size() > 0) {
                    for (int i = 0; i < countryListData.size(); i++) {
                        countryList.add(countryListData.get(i).getCountryName());
                    }
                    setCountrySpinner(countryListData);
                }
            }
        }

        private void getStateList (String country_id){
            stateList = new ArrayList<>();
            if (Utility.isNetworkAvailable(context)) {
                //myProgressDialog.show(getString(R.string.please_wait));
                stateModel.getStateResponse(context, new StateRequest(country_id)).observe((LifecycleOwner) context, stateResponse -> {
                    if (stateResponse != null) {
                        if (stateResponse.getResStatus().equalsIgnoreCase("200")) {
                            stateListData = stateResponse.getStateList();
                            stateList.add(0, getString(R.string.select_state));
                            for (int i = 0; i < stateListData.size(); i++) {
                                stateList.add(i + 1, stateListData.get(i).getStateName());
                            }

                            setStateSpinner(stateList);
                        } else if (stateResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show(getString(R.string.err_no_state_found), Toast.LENGTH_SHORT, false);
                        }
                    } else {
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    }
                    myProgressDialog.dismiss();
                });
            } else {
                stateListData = dbGetState.getStateList();
                stateList.add(0, getString(R.string.select_state));
                if (stateListData.size() > 0) {
                    for (int i = 0; i < stateListData.size(); i++) {
                        stateList.add(i + 1, stateListData.get(i).getStateName());
                    }
                    setStateSpinner(stateList);
                }
            }
            if (action != null && action.equalsIgnoreCase("update"))
                fillData();
        }

        private void getDocuments () {
            if (Utility.isNetworkAvailable(context)) {
                myProgressDialog.show(getString(R.string.please_wait));

                documentListViewModel.getDocumentListResponse(new DocumentListRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, documentListResponse -> {

                    if (documentListResponse != null) {
                        if (documentListResponse.getResStatus().equalsIgnoreCase("200") && documentListResponse.getResData() != null) {

                            Dialog dialog = new Dialog(context);
                            View view = LayoutInflater.from(context).inflate(R.layout.uploaded_doc_dialog, null);
                            dialog.setContentView(view);
                            ListView listView = view.findViewById(R.id.listView);
                            UploadedFileAdapter documentAdapter = new UploadedFileAdapter(context, documentListResponse.getResData(), dialog);
                            listView.setAdapter(documentAdapter);
                            //doc_list_size = documentListResponse.getResData().size();
                            dialog.show();

                        } else if (documentListResponse.getResStatus().equalsIgnoreCase("401")) {
                            myToast.show("No uploaded document found", Toast.LENGTH_SHORT, false);
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
