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
import com.ztp.app.Utils.Utility;

import java.util.Date;
import java.util.List;
import java.util.Map;

class VolunteerAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<VolunteerModel> dataList;
    MyToast myToast;

    public VolunteerAdapter(Context context, List<VolunteerModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public VolunteerModel getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        VolunteerModel dataModel = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_volunteers_row, null);
            holder.date = view.findViewById(R.id.tv_date);
            holder.time = view.findViewById(R.id.tv_time);
            holder.name = view.findViewById(R.id.tv_name);
            holder.tv_heading = view.findViewById(R.id.tv_heading);
            holder.tv_desc = view.findViewById(R.id.tv_description);
            holder.edit = view.findViewById(R.id.imv_edit);
            holder.rb_rank = view.findViewById(R.id.rb_rank);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_heading.setText(dataModel.getName());


//        holder.date.setText(dataModel.getDate());

        Date d = Utility.convertStringToDateWithoutTime(dataModel.getDate());
        holder.date.setText(Utility.formatDateFull(d));


        holder.time.setText(dataModel.getTime());
        holder.name.setText(dataModel.getStudent_name());
        holder.tv_desc.setText(dataModel.getDescrip());
      //  holder.name.setCh(dataModel.getHours());

        holder.edit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.edit:
             //   v.setBackgroundColor(context.getResources().getColor(R.color.blue_light));

                break;
        }
    }



    private class Holder {
        MyTextView date, time, name,tv_heading, tv_desc;
        ScaleRatingBar rb_rank;
        ImageView edit;

    }
}
