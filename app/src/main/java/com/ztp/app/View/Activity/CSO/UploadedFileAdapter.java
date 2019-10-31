package com.ztp.app.View.Activity.CSO;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.DocumentDeleteRequest;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.DocumentDeleteViewModel;

import java.util.List;

public class UploadedFileAdapter extends BaseAdapter
{
    Context context;
    List<DocumentListResponse.Document> data;
    private MyProgressDialog myProgressDialog;
    SharedPref sharedPref;
    DocumentDeleteViewModel documentDeleteViewModel;
    MyToast myToast;
    Dialog dialog;

    public UploadedFileAdapter(Context context, List<DocumentListResponse.Document> data,Dialog dialog) {
        this.context = context;
        this.data = data;
        documentDeleteViewModel = ViewModelProviders.of((FragmentActivity) context).get(DocumentDeleteViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        this.dialog = dialog;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DocumentListResponse.Document getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        DocumentListResponse.Document document = getItem(position);
        if (view == null) {

            view = LayoutInflater.from(context).inflate(R.layout.upload_document_layout, null);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.bin = view.findViewById(R.id.bin);
            holder.type = view.findViewById(R.id.type);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.description.setText(document.getDocument_name());
        String name = document.getDocument_file_name().substring(document.getDocument_file_name().lastIndexOf("/") + 1);
        holder.title.setText(name);

        String title = name.substring(name.indexOf("."));
        title = title.toLowerCase();
        if (title.contains(".jpg") || title.contains(".jpeg")) {
            holder.type.setImageResource(R.drawable.ic_jpg);
        } else if (title.contains(".pdf")) {
            holder.type.setImageResource(R.drawable.ic_pdf);
        } else if (title.contains(".doc") || title.contains(".docx")) {
            holder.type.setImageResource(R.drawable.ic_doc);
        } else if (title.contains(".ppt") || title.contains(".pptx")) {
            holder.type.setImageResource(R.drawable.ic_ppt);
        } else if (title.contains(".xls") || title.contains(".xlsx")) {
            holder.type.setImageResource(R.drawable.ic_xls);
        } else if (title.contains(".png"))
            holder.type.setImageResource(R.drawable.ic_png);
        else if (title.contains(".txt"))
            holder.type.setImageResource(R.drawable.ic_txt);
        else {
            holder.type.setImageResource(R.drawable.ic_undefined);
        }

        holder.bin.setTag(document);

        holder.bin.setOnClickListener(v -> {

            if (Utility.isNetworkAvailable(context)) {
                DocumentListResponse.Document doc = (DocumentListResponse.Document) v.getTag();
                showDialog(doc, context.getString(R.string.delete_document), context.getString(R.string.delete_msg));

            } else {
                myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }

        });

        return view;
    }
    private class Holder {
        MyTextView description, title;
        ImageView  bin, type;
    }

    private void showDialog(DocumentListResponse.Document doc, String txt_title, String txt_message) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.download_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        LinearLayout yes = view.findViewById(R.id.yes);
        LinearLayout no = view.findViewById(R.id.no);
        MyHeadingTextView title = view.findViewById(R.id.title);
        MyTextView message = view.findViewById(R.id.message);

        title.setText(txt_title);
        message.setText(txt_message);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteDocument(doc);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deleteDocument(DocumentListResponse.Document doc) {
        myProgressDialog.show(context.getString(R.string.please_wait));
        DocumentDeleteRequest documentDeleteRequest = new DocumentDeleteRequest();
        documentDeleteRequest.setDocumentId(doc.getDocument_id());
        documentDeleteRequest.setUserDevice(Utility.getDeviceId(context));
        documentDeleteRequest.setUserType(sharedPref.getUserType());
        documentDeleteRequest.setUserId(sharedPref.getUserId());

        documentDeleteViewModel.deleteDocument(documentDeleteRequest).observe((LifecycleOwner) context, documentDeleteResponse ->
        {

            if (documentDeleteResponse != null) {
                if (documentDeleteResponse.getResStatus().equalsIgnoreCase("200")) {
                    myToast.show(context.getString(R.string.document_deleted), Toast.LENGTH_SHORT, true);
                    data.remove(doc);
                    notifyDataSetChanged();
                    if(data.size()==0)
                        dialog.dismiss();
                    if(doc.getDocument_name().equalsIgnoreCase(Constants.Certificate501C3_Name))
                        CsoRegisterStep_2Activity.certified501c3 = false;

                } else if (documentDeleteResponse.getResStatus().equalsIgnoreCase("401")) {

                    myToast.show(context.getString(R.string.err_delete_document), Toast.LENGTH_SHORT, false);

                } else {
                    myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
            } else {
                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
            }

            myProgressDialog.dismiss();

        });
    }
}
