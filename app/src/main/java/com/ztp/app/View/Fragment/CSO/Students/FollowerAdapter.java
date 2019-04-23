package com.ztp.app.View.Fragment.CSO.Students;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.willy.ratingbar.ScaleRatingBar;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.View.Fragment.CSO.Dashboard.UpcomingEventAdapter;

import java.util.List;
import java.util.Map;

class FollowerAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<FollowerModel> dataList;
    MyToast myToast;

    public FollowerAdapter(Context context, List<FollowerModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public FollowerModel getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        FollowerModel dataModel = getItem(position);

        Holder holder = new Holder();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_student_row, null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_date = view.findViewById(R.id.tv_date);
            holder.tv_hrs = view.findViewById(R.id.tv_hrs);
            holder.rb_rank = view.findViewById(R.id.rb_rank);
            holder.imv_chat = view.findViewById(R.id.imv_chat);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }


        holder.tv_name.setText(dataModel.getName());
        holder.tv_date.setText(dataModel.getDate());
        holder.tv_hrs.setText(dataModel.getHours());
       // holder.rb_rank.setText(dataModel.getRank());
//        holder.date.setText(dataModel.getDate());

        holder.imv_chat.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imv_chat:

                break;
        }
    }



    private class Holder {
        MyTextView tv_name, tv_date,tv_hrs;
       ScaleRatingBar rb_rank;
        ImageView imv_chat;
    }
}
