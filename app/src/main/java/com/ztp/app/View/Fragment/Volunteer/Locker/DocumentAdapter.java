package com.ztp.app.View.Fragment.Volunteer.Locker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.DocumentDeleteRequest;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse.Document;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.DocumentDeleteViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocumentAdapter extends BaseAdapter {
    Context context;
    List<Document> data;
    private MyProgressDialog myProgressDialog;
    SharedPref sharedPref;
    DocumentDeleteViewModel documentDeleteViewModel;
    MyToast myToast;

    public DocumentAdapter(Context context, List<Document> data) {
        this.context = context;
        this.data = data;
        myProgressDialog = new MyProgressDialog(context);
        sharedPref = SharedPref.getInstance(context);
        myToast = new MyToast(context);
        documentDeleteViewModel = ViewModelProviders.of((FragmentActivity) context).get(DocumentDeleteViewModel.class);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Document getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        Document document = getItem(position);
        if (view == null) {

            view = LayoutInflater.from(context).inflate(R.layout.document_list_layout, null);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.download = view.findViewById(R.id.download);
            holder.bin = view.findViewById(R.id.bin);
            holder.type = view.findViewById(R.id.type);
            holder.date = view.findViewById(R.id.date);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        holder.title.setText(document.getDocument_type_name());
        holder.description.setText(document.getDocument_name());
       // holder.typeText.setText(document.getDocument_type_name());
        String name = document.getDocument_file_name().substring(document.getDocument_file_name().lastIndexOf("/") + 1);
        holder.date.setText("Date : " + document.getDocument_add_date());

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

        holder.download.setTag(document);

        holder.download.setOnClickListener(v -> {

            if (Utility.isNetworkAvailable(context)) {

                Document doc = (Document) v.getTag();
                showDialog(doc, context.getString(R.string.download_document), context.getString(R.string.download_msg), Constants.download);

            } else {
                myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }
        });


        holder.bin.setOnClickListener(v -> {

            if (Utility.isNetworkAvailable(context)) {
                Document doc = (Document) v.getTag();
                showDialog(doc, context.getString(R.string.delete_document), context.getString(R.string.delete_msg), Constants.delete);

            } else {
                myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
            }

        });

        return view;
}

    private class Holder {
        TextView description, title, date;
        ImageView download, bin, type, show;
    }

    private String writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {

           // File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);


            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name));

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            }

            File f = new File(mediaStorageDir,fileName);

//            String rootPath = context.getExternalCacheDir() + "/Documents/";
//            File root = new File(rootPath);
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            File f = new File(root + fileName);

            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();


            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(f);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                return f.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void showDialog(Document doc, String txt_title, String txt_message, String action) {
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

                if (action.equalsIgnoreCase(Constants.delete))
                    deleteDocument(doc);
                else if (action.equalsIgnoreCase(Constants.download)) {
                    downloadDocument(doc);
                }
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

    private void deleteDocument(Document doc) {
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

    private void downloadDocument(Document doc) {
        myProgressDialog.show(context.getString(R.string.please_wait));
        Call<ResponseBody> call = Api.getClient().downloadFile((doc.getDocument_file_name()));

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    String fileWithExt = doc.getDocument_file_name().substring(doc.getDocument_file_name().lastIndexOf('/') + 1);

                    new AsyncTask<Void, Void, String>() {

                        @Override
                        protected void onPostExecute(String text) {
                            super.onPostExecute(text);

                            if (!text.isEmpty()) {
                                myToast.show("File saved inside "+context.getString(R.string.app_name)+" folder in device memory"/*context.getString(R.string.file_saved_at) + text*/, Toast.LENGTH_LONG, true);
                            } else {
                                myToast.show(context.getString(R.string.file_not_saved), Toast.LENGTH_SHORT, false);
                            }
                            myProgressDialog.dismiss();
                        }

                        @Override
                        protected String doInBackground(Void... voids) {
                            if (response.body() != null)
                                return writeResponseBodyToDisk(response.body(), fileWithExt);
                            else
                                return "";
                        }
                    }.execute();


                } else {
                    myToast.show(context.getString(R.string.download_failed), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                myToast.show(context.getString(R.string.download_failed), Toast.LENGTH_SHORT, false);
                t.printStackTrace();
                myProgressDialog.dismiss();
            }
        });
    }
}
