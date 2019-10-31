package com.ztp.app.Data.Local.Room.Async.Save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;

import java.util.List;

public class DBSaveDocument extends AsyncTask<Void,Void,Void>
{
    private List<DocumentListResponse.Document> documentList;
    private RoomDB roomDB;
    @SuppressLint("StaticFieldLeak")
    Context context;

    public DBSaveDocument(Context context,List<DocumentListResponse.Document> documentList) {
        this.documentList = documentList;
        this.context = context;
        roomDB = RoomDB.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (roomDB.getDocumentListResponseDao().getDocumentList().size() == 0) {
            roomDB.getDocumentListResponseDao().insertAll(documentList);
        } else {
            roomDB.getDocumentListResponseDao().deleteAll();
            roomDB.getDocumentListResponseDao().insertAll(documentList);
        }
        return null;
    }
}
