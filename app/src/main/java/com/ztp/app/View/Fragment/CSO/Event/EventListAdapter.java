package com.ztp.app.View.Fragment.CSO.Event;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.EventDeleteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private List<GetEventsResponse.EventData> eventDataList;
    String[] month = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private MyProgressDialog myProgressDialog;
    EventDeleteViewModel eventDeleteViewModel;

    public EventListAdapter(Context context, List<GetEventsResponse.EventData> eventDataList) {
        this.context = context;
        this.eventDataList = eventDataList;
        myProgressDialog = new MyProgressDialog(context);
        eventDeleteViewModel = ViewModelProviders.of((FragmentActivity) context).get(EventDeleteViewModel.class);
    }

    @Override
    public int getCount() {
        return eventDataList.size();
    }

    @Override
    public GetEventsResponse.EventData getItem(int position) {
        return eventDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        GetEventsResponse.EventData eventData = getItem(position);
        try {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.event_list_item, null);
                holder.date = view.findViewById(R.id.date);
                holder.month = view.findViewById(R.id.month);
                holder.title = view.findViewById(R.id.title);
                holder.day = view.findViewById(R.id.day);
                holder.time = view.findViewById(R.id.time);
//                holder.edit = view.findViewById(R.id.edit);
                holder.schedule = view.findViewById(R.id.schedule);
//                holder.shift_li = view.findViewById(R.id.shift_li);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.title.setText(eventData.getEventHeading());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date addDate = sdf.parse(eventData.getEventAddDate());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(addDate.getTime());

//            holder.date.setText(String.valueOf(cal.get(Calendar.DATE)));


            Date d = Utility.convertStringToDateWithoutTime(eventData.getEventAddDate());
            String upToNCharacters = Utility.formatDateFull(d).substring(0, Math.min(Utility.formatDateFull(d).length(), 2));

            holder.date.setText(upToNCharacters);

            holder.month.setText(month[cal.get(Calendar.MONTH)]);
            //holder.day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            holder.time.setText(String.valueOf(cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)));


            holder.schedule.setOnClickListener(v -> {


                Dialog dialog = new Dialog(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.event_action_layout, null);
                dialog.setContentView(view1);
                //dialog.setCancelable(false);

                dialog.show();

                LinearLayout edit_event = view1.findViewById(R.id.edit_event);
                LinearLayout delete_event = view1.findViewById(R.id.delete_event);
                LinearLayout add_shift = view1.findViewById(R.id.add_shift);
                LinearLayout view_shift = view1.findViewById(R.id.view_shift);
                //ImageView img_close=view1.findViewById(R.id.img_close);


                /*img_close.setOnClickListener(vs -> {
                    dialog.dismiss();

                });*/

                edit_event.setOnClickListener(vs -> {
                    dialog.dismiss();

                    UpdateEventFragment updateEventFragment = new UpdateEventFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("eventData", eventData);
                    updateEventFragment.setArguments(bundle);
                    Utility.replaceFragment(context,updateEventFragment,"updateEventFragment");

                });

                delete_event.setOnClickListener(vs -> {
                    dialog.dismiss();

                    DeleteEventRequest deleteEventRequest = new DeleteEventRequest();
                    deleteEventRequest.setEvent_id(eventData.getEventId());

                    myProgressDialog.show("Deleting event...");

                    eventDeleteViewModel.getDeleteEventResponse(deleteEventRequest).observe((LifecycleOwner) context, deleteEventResponse -> {

                        if (deleteEventResponse != null && deleteEventResponse.getResStatus().equalsIgnoreCase("200")) {

                            new MyToast(context).show("Event Deleted Successfully", Toast.LENGTH_SHORT, true);

                            eventDataList.remove(eventData);

                            notifyDataSetChanged();

                        } else {

                            new MyToast(context).show("Failed", Toast.LENGTH_SHORT, false);

                        }

                        myProgressDialog.dismiss();
                    });
                });

                add_shift.setOnClickListener(vs -> {
                    dialog.dismiss();
                    AddNewShiftFragment addNewShiftFragment = new AddNewShiftFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", eventData.getEventId());
                    bundle.putString("status", "add");
                    addNewShiftFragment.setArguments(bundle);
                    Utility.replaceFragment(context, addNewShiftFragment, "AddNewShiftFragment");
                });

                view_shift.setOnClickListener(vs -> {
                    dialog.dismiss();
                    ShiftListFragment shiftListFragment = new ShiftListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", eventData.getEventId());
                    shiftListFragment.setArguments(bundle);
                    Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");
                });
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private class Holder {
        MyBoldTextView date, month, title;
        MyTextView day, time;
        ImageView schedule;

    }
}