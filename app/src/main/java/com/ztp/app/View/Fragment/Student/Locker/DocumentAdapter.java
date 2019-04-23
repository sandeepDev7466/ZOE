package com.ztp.app.View.Fragment.Student.Locker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;

import java.util.List;
import java.util.Map;

class DocumentAdapter extends BaseAdapter {
    Context context;
    List<Map<String, String>> data;

    public DocumentAdapter(Context context, List<Map<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String, String> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        Map<String, String> map = getItem(position);
        if (view == null) {

            view = LayoutInflater.from(context).inflate(R.layout.document_list_layout, null);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.forward = view.findViewById(R.id.forward);
            holder.bin = view.findViewById(R.id.bin);
            holder.type = view.findViewById(R.id.type);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        holder.title.setText(map.get("title"));
        holder.description.setText(map.get("description"));

        if (map.get("title") != null && map.get("title").split("\\.")[1].equalsIgnoreCase("jpg")) {
            holder.type.setImageResource(R.drawable.ic_jpg);
        } else if (map.get("title") != null && map.get("title").split("\\.")[1].equalsIgnoreCase("pdf")) {
            holder.type.setImageResource(R.drawable.ic_pdf);
        } else if (map.get("title") != null && map.get("title").split("\\.")[1].equalsIgnoreCase("doc")) {
            holder.type.setImageResource(R.drawable.ic_doc);
        } else if (map.get("title") != null && map.get("title").split("\\.")[1].equalsIgnoreCase("mp3")) {
            holder.type.setImageResource(R.drawable.ic_mp3);
        } else if (map.get("title") != null && (map.get("title").split("\\.")[1].equalsIgnoreCase("mp4") || map.get("title").split("\\.")[1].equalsIgnoreCase("avi"))) {
            holder.type.setImageResource(R.drawable.ic_video);
        }

        return view;
    }

    private class Holder {
        MyTextView description, title;
        ImageView forward, bin, type;
    }
}
