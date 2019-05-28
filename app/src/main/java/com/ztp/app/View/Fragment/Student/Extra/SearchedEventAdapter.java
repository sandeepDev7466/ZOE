package com.ztp.app.View.Fragment.Student.Extra;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Student.Booking.ShiftListFragment;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;

import java.util.Date;
import java.util.List;

public class SearchedEventAdapter extends BaseAdapter {
    Context context;
    List<SearchEventResponse.SearchedEvent> searchEventList;

    public SearchedEventAdapter(Context context, List<SearchEventResponse.SearchedEvent> searchEventList) {
        this.context = context;
        this.searchEventList = searchEventList;
    }

    @Override
    public int getCount() {
        return searchEventList.size();
    }

    @Override
    public SearchEventResponse.SearchedEvent getItem(int position) {
        return searchEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        SearchEventResponse.SearchedEvent searchedEvent = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.search_event_list_item, null);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.date = view.findViewById(R.id.date);
            holder.month = view.findViewById(R.id.month);
            holder.day = view.findViewById(R.id.day);
            holder.imv_view = view.findViewById(R.id.imv_view);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        holder.title.setText(searchedEvent.getEventHeading());
        holder.description.setText(searchedEvent.getEventDetails());
        Date date = Utility.convertStringToDateWithoutTime(searchedEvent.getEventAddDate());

        String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        holder.date.setText(day);
        holder.month.setText(monthString);
        holder.day.setText(dayOfTheWeek);


        view.setOnClickListener(v -> {


            Dialog dialog = new Dialog(context);
            View view1 = LayoutInflater.from(context).inflate(R.layout.event_action_layout_view_only, null);
            dialog.setContentView(view1);
            dialog.show();


            LinearLayout view_shift = view1.findViewById(R.id.view_shift);
            LinearLayout view_event = view1.findViewById(R.id.view_event);

            view_shift.setOnClickListener(vs -> {
                dialog.dismiss();
                ShiftListFragment shiftListFragment = new ShiftListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id", searchedEvent.getEventId());
                shiftListFragment.setArguments(bundle);
                Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");
            });
            view_event.setOnClickListener(vs -> {
                dialog.dismiss();
                EventDetailFragment eventFragment = new EventDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id", searchedEvent.getEventId());
                eventFragment.setArguments(bundle);
                Utility.replaceFragment(context, eventFragment, "EventDetailFragment");
            });
        });

        return view;
    }

    private class Holder {
        MyBoldTextView title,date,month,day;
        MyTextView description;
        ImageView imv_view;
    }
}
