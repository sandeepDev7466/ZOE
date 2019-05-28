package com.ztp.app.View.Fragment.Student.Extra;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

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
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        Date registeredStartDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterStartDate());
        //Date registeredEndDate = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterEndDate());

        holder.heading.setText(eventData.getEventHeading());
        holder.date.setText((String) DateFormat.format("dd",registeredStartDate));
        holder.month.setText((String) DateFormat.format("MMM",registeredStartDate));
        holder.time.setText(context.getString(R.string.Registration)+" "+eventData.getEventRegisterStartDate()+" "+context.getString(R.string.to)+" "+eventData.getEventRegisterEndDate());
        return view;
    }

    private class Holder {
        MyBoldTextView date, month, heading;
        MyTextView time;
    }
}
