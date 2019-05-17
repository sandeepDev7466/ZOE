package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class TimelineAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> data;
    MyToast myToast;

    public TimelineAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        myToast = new MyToast(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        Map<String, Object> map = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.timeline_layout, null);
            holder.name = view.findViewById(R.id.name);
            holder.text = view.findViewById(R.id.text);
            holder.msgImage = view.findViewById(R.id.msgImage);
            holder.reply = view.findViewById(R.id.reply);
            holder.userImage = view.findViewById(R.id.userImage);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.name.setText(String.valueOf(map.get("name")));
        holder.text.setText(String.valueOf(map.get("text")));
        //Picasso.with(context).load(Integer.parseInt(String.valueOf(map.get("msg_image")))).into(holder.msgImage);
        Picasso.with(context).load(Integer.parseInt(String.valueOf(map.get("user_image")))).fit().into(holder.userImage);
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private class Holder {
        MyTextView name, text;
        ImageView msgImage, reply;
        CircleImageView userImage;
    }
}
