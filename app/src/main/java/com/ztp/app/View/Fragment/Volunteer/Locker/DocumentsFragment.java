package com.ztp.app.View.Fragment.Volunteer.Locker;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.Room.Async.Get.DBGetDocument;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.DocumentListRequest;
import com.ztp.app.Data.Remote.Model.Response.DocumentTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadLockerDocumentResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import com.ztp.app.Helper.MyEditText;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.DocumentListViewModel;
import com.ztp.app.Viewmodel.DocumentTypeViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentsFragment extends Fragment {

    Context context;
    FloatingActionButton fab_upload;
    ListView listView;
    MyTextView noData;
    //    List<Map<String,String>> dataList = new ArrayList<>();
    SharedPref sharedPref;
    DocumentListViewModel documentListViewModel;
    DocumentTypeViewModel documentTypeViewModel;
    MyProgressDialog myProgressDialog;
    MyToast myToast;
    String path;
    HashMap<String, DocumentTypeResponse.Document> documentHashMap = new HashMap<>();
    private static final int PICKFILE_REQUEST_CODE = 100;
    int doc_list_size = 0;
    DBGetDocument dbGetDocument;

    public DocumentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbGetDocument = new DBGetDocument(context);
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        documentListViewModel = ViewModelProviders.of((FragmentActivity) context).get(DocumentListViewModel.class);
        documentTypeViewModel = ViewModelProviders.of((FragmentActivity) context).get(DocumentTypeViewModel.class);
        noData = view.findViewById(R.id.noData);

        fab_upload = view.findViewById(R.id.fab_upload);
        listView = view.findViewById(R.id.listView);

        getDocuments();
        fab_upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (doc_list_size <= 30) {

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
                    myToast.show(getString(R.string.upload_limit), Toast.LENGTH_SHORT, false);
                }
            }
        });
        return view;
    }

    private void getDocuments() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            DocumentListRequest documentListRequest = new DocumentListRequest(sharedPref.getUserId());
            Log.i("","" + new Gson().toJson(documentListRequest));
            documentListViewModel.getDocumentListResponse(documentListRequest).observe((LifecycleOwner) context, documentListResponse -> {

                if (documentListResponse != null) {
                    Log.i("","" + new Gson().toJson(documentListResponse));
                    if (documentListResponse.getResStatus().equalsIgnoreCase("200") && documentListResponse.getResData() != null) {
                        listView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.INVISIBLE);
                        DocumentAdapter documentAdapter = new DocumentAdapter(context, documentListResponse.getResData());
                        listView.setAdapter(documentAdapter);
                        doc_list_size = documentListResponse.getResData().size();
                    } else if (documentListResponse.getResStatus().equalsIgnoreCase("401")) {
//                        myToast.show(getString(R.string.err_no_document_found), Toast.LENGTH_SHORT, false);
                        noData.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }

                } else {
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    noData.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                myProgressDialog.dismiss();

            });


        } else {
            if(dbGetDocument.getDocumentList()!=null && dbGetDocument.getDocumentList().size()>0)
            {
                listView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.INVISIBLE);
                DocumentAdapter documentAdapter = new DocumentAdapter(context, dbGetDocument.getDocumentList());
                listView.setAdapter(documentAdapter);
                doc_list_size = dbGetDocument.getDocumentList().size();
            }
            else
            {
                myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                noData.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }
    }

    private void getDocumentType() {
        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));

            documentTypeViewModel.getDocumentResponse(context).observe((LifecycleOwner) context, documentListResponse -> {

                if (documentListResponse != null) {
                    if (documentListResponse.getResStatus().equalsIgnoreCase("200") && documentListResponse.getResData() != null) {
                        ArrayList<String> nameList = new ArrayList<String>();
                        for (DocumentTypeResponse.Document doc : documentListResponse.getResData()) {
                            documentHashMap.put(doc.getDocument_type_name(), doc);
                            nameList.add(doc.getDocument_type_name());
                        }
                        //First Step: convert ArrayList to an Object array.
                        Object[] objNames = nameList.toArray();
                        //Second Step: convert Object array to String array
                        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);
                        showPopup(strNames);
                    } else if (documentListResponse.getResStatus().equalsIgnoreCase("401")) {
                        myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE && data != null) {

            try {
                Uri uri = data.getData();
                path = Utility.getPath(context, uri);
                if (path != null && !path.isEmpty()) {
                    File f = new File(path);
                    /* if (f.isFile()) {*/
                    String file_name = f.getName();
                    file_name = file_name.toLowerCase();
                    if (file_name.contains("jpeg") || file_name.contains("png") || file_name.contains("jpg") || file_name.contains("xls") || file_name.contains("xlsx") || file_name.contains("doc") || file_name.contains("docx") || file_name.contains("txt") || file_name.contains("ppt") || file_name.contains("pptx") || file_name.contains("pdf")) {
                        if (Utility.getFileSizeMegaBytes(f) <= 1)
                            getDocumentType();
                        else
                            myToast.show(getString(R.string.support_size_msg), Toast.LENGTH_SHORT, false);
                    } else
                        myToast.show(getString(R.string.err_document_support), Toast.LENGTH_SHORT, false);

                    /* }*/
                } else
                    myToast.show(getString(R.string.err_select_other_path), Toast.LENGTH_SHORT, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadDocument(File fileToUpload, String document_type_id, String document_type_name) {

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
                        myToast.show(response.body().getResMessage(), Toast.LENGTH_SHORT, true);
                        //myToast.show(getString(R.string.file_uploaded_successfully), Toast.LENGTH_SHORT, true);
                        getDocuments();
                    } else {
                        myProgressDialog.dismiss();
                        if (!response.body().getResMessage().equalsIgnoreCase(""))
                            myToast.show(response.body().getResMessage(), Toast.LENGTH_SHORT, false);
                        else
                            myToast.show(getString(R.string.err_file_upload_failed), Toast.LENGTH_SHORT, false);
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

    private void showPopup(String[] data) {
        Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.document_type_dialog, null);
        Spinner sp_docType = view1.findViewById(R.id.sp_docType);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_dropdown_item, R.id.text, data);
        sp_docType.setAdapter(spinnerArrayAdapter);

        MyEditText et_doc_name = view1.findViewById(R.id.et_doc_name);

        LinearLayout ok = view1.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
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
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view1);
        dialog.show();

    }


}
