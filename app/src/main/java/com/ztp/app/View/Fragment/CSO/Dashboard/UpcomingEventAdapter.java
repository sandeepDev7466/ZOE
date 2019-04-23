package com.ztp.app.View.Fragment.CSO.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ztp.app.R;

import java.util.ArrayList;

/**
 * Created by htl-dev on 01-04-2019.
 */

public class UpcomingEventAdapter extends ArrayAdapter<UpcomingEventModel> implements View.OnClickListener{

    private ArrayList<UpcomingEventModel> dataSet;
    Context mContext;
    private boolean mNotifyOnChange = true;


    // View lookup cache
    private static class ViewHolder {
        TextView textView_name;
        TextView textView_time;
        TextView month;
        TextView day;
        TextView date;

    }

    public UpcomingEventAdapter(ArrayList<UpcomingEventModel> data, Context context) {
        super(context, R.layout.upcoming_event_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        UpcomingEventModel dataModel=(UpcomingEventModel)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    @Override
    public int getCount() {
        return dataSet .size();
    }

    @Override
    public UpcomingEventModel getItem(int position) {
        return dataSet .get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UpcomingEventModel dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.upcoming_event_layout, parent, false);
            viewHolder.textView_name = (TextView) convertView.findViewById(R.id.textView_name);
            viewHolder.textView_time = (TextView) convertView.findViewById(R.id.textView_time);
            viewHolder.month = (TextView) convertView.findViewById(R.id.month);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.textView_name.setText(dataModel.getName());
        viewHolder.textView_time.setText(dataModel.getTime());
        viewHolder.month.setText(dataModel.getMonth());
        viewHolder.day.setText(dataModel.getDay());
        viewHolder.date.setText(dataModel.getDate());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

}