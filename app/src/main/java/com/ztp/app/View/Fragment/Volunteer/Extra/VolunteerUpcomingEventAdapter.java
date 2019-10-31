package com.ztp.app.View.Fragment.Volunteer.Extra;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.EventDescriptionFragment;

import java.util.Date;
import java.util.List;

public class VolunteerUpcomingEventAdapter extends BaseAdapter {
    List<VolunteerDashboardCombineResponse.EventData> eventDataList;
    Context context;

    public VolunteerUpcomingEventAdapter(Context context, List<VolunteerDashboardCombineResponse.EventData> eventDataList) {
        this.eventDataList = eventDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return eventDataList.size();
    }

    @Override
    public VolunteerDashboardCombineResponse.EventData getItem(int position) {
        return eventDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        VolunteerDashboardCombineResponse.EventData eventData = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.volunteer_upcoming_event_layout, parent, false);
            holder.date = view.findViewById(R.id.date);
            holder.month = view.findViewById(R.id.month);
            holder.heading = view.findViewById(R.id.heading);
            holder.time = view.findViewById(R.id.time);
            holder.shift = view.findViewById(R.id.shift);
            holder.day = view.findViewById(R.id.day);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Date registeredStartDate = Utility.convertStringToDateWithoutTime(eventData.getShiftDate());
        //Date registeredEndDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterEndDate());

        holder.heading.setText(eventData.getEventHeading());
        holder.date.setText((String) DateFormat.format("dd",registeredStartDate));
        holder.month.setText((String) DateFormat.format("MMM",registeredStartDate));
        holder.day.setText((String) DateFormat.format("EEEE",registeredStartDate));

        holder.time.setText(context.getString(R.string.event_date)+" : "+eventData.getEventRegisterStartDate()+" "+context.getString(R.string.to)+" "+eventData.getEventRegisterEndDate());
        if(eventData.getShift_task_name()==null)
            eventData.setShift_task_name("");
        holder.shift.setText(eventData.getShift_task_name()+" : "+eventData.getShiftDate()+" - "+eventData.getShiftStartTime()+" "+context.getString(R.string.to)+" "+eventData.getShiftEndTime());

        view.setId(position);
        view.setOnClickListener(v -> {

            VolunteerDashboardCombineResponse.EventData eventData1 = eventDataList.get(v.getId());
            EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("model", eventData1);
            bundle.putString("event_id", eventData1.getEventId());
            if (eventData1.getEventStatus().equalsIgnoreCase("10"))
                bundle.putString("status", "p");
            else if(eventData1.getEventStatus().equalsIgnoreCase("20"))
                bundle.putString("status", "u");
            eventDescriptionFragment.setArguments(bundle);
            Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");
        });

        return view;
    }

    private class Holder {
        TextView date, month, heading,time,shift,day;
    }
}
