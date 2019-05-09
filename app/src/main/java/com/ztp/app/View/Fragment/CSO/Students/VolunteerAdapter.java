package com.ztp.app.View.Fragment.CSO.Students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.willy.ratingbar.ScaleRatingBar;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

import java.util.Date;
import java.util.List;

class VolunteerAdapter extends BaseAdapter {
    private Context context;
    List<CSOAllResponse.ResData> dataList;

    public VolunteerAdapter(Context context, List<CSOAllResponse.ResData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CSOAllResponse.ResData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        CSOAllResponse.ResData dataModel = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_volunteers_row, null);
            holder.date = view.findViewById(R.id.tv_date);
            holder.time = view.findViewById(R.id.tv_time);
            holder.name = view.findViewById(R.id.tv_name);
            holder.tv_heading = view.findViewById(R.id.tv_heading);
            holder.tv_desc = view.findViewById(R.id.tv_description);
            holder.rb_rank = view.findViewById(R.id.rb_rank);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_heading.setText(dataModel.getEventHeading());


        //Date d = Utility.convertStringToDateWithoutTime(dataModel.getDate());
        holder.date.setText(dataModel.getShiftDate());


        holder.time.setText(dataModel.getShiftStartTime()+" - "+dataModel.getShiftEndTime());
        holder.name.setText("Hardcoded");
        holder.tv_desc.setText(dataModel.getShiftTask());
        holder.rb_rank.setRating(Float.parseFloat(dataModel.getShiftRank()));


        return view;
    }

    private class Holder {
        MyTextView date, time, name, tv_heading, tv_desc;
        ScaleRatingBar rb_rank;
    }
}
