package com.ztp.app.View.Fragment.CSO.Dashboard;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UpcomingEventAdapter extends BaseAdapter {

    Context context;
    List<CsoDashboardCombinedResponse.EventData> eventDataList;

    public UpcomingEventAdapter(Context context, List<CsoDashboardCombinedResponse.EventData> eventDataList) {
        this.context = context;
        this.eventDataList = eventDataList;
    }

    @Override
    public int getCount() {
        return eventDataList.size();
    }

    @Override
    public CsoDashboardCombinedResponse.EventData getItem(int position) {
        return eventDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        CsoDashboardCombinedResponse.EventData eventData = getItem(position);
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.upcoming_event_layout, parent, false);
            holder.date = view.findViewById(R.id.date);
            holder.month = view.findViewById(R.id.month);
            holder.heading = view.findViewById(R.id.heading);
            holder.time = view.findViewById(R.id.time);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder) view.getTag();
        }

        Date registeredStartDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterStartDate());
        Date registeredEndDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterEndDate());

        holder.heading.setText(eventData.getEventHeading());
        holder.date.setText((String) DateFormat.format("dd",registeredStartDate));
        holder.month.setText((String) DateFormat.format("MMM",registeredStartDate));
        holder.time.setText(context.getString(R.string.Registration)+" "+eventData.getEventRegisterStartDate()+" "+context.getString(R.string.to)+" "+eventData.getEventRegisterEndDate());

        return view;
    }

    private class Holder
    {
        MyBoldTextView date,month,heading;
        MyTextView time;
    }
}