package com.ztp.app.Data.Local.Room.Async.Get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;

import java.util.ArrayList;
import java.util.List;

public class DBGetDocument
{
    private Context context;
    private List<DocumentListResponse.Document> documentList = new ArrayList<>();

    public DBGetDocument(Context context) {
        this.context = context;
        new AsyncGetDocument().execute();
    }

    public List<DocumentListResponse.Document> getDocumentList() {
        return documentList;
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncGetDocument extends AsyncTask<Void, Void, Void> {
        private RoomDB roomDB;

        @Override
        protected Void doInBackground(Void... voids) {
            roomDB = RoomDB.getInstance(context);
            documentList = roomDB.getDocumentListResponseDao().getDocumentList();
            return null;
        }
    }
}
