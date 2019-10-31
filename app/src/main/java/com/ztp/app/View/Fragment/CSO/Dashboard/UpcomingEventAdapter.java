package com.ztp.app.View.Fragment.CSO.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;

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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.upcoming_event_layout, parent, false);
            holder.date = view.findViewById(R.id.date);
            holder.month = view.findViewById(R.id.month);
            holder.heading = view.findViewById(R.id.heading);
            holder.shift = view.findViewById(R.id.shift);
            holder.time = view.findViewById(R.id.time);
            holder.mainLayout = view.findViewById(R.id.mainLayout);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Date registeredStartDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterStartDate());
        Date registeredEndDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterEndDate());
        Date shiftStartDate = Utility.convertStringToDateWithoutTime(eventData.getShiftDate());

        holder.heading.setText(eventData.getEventHeading());
        holder.date.setText((String) DateFormat.format("dd", shiftStartDate));
        holder.month.setText((String) DateFormat.format("MMM", shiftStartDate));

        holder.time.setText(context.getString(R.string.event_date) + " : " + eventData.getEventRegisterStartDate() + " " + context.getString(R.string.to) + " " + eventData.getEventRegisterEndDate());
        holder.shift.setText(eventData.getShiftTaskName() + " : " + eventData.getShiftDate() + " - " + eventData.getShiftStartTime() + " " + context.getString(R.string.to) + " " + eventData.getShiftEndTime());

        holder.mainLayout.setId(position);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CsoDashboardCombinedResponse.EventData eventData = eventDataList.get(v.getId());

                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id", eventData.getEventId());
                eventDetailFragment.setArguments(bundle);
                Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");

            }
        });

        return view;
    }

    private class Holder {
        TextView date, month, heading;
        MyTextView time, shift;
        LinearLayout mainLayout;
    }
}